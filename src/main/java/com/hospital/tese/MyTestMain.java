//package com.older.tese;
//
//import com.older.user.beans.MyUser;
//import org.apache.ibatis.javassist.ClassPath;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//public class MyTestMain {
//    public static void main(String[] args) {
//        //这个ClassPathXmlApplicationContext是spring管理xml文件提供的一个类
//        ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("spring-content.xml");
//        Object myuser=context.getBean("myuserbean");
//        MyUser myotheruser=(MyUser)myuser;
//        System.out.println(myotheruser);
//    }
//}
