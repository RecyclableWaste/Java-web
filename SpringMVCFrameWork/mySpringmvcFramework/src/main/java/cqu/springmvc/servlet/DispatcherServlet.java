package cqu.springmvc.servlet;

import com.alibaba.fastjson.JSON;
import cqu.ioc.context.ApplicationContext;
import cqu.springmvc.annotation.ResponseBody;
import cqu.springmvc.constants.DispatcherType;
import cqu.springmvc.context.SpringmvcApplicationContext;
import cqu.springmvc.handle.Handler;
import cqu.springmvc.handle.RequestMappingHandler;
import cqu.springmvc.modelAndView.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

public class DispatcherServlet extends HttpServlet {
    private ApplicationContext ac = null;
    private SpringmvcApplicationContext sac = null;

    public DispatcherServlet() throws Exception {
    }

    private void doDispatcher(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String uri = req.getRequestURI();
        System.out.println("uri:" + uri);

        Handler handler = (Handler) ac.getBean("handler");
        handler.setApplicationContext(ac);
        handler.setSprintmvcApplicationContext(sac);

        RequestMappingHandler rmHandler = (RequestMappingHandler) ac.getBean("rmHandler");
        Object object = rmHandler.getObject(sac, uri);
        Method method = rmHandler.getMethod(sac, uri);

        String requestMethod = rmHandler.getRequestMethod(sac, uri);

        if (method != null && req.getMethod().toLowerCase().equals(requestMethod.toLowerCase())) {
            try {
                ModelAndView modelAndView = handler.getModelAndView(method, object, req, resp);
                if (modelAndView == null) {
                    return;
                }
                String viewName = modelAndView.getViewName();
                if (viewName != null) {
                    doView(modelAndView, req, resp);
                } else {
                    doNoView(modelAndView, req, resp, method);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {

//            resp.setStatus(404);
        }

    }

    private void doView(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        if (modelAndView.getDispatcherType() == DispatcherType.DISPATCHER_FORWARD) {
            Map<String, Object> models = modelAndView.getModelObjects();
            if (models != null) {
                setRequestAttributes(models, req);
            }
            req.getRequestDispatcher(modelAndView.getViewName()).forward(req, resp);
        } else {
            resp.sendRedirect(modelAndView.getViewName());
        }
    }

    private void doNoView(ModelAndView modelAndView, HttpServletRequest req, HttpServletResponse resp, Method method) throws Exception {
        PrintWriter printWriter = resp.getWriter();
        Map<String, Object> models = modelAndView.getModelObjects();
        if (models != null) {
            for (Object obj : models.values()) {
                Object newObj = obj;
                if (method.getReturnType().getAnnotation(ResponseBody.class) != null || isJsonType(resp.getContentType())) {
                    newObj = JSON.toJSONString(obj);
                }
                printWriter.println(newObj);
            }
        }
        printWriter.close();
    }

    private boolean isJsonType(String contentType) {
        if (contentType == null) {
            return false;
        }
        return contentType.toLowerCase().contains("application/json");
    }

    private void setRequestAttributes(Map<String, Object> models, HttpServletRequest request) {
        Set<String> keys = models.keySet();
        if (keys != null) {
            for (String key : keys) {
                request.setAttribute(key, models.get(key));
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doDispatcher(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doDispatcher(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            this.ac = new ApplicationContext("cqu.springmvc", null);
            this.sac = new SpringmvcApplicationContext();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
