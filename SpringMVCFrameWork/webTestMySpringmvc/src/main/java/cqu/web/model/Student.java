package cqu.web.model;

import com.alibaba.fastjson.annotation.JSONField;

public class Student {
    @JSONField(name = "id")
    public int id;
    @JSONField(name = "name")
    public String name;

    public Student(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public String toString() {
        return id + ":" + name;
    }
}
