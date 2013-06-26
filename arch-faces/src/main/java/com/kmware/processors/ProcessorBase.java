package com.kmware.processors;

import com.kmware.ui.scaffold.EditField;

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
public abstract class ProcessorBase {

    public static final String GET_CLASS_METHOD = "getClass";
    public static final  String GET_METHOD_DEFINITION = "get";
    protected Class<?> klazz;
    protected String beanName;
    protected String entityField;

    public ProcessorBase(Class<?> klazz, String beanName,String entityField) {
        this.klazz = klazz;
        this.beanName = beanName;
        this.entityField = entityField;
    }

    public abstract List<EditField> processEditFields();
//    public abstract List<EditField> processViewFields();
//    public abstract List<EditField> processListFields();

    protected List<Method> getGetters(){
        List<Method> methods = new ArrayList<Method>();

        methods.addAll(fetchOnlyGetters(Arrays.asList(klazz.getDeclaredMethods())));

        Class parent = klazz.getSuperclass();
        while (!parent.equals(Object.class)) {
            methods.addAll(fetchOnlyGetters(Arrays.asList(parent.getDeclaredMethods())));
            parent = parent.getSuperclass();
        }
        return methods;
    }

    protected List<Method> fetchOnlyGetters(List<Method> methods){
        List<Method> result = new ArrayList<Method>();
        for(Method m : methods){
            if(isValidGetter(m)){
                result.add(m);
            }
        }
        return result;

    }

    protected boolean isValidGetter(Method m){
        return (!m.getName().startsWith(GET_CLASS_METHOD) && m.getName().startsWith(GET_METHOD_DEFINITION)) ;
    }

    protected String getFieldName(Method method){
        String name = method.getName();
        name = name.substring(3);
        String firstLetter = name.charAt(0)+"";
        name = firstLetter.toLowerCase()+name.substring(1);
        return name;
    }



}
