package com.sunhz.projectutils.jsonutils;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.lang.reflect.Type;

/**
 * GsonUtils依赖
 * 解决方案为:http://stackoverflow.com/questions/14139437/java-type-generic-as-argument-for-gson
 * Created by Spencer on 15/2/27.
 */
public class ListOfSomething<X> implements ParameterizedType {

    private Class<?> wrapped;

    public ListOfSomething(Class<X> wrapped) {
        this.wrapped = wrapped;
    }

    public Type[] getActualTypeArguments() {
        return new Type[]{wrapped};
    }

    public Type getRawType() {
        return List.class;
    }

    public Type getOwnerType() {
        return null;
    }

}
