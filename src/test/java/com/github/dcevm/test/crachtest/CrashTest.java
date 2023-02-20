package com.github.dcevm.test.crachtest;
import static com.github.dcevm.test.util.HotSwapTestHelper.__toVersion__;

/**  * Swap in active method will change the class definition, however the method finishes in the old class.  */

public class CrashTest {
    public static void loop(A a, int i, long time0) {
        long time = System.currentTimeMillis();
        System.out.println("Value: " + a.value());
        __toVersion__(1);
        System.out.println("Value: " + a.value());
        __toVersion__(0);
        System.out.println(i + " " + (System.currentTimeMillis() - time) + "/" + (System.currentTimeMillis() - time0) + "ms");
    }
    public static void main(String[] args) {
        long time0 = System.currentTimeMillis();

          A a = new A();

          for (int i = 0; i < 10000; i++) {
              loop(a, i, time0);
          }
    }

    public static class A {
        public String value() {
            return stringFld;
        }
        public String stringFld = "OLD";
    }

    // Version 1
    public static class A___1 {
        public String value() {
            stringComplNewFld = "completely new String field";
            return stringComplNewFld;
        }
        public String stringFld = "NEW";
        public int intComplNewFld = 333;
        public long longComplNewFld = 444L;
        public String stringComplNewFld = "completely new String field";
    }
}
