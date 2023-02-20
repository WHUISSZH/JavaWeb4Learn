package com.zh.mySSM.mySpringMVC;

import com.zh.mySSM.util.StringUtil;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/18 14:54
 * @description：相当于简单实现SpringMvc,把视图解析和参数方法调用分开来,实现统一管理跳转 中央处理器负责获取数据
 * @modified By：
 * @version:
 */

// 通配符，不需要加 /
// 精准匹配已有的servlet，如果没有匹配上然后再进行通配符匹配
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet {
    private HashMap<String, Object> beanMap = new HashMap<>();

    public DispatcherServlet() {
    }

    //    初始化，从配置文件中获取controller类对象和对应url中servlet路径的对应关系
    public void init() throws ServletException {
        super.init();
        try {
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            //1.创建DocumentBuilderFactory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            //2.创建DocumentBuilder对象
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            //3.创建Document对象
            Document document = documentBuilder.parse(inputStream);
            //4.获取所有的bean节点
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
//                    et, al. beanID = fruit
                    String beanID = beanElement.getAttribute("id");
//                    beanId 对应调用的controller类
                    String className = beanElement.getAttribute("class");
//                    反射构造对应的controller类对象
                    Class controllerBeanClass = Class.forName(className);
                    Object beanObj = controllerBeanClass.getConstructor().newInstance();
//                    建立controller类对象和对应url中servlet路径的对应关系
                    beanMap.put(beanID, beanObj);
                }
            }
        } catch (SAXException | IOException | ParserConfigurationException | ClassNotFoundException |
                NoSuchMethodException | InvocationTargetException | InstantiationException |
                IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");

//        获取url中的servlet路径
        String servletPath = request.getServletPath();
        servletPath = servletPath.substring(1);
        int lastIndex = servletPath.lastIndexOf(".do");
        servletPath = servletPath.substring(0, lastIndex);

//        获取对应controller类对象
        Object controllerBeanObj = beanMap.get(servletPath);

//        获取操作标识参数
        String operate = request.getParameter("operate");
        if (StringUtil.isEmpty(operate)) {
            operate = "index";
        }

        Method[] declaredMethods = controllerBeanObj.getClass().getDeclaredMethods();
        for (Method method : declaredMethods) {
//            获取当前方法名
            String methodName = method.getName();
//            如果方法名和标识参数值相同，则通过反射技术调用此方法。
//            如此判断的前提是标识参数的值和方法名的命名（规则）要统一
            if (methodName.equals(operate)) {
                try {
                    //1.统一获取请求参数

                    //1-1.获取当前方法的参数，返回参数数组
                    Parameter[] parameters = method.getParameters();
//                        获取参数名
                    DefaultParameterNameDiscoverer discover = new DefaultParameterNameDiscoverer();
                    String[] parameterNames = discover.getParameterNames(method);
                    //1-2.parameterValues 用来承载参数的值
                    Object[] parameterValues = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
//                        String parameterName = parameter.getName();
                        String parameterName = parameterNames[i];
                        // 如果参数名是request,response,session，那么直接把本方法（service方法）参数中的
                        // request,response,session赋值给方法参数
                        if ("request".equals(parameterName)) {
                            parameterValues[i] = request;
                        } else if ("response".equals(parameterName)) {
                            parameterValues[i] = response;
                        } else if ("session".equals(parameterName)) {
                            parameterValues[i] = request.getSession();
                        } else { // 是request中携带的参数 / url中的参数
                            String parameterValue = request.getParameter(parameterName);
                            String typeName = parameter.getType().getName();

                            Object parameterObj = parameterValue;

                            if (parameterObj != null) {
//                                就本项目来说， controller方法中的参数有两种类型：String 和 Integer
//                                Integer类型需要从String转换过来
                                if ("java.lang.Integer".equals(typeName)) {
                                    parameterObj = Integer.parseInt(parameterValue);
                                }
                            }
                            parameterValues[i] = parameterObj;
                        }
                    }
                    // 2.controller组件中的方法调用
//                    去执行index()  edit()等方法与数据库进行交互
                    method.setAccessible(true);
                    Object returnObj = method.invoke(controllerBeanObj, parameterValues);

//                    3. 视图处理
                    String methodReturnStr = (String) returnObj;
//                    客户端重定向请求，比如：  redirect:fruit.do
                    if (methodReturnStr.startsWith("redirect:")) {
                        String redirectPath = methodReturnStr.substring("redirect:".length());
                        response.sendRedirect(redirectPath);
                    } else { // 渲染点击连接跳转页面，例如跳转到 index.html edit.html 和add.html
                        super.processTemplate(methodReturnStr, request, response);
                    }
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}