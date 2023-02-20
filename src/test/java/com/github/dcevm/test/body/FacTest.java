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

package com.github.dcevm.test.body;

import org.junit.Before;
import org.junit.Test;

import static com.github.dcevm.test.util.HotSwapTestHelper.__toVersion__;
import static com.github.dcevm.test.util.HotSwapTestHelper.__version__;
import static org.junit.Assert.assertEquals;

/**
 * Recursive implementation of the factorial function using class redefinition.
 *
 * @author Thomas Wuerthinger
 */
public class FacTest {

  public static abstract class Base {

    protected int calc() {
      return calc(__version__());
    }

    public int calcAt(int version) {
      __toVersion__(version);
      int result = calc();
      __toVersion__(0);
      return result;
    }

    protected int calc(int version) {
      return calc();
    }
  }

  public static class Factorial extends Base {

    @Override
    protected int calc(int n) {
      return n * calcAt(n - 1);
    }
  }

  public static class Factorial___1 extends Base {

    @Override
    protected int calc() {
      return 1;
    }
  }

  @Before
  public void setUp() throws Exception {
    __toVersion__(0);
  }

  @Test
  public void testFac() {

    assert __version__() == 0;
    Factorial f = new Factorial();

    assertEquals(1, f.calcAt(1));

    assert __version__() == 0;
    assertEquals(2, f.calcAt(2));

    assert __version__() == 0;
    assertEquals(6, f.calcAt(3));

    assert __version__() == 0;
    assertEquals(24, f.calcAt(4));

    assert __version__() == 0;
    assertEquals(120, f.calcAt(5));

    assert __version__() == 0;
    assertEquals(479001600, f.calcAt(12));
  }
}
