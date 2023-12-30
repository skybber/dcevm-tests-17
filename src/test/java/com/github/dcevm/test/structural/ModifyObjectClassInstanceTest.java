package com.github.dcevm.test.structural;

import com.github.dcevm.ClassRedefinitionPolicy;
import jdk.internal.vm.annotation.IntrinsicCandidate;
import org.junit.Before;
import org.junit.Test;

import static com.github.dcevm.test.util.HotSwapTestHelper.__toVersion__;
import static com.github.dcevm.test.util.HotSwapTestHelper.__version__;
import static org.junit.Assert.assertEquals;

/**
 * Test case for addition fields to java/lang/Object
 */
public class ModifyObjectClassInstanceTest {

    // Version 0
    public static class Helper {
        public static String access(Object o) {
            return "";
        }
    }

    // Version 1

    public static class Helper___1 {
        public static String access(Object o) {
            return ((A___1) o).myTestFunction___();
        }
    }

    @ClassRedefinitionPolicy(alias = java.lang.Object.class)
    public static class A___1 {

        @IntrinsicCandidate
        public A___1() {}

        @IntrinsicCandidate
        public final native Class<? extends Object> getClass___();

        @IntrinsicCandidate
        public native int hashCode();

        public boolean equals(Object obj) {
            return (this == obj);
        }

        public static int x;
        public static int x1;
        public static int x2;
        public static int x3;
        public static int x4;
        public static int x5;

        @IntrinsicCandidate
        protected native Object clone() throws CloneNotSupportedException;

        public String toString() {
            System.out.println("x=" + (x++));
            return getClass().getName() + "@" + Integer.toHexString(hashCode());// myTestFunction___();
        }

        public final String myTestFunction___() {
            return "test";
        }

        @IntrinsicCandidate
        public final native void notify___();

        @IntrinsicCandidate
        public final native void notifyAll___();

        public final native void wait___(long timeout) throws InterruptedException;

        public final void wait___(long timeout, int nanos) throws InterruptedException {


            if (timeout < 0) {
                throw new IllegalArgumentException("timeout value is negative");
            }

            if (nanos < 0 || nanos > 999999) {
                throw new IllegalArgumentException(
                        "nanosecond timeout value out of range");
            }

            if (nanos >= 500000 || (nanos != 0 && timeout == 0)) {
                timeout++;
            }

            wait(timeout);
        }

        public final void wait___() throws InterruptedException {
            wait(0);
        }

        protected void finalize() throws Throwable {
        }
    }

    @Before
    public void setUp() throws Exception {
        __toVersion__(0);
    }

    @Test
    public void testRedefineObject() {

        assert __version__() == 0;

        Object o = new Object();
        __toVersion__(1);

        System.out.println(this.toString());
        System.out.println(o.toString());
        System.out.println(this.toString());

        //assertEquals("test", o.toString());
        assertEquals("test", Helper.access(o));
        __toVersion__(0);
        __toVersion__(1);
        __toVersion__(0);
        __toVersion__(1);
        __toVersion__(0);
    }
}
