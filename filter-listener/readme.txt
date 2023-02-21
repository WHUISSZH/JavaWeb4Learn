1. 过滤器Filter
1) Filter也属于Servlet规范
2) Filter开发步骤：新建类实现Filter接口，然后实现其中的三个方法：init、doFilter、destroy
   配置Filter，可以用注解@WebFilter，也可以使用xml文件 <filter> <filter-mapping>
3) Filter在配置时，和servlet一样，也可以配置通配符，例如 @WebFilter("*.do")表示拦截所有以.do结尾的请求
4) 过滤器链
   1）执行的顺序依次是： A B demo01  B2 A2
   2）如果采取的是注解的方式进行配置，那么过滤器链的拦截顺序是按照全类名的先后顺序排序的
   3）如果采取的是xml的方式进行配置，那么按照配置的先后顺序进行排序

3. 监听器
    1) ServletContextListener - 监听ServletContext对象的创建和销毁的过程
    2) HttpSessionListener - 监听HttpSession对象的创建和销毁的过程
    3) ServletRequestListener - 监听ServletRequest对象的创建和销毁的过程

    4) ServletContextAttributeListener - 监听ServletContext的保存作用域的改动(add,remove,replace)
    5) HttpSessionAttributeListener - 监听HttpSession的保存作用域的改动(add,remove,replace)
    6) ServletRequestAttributeListener - 监听ServletRequest的保存作用域的改动(add,remove,replace)

    7) HttpSessionBindingListener - 监听某个对象在Session域中的创建与移除
    8) HttpSessionActivationListener - 监听某个对象在Session域中的序列化和反序列化

4. ServletContextListener的应用 - ContextLoaderListener













