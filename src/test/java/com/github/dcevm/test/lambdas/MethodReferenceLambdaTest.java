/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 *
 */

package com.github.dcevm.test.lambdas;

import static com.github.dcevm.test.util.HotSwapTestHelper.__toVersion__;
import static com.github.dcevm.test.util.HotSwapTestHelper.__version__;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.Callable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests for lambda expressions
 *
 * @author Ivan Dubrov
 */
public class MethodReferenceLambdaTest {

  @Before
  @After
  public void setUp() throws Exception {
    __toVersion__(0);
  }

  // Version 0
  public static class A {
    private int fieldA = 10;

    public static Callable<Integer> createLambda() {
      return A::value;
    }

    public Callable<Integer> createLambda2() {
      return this::fieldValue;
    }

    public static int value() {
      return 1;
    }

    public int fieldValue() {
      return -1;
    }
  }

  // Version 1
  public static class A___1 {
    private int fieldA;

    public static Callable<Integer> createLambda() {
      return A::value;
    }

    public Callable<Integer> createLambda2() {
      return this::fieldValue;
    }

    public static int value() {
      return 2;
    }

    public int fieldValue() {
      return fieldA;
    }
  }

  @Test
  public void testMethodLambda() throws Exception {
    assert __version__() == 0;

    Callable<Integer> lambda = A.createLambda();
    Callable<Integer> lambda2 = new A().createLambda2();

    assertEquals(1, (int) lambda.call());
    assertEquals(-1, (int) lambda2.call());

    __toVersion__(1);

    assertEquals(2, (int) lambda.call());
    assertEquals(10, (int) lambda2.call());
  }

  public static class A1 {
    public Callable<Integer> createLambda() {
      return this::value;
    }

    public int value() {
      return 0;
    }
  }

  public static class A1___1 {
    public Callable<Integer> createLambda() {
      return null;
    }
  }

  @Test
  public void testDeletedMethod() throws Exception {
    assert __version__() == 0;

    Callable<Integer> lambda = (new A1()).createLambda();
    assertEquals(0, (int) lambda.call());
    __toVersion__(1);

    try {
      lambda.call();
      fail("Should get NoSuchMethodError as method implementing lambda should be gone in version 1");
    } catch (NoSuchMethodError e) {
      // Ok!
    }
  }

}