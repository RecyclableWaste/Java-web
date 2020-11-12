package demo;

import IOC.AnnotationApplicationContext;
import IOC.ApplicationContext;
import IOC.annotation.AutoWired;
import IOC.annotation.Controller;

@Controller
public class MyController {

    @AutoWired("myservice")
    private MyService service;

    public void test() {
        service.say("hello world");
    }

    public static void main(String[] args){
        ApplicationContext context = new AnnotationApplicationContext("applicationContext.properties");
        MyController controller = context.getBean("demo.MyController", MyController.class);
        controller.test();
    }
}