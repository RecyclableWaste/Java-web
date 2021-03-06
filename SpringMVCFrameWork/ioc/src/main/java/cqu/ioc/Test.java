package cqu.ioc;

import cqu.ioc.annotation.Autowired;
import cqu.ioc.annotation.Component;

import java.util.ArrayList;
import java.util.List;

interface USB {
    public String read();
}

public class Test {
    String name;
    int id;
    double price;

    public static void main(String[] args) throws Exception {
//        ApplicationContext ac = new ApplicationContext("cqu",null);
//        Computer comp = (Computer) ac.getBean("computer");
//        comp.readFromUsb();
//        comp.printInfo();
        List<Computer> list = new ArrayList<>();
        Computer c1 = new Computer();
        list.add(c1);
        Object[] os = list.toArray();
        c1.id = 45;
        ((Computer) os[0]).id = 90;
        System.out.println(((Computer) os[0]).id);
        System.out.println(c1.id);
    }
}

@Component("computer")
class Computer {
    @Autowired("1235")
    public int id;
    @Autowired("camera")
    USB usb;
    @Autowired("computer1")
    private String name;

    public void readFromUsb() {
        System.out.println(usb.read());
    }

    public void printInfo() {
        System.out.print("id:" + id + " name:" + name);
    }
}

@Component("disk")
class Disk implements USB {

    @Override
    public String read() {
        return "disk read";
    }
}

@Component("camera")
class Camera implements USB {

    @Override
    public String read() {
        return "camera read";
    }
}

