package cqu.test;

import cqu.ma.ioc.ApplicationContext;
import cqu.ma.ioc.annotation.Component;

@Component("test")
public class Test {
    public static void main(String[] args) throws Exception {
        ApplicationContext ac = new ApplicationContext("",null);
        ac.getBean("test");
    }
}
