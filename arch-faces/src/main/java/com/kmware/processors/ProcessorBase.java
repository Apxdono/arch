package com.kmware.processors;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 6/20/13
 * Time: 4:20 PM
 * To change this template use File | Settings | File Templates.
 */
public class ProcessorBase {

    protected Class<?> klazz;
    protected String beanName;

    public ProcessorBase(Class<?> klazz, String beanName) {
        this.klazz = klazz;
        this.beanName = beanName;
    }


    protected List<Method> getGetters(){
        List<Method> methods = new ArrayList<Method>();
        methods.addAll(Arrays.asList(klazz.getDeclaredMethods()));

        Class parent = klazz.getSuperclass();
        while (!parent.equals(Object.class)) {
            methods.addAll(Arrays.asList(parent.getDeclaredMethods()));
            parent = parent.getSuperclass();
        }

        return methods;
    }



}
