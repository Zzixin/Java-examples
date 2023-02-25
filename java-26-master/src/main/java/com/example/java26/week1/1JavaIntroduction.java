package com.example.java26.week1;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 *  primitive type (int, boolean...), object
 *  ****  **** **** **** **** **** **** **** **** ****
 *  OOP
 *      polymorphism : overriding + overloading
 *          Car car = new BMW();
 *      encapsulation: access modifier
 *              private
 *              default
 *              protected
 *              public
 *      abstraction:
 *              abstract class
 *              interface
 *                  int a = 5;
 *                  default method() {}
 *                  private method()
 *      inheritance
 *              class A extends B implements interface1, 2, 3 {
 *
 *              }
 */
interface AbstractionDemo {
    int a = 6;
}
class EncapsulationExample {
    protected String name;

    public String getName() {
        return name;
    }
}
/**
 * 	SOLID
 * 	Single Responsibility
 * 	        class StudentService {
 *
 * 	        }
 * 	Open Close
 * 	        extends
 * 	Liskov Substitution
 * 	        class HawaiiPizza extends Pizza {}
 * 	        Pizza p = new HawaiiPizza();
 * 	Interface Segregation
 * 	        interface Car {
 * 	            //1000 methods
 * 	        }
 * 	Dependency Inversion
 * 	        class A {
 * 	            private Pizza p;
 * 	            public A (..) {
 *
 * 	            }
 * 	        }
 *
 *  class A {
 *      public void xx() {
 *          for() {
 *              if() {
 *                  for() {
 *
 *                  }
 *              }
 *          }
 *          int x = 5;
 *          int y = 5;
 *      }
 *  }
 */

/**
 * 	pass by value
 *
 */
class PassByValueExample {
    public static void func1(int a) {
        a = 5;
    }
    //list 0x888[0x000] -> Arrays.asList()
    public static void func2(List<Integer> list) {
        //list 0x888[0x555] -> empty list
        list = new ArrayList<>();
    }

    public static void main(String[] args) {
//        int a = 10;
//        func1(a);
//        System.out.println(a); //5 or 10

        // 0x000 -> Arrays.asList(1, 2, 3);
        //list 0x777[0x000] -> -> Arrays.asList(1, 2, 3)
        List<Integer> list = Arrays.asList(1, 2, 3);
        //func2(0x000)
        func2(list);
        //print 0x777[0x000]
        System.out.println(list);
    }
}
/**
 * generic
 */
class Day1Node<T> {
    private T val;

    public Day1Node(T val) {
        this.val = val;
    }
}

/**
 * 	static -> class obj
 * 	non-static -> this obj
 */
class Day1ClassObjDemo {
    public static int val1 = 5;
    public int val2 = 5;

    public Day1ClassObjDemo(int val2) {
        this.val2 = val2;
    }

    public static void main(String[] args) {
//        Day1ClassObjDemo d1 = new Day1ClassObjDemo();
//        d1.val2 = 3;
//        Day1ClassObjDemo d2 = new Day1ClassObjDemo();
//        d2.val2 = 4;
        Day1ClassObjDemo d2 = new Day1ClassObjDemo(10);
        System.out.println(d2.val2); //5
    }
}

/**
 * 	immutable / string / how to create immutable
 *
 * 	String : constant string pool
 */
class StringImmutableDemo {
    /**
     * constant string pool :
     *      "abc"
     *      "abcd"
     */
    public static void main(String[] args) {
//        String s1 = "abc"; //Constant string pool
//        String s2 = "abc"; //Constant string pool
//        String s3 = new String("abc"); //instance1 in heap
//        String s4 = new String("abc"); //instance2 in heap
//        System.out.println(s1 == s2);
//        System.out.println(s2 == s3);
//        System.out.println(s3 == s4);
//        System.out.println(s3.equals(s4));


        Integer v1 = 1;
        Integer v2 = 1;
        Integer v3 = 300;
        Integer v4 = 300;

        int v5 = 300;
        System.out.println(v5 == v4);
    }

    //str = null;
    public static String reverse(String str) {
        if(str == null) {
            //log
            throw new IllegalArgumentException("..");
        }
        StringBuilder sb = new StringBuilder();
        for(int i = str.length() - 1; i >= 0; i--) {
            sb.append(str.charAt(i));
        }
        return sb.toString();
        //return new StringBuilder(str).reverse().toString();
    }
}


/**
 * shallow copy vs deep copy
 * class Emp {
 *     private String name;
 *     private Address ads;
 * }
 *
 * class Address {
 *     private String street;
 * }
 * Address ad1 = new Address();
 * Emp e1 = new Emp(ad1);
 * //shallow copy e1 to e2,  e1 + e2 use same ad1
 * Emp e2 = e1.clone();
 * //deep copy e1 to e2, e1, e2 use diff address instance
 * Emp e2 = e1.clone();
 *
 */
final class Day1MyImmutableInstance {
    private final String name;
    private final List<Object> list;

    public Day1MyImmutableInstance(String name, List<Object> x) {
        this.name = name;

        this.list = new ArrayList<>();
        for(Object obj: x) {
            //list.add(deep copy obj)
        }
    }

    public List<Object> getList() {
        List<Object> ans = new ArrayList<>();
        for(Object obj: this.list) {
            //ans.add(deep copy obj)
        }
        return ans;
//        return this.list;
    }

    public String getName() {
        return name;
    }
}
/**
 * instance1 = new Day1MyImmutableInstance()
 * List<Object> l1 = instance1.getList();
 * List<Object> l2 = instance1.getList();
 * Object obj = l1.get(0);
 */


/**
 * exception
 *
 *          Throwable class
 *          /           \
 *       Error          Exception (compile)
 *                        \
 *                        RuntimeException
 *                         \
 *
 *
 */
class MyRuntimeException extends RuntimeException {
    public MyRuntimeException(String message) {
        super(message);
    }
}
class MyCompileTimeException extends Exception {
    public MyCompileTimeException(String message) {
        super(message);
    }
}
class Day1ExceptionDemo {
    public static void main(String[] args) {
        func3();
    }
    private static void func3() {
        func2();
    }

    private static void func2() {
        try {
            func1();
        } catch (Exception ex) {
            throw new IllegalArgumentException("ccc");
        }
    }

    private static void func1() throws Exception {
//        throw new MyCompileTimeException("aaa");

//        try {
            throw new MyCompileTimeException("aaa");
//        } catch (Exception ex) {
//
//        } finally {
//            //release lock / resources / connection
//        }
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@interface MyAnnotation {
    String name() default "Tom";
}

@MyAnnotation(name = "Jerry")
class Day1Student {
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Day1Student{" +
                "name='" + name + '\'' +
                '}';
    }
}
class Day1ReflectionDemo {
    public static void main(String[] args) throws Exception {
        Class<?> clazz = Day1Student.class;
        Day1Student stu = (Day1Student) clazz.getDeclaredConstructors()[0].newInstance();
        Field f = clazz.getDeclaredField("name");
        f.setAccessible(true);
        f.set(stu, "Tom");
        System.out.println(stu);
    }
}

/**
 * 	final / finally
 *
 * 	final List<Integer> list = new ArrayList<>();
 * 	list.
 */

/**
 *  Array
 *  List vs Map vs Set
 *  ArrayList, LinkedList
 *  Hashtable, HashMap, TreeMap
 *  HashSet, TreeSet
 *  PriorityQueue
 *  Stack, Queue, Deque
 */