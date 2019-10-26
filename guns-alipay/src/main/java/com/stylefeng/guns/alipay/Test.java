package com.stylefeng.guns.alipay;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ztkj-hzb
 * @Date 2019/10/13 16:46
 * @Description
 */
public class Test {


    public static void main(String[] args) {

        School school = new School();
        List<User> users = new ArrayList<>();
        users.add(new User("aa","sfdaf"));
        users.add(new User("bb","sfdaf"));
        school.setUsers(users);


        Map<String,String> paramMap = new HashMap<>();

        BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(school);
        BeanWrapper beanWrapper2 = PropertyAccessorFactory.forBeanPropertyAccess(paramMap);


        String userName = (String) beanWrapper.getPropertyValue("users[0].userName");

        beanWrapper2.setPropertyValue("test",userName);

        System.out.println(paramMap);

    }


    @Data
    public static class School{
        private List<User> users;
    }

    @Data
    @AllArgsConstructor
    public static class User{
        private String userName;

        private String address;
    }
}
