1. 再次学习Servlet的初始化方法
 1) Servlet生命周期：实例化、初始化、服务、销毁
 2) Servlet中的初始化方法有两个：init() , init(config)
   其中带参数的方法代码如下：
   public void init(ServletConfig config) throws ServletException {
     this.config = config ;
     init();
   }
   另外一个无参的init方法如下：
   public void init() throws ServletException{
   }
   如果我们想要在Servlet初始化时做一些准备工作，那么我们可以重写init方法
   我们可以通过如下步骤去获取初始化设置的数据
   - 获取config对象：ServletConfig config = getServletConfig();
   - 获取初始化参数值： config.getInitParameter(key);
 3) 在web.xml文件中配置Servlet
    <servlet>
        <servlet-name>Demo01Servlet</servlet-name>
        <servlet-class>com.atguigu.servlet.Demo01Servlet</servlet-class>
        <init-param>
            <param-name>hello</param-name>
            <param-value>world</param-value>
        </init-param>
        <init-param>
            <param-name>uname</param-name>
            <param-value>jim</param-value>
        </init-param>
    </servlet>
    <servlet-mapping>
        <servlet-name>Demo01Servlet</servlet-name>
        <url-pattern>/demo01</url-pattern>
    </servlet-mapping>
 4) 也可以通过注解的方式进行配置：
 @WebServlet(urlPatterns = {"/demo01"} ,
     initParams = {
         @WebInitParam(name="hello",value="world"),
         @WebInitParam(name="uname",value="jim")
     })

2. 学习Servlet中的ServletContext和<context-param>
    1) 获取ServletContext，有很多方法
       在初始化方法中： ServletContxt servletContext = getServletContext();
       在服务方法中也可以通过request对象获取，也可以通过session获取：
       request.getServletContext(); session.getServletContext()
    2) 获取初始化值：
       servletContext.getInitParameter();
