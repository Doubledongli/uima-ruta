/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.uima.ruta.action;

import org.apache.uima.cas.CAS;
import org.apache.uima.ruta.engine.Ruta;
import org.apache.uima.ruta.engine.RutaTestUtils;
import org.junit.Test;

public class MacroActionTest {


  @Test
  public void test() {
    String document = "Test";
    String script = "INT j;\n";
    script += "ACTION macro(TYPE t, INT inc) = MARK(t),ASSIGN(j,j+inc);\n";
    script += "Document{-> macro(T1,1)};\n";
    script += "Document{(j>0)->T2};\n";


    CAS cas = null;
    try {
      cas = RutaTestUtils.getCAS(document);
      Ruta.apply(cas, script);
    } catch (Exception e) {
      e.printStackTrace();
    }

    RutaTestUtils.assertAnnotationsEquals(cas, 1, 1, "Test");
    RutaTestUtils.assertAnnotationsEquals(cas, 2, 1, "Test");

    cas.release();
  }
  
  @Test
  public void testNoArgs() {
    String document = "Test.";
    String script = "INT j;\n";
    script += "ACTION macro() = MARK(T1), MARK(T2);\n";
    script += "W{-> macro()};\n";

    CAS cas = null;
    try {
      cas = RutaTestUtils.getCAS(document);
      Ruta.apply(cas, script);
    } catch (Exception e) {
      e.printStackTrace();
    }

    RutaTestUtils.assertAnnotationsEquals(cas, 1, 1, "Test");
    RutaTestUtils.assertAnnotationsEquals(cas, 2, 1, "Test");

    cas.release();
  }
}