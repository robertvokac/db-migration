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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.robertvokac.dbmigration.core.main.DBMigrationException;
import com.robertvokac.dbmigration.core.entity.DBMigrationSchemaHistory;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author <a href="mailto:robertvokac@robertvokac.com">Robert Vokac</a>
 * @since 0.1.0
 */
public class SqlFileNameTest {

    public SqlFileNameTest() {
    }

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }

    //V2_1__First_Changes.sql
    /**
     * Test of SqlFileName constructor, of class SqlFileName.
     */
    @org.junit.jupiter.api.Test
    public void testConstructor_2args_OK() {
        System.out.println("testConstructor_2args_OK");
        SqlFileName sqlFileName = new SqlFileName("V002_001__First_Changes.sql");
        String expFileName = "V002_001__First_Changes.sql";
        String expVersion = "2.1";
        String expDescription = "First Changes";
        assertEquals(expFileName, sqlFileName.getFileName());
        assertEquals(expVersion, sqlFileName.getVersion());
        assertEquals(expDescription, sqlFileName.getDescription());
    }

    /**
     * Test of SqlFileName constructor, of class SqlFileName.
     */
    @org.junit.jupiter.api.Test
    public void testConstructor_2args_OK2() {
        System.out.println("testConstructor_2args_OK2");
        SqlFileName sqlFileName = new SqlFileName("V2_001__First_Changes.sql");
        String expFileName = "V2_001__First_Changes.sql";
        String expVersion = "2.1";
        String expDescription = "First Changes";
        assertEquals(expFileName, sqlFileName.getFileName());
        assertEquals(expVersion, sqlFileName.getVersion());
        assertEquals(expDescription, sqlFileName.getDescription());
    }

    /**
     * Test of SqlFileName constructor, of class SqlFileName.
     */
    @org.junit.jupiter.api.Test
    public void testConstructor_2args_OK3() {
        System.out.println("testConstructor_2args_OK3");
        SqlFileName sqlFileName = new SqlFileName("V002_1__First_Changes.sql");
        String expFileName = "V002_1__First_Changes.sql";
        String expVersion = "2.1";
        String expDescription = "First Changes";
        assertEquals(expFileName, sqlFileName.getFileName());
        assertEquals(expVersion, sqlFileName.getVersion());
        assertEquals(expDescription, sqlFileName.getDescription());
    }

    /**
     * Test of SqlFileName constructor, of class SqlFileName.
     */
    @org.junit.jupiter.api.Test
    public void testConstructor_2args_OK4() {
        System.out.println("testConstructor_2args_OK4");
        SqlFileName sqlFileName = new SqlFileName("V2_1__First_Changes.sql");
        String expFileName = "V2_1__First_Changes.sql";
        String expVersion = "2.1";
        String expDescription = "First Changes";
        assertEquals(expFileName, sqlFileName.getFileName());
        assertEquals(expVersion, sqlFileName.getVersion());
        assertEquals(expDescription, sqlFileName.getDescription());
    }

    /**
     * Test of SqlFileName constructor, of class SqlFileName.
     */
    @org.junit.jupiter.api.Test
    public void testConstructor_2args_OK5() {
        System.out.println("testConstructor_2args_OK5");
        SqlFileName sqlFileName = new SqlFileName("V2_1_000003_5__First_Changes.sql");
        String expFileName = "V2_1_000003_5__First_Changes.sql";
        String expVersion = "2.1.3.5";
        String expDescription = "First Changes";
        assertEquals(expFileName, sqlFileName.getFileName());
        assertEquals(expVersion, sqlFileName.getVersion());
        assertEquals(expDescription, sqlFileName.getDescription());
    }

    /**
     * Test of SqlFileName constructor, of class SqlFileName.
     */
    @org.junit.jupiter.api.Test
    public void testConstructor_2args_OK6() {
        System.out.println("testConstructor_2args_OK6");
        SqlFileName sqlFileName = new SqlFileName("V2_1_3_5__First_Changes.sql");
        String expFileName = "V2_1_3_5__First_Changes.sql";
        String expVersion = "2.1.3.5";
        String expDescription = "First Changes";
        assertEquals(expFileName, sqlFileName.getFileName());
        assertEquals(expVersion, sqlFileName.getVersion());
        assertEquals(expDescription, sqlFileName.getDescription());
    }

    /**
     * Test of SqlFileName constructor, of class SqlFileName.
     */
    @org.junit.jupiter.api.Test
    public void testConstructor_2args_OK7() {
        System.out.println("testConstructor_2args_OK7");
        SqlFileName sqlFileName = new SqlFileName("V4__First_Changes.sql");
        String expFileName = "V4__First_Changes.sql";
        String expVersion = "4";
        String expDescription = "First Changes";
        assertEquals(expFileName, sqlFileName.getFileName());
        assertEquals(expVersion, sqlFileName.getVersion());
        assertEquals(expDescription, sqlFileName.getDescription());
    }

    /**
     * Test of SqlFileName constructor, of class SqlFileName.
     */
    @org.junit.jupiter.api.Test
    public void testConstructor_2args_OK8() {
        System.out.println("testConstructor_2args_OK8");
        SqlFileName sqlFileName = new SqlFileName("V4_0__First_Changes.sql");
        String expFileName = "V4_0__First_Changes.sql";
        String expVersion = "4.0";
        String expDescription = "First Changes";
        assertEquals(expFileName, sqlFileName.getFileName());
        assertEquals(expVersion, sqlFileName.getVersion());
        assertEquals(expDescription, sqlFileName.getDescription());
    }

    /**
     * Test of SqlFileName constructor, of class SqlFileName.
     */
    @org.junit.jupiter.api.Test
    public void testConstructor_2args_OK9() {
        System.out.println("testConstructor_2args_OK9");
        SqlFileName sqlFileName = new SqlFileName("V4_000__First_Changes.sql");
        String expFileName = "V4_000__First_Changes.sql";
        String expVersion = "4.0";
        String expDescription = "First Changes";
        assertEquals(expFileName, sqlFileName.getFileName());
        assertEquals(expVersion, sqlFileName.getVersion());
        assertEquals(expDescription, sqlFileName.getDescription());
    }

    /**
     * Test of SqlFileName constructor, of class SqlFileName.
     */
    @org.junit.jupiter.api.Test
    public void testConstructor_2args_KO() {
        System.out.println("testConstructor_2args_KO");

        Throwable exception = assertThrows(DBMigrationException.class, () -> new SqlFileName("B4_000__First_Changes.sql"));
        assertEquals("File B4_000__First_Changes.sql does not start with \"V\".", exception.getMessage());

    }

    /**
     * Test of SqlFileName constructor, of class SqlFileName.
     */
    @org.junit.jupiter.api.Test
    public void testConstructor_2args_KO2() {
        System.out.println("testConstructor_2args_KO2");

        Throwable exception = assertThrows(DBMigrationException.class, () -> new SqlFileName("V4_000_First_Changes.sql"));
        assertEquals("File V4_000_First_Changes.sql does not contain \"__\".", exception.getMessage());

    }
    
    /**
     * Test of calculateHash method, of class SqlFile.
     */
    @Test
    public void testConstructor_2args_KO3() {
        System.out.println("testConstructor_2args_KO3");
        //prepare
        String fileName = "V002_001__First_Changes.txt";
        //execute
        //assert
        Throwable exception = assertThrows(DBMigrationException.class, () -> new SqlFileName(fileName));
        assertEquals("File V002_001__First_Changes.txt does not end with \".sql\".", exception.getMessage());
    }
    /**
     * Test of compareTo method, of class SqlFile.
     */
    @org.junit.jupiter.api.Test
    public void testCompareTo() {
        System.out.println("compareTo");
        //prepare
        SqlFile sf1 = new SqlFile("V4_0__First_Changes.sql", "select 1;");
        SqlFile sf2 = new SqlFile("V4_0_1__First_Changes2.sql", "select 1;");
        SqlFile sf3 = new SqlFile("V4_0_2__First_Changes3.sql", "select 1;");
        SqlFile sf4 = new SqlFile("V4_1__First_Changes4.sql", "select 1;");
        SqlFile sf5 = new SqlFile("V4_1_2__First_Changes5.sql", "select 1;");
        SqlFile sf6 = new SqlFile("V4_2_3__First_Changes6.sql", "select 1;");
        SqlFile sf7 = new SqlFile("V4_2_4__First_Changes7.sql", "select 1;");
        SqlFile sf8 = new SqlFile("V5_1_4__First_Changes8.sql", "select 1;");
        SqlFile sf9 = new SqlFile("V5_1_5__First_Changes9.sql", "select 1;");
        SqlFile sf10 = new SqlFile("V9_9_9_0__First_Changes10.sql", "select 1;");
        SqlFile sf11 = new SqlFile("V9_9_9_1__First_Changes11.sql", "select 1;");
        SqlFile sf12 = new SqlFile("V9_9_9_9__First_Changes12.sql", "select 1;");
        SqlFile sf13 = new SqlFile("V9_9_9_9_1__First_Changes13.sql", "select 1;");
        SqlFile sf14 = new SqlFile("V9_9_9_9_4__First_Changes14.sql", "select 1;");
        List<SqlFile> sqlFilesOrig = new ArrayList<>();
        sqlFilesOrig.add(sf1);
        sqlFilesOrig.add(sf2);
        sqlFilesOrig.add(sf3);
        sqlFilesOrig.add(sf4);
        sqlFilesOrig.add(sf5);
        sqlFilesOrig.add(sf6);
        sqlFilesOrig.add(sf7);
        sqlFilesOrig.add(sf8);
        sqlFilesOrig.add(sf9);
        sqlFilesOrig.add(sf10);
        sqlFilesOrig.add(sf11);
        sqlFilesOrig.add(sf12);
        sqlFilesOrig.add(sf13);
        sqlFilesOrig.add(sf14);
        List<SqlFile> sqlFiles = new ArrayList<>();
        sqlFiles.add(sf1);
        sqlFiles.add(sf2);
        sqlFiles.add(sf3);
        sqlFiles.add(sf4);
        sqlFiles.add(sf5);
        sqlFiles.add(sf6);
        sqlFiles.add(sf7);
        sqlFiles.add(sf8);
        sqlFiles.add(sf9);
        sqlFiles.add(sf10);
        sqlFiles.add(sf11);
        sqlFiles.add(sf12);
        sqlFiles.add(sf13);
        sqlFiles.add(sf14);
        Collections.shuffle(sqlFiles);
        StringBuilder sb = new StringBuilder();
        for(SqlFile sf:sqlFilesOrig) {
            sb.append(sf.getFileName());
            sb.append("####");
        }
        String expResult = sb.toString();
        //execute
        Collections.sort(sqlFiles);
        StringBuilder sb2 = new StringBuilder();
        for(SqlFile sf:sqlFiles) {
            sb2.append(sf.getFileName());
            sb2.append("####");
        }
        String returnedResult = sb2.toString();
        //assert
        assertEquals(expResult, returnedResult);
    }
    /**
     * Test of compareTo method, of class SqlFile.
     */
    @org.junit.jupiter.api.Test
    public void testCompareTo2() {
        System.out.println("testCompareTo2");
        //prepare
        SqlFileName sf1 = new SqlFileName("V4_0_1__First_Changes.sql");
        SqlFileName sf2 = new SqlFileName("V4_0_1__First_Changes2.sql");
        //execute
        Throwable exception = assertThrows(DBMigrationException.class, () -> sf1.compareTo(sf2));
        System.out.println("e=" + exception.getMessage());
        assertEquals(true , exception.getMessage().startsWith("Cannot compare instances of SqlFileName with same version and different file names:"));
        //assert
    }
    
    /**
     * Test of compareTo method, of class SqlFile.
     */
    @org.junit.jupiter.api.Test
    public void testCompareTo3() {
        System.out.println("testCompareTo3");
        //prepare
        SqlFileName sf1 = new SqlFileName("V4_0_1__First_Changes.sql");
        SqlFileName sf2 = new SqlFileName("V4_0_1__First_Changes.sql");
        //execute
        int result = sf1.compareTo(sf2);
        final int expectedResult = 0;
        //assert
        assertEquals(expectedResult, result);
    }
    
            /**
     * Test of compareTo method, of class SqlFile.
     */
    @org.junit.jupiter.api.Test
    public void testCompareTo4() {
        System.out.println("compareTo");
        //prepare
        String data = """
                      V000001__create_table_test1.sql
                      V000001_1__add_column_column1.sql
                      V000010__add_column_column1.sql
                      V000011__add_column_column1.sql
                      V000012__add_column_column1.sql
                      V000002_0__add_column_column1.sql
                      V000002_1__add_column_column1.sql
                      V000002_2__add_column_column1.sql
                      V000002_3__add_column_column1.sql
                      V000002_4__add_column_column1.sql
                      V000002_5__add_column_column1.sql
                      V000009__add_column_column1.sql
                      """;
        List<SqlFileName> sqlFileNames = new ArrayList<>();
        for(String s:data.split("\\r?\\n")) {
            SqlFileName sfn=new SqlFileName(s);
            sqlFileNames.add(sfn);
        }

        String expResult = """
                      V000001__create_table_test1.sql
                      V000001_1__add_column_column1.sql
                      V000002_0__add_column_column1.sql
                      V000002_1__add_column_column1.sql
                      V000002_2__add_column_column1.sql
                      V000002_3__add_column_column1.sql
                      V000002_4__add_column_column1.sql
                      V000002_5__add_column_column1.sql
                      V000009__add_column_column1.sql
                      V000010__add_column_column1.sql
                      V000011__add_column_column1.sql
                      V000012__add_column_column1.sql                      
                      """;
        //execute
        Collections.sort(sqlFileNames);
        StringBuilder sb = new StringBuilder();
        for(SqlFileName sfn:sqlFileNames) {
            sb.append(sfn.getFileName());
            sb.append("\n");
        }
        String returnedResult = sb.toString();
        //assert
        assertEquals(expResult, returnedResult);
    }
    
                /**
     * Test of compareTo method, of class SqlFile.
     */
    @org.junit.jupiter.api.Test
    public void testCompareTo5() {
        System.out.println("compareTo");
        //prepare
        SqlFileName sfn1 = new SqlFileName("V000010__add_column_column1.sql");
        SqlFileName sfn2 = new SqlFileName("V000002_0__add_column_column1.sql");
        int expResult = 1;
        //execute
        int returnedResult = sfn1.compareTo(sfn2);
        
        //assert
        assertEquals(expResult, returnedResult);
    }
}
