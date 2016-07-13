package com.baiye;

import com.baiye.bean.Data;
import com.baiye.bean.Handler;
import com.baiye.bean.Param;
import com.baiye.bean.View;
import com.baiye.helper.BeanHelper;
import com.baiye.helper.ConfigHelper;
import com.baiye.helper.ControllerHelper;
import com.baiye.helper.DataContext;
import com.baiye.utils.CodecUtil;
import com.baiye.utils.JsonUtil;
import com.baiye.utils.ReflectionUtil;
import com.baiye.utils.StreamUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Baiye on 2016/7/4.
 */
@WebServlet(urlPatterns = "/*" ,loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{


    @Override
    public void init(ServletConfig config) throws ServletException {
        //super.init(config);
        HelperLoader.init();

        ServletContext servletContext = config.getServletContext();

        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");

        jspServlet.addMapping(ConfigHelper.getAppJspPath() + "*");

        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");

        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
      //  super.service(req, resp);
        DataContext.init(req,res);
        String requestMethod = req.getMethod().toLowerCase();
        String requestPath = req.getPathInfo();

        Handler handler = ControllerHelper.getHanlder(requestMethod,requestPath);

        if(handler != null)
        {
            Class<?> controllerClass = handler.getControllerClass();
            Object controllerBean = BeanHelper.getBean(controllerClass);

            Map<String,Object> paramMap = new HashMap<String,Object>();

            Enumeration<String> paramNames = req.getParameterNames();
            while(paramNames.hasMoreElements())
            {
                String paramName = paramNames.nextElement();
                String paramValue = req.getParameter(paramName);
                paramMap.put(paramName,paramValue);
            }

            String body = CodecUtil.decodeURL(StreamUtil.getString(req.getInputStream()));
            if(StringUtils.isNotEmpty(body))
            {
                String[] params = StringUtils.split(body,"&");
                if(ArrayUtils.isNotEmpty(params))
                {
                    for(String param : params)
                    {
                        String[] array = StringUtils.split(param,"=");
                        if(ArrayUtils.isNotEmpty(array) && array.length == 2)
                        {
                            String paramName = array[0];
                            String paramValue = array[1];
                            paramMap.put(paramName,paramValue);
                        }
                    }
                }
            }

            Param param = new Param(paramMap);

            Method actionMethod = handler.getActionMethod();
            Object result = ReflectionUtil.invokeMethod(controllerBean,actionMethod,param);

            if(result instanceof View)
            {
                View view = (View) result;
                String path = view.getPath();
                if(StringUtils.isNotEmpty(path))
                {
                    if(path.startsWith("/"))
                        res.sendRedirect(req.getContextPath() + path);
                    else
                    {
                        Map<String,Object> model = view.getModel();
                        for(Map.Entry<String,Object> entry : model.entrySet())
                        {
                            req.setAttribute(entry.getKey(),entry.getValue());
                        }
                        req.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(req,res);

                    }
                }
            }
            else if(result instanceof Data)
            {
                Data data = (Data) result;
                Object model = data.getModel();
                if(model != null)
                {
                    res.setContentType("application/json");
                    res.setCharacterEncoding("UTF-8");
                    PrintWriter writer = res.getWriter();
                    String json = JsonUtil.toJson(model);
                    writer.write(json);
                    writer.flush();
                    writer.close();

                }
            }
        }
    }


}
