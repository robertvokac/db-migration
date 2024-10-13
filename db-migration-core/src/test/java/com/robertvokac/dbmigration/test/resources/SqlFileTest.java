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

package com.robertvokac.dbmigration.core.resources;

import com.robertvokac.dbmigration.core.main.DBMigrationException;
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
public class SqlFileTest {
    
    public SqlFileTest() {
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
    /**
     * Test of calculateHash method, of class SqlFile.
     */
    @Test
    public void testConstructor_String_String() {
        System.out.println("testConstructor_String_String");
        //prepare
        SqlFile instance;
        //execute
        instance = new SqlFile("V002_001__First_Changes.sql","select 1;");
        //assert
        assertEquals("V002_001__First_Changes.sql", instance.getFileName());
        assertEquals("select 1;", instance.getContent());
    }

    /**
     * Test of calculateHash method, of class SqlFile.
     */
    @Test
    public void testCalculateHash() {
        System.out.println("testCalculateHash");
        //prepare
        SqlFile instance = new SqlFile("V002_001__First_Changes.sql","select 1;");
        String expResult = "4f619e84816024661eb6dd1247713f342da23364";
        //execute
        String result = instance.calculateHash();
        //assert
        assertEquals(expResult, result);
    }

//    /**
//     * Test of compareTo method, of class SqlFile.
//     */
//    @Test
//    public void testCompareTo() {
//        System.out.println("compareTo");
//        SqlFile o = null;
//        SqlFile instance = null;
//        int expResult = 0;
//        int result = instance.compareTo(o);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
