1. 什么是业务层
    1) Model1和Model2
    MVC : Model（模型）、View（视图）、Controller（控制器）
    视图层：用于做数据展示以及和用户交互的一个界面
    控制层：能够接受客户端的请求，具体的业务功能还是需要借助于模型组件来完成
    模型层：模型分为很多种：有比较简单的pojo/vo(value object)，有业务模型组件，有数据访问层组件
        1) pojo/vo : 值对象
        2) DAO ： 数据访问对象
        3) BO ： 业务对象

    2) 区分业务对象和数据访问对象：
      1） DAO中的方法都是单精度方法或者称之为细粒度方法。什么叫单精度？一个方法只考虑一个操作，比如添加，那就是insert操作、查询那就是select操作....
      2） BO中的方法属于业务方法，也实际的业务是比较复杂的，因此业务方法的粒度是比较粗的
          注册这个功能属于业务功能，也就是说注册这个方法属于业务方法。
          那么这个业务方法中包含了多个DAO方法。也就是说注册这个业务功能需要通过多个DAO方法的组合调用，从而完成注册功能的开发。
          注册：
                1. 检查用户名是否已经被注册 - DAO中的select操作
                2. 向用户表新增一条新用户记录 - DAO中的insert操作
                3. 向用户积分表新增一条记录（新用户默认初始化积分100分） - DAO中的insert操作
                4. 向系统消息表新增一条记录（某某某新用户注册了，需要根据通讯录信息向他的联系人推送消息） - DAO中的insert操作
                5. 向系统日志表新增一条记录（某用户在某IP在某年某月某日某时某分某秒某毫秒注册） - DAO中的insert操作
                6. ....
    3) 在库存系统中添加业务层组件

2. IOC
    1) 耦合/依赖
      依赖指的是某某某离不开某某某
      在软件系统中，层与层之间是存在依赖的。我们也称之为耦合。
      我们系统架构或者是设计的一个原则是： 高内聚低耦合。
      层内部的组成应该是高度聚合的，而层与层之间的关系应该是低耦合的，最理想的情况0耦合（就是没有耦合）
    2) IOC - 控制反转 / DI - 依赖注入
        控制反转：
        1) 之前在Servlet中，我们创建service对象 ， FruitService fruitService = new FruitServiceImpl();
           这句话如果出现在servlet中的某个方法内部，那么这个fruitService的作用域（生命周期）应该就是这个方法级别；
           如果这句话出现在servlet的类中，也就是说fruitService是一个成员变量，那么这个fruitService的作用域（生命周期）应该就是这个servlet实例级别
        2) 之后我们在applicationContext.xml中定义了这个fruitService。然后通过解析XML，产生fruitService实例，存放在beanMap中，这个beanMap在一个BeanFactory中
           因此，我们转移（改变）了之前的service实例、dao实例等等他们的生命周期。控制权从程序员转移到BeanFactory。这个现象我们称之为控制反转

        依赖注入：
        1) 之前我们在控制层出现代码：FruitService fruitService = new FruitServiceImpl()；
           那么，控制层和service层存在耦合。
        2) 之后，我们将代码修改成FruitService fruitService = null ;
           然后，在配置文件中配置:
           <bean id="fruit" class="FruitController">
                <property name="fruitService" ref="fruitService"/>
           </bean>

    今日内容：
        1. 过滤器Filter
        2. 事务管理(TransactionManager、ThreadLocal、OpenSessionInViewFilter)
        3. 监听器(Listener , ContextLoaderListener)

    1. 过滤器Filter
    1) Filter也属于Servlet规范
    2) Filter开发步骤：新建类实现Filter接口，然后实现其中的三个方法：init、doFilter、destroy
       配置Filter，可以用注解@WebFilter，也可以使用xml文件 <filter> <filter-mapping>
    3) Filter在配置时，和servlet一样，也可以配置通配符，例如 @WebFilter("*.do")表示拦截所有以.do结尾的请求
    4) 过滤器链
       1）执行的顺序依次是： A B C demo03 C2 B2 A2
       2）如果采取的是注解的方式进行配置，那么过滤器链的拦截顺序是按照全类名的先后顺序排序的
       3）如果采取的是xml的方式进行配置，那么按照配置的先后顺序进行排序

    2. 事务管理
       1) 涉及到的组件：
         - OpenSessionInViewFilter
         - TransactionManager
         - ThreadLocal
         - ConnUtil
         - BaseDAO

       2) ThreadLocal
         - get() , set(obj)
         - ThreadLocal称之为本地线程 。 我们可以通过set方法在当前线程上存储数据、通过get方法在当前线程上获取数据
         - set方法源码分析：
         public void set(T value) {
             Thread t = Thread.currentThread(); //获取当前的线程
             ThreadLocalMap map = getMap(t);    //每一个线程都维护各自的一个容器（ThreadLocalMap）
             if (map != null)
                 map.set(this, value);          //这里的key对应的是ThreadLocal，因为我们的组件中需要传输（共享）的对象可能会有多个（不止Connection）
             else
                 createMap(t, value);           //默认情况下map是没有初始化的，那么第一次往其中添加数据时，会去初始化
         }
         -get方法源码分析：
         public T get() {
             Thread t = Thread.currentThread(); //获取当前的线程
             ThreadLocalMap map = getMap(t);    //获取和这个线程（企业）相关的ThreadLocalMap（也就是工作纽带的集合）
             if (map != null) {
                 ThreadLocalMap.Entry e = map.getEntry(this);   //this指的是ThreadLocal对象，通过它才能知道是哪一个工作纽带
                 if (e != null) {
                     @SuppressWarnings("unchecked")
                     T result = (T)e.value;     //entry.value就可以获取到工具箱了
                     return result;
                 }
             }
             return setInitialValue();
         }
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