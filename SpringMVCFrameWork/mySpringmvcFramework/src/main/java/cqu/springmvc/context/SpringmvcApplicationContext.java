package cqu.springmvc.context;

import cqu.ioc.annotation.Autowired;
import cqu.ioc.context.ApplicationContext;
import cqu.springmvc.annotation.Controller;

public class SpringmvcApplicationContext extends ApplicationContext {
    private RequestMappingApplicationContext rac = null;

    public SpringmvcApplicationContext() throws Exception {
        this("");
    }

    public SpringmvcApplicationContext(String basicPackageName) throws Exception {
        this.initFromAnnotation(basicPackageName);
        this.initMapping();
    }

    public RequestMappingApplicationContext getRequestMappingApplicationContext() {
        return this.rac;
    }

    private void initFromAnnotation(String basicPackageName) throws Exception {
        this.registerBean(basicPackageName);
        super.injectBean(Autowired.class, "value");


    }

    private void registerBean(String basicPackageName) throws Exception {
        super.registerBean(basicPackageName, Controller.class, "value");
    }

    private void initMapping() throws Exception {
        rac = new RequestMappingApplicationContext(this);
    }
}
