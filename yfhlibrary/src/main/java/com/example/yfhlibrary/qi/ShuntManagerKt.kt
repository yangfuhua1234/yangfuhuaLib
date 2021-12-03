package com.sp.yangfuhualib.distribute

import android.util.Log
import java.lang.Exception
import java.lang.reflect.Method
import java.util.HashSet

/**
 * create yangfuhua
 * 用于数据传递
 */
object ShuntManagerKt {
    private val TAG = "ShuntManagerKt"
    private val mSet: MutableSet<Any> = HashSet()
    fun register(obj: Any) {
        mSet.add(obj)
    }

    fun unregister(obj: Any) {
        mSet.remove(obj)
    }

    /*** 向注册者发送数据
     * json:发送的数据
     * description:接收数据的注解描述
     * */
    fun shuntData(json: String?, description: String) {
        try {
            Log.i(TAG, "shuntData() description:$description")
            if (mSet.isEmpty()) {
                Log.i(TAG, "shuntData() ms  is  empty!")
                return
            }
            Log.i(TAG, "shuntData() ms.size = " + mSet.size)
            for (obj in mSet) {
                val c: Class<*> = obj.javaClass
//                Log.i(TAG, "shuntData() NAME:" + c.name)
                val meth = c.declaredMethods
                for (m in meth) {
                    Log.i(TAG, "shuntData() NAME:" + m.getName());
                    if (m.isAnnotationPresent(YyData::class.java)) {
                        var annotation = m.getAnnotation(YyData::class.java)
                        Log.i(TAG, "shuntData() 字段:[" + m.name + "], name:[" + annotation.name + "], 长度:[" + annotation.value + "]")
                        if (annotation.name.isNullOrEmpty() || description == annotation.name) {
                            Log.i(TAG, "shuntData() invoke:" + m.name)
                            m.invoke(obj, json)
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.i(TAG, "tototo() catch:" + e.message)
        }
    }

    /**
     * 指定发送到某个方法
     * json:发送的数据
     * description:接收数据的注解描述
     * methodName:方法名称
     * */
    fun shuntData(json: String?, description: String, methodName: String) {
        Log.i(TAG, "shuntData() description:$description")
        if (mSet.isEmpty()) {
            Log.i(TAG, "shuntData() ms  is  empty!")
            return
        }
        for (ob in mSet) {
            var c: Class<*> = ob.javaClass
            Log.i(TAG, "shuntData() NAME:" + c.name)
            try {
                var m: Method = c.getMethod(methodName, Class.forName("java.lang.String"))
//                    Log.i(TAG, "shuntData() NAME:" + m.getName());
                Log.i(TAG, "shuntData() $m")
                if (m == null) {
                    Log.i(TAG, "shuntData() Method is empty ")
                    return
                }
                if (m.isAnnotationPresent(YyData::class.java)) {
                    var annotation = m.getAnnotation(YyData::class.java)
                    Log.i(
                        TAG,
                        "shuntData() 字段:[" + m.name + "], name:[" + annotation.name + "], 长度:[" + annotation.value + "]"
                    )
                    if (annotation.name.isNullOrEmpty() || description == annotation.name) {
                        Log.i(TAG, "shuntData() invoke:" + m.name)
                        m.invoke(ob, json)
                    }
                }
            } catch (e: Exception) {
                Log.i(TAG, "shuntData() catch: ${e.message}")
            }
        }


    }
}