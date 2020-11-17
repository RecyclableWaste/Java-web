import java.io.File;

public class Test {
    public static void main(String[] args) throws Exception {
        String path = "D:/Program Files/tomcat/apache-tomcat-9.0.35/webapps/ROOT/WEB-INF/lib/mySpringmvcFramework-1.0-SNAPSHOT.jar!/cqu/springmvc/";
        File f = new File(path);
        System.out.println(f.exists());

    }
}

