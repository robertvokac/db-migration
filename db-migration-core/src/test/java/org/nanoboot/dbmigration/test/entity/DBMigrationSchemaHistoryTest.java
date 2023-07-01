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

package org.nanoboot.dbmigration.core.entity;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManagerFactory;
import org.nanoboot.dbmigration.core.main.DBMigrationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author <a href="mailto:robertvokac@nanoboot.org">Robert Vokac</a>
 * @since 0.1.0
 */
public class DBMigrationSchemaHistoryTest {

    public DBMigrationSchemaHistoryTest() {
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
     * Test of calculateHash method, of class DBMigrationSchemaHistory.
     */
    @Test
    public void testCalculateHash() {
        System.out.println("calculateHash");
        //prepare
        String hashOfPreviousMigration = "4233137d1c510f2e55ba5cb220b864b11033f156";
        String hashOfScriptContentOfThisMigration = "dad372493dc2eeb685aac7fc48a27b6bed1b3af1";
        String expResult = "496cfd882d65c83c54a81065991096ae848bee68";
        //execute
        String result = DBMigrationSchemaHistory.calculateHash(hashOfPreviousMigration, hashOfScriptContentOfThisMigration);
        //assert
        assertEquals(expResult, result);
    }


    /**
     * Test of compareTo method, of class DBMigrationSchemaHistory.
     */
    @Test
    public void testCompareTo() {
        System.out.println("compareTo");
        //prepare
        DBMigrationSchemaHistory h1 = new DBMigrationSchemaHistory();
        DBMigrationSchemaHistory h2 = new DBMigrationSchemaHistory();
        DBMigrationSchemaHistory h3 = new DBMigrationSchemaHistory();
        DBMigrationSchemaHistory h4 = new DBMigrationSchemaHistory();
        h1.setInstalledRank(1);
        h2.setInstalledRank(2);
        h3.setInstalledRank(3);
        h4.setInstalledRank(4);
        //
        h1.setMigrationGroup("group1");
        h2.setMigrationGroup("group1");
        h3.setMigrationGroup("group1");
        h4.setMigrationGroup("group1");
        //
        int expectedResult_h1_h2 = -1;
        int expectedResult_h1_h3 = -1;
        int expectedResult_h1_h4 = -1;
        int expectedResult_h2_h1 = 1;
        int expectedResult_h2_h3 = -1;
        int expectedResult_h2_h4 = -1;
        int expectedResult_h3_h1 = 1;
        int expectedResult_h3_h2 = 1;
        int expectedResult_h3_h4 = -1;
        int expectedResult_h4_h1 = 1;
        int expectedResult_h4_h2 = 1;
        int expectedResult_h4_h3 = 1;
        //execute
        int result_h1_h2 = h1.compareTo(h2);
        int result_h1_h3 = h1.compareTo(h3);
        int result_h1_h4 = h1.compareTo(h4);
        int result_h2_h1 = h2.compareTo(h1);
        int result_h2_h3 = h2.compareTo(h3);
        int result_h2_h4 = h2.compareTo(h4);
        int result_h3_h1 = h3.compareTo(h1);
        int result_h3_h2 = h3.compareTo(h2);
        int result_h3_h4 = h3.compareTo(h4);
        int result_h4_h1 = h4.compareTo(h1);
        int result_h4_h2 = h4.compareTo(h2);
        int result_h4_h3 = h4.compareTo(h3);
        //assert
        assertEquals(expectedResult_h1_h2, result_h1_h2);
        assertEquals(expectedResult_h1_h3, result_h1_h3);
        assertEquals(expectedResult_h1_h4, result_h1_h4);
        assertEquals(expectedResult_h2_h1, result_h2_h1);
        assertEquals(expectedResult_h2_h3, result_h2_h3);
        assertEquals(expectedResult_h2_h4, result_h2_h4);
        assertEquals(expectedResult_h3_h1, result_h3_h1);
        assertEquals(expectedResult_h3_h2, result_h3_h2);
        assertEquals(expectedResult_h3_h4, result_h3_h4);
        assertEquals(expectedResult_h4_h1, result_h4_h1);
        assertEquals(expectedResult_h4_h2, result_h4_h2);
        assertEquals(expectedResult_h4_h3, result_h4_h3);
    }

    /**
     * Test of compareTo method, of class DBMigrationSchemaHistory.
     */
    @Test
    public void testCompareTo2() {
        System.out.println("compareTo2");
        //prepare
        DBMigrationSchemaHistory h1 = new DBMigrationSchemaHistory();
        DBMigrationSchemaHistory h2 = new DBMigrationSchemaHistory();
        h1.setInstalledRank(1);
        h2.setInstalledRank(2);
        //
        h1.setMigrationGroup("group1");
        h2.setMigrationGroup("group2");
        //execute
        Throwable exception = assertThrows(DBMigrationException.class, () -> h1.compareTo(h2));
        assertEquals("Cannot compare, if migration groups are different: this.getMigrationGroup()=group1, o.getMigrationGroup()=group2", exception.getMessage());
        //assert
    }
}
