package com.demo.config.mfi;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Main {
    public static void main(String[] args) {
        Inner inner = new Inner();
        Type t = (new Test()).getClass().getGenericSuperclass();
        ParameterizedType p = (ParameterizedType) t;
        Type[] a = p.getActualTypeArguments();
        try {
            Custom c = (Custom) ((Class) a[0]).newInstance();
            c.f();
        } catch (Exception e){}
    }

    private static abstract class AbstractClass<T> {
        public abstract void doSth();
    }
    private static class Inner extends AbstractClass<Custom>{
        public void doSth() {
        }
    }
    private static class Custom{
        public Custom(){

        }
        public void f(){
            System.out.println("Custom");
        }
    }

    private static class Test extends MyType<String>  {

        @Override
        public String get() {
            return null;
        }
    }
}