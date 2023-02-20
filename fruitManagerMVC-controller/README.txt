整体流程，以编辑库存操作为例：
1.启动项目，中央控制器初始化，执行init（）方法，从配置文件中获取controller类对象和对应url中servlet路径的对应关系。
2.浏览器url：http://localhost:8888/fruit.do?fid=13&operate=directEditPage
3.中央控制器匹配到fruit.do请求，执行service方法，截取servlet 路径fruit，获取controller类对象和对应url中servlet路径的对应关系（fruit -> com.zh.fruit.controllers.FruitController），获取获取对应controller类对象及其方法
4.从url 请求中获取operate参数值，通过此参数值与controller类对象的方法名进行匹配，如果方法名和标识参数值相同，则通过反射技术调用此方法（这里就是调用FruitController的directEditPage方法）。（如此判断的前提是标识参数的值和方法名的命名（规则）要统一）
5.controller组件中的方法调用。统一获取directEditPage方法的参数名，把url request中携带的参数传递给directEditPage方法进行具体的数据处理，与数据库进行交互。交互后只返回处理结果标志符号。
6.视图处理。通过处理结果标志符号去进行视图处理，具体做页面的重定向、渲染展示。


review:
1. 最初的做法是： 一个请求对应一个Servlet，这样存在的问题是servlet太多了
2. 把一些列的请求都对应一个Servlet, IndexServlet/AddServlet/EditServlet/DelServlet/UpdateServlet -> 合并成FruitServlet
   通过一个operate的值来决定调用FruitServlet中的哪一个方法
   使用的是switch-case
3. 在上一个版本中，Servlet中充斥着大量的switch-case，试想一下，随着我们的项目的业务规模扩大，那么会有很多的Servlet，也就意味着会有很多的switch-case，这是一种代码冗余
   因此，我们在servlet中使用了反射技术，我们规定operate的值和方法名一致，那么接收到operate的值是什么就表明我们需要调用对应的方法进行响应，如果找不到对应的方法，则抛异常
4. 在上一个版本中我们使用了反射技术，但是其实还是存在一定的问题：每一个servlet中都有类似的反射技术的代码。因此继续抽取，设计了中央控制器类：DispatcherServlet
   DispatcherServlet这个类的工作分为两大部分：
   1.根据url定位到能够处理这个请求的controller组件：
    1)从url中提取servletPath : /fruit.do -> fruit
    2)根据fruit找到对应的组件:FruitController ， 这个对应的依据我们存储在applicationContext.xml中
      <bean id="fruit" class="com.atguigu.fruit.controllers.FruitController/>
      通过DOM技术我们去解析XML文件，在中央控制器中形成一个beanMap容器，用来存放所有的Controller组件
    3)根据获取到的operate的值定位到我们FruitController中需要调用的方法
   2.调用Controller组件中的方法：
    1) 获取参数
       获取即将要调用的方法的参数签名信息: Parameter[] parameters = method.getParameters();
       通过parameter.getName()获取参数的名称；
       准备了Object[] parameterValues 这个数组用来存放对应参数的参数值
       另外，我们需要考虑参数的类型问题，需要做类型转化的工作。通过parameter.getType()获取参数的类型
    2) 执行方法
       Object returnObj = method.invoke(controllerBean , parameterValues);
    3) 视图处理
       String returnStr = (String)returnObj;
       if(returnStr.startWith("redirect:")){
        ....
       }else if.....