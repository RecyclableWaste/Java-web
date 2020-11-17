package cqu.springmvc.handle;

import cqu.ioc.annotation.Component;
import cqu.ioc.context.ApplicationContext;
import cqu.springmvc.context.SpringmvcApplicationContext;
import cqu.springmvc.modelAndView.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component("handler")
public class Handler {
    private ApplicationContext ac;
    private SpringmvcApplicationContext sac;

    public void setApplicationContext(ApplicationContext ac) {
        this.ac = ac;
    }

    public void setSprintmvcApplicationContext(SpringmvcApplicationContext sac) {
        this.sac = sac;
    }

    public ModelAndView getModelAndView(Method method, Object object, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        check();
        //
        MethodHandler mHandler = (MethodHandler) ac.getBean("mHandler");
        Class returnType = method.getReturnType();
        ModelAndView modelAndView = null;
        Object returned = mHandler.invokeMethod(method, object, mHandler.getArgumentObjects(method, req, resp));
        if (returnType == String.class) {
            modelAndView = new ModelAndView((String) returned);
        } else if (returnType == ModelAndView.class) {
            modelAndView = (ModelAndView) returned;
        } else if (returnType == void.class) {
            modelAndView = null;
//        } else if(Collection.class.isAssignableFrom(returnType)){
//
//        } else if(Map.class.isAssignableFrom(returnType)){
//
//        } else if(returnType.isArray()){
//        } else if(returnType.isPrimitive()) {
//            modelAndView = new ModelAndView(null);
        } else {
            String returnTypeName = returnType.getName();
            if (returnTypeName.lastIndexOf(".") != -1) {
                returnTypeName = returnTypeName.substring(returnTypeName.lastIndexOf(".") + 1);
            }
            returnTypeName = returnTypeName.substring(0, 1).toLowerCase() + returnTypeName.substring(1);
            modelAndView = new ModelAndView(null);
            modelAndView.addModelObject(returnTypeName, returned);
        }
        return modelAndView;
    }

    private void check() throws Exception {
        if (this.sac == null || this.ac == null) {
            throw new NullPointerException("sac/ac is null");
        }
    }

}
