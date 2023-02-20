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
package com.github.dcevm.test.fields;

import static com.github.dcevm.test.util.HotSwapTestHelper.__toVersion__;
import static com.github.dcevm.test.util.HotSwapTestHelper.__version__;
import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;

import org.junit.Before;
import org.junit.Test;

/**
 * Test for replacing field with FieldHandle pointing to it.
 * <p>
 *
 * @author Vladimir Dvorak
 */
public class VarHandleTest {

  // Version 0
  public static class A {
    public int field;
    public void setField() {
      field = 1;
    }
  }

  // Version 1
  public static class A___1 {
    public String field;
    public void setField() {
      field = "hello";
    }
  }

  @Before
  public void setUp() throws Exception {
    __toVersion__(0);
  }

  @Test
  public void testMethodHandleUpdated() throws Throwable {

    assert __version__() == 0;

    VarHandle varHandle = MethodHandles.lookup().findVarHandle(A.class, "field", int.class);

    A a = new A();
    a.setField();

    assertEquals(1, (int) varHandle.get(a));

    __toVersion__(1);

    a.setField();

    assertEquals("hello", (String) varHandle.get(a));
  }

}
