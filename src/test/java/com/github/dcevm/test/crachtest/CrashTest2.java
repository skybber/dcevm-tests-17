package com.github.dcevm.test.crachtest;
import static com.github.dcevm.test.util.HotSwapTestHelper.__toVersion__;

public class CrashTest2 {
    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10000; i++) {
                long time = System.currentTimeMillis();
                __toVersion__(0);
                __toVersion__(1);
                System.out.println(i + " " + (System.currentTimeMillis() - time) + "ms");
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

    public class A {
    }

    public class A___1 {
    }
}
