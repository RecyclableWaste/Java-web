package cqu.springmvc.handle;

import cqu.ioc.annotation.Component;
import cqu.springmvc.annotation.ParameterName;
import cqu.springmvc.annotation.ResponseBody;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.List;

@Component("mHandler")
public class MethodHandler {
    public Object[] getArgumentObjects(Method method, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        Parameter[] parameters = method.getParameters();
        Object[] arguments = new Object[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];

            if (parameter.getType() == HttpServletRequest.class) {
                arguments[i] = req;
            } else if (parameter.getType() == HttpServletResponse.class) {
                arguments[i] = resp;
            } else if (parameter.getType() == String.class) {
                String parameterName = null;
                if (parameter.getAnnotation(ResponseBody.class) != null) {
                    BufferedReader reader = req.getReader();
                    StringBuffer sb = new StringBuffer();
                    String str = null;
                    while ((str = reader.readLine()) != null) {
                        sb.append(str);
                    }
                    reader.close();
                    arguments[i] = sb.toString();
                } else {
                    if (!parameter.isNamePresent()) {
                        parameterName = parameter.getAnnotation(ParameterName.class).value();
                    } else {
                        parameterName = parameter.getName();
                    }
                    arguments[i] = req.getParameter(parameterName);
                }
            } else if (parameter.getType() == FileItem.class) {
                List<FileItem> list = getFileItemList(req);
                arguments[i] = list.get(0);
            } else if (parameter.getType().isArray()) {
                arguments[i] = getArray(req, parameter.getType().getComponentType());
//            } else if(parameter.getType()== Collection.class){
//                arguments[i] = getCollection(req,)
//            } else if(parameter.getType()== Map.class){

            } else {
//                System.out.println("unhandled parameter type");
                throw new Exception("MethodHandler:unhandled parameter type");
            }
        }
        return arguments;
    }

    public Object invokeMethod(Method method, Object object, Object[] arguments) throws Exception {
        System.out.println("method:" + method.getName());
        for (Object obj : arguments) {
            System.out.println("argument:" + obj.getClass().getName());
        }
        return method.invoke(object, arguments);
    }

    private List<FileItem> getFileItemList(HttpServletRequest req) throws FileUploadException {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> list = upload.parseRequest(req);
        return list;
    }

    private Object getArray(HttpServletRequest req, Class clazz) throws FileUploadException {
        Object obj = null;
        if (clazz == FileItem.class) {
            List<FileItem> list = getFileItemList(req);
            obj = list.toArray();
        }
        return obj;
    }

}
