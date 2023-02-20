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
import org.junit.Before;
import org.junit.Test;

import static com.github.dcevm.test.util.HotSwapTestHelper.__toVersion__;
import static com.github.dcevm.test.util.HotSwapTestHelper.__version__;
import static org.junit.Assert.assertEquals;


/**
 * Test that changes the order of two int fields.
 *
 * @author Vladimir Dvorak
 */
public class RecordTest {

    // Version 0
    public static record A(int value1, int value2) {
    }

    public static record A__1(int value2, int value1) {
    }

  @Before
  public void setUp() throws Exception {
    __toVersion__(0);
  }

  @Test
  public void testRenameField() {
    assert __version__() == 0;
    A a = new A(1, 2);
//    assertObjectOK(a);
//    __toVersion__(1);
//    assertObjectOK(a);
//    __toVersion__(1);
//    assertObjectOK(a);
//    __toVersion__(0);
//    assertObjectOK(a);
  }

  /**
   * Checks that the given object is unmodified (i.e. the values of the fields are correct)
   *
   * @param a the object to be checked
   */
  private void assertObjectOK(A a) {
    assertEquals(1, a.value1());
    assertEquals(2, a.value2());
  }

}
