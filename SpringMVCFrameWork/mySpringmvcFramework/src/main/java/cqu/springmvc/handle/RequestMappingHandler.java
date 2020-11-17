package cqu.springmvc.handle;

import cqu.ioc.annotation.Component;
import cqu.springmvc.context.SpringmvcApplicationContext;

import java.lang.reflect.Method;

@Component("rmHandler")
public class RequestMappingHandler {
    public Method getMethod(SpringmvcApplicationContext sac, String uri) {
        return sac.getRequestMappingApplicationContext().getMappingMethod(uri);
    }

    public Object getObject(SpringmvcApplicationContext sac, String uri) {
        return sac.getBean(sac.getRequestMappingApplicationContext().getMappingControllerBeanName(uri));
    }

    public String getRequestMethod(SpringmvcApplicationContext sac, String uri) {
        return sac.getRequestMappingApplicationContext().getMappingRequestMethod(uri);
    }
}
