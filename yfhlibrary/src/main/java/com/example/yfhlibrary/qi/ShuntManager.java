package com.sp.yangfuhualib.distribute;

import android.util.Log;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * 用于数据传递
 */
public class ShuntManager {

    private String TAG = "ShuntManager";

    private Set<Object> mSet = new HashSet();

    private static ShuntManager instance = null;

    public static ShuntManager getInstance() {
        if (instance == null) {
            instance = new ShuntManager();
        }
        return instance;
    }

    private ShuntManager() {

    }


    public void register(Object obj) {
        mSet.add(obj);
    }

    public void unregister(Object obj) {
        mSet.remove(obj);
    }

    public void shuntData(String json) {
        shuntData(json, "");
    }

    /**
     *
     */
    public void shuntData(String json, String description) {
        try {
            Log.i(TAG, "shuntData() description:" + description);
            if (mSet.isEmpty()) {
                Log.i(TAG, "shuntData() ms  is  empty!");
                return;
            }
            Log.i(TAG, "shuntData() ms.size = " + mSet.size());
            for (Object ob : mSet) {
                Class c = ob.getClass();
                //----------------------
                //---------------------------
                Log.i(TAG, "shuntData() NAME:" + c.getName());
                Method[] meth = c.getDeclaredMethods();
                for (Method m : meth) {
//                    Log.i(TAG, "shuntData() NAME:" + m.getName());
                    if (m.isAnnotationPresent(YyData.class)) {
                        YyData annotation = m.getAnnotation(YyData.class);
                        Log.i(TAG, "shuntData() 字段:[" + m.getName() + "], name:[" + annotation.name() + "], 长度:[" + annotation.value() + "]");
                        if (description.equals(annotation.name())) {
                            Log.i(TAG, "shuntData() invoke:" + m.getName());
                            m.invoke(ob, json);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "tototo() catch:" + e.getMessage());
        }

    }

    public void shuntDataByMethod(String json, String description){
        try {
            Log.i(TAG, "shuntData() description:" + description);
            if (mSet.isEmpty()) {
                Log.i(TAG, "shuntData() ms  is  empty!");
                return;
            }
            Log.i(TAG, "shuntData() ms.size = " + mSet.size());
            for (Object ob : mSet) {
                Class c = ob.getClass();
                //----------------------
                //---------------------------
                Log.i(TAG, "shuntData() NAME:" + c.getName());
//                Method[] meth = c.getDeclaredMethods();
                c.getMethod("",Class.forName("java.lang.String"));
              /*  for (Method m : meth) {
//                    Log.i(TAG, "shuntData() NAME:" + m.getName());
                    if (m.isAnnotationPresent(YyData.class)) {
                        YyData annotation = m.getAnnotation(YyData.class);
                        Log.i(TAG, "shuntData() 字段:[" + m.getName() + "], name:[" + annotation.name() + "], 长度:[" + annotation.value() + "]");
                        if (description.equals(annotation.name())) {
                            Log.i(TAG, "shuntData() invoke:" + m.getName());
                            m.invoke(ob, json);
                        }
                    }
                }*/
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "tototo() catch:" + e.getMessage());
        }

    }
}
