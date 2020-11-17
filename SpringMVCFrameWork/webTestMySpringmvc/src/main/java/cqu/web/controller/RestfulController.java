package cqu.web.controller;

import com.alibaba.fastjson.JSON;
import cqu.springmvc.annotation.Controller;
import cqu.springmvc.annotation.ParameterName;
import cqu.springmvc.annotation.RequestMapping;
import cqu.springmvc.annotation.ResponseBody;
import cqu.springmvc.constants.RequestMethod;
import cqu.web.database.Database;
import cqu.web.model.Student;
import org.apache.commons.fileupload.FileItem;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

@Controller
public class RestfulController {

    @RequestMapping(value = "/html")
    public String getHtml() {
        return "/index.jsp";
    }

    @RequestMapping(value = "/json/student/post", method = RequestMethod.POST)
    public boolean getJson(@ResponseBody String json) {
        System.out.println(json);
        Student stu = JSON.parseObject(json, Student.class);
        return Database.add(stu);
    }


    @RequestMapping(value = "/students/get")
    public List<Student> print(HttpServletResponse resp) {
        resp.setContentType("application/json");
        return Database.getAll();
    }

    @RequestMapping(value = "/students/post", method = RequestMethod.POST)
    public boolean add(@ParameterName("id") String id, @ParameterName("name") String name) {
        return Database.add(new Student(Integer.parseInt(id), name));
    }

    @RequestMapping(value = "/students/delete", method = RequestMethod.POST)
    public boolean delete(@ParameterName("id") String id) {
        return Database.delete(Integer.parseInt(id));
    }


    @RequestMapping(value = "/file/post", method = RequestMethod.POST)
    public boolean upload(@ParameterName("file") FileItem file) {
        if (file == null) {
            return false;
        }
        String fileName = file.getName();
        System.out.println(fileName);
        String dirPath = "/Users/chiyikou/Desktop/java/upload/";
        try {
            File f = new File(dirPath);
            if (!f.exists()) {
                f.mkdir();
            }
            file.write(new File(dirPath + fileName));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public String getWEBINFpath() {
        String classesPath = RestfulController.class.getClassLoader().getResource("").getPath().replace("%20", " ");
        if (classesPath.endsWith(getSeparator(classesPath))) {
            classesPath = classesPath.substring(0, classesPath.lastIndexOf(getSeparator(classesPath)));
        }
        return classesPath.substring(0, classesPath.lastIndexOf(getSeparator(classesPath)));
    }

    public String getSeparator(String path) {
        return path.contains("/") ? "/" : "\\";
    }
}
