package cqu.springmvc.context;

import cqu.springmvc.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class RequestMappingApplicationContext {
    private Map<String, Pair> requestMap = new HashMap<>();

    public RequestMappingApplicationContext(SpringmvcApplicationContext sac) throws Exception {
        init(sac);
    }

    private void init(SpringmvcApplicationContext sac) {
        System.out.println("mapping init");
        Map beans = sac.getBeansMap();
        Set<String> keys = beans.keySet();
        if (keys == null) {
            return;
        }
        for (String key : keys) {
            Object bean = sac.getBean(key);
            System.out.println("mapping: bean:" + bean.getClass().getName());
            String uriInController = "";
            if (bean.getClass().getAnnotation(RequestMapping.class) != null) {
                uriInController += bean.getClass().getAnnotation(RequestMapping.class).value();
            }
            Method[] methods = bean.getClass().getDeclaredMethods();
            for (Method method : methods) {
                System.out.println("mapping method:" + method.getName());
                String uri = "";
                method.setAccessible(true);
                if (method.getAnnotation(RequestMapping.class) != null) {
                    uri = uriInController + method.getAnnotation(RequestMapping.class).value();
                    requestMap.put(uri, new Pair(key, method));
                    System.out.println("mapping:" + uri + "->" + method.getName());
                }
            }
        }
    }

    public Pair getMapping(String uri) {
        return requestMap.get(uri);
    }

    public Method getMappingMethod(String uri) {
        Pair p = this.getMapping(uri);
        if (p != null) {
            return p.method;
        }
        return null;
    }

    public String getMappingRequestMethod(String uri) {
        Method method = this.getMappingMethod(uri);
        if (method != null) {
            return method.getAnnotation(RequestMapping.class).method();
        }
        return null;

    }

    public String getMappingControllerBeanName(String uri) {
        Pair p = this.getMapping(uri);
        if (p != null) {
            return p.controllerBeanName;
        }
        return null;

    }

    class Pair {
        public String controllerBeanName;
        public Method method;

        public Pair(String controllerBeanName, Method method) {
            this.controllerBeanName = controllerBeanName;
            this.method = method;
        }
    }
}
