package cqu.ma.ioc;

import cqu.ma.ioc.annotation.Autowired;
import cqu.ma.ioc.annotation.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationContext {
    protected Map<String,Object> beans = new HashMap<>();
    protected List<String> classNames = new ArrayList<>();

    public ApplicationContext() throws Exception {
        this(null,null);
    }
    public ApplicationContext(String basicPackageName,String configFileName) throws Exception {
        if(basicPackageName!=null) {
            this.initFromAnnotation(basicPackageName,Component.class,"value",
                    Autowired.class,"value");
        }
        if(configFileName!=null && !configFileName.equals("")){
            this.initFromXml(configFileName);
        }

    }
    protected void registerBean(String basicPackageName,Class annotation,String methodName) throws Exception {
        String path = basicPackageName.replace(".","/");
        basicPackageName = basicPackageName.equals("")?"":basicPackageName+".";
        this.scanBase(this.getClass().getClassLoader().getResource(path).getPath(),basicPackageName);
        if(classNames.size()==0)
            return;
        for(String name:classNames){
            System.out.println("class:"+name);
        }
        for(String className:classNames){
            try {
                Class clazz = Class.forName(className);
                if(clazz.getAnnotation(annotation)!=null){
                    Object obj = clazz.newInstance();
                    String key = (String) clazz.getAnnotation(annotation)
                            .getClass()
                            .getDeclaredMethod(methodName)
                            .invoke(clazz.getAnnotation(annotation));
                    if(key.equals("")){
                        String name = className.substring(className.lastIndexOf(".")+1);
                        String start = name.substring(0,1);
                        key = start.toLowerCase()+name.substring(1);
                    }
                    beans.put(key,obj);
                }
            }catch (NoClassDefFoundError e){
                System.out.println(e.getMessage());
            }
        }
    }
    protected void injectBean(Class annotation,String methodName) throws Exception {
        for(Object obj:beans.values()){
            Field[] fields = obj.getClass().getDeclaredFields();
            for(Field field: fields){
                field.setAccessible(true);
                if(field.getAnnotation(annotation)!=null){
                    String value = (String) field
                            .getAnnotation(annotation)
                            .getClass()
                            .getDeclaredMethod(methodName)
                            .invoke(field.getAnnotation(annotation));
                    if(field.getType().isPrimitive()){
                        field.set(obj,getBasicType(value,field.getType()));
                    } else if(field.getType()==String.class){
                        field.set(obj,value);
                    } else {
                        field.set(obj,beans.get(value));
                    }
                }
            }
        }
    }
    private void initFromAnnotation(String basicPackageName,Class annotationForRegister,String methodNameForRegister
            ,Class annotationForInject,String methodNameForInject) throws Exception {
        registerBean(basicPackageName,annotationForRegister,methodNameForRegister);
        injectBean(annotationForInject,methodNameForInject);
    }
    private void scanBase(String classPath,String packageName) {
        System.out.println("now scan:"+classPath);
        File file = new File(classPath);
        if(file.isFile()) {
            if(file.getName().endsWith(".class")){
                String fileName = file.getName();
                classNames.add(packageName+fileName.substring(0,fileName.indexOf(".")));
            }
            return;
        } else{
            System.out.println(file.getAbsolutePath());
            File[] files = file.listFiles();
            System.out.println(files==null);
            for(File each: files){
                String eachPath = each.getPath();
                if(each.isFile()) {
                    scanBase(eachPath,packageName);
                } else{
                    String nextPackageName;
                    String separator = getSeparator(eachPath);
                    if(eachPath.endsWith(separator)){
                        String path2 = eachPath.substring(0,eachPath.lastIndexOf(separator));
                        nextPackageName = path2.substring(path2.lastIndexOf(separator));
                    }else {
                        nextPackageName = eachPath.substring(eachPath.lastIndexOf(separator)+1);
                    }
                    scanBase(eachPath,packageName+nextPackageName+".");
                }
            }
        }
    }
    private String getSeparator(String path)
    {
        return path.contains("/")?"/":"\\";
    }

    private void initFromXml(String configFileName) throws Exception
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(this.getClass().getResourceAsStream(configFileName));
        NodeList beans = document.getElementsByTagName("bean");
        for(int i=0;i<beans.getLength();i++)
        {
            Node bean = beans.item(i);
            String id = bean.getAttributes().getNamedItem("id").getNodeValue();
            String className = bean.getAttributes().getNamedItem("class").getNodeValue();
            Object object = Class.forName(className).newInstance();
            this.beans.put(id,object);
            for(int j=0;j<bean.getChildNodes().getLength();j++) {
                if(bean.getChildNodes().item(j).getNodeName().equals("property")) {
                    Node property = bean.getChildNodes().item(j);
                    String propertyName = property.getAttributes().getNamedItem("name").getNodeValue();
                    Field field = object.getClass().getDeclaredField(propertyName);
                    field.setAccessible(true);
                    //引用类型
                    Node refBean = property.getAttributes().getNamedItem("ref-bean");
                    //基本类型 + String
                    Node value = property.getAttributes().getNamedItem("value");
                    if(refBean!=null) {
                        String refBeanName = refBean.getNodeValue();
                        field.set(object,this.beans.get(refBeanName));
                    } else if(value!=null) {
                        String v = value.getNodeValue();
                        if(field.getType()==String.class) {
                            field.set(object,v);
                        } else {
                            field.set(object,getBasicType(v,field.getType()));
                        }
                    }
                }
            }
        }
    }

    private Object getBasicType(String value, Class basicClass) throws Exception {
        Object obj = null;
        if(basicClass==char.class) {
            obj = value.charAt(0);
        } else if(basicClass==short.class) {
            obj = Short.parseShort(value);
        } else if(basicClass==byte.class) {
            obj = Byte.parseByte(value);
        }
        else if(basicClass==int.class)
        {
            obj = Integer.parseInt(value);
        }
        else if(basicClass==long.class)
        {
            obj = Long.parseLong(value);
        }
        else if(basicClass==float.class)
        {
            obj = Float.parseFloat(value);
        }
        else if(basicClass==double.class)
        {
            obj = Double.parseDouble(value);
        }
        else if(basicClass==boolean.class)
        {
            obj = Boolean.parseBoolean(value);
        }
        else
        {
            throw new Exception("not basic type");
        }
        return obj;
    }
    public Object getBean(String beanName)
    {
        return beans.get(beanName);
    }
    public Map getBeansMap(){
        return beans;
    }
    public Object[] getAllBeans(){
        return beans.values().toArray();
    }
}
