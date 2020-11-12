package demo;

import IOC.annotation.Component;
@Component("myservice")
public class MyService {
   public void say(String s){
       System.out.println(s);
   }

}
