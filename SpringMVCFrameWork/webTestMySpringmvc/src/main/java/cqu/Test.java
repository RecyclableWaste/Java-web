package cqu;

import cqu.ioc.util.ClazzUtil;

import java.util.List;

public class Test {

    public static void main(String[] args) throws Exception {
//        SpringmvcApplicationContext ac = new SpringmvcApplicationContext();
//        File file = new File("D:/Program Files/tomcat/apache-tomcat-9.0.35/webapps/ROOT/WEB-INF/classes");
//        System.out.println(file.exists());
//        System.out.println(file.listFiles()==null);
        List<String> list = ClazzUtil.getClazzName("", true);
        for (String name : list) {
            System.out.println(name);
        }
    }
}
