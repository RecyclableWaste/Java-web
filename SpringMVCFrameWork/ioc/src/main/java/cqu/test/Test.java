package cqu.test;

import cqu.ioc.annotation.Component;
import cqu.ioc.context.ApplicationContext;

@Component("test")
public class Test {
    public static void main(String[] args) throws Exception {
        ApplicationContext ac = new ApplicationContext("", null);
        ac.getBean("test");
    }
}
