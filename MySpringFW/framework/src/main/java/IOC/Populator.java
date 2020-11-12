package IOC;

import IOC.annotation.AutoWired;
import java.lang.reflect.Field;
import java.util.Map;

public class Populator {

    public Populator(){ };
    public void populate(Map<String,Object> instanceMapping){
        if(instanceMapping.isEmpty()){
            return;        //判断ioc容器是否为空
        }
        for (Map.Entry<String,Object> entry:instanceMapping.entrySet()){
            Field[] fields = entry.getValue().getClass().getDeclaredFields();   //获取对象的字段
            for (Field field:fields){
                if(!field.isAnnotationPresent(AutoWired.class)){
                    continue;
                }
                AutoWired autowired = field.getAnnotation(AutoWired.class);
                String id = autowired.value(); //后去字段要注入的id value  为空则按类名  接口名自动注入
                if("".equals(id)){
                    id = field.getType().getName();
                }
                field.setAccessible(true);
                try {
                    field.set(entry.getValue(),instanceMapping.get(id));    //反射注入
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}