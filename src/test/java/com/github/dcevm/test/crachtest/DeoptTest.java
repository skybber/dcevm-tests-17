package com.github.dcevm.test.crachtest;
import static com.github.dcevm.test.util.HotSwapTestHelper.__toVersion__;

public class DeoptTest {

    public static A1 a = new A1();
    public static B b = new B();
    public static int sw = 0;

    public static void main(String[] args) {
        try {
            for (int i = 0; i < 30; i++) {
                for (int j=0; j<20000; j++) {
                    a.getValue();
                }
                System.out.println(a.getValue());
                sw ++;
                if (sw % 2 == 1) {
                __toVersion__(1);
                } else {
                    __toVersion__(0);
                }
            }
        } catch (InternalError e) {
            System.out.println("INTERNAL ERROR");
            e.printStackTrace();
            if (e.getCause() != null) {
                System.out.println(e.getCause());
                e.getCause().printStackTrace();
            }
        }
    }

    public static class B {
        public void print(A1 a) {
            System.out.println("value=" + a.getValue());
        }
    }

    public interface A0 {
    }

    public interface A0___1 {
        public default int getValue() {
            return 1;
        }
    }

    public static class A1 implements A0 {
        public int getValue() {
            return 0;
        }
    }

    public static class A1___1 implements A0 {
    }
}
