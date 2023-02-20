package com.zh.mySSM.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/**
 * @author ：HuiZhang
 * @date ：Created in 2023/2/20 21:30
 * @description：
 * @modified By：
 * @version:
 */
public class ClassPathXmlApplicationContext implements BeanFactory {
    private HashMap<String, Object> beanMap = new HashMap<>();

    public ClassPathXmlApplicationContext() {
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
//                    beanId 对应调用的类
                    String className = beanElement.getAttribute("class");
//                    反射构造对应的类对象
                    Class beanClass = Class.forName(className);
                    Object beanObj = beanClass.getConstructor().newInstance();
//                    建立类对象和bean id 对应关系
                    //到目前为止，此处需要注意的是，bean和bean之间的依赖关系还没有设置
                    beanMap.put(beanID, beanObj);
                }
            }
            //5.组装bean之间的依赖关系
            for (int i = 0; i < beanNodeList.getLength(); i++) {
                Node beanNode = beanNodeList.item(i);
                if (beanNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    NodeList beanElementChildNodes = beanElement.getChildNodes();
                    for (int j = 0; j < beanElementChildNodes.getLength(); j++) {
                        Node beanChildNode = beanElementChildNodes.item(j);
                        if (beanChildNode.getNodeType() == Node.ELEMENT_NODE && beanChildNode.getNodeName().equals("property")) {
                            Element propertyElement = (Element) beanChildNode;
                            String propertyName = propertyElement.getAttribute("name");
                            String propertyRef = propertyElement.getAttribute("ref");
                            // 1) 找到propertyRef对应的实例
                            Object refObj = beanMap.get(propertyRef);
                            // 2) 将refObj设置到当前bean对应的实例的property属性上去
                            Object beanObj = beanMap.get(beanId);
                            Class beanObjClass = beanObj.getClass();
                            Field propertyField = beanObjClass.getDeclaredField(propertyName);
                            propertyField.setAccessible(true);
                            propertyField.set(beanObj, refObj);
                        }
                    }
                }
            }
        } catch (SAXException | IOException | ParserConfigurationException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getBean(String id) {
        return beanMap.get(id);
    }
}
