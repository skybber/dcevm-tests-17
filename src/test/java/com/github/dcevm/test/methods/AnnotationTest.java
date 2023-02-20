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
package com.github.dcevm.test.methods;

import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static com.github.dcevm.test.util.HotSwapTestHelper.__toVersion__;
import static com.github.dcevm.test.util.HotSwapTestHelper.__version__;
import static org.junit.Assert.assertEquals;

/**
 * Tests for adding / removing annotations on methods, fields and classes.
 *
 * @author Thomas Wuerthinger
 * @author Jiri Bubnik
 */
public class AnnotationTest {

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
  public @interface TestAnnotation {
  }

  @Retention(RetentionPolicy.RUNTIME)
  @Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
  public @interface TestAnnotation2 {
  }


  // Version 0
  public static class A {
    public int testField;

    public void testMethod() {
    }
  }

  // Version 1
  @TestAnnotation
  public static class A___1 {
    @TestAnnotation
    public int testField;

    @TestAnnotation
    public void testMethod() {
    }
  }

  // Version 2
  @TestAnnotation2
  public static class A___2 {
    @TestAnnotation2
    public int testField;

    @TestAnnotation2
    public void testMethod() {
    }
  }

  @Before
  public void setUp() throws Exception {
    __toVersion__(0);
  }

  private void checkAnnotation(Class<?> c, Class<? extends Annotation> expectedAnnotation) throws NoSuchMethodException, NoSuchFieldException {
    Class<? extends Annotation> annotation = c.getAnnotation(expectedAnnotation).annotationType();
    assertEquals(expectedAnnotation, annotation);
    Method m = c.getMethod("testMethod");
    annotation = m.getAnnotation(expectedAnnotation).annotationType();
    assertEquals(expectedAnnotation, annotation);
    Field f = c.getField("testField");
    annotation = f.getAnnotation(expectedAnnotation).annotationType();
    assertEquals(expectedAnnotation, annotation);
  }

  private void checkAnnotationMissing(Class<A> c) throws NoSuchMethodException, NoSuchFieldException {
    assertEquals(0, c.getAnnotations().length);
    assertEquals(0, c.getField("testField").getAnnotations().length);
    assertEquals(0, c.getMethod("testMethod").getAnnotations().length);
  }

  @Test
  public void testAddMethodToKlassWithEMCPExceptionMethod() throws NoSuchMethodException, NoSuchFieldException {

    assert __version__() == 0;
    checkAnnotationMissing(A.class);
    __toVersion__(1);
    checkAnnotation(A.class, TestAnnotation.class);
    __toVersion__(2);
    checkAnnotation(A.class, TestAnnotation2.class);
    __toVersion__(0);
    checkAnnotationMissing(A.class);
  }

}