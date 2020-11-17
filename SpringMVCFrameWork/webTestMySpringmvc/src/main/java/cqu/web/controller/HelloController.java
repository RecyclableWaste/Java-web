package cqu.web.controller;

import cqu.springmvc.annotation.Controller;
import cqu.springmvc.annotation.ParameterName;
import cqu.springmvc.annotation.RequestMapping;
import cqu.springmvc.modelAndView.ModelAndView;
import cqu.web.database.Database;

@Controller
public class HelloController {

    @RequestMapping(value = "/hello.do")
    public String hello(@ParameterName("name") String name) {
        System.out.println("helloController name:" + name);
        return "hello";
    }

    //    @RequestMapping(value = "/add.do", method = RequestMethod.POST)
//    public ModelAndView add(@ParameterName("id") String id, @ParameterName("name") String name){
//        ModelAndView modelAndView = new ModelAndView("/WEB-INF/jsp/hello.jsp");
//        Student stu = new Student(Integer.parseInt(id),name);
//        Database.students.add(stu);
//        modelAndView.addModelObject("students", Database.students);
//        return modelAndView;
//    }
    @RequestMapping(value = "/show.do")
    public ModelAndView show() {
        ModelAndView m = new ModelAndView("/WEB-INF/jsp/hello.jsp");
        m.addModelObject("students", Database.students);
        return m;
    }

}

