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