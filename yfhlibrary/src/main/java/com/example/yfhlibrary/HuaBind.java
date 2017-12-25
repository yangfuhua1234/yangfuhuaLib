package com.example.yfhlibrary;

import android.util.Log;

import com.example.yfhlibrary.qi.QiAutowired;

import java.lang.reflect.Field;

/**
 * Created by User2 on 2017/11/23.
 */

public class HuaBind {


    public static void BindAutowired(Object obj) {

        //得到类对象
        Class cla = (Class) obj.getClass();

        /*
        * 得到类中的所有属性集合
        */
        Field[] fs = cla.getDeclaredFields();

        for(int i = 0 ; i < fs.length; i++){
            // 判断这个字段是否取ASNAME
            if (fs[i].isAnnotationPresent(QiAutowired.class)) {
                fs[i].setAccessible(true); //设置些属性是可以访问的
                QiAutowired autowired = fs[i].getAnnotation(QiAutowired.class);
                try {
                    fs[i].set(obj,fs[i].getType().newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
