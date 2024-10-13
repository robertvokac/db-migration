///////////////////////////////////////////////////////////////////////////////////////////////
// db-migration: A database schema versioning tool.
// Copyright (C) 2021-2022 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation;
// version 2.1 of the License only.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

package com.robertvokac.dbmigration.core.utils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import lombok.experimental.Helper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author <a href="mailto:robertvokac@robertvokac.com">Robert Vokac</a>
 * @since 0.1.0
 */
public class DBMigrationUtilsTest {
    
    public DBMigrationUtilsTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    @Test
    public void DBMigrationUtils_constructor_noArgs() throws SecurityException, NoSuchMethodException {
        //prepare
        Constructor constructor = DBMigrationUtils.class.getDeclaredConstructor();
        //execute
        //assert
        assertTrue(Modifier.isPrivate(constructor.getModifiers()), "Constructor is not private");
        constructor.setAccessible(true);
        Throwable exception = assertThrows(java.lang.reflect.InvocationTargetException.class, () -> constructor.newInstance());
    }
//    /**
//     * Test of writeToFile method, of class DBMigrationUtils.
//     */
//    @Test
//    public void testWriteToFile() {
//        System.out.println("writeToFile");
//        String path = "";
//        String text = "";
//        DBMigrationUtils.writeToFile(path, text);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of readTextFromFile method, of class DBMigrationUtils.
//     */
//    @Test
//    public void testReadTextFromFile() {
//        System.out.println("readTextFromFile");
//        String path = "";
//        String expResult = "";
//        String result = DBMigrationUtils.readTextFromFile(path);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
