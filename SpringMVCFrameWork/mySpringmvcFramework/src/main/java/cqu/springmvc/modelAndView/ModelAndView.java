package cqu.springmvc.modelAndView;

import cqu.springmvc.constants.DispatcherType;

import java.util.HashMap;
import java.util.Map;

public class ModelAndView {
    private String viewName;
    private Map<String, Object> modelObjects;
    private int dispatcherType;

    public ModelAndView() {
        this(null);
    }

    public ModelAndView(String viewName) {
        this(viewName, DispatcherType.DISPATCHER_FORWARD);
    }

    public ModelAndView(String viewName, int dispatcherType) {
        this.viewName = viewName;
        this.dispatcherType = dispatcherType;
        this.modelObjects = new HashMap<>();
    }

    public String getViewName() {
        return this.viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public int getDispatcherType() {
        return this.dispatcherType;
    }

    public void setDispatcherType(int dispatcherType) {
        this.dispatcherType = dispatcherType;
    }

    public Object getModelObject(String modelName) {
        return this.modelObjects.get(modelName);
    }

    public void addModelObject(String modelName, Object modelObject) {
        this.modelObjects.put(modelName, modelObject);
    }

    public Map<String, Object> getModelObjects() {
        return this.modelObjects;
    }
}
