package com.example.java26.week1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * primitive type (int, boolean ...), object
 * other than primitive type, others are all the objects / instances
 * compare object, class !!!!
 * OOP
 *    polymorphism: overriding + overloading
 *               Car car  = new BMW()
 *    encapsulation: access modifier
 *             private: 封装，不让其他程序员访问
 *             default
 *             protected
 *             public
 *    Abstraction：
 *             都可以有METHOD FUNCTION
 *             两者对变量处理不同
 *
 *             abstract class:
 *                   要处理变量，construction,
 *                   类单一继承, single extend, cLASS只能继承一个CLASS
 *             interface:
 *                   MULTIPLE INTERFACES
 *                   method: 非抽象方法
 *                   default method() {} after java 8
 *                   private method() {} after java 9
 *             一个类只能继承一个抽象类，而一个类却可以实现多个接口
 *    inheritance:
 *             class A extends B implements interface1, 2, 3{
 *                it's better to reuse rather than develop!
 *                reuse interface;
 *                reuse class
 *             }
 */

interface AbstractionDemo {
   int a = 6; //没有办法通过正常手段修改
   //JAVA 8 - > 16
}

class EncapsulationExample {
   private String name; // use getter / setter to change and view private variable
   public int a; // 任何CLASS都可以访问
   protected int b; // 继承了EXTEND CLASS可以访问
   int c; // 只有当前PACKAGE SUBCLASS可以访问


   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }
}

class Test {

}

 /**
 * SOlID
  * Single Responsibility
  *         class StudentService{
  *            // 只做一件事情
  *         }
  *
  * Open Close
  *         //  develop a new feature - try to extend a class
  *         open to extends
  *         close to modified
  *
  * Liskov substitution
  *         extend similar class
  *         class Car extends Pizza {} // doesn't make any sense!!
  *         Piazza p = new HawaiiPizza();  // make sense!
  *
  * Interface Segregation
  *         interface Car{
  *            // 100 methods - stop, run,
  *         }
  *         there may be many functions, it's difficult to implement so many functions in one interface
  *         如果INTERFACE非常臃肿，可以将其分成不同的INTERFACE去实现
  *
  * Dependency Inversion
  *         class A {
  *            调用CLASS B
  *            private HawaiiPizza / Pizza p; ??
  *            private Pizza p; // better to use general type
  *            class A has nothing to do with Pizza;
  *            public A (...) { // 用PIZZA,可以在里面填任何类型的PIZZA
  *
  *            }
  *         }
  *
  *         dependency injection?
  *
  */

/**
 *
 */

/**
* pass by value
 * Java pass by value! primitive type & object - pass by value
 *
 */

class PassByValueExample {
   public static void func1(int a) {
      a = 5;
   }

   // list addr3[addr1] => Arrays.asList()
   public static void func2(List<Integer> list) {
      // lsit addr3[addr1] =>
      list = new ArrayList<>();
   }

   public static void main(String[] args) {
      int a = 10;
      func1(a);
      System.out.println(a); // 10
      // addr1 => Arrays.asList(1,2,3);
      // list addr2[addr1] => => Arrays.asList(1,2,3);
      List<Integer> list = Arrays.asList(1,2, 3);
      // func2(addr1)
      func2(list);
      // print addr2[addr1]
      System.out.println(list); // 123

      new Day1Node2<Integer>; // generic unsure
   }
   /*
   list1 ->
   list2 -> smae instance
   then
   list2 -> other instance
    */
}

/**
 * generic
 *  List<Integer> list // make sure what data type is inside the list
 */
class Day1Node {
   private Object val;
   public Day1Node(Object val) {
      this.val = val; // not know if it's boolean, string ...
   }
}

// the generic one
class Day1Node2 {
   private T val;
   public Day1Node2(T val) {
      this.val = val;
   }
}

/**
* static / class obj: every class will generate an object
 * class object - instance, this contains other instances
 *
 * static -> class objec， JVM对自动生成对应的INSTANCE,  * 不需要实例化就可以访问static
 * non-staic -> this obj, 对应当前的INSTANCE
 */
class Day1ClassObjDemo {
   // staic: class object, put it into the heap
   // instance - put in the heap
   public static int val1 = 5;
   public int val2 = 5; // val2要等对象初始化才生成
   // every class has a default constructor
   public Day1ClassObjDemo(int val2) {
      val2 = val2; // make no sense 自己给自己赋值
      this.val2 = val2; // make sense
   }
   // 要么创建STAIC INSTANCE,要么创建METHOD调用对应的NON STATIC INSTANCE
   public static void main(String[] args) {
      Day1ClassObjDemo d1 = new Day1ClassObjDemo();
      d1.val2 = 3;
      Day1ClassObjDemo d2 = new Day1ClassObjDemo();
      d2.val2 = 4;
//      Class<?> clazz = Day1Node.class;
//      Field f = clazz.getDeclaredFields()[0];
//      System.out.println(val2); // staic cannot implement not-staic
      }
}

/*
* immutable:/string/how to create immutable
*/
class StringImmutableDemo {
   /**
    *
    * constant string pool :
    *       "abc"
    */
   public static void main(String[] args) {
      String s1 = "abc"; // Constant String poll
      String s2 = "abs"; // Constant String poll
      String s3 = new String("abc"); // instance1 in heap创建在HEAP里面
      String s4 = new String("abc"); // instance2 in heap
      s1 == s2; // true
      s2 == s3; // false
      s3 == s4; // false
      s3.equals(s4); // false, equalS比较地址
      Comparator<Integer> cpt = (v1, v2) -> (v1 - v2);
   }

   public static StringBuilder sb = new StringBuilder();
   public static String reverse(String str) {
      String res = "";
      if (str == null) { // if str is null, for loop will 奔溃 non pointer
         throw new IllegalArgumentException("..");
      }
      for(int i = str.length() - 1; i >= 0; i--) {
         // 每次都创建一个新的string
         // can use string builder here, like a charArray
         res += str.charAt(i); // occupy a lot of memory
         // 最好将stringbuilder放到method里面
         sb.append(str.charAt(i)); // sb会被共享，里面的东西可能没有被清空
      }
      return res;
   }
}
/*
* final;
*/
