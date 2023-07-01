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

package org.nanoboot.dbmigration.core.configuration;

import java.util.ArrayList;
import java.util.List;
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
public class ConfigurationTest {

    public ConfigurationTest() {
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
     * Test of add method, of class Configuration.
     */
    @Test
    public void testAdd_ConfigurationEntry() {
        System.out.println("testAdd_ConfigurationEntry");
        //prepare
        ConfigurationEntry ce = new ConfigurationEntry("dbmigration.a", "p");
        Configuration conf = new Configuration();
        //execute
        conf.add(ce);
        //assert
        assertTrue(conf.has("dbmigration.a"));
        assertEquals("p", conf.get("dbmigration.a"));
    }

    /**
     * Test of add method, of class Configuration.
     */
    @Test
    public void testAdd_ConfigurationEntry2() {
        System.out.println("testAdd_ConfigurationEntry2");
        //prepare
        ConfigurationEntry ce = new ConfigurationEntry("a", "p");
        Configuration conf = new Configuration();
        //execute
        Throwable exception = assertThrows(DBMigrationException.class, () -> conf.add(ce));
        assertEquals("Key does not start with prefix dbmigration.", exception.getMessage());
        //assert
    }

    /**
     * Test of add method, of class Configuration.
     */
    @Test
    public void testAdd_String_String() {
        System.out.println("testAdd_String_String");
        //prepare
        Configuration conf = new Configuration();
        //execute
        conf.add("dbmigration.a", "p");
        //assert
        assertTrue(conf.has("dbmigration.a"));
        assertEquals("p", conf.get("dbmigration.a"));
    }

    /**
     * Test of add method, of class Configuration.
     */
    @Test
    public void testAdd_String_String2() {
        System.out.println("testAdd_String_String2");
        //prepare
        Configuration conf = new Configuration();
        //execute
        Throwable exception = assertThrows(DBMigrationException.class, () -> conf.add("a", "p"));
        assertEquals("Key does not start with prefix dbmigration.", exception.getMessage());
        //
        conf.add("dbmigration.kkk", null);
        assertEquals(false, conf.has("dbmigration.kkk"));
        //assert
    }

    /**
     * Test of get method, of class Configuration.
     */
    @Test
    public void testGet() {
        System.out.println("testGet");
        //prepare
        Configuration conf = new Configuration();
        //execute
        conf.add("dbmigration.a", "p");
        //assert
        assertEquals("p", conf.get("dbmigration.a"));
        //
        Throwable exception = assertThrows(DBMigrationException.class, () -> conf.get("dbmigration.b"));
        assertEquals("There is no key: dbmigration.b", exception.getMessage());
    }

    /**
     * Test of getMandatory method, of class Configuration.
     */
    @Test
    public void testGetMandatory() {
        System.out.println("testGetMandatory");
        //prepare
        Configuration conf = new Configuration();
        //execute
        conf.add("dbmigration.a", "p");
        //assert
        assertEquals("p", conf.getMandatory("dbmigration.a"));
        //
        Throwable exception = assertThrows(DBMigrationException.class, () -> conf.getMandatory("dbmigration.b"));
        assertEquals("There is no key: dbmigration.b", exception.getMessage());
    }

    /**
     * Test of getOptional method, of class Configuration.
     */
    @Test
    public void testGetOptional() {
        System.out.println("testGetOptional");
        //prepare
        Configuration conf = new Configuration();
        //execute
        conf.add("dbmigration.a", "p");
        //assert
        assertEquals("p", conf.getOptional("dbmigration.a"));
        assertNull(conf.getOptional("dbmigration.z"));

    }

    /**
     * Test of has method, of class Configuration.
     */
    @Test
    public void testHas() {
        System.out.println("has");
        //prepare
        String key = "dbmigration.e";
        Configuration instance = new Configuration();
        //execute
        instance.add("dbmigration.e", "f");
        boolean expResult = true;
        boolean result = instance.has(key);
        assertEquals(expResult, result);
        Throwable exception = assertThrows(DBMigrationException.class, () -> instance.has("e"));
        assertEquals("Key does not start with prefix dbmigration.", exception.getMessage());
        assertEquals(false, instance.has("dbmigration.k"));
    }

    /**
     * Test of remove method, of class Configuration.
     */
    @Test
    public void testRemove() {
        System.out.println("testRemove");
        //prepare
        Configuration instance = new Configuration();
        //execute
        assertEquals(false, instance.has("dbmigration.e"));
        instance.add("dbmigration.e", "f");
        assertEquals(true, instance.has("dbmigration.e"));
        instance.remove("dbmigration.e");
        assertEquals(false, instance.has("dbmigration.e"));
    }

    /**
     * Test of listKeys method, of class Configuration.
     */
    @Test
    public void testListKeys() {
        System.out.println("testListKeys");
        //prepare
        Configuration instance = new Configuration();
        //execute
        instance.add("dbmigration.a", "b");
        instance.add("dbmigration.c", "d");
        instance.add("dbmigration.k", "l");
        instance.add("dbmigration.m", "n");
        List<String> result = instance.listKeys();
        //assert
        assertEquals(4, result.size());
        assertEquals(true, result.contains("dbmigration.a"));
        assertEquals(true, result.contains("dbmigration.c"));
        assertEquals(true, result.contains("dbmigration.k"));
        assertEquals(true, result.contains("dbmigration.m"));
        assertEquals(false, result.contains("dbmigration.o"));
    }

    /**
     * Test of list method, of class Configuration.
     */
    @Test
    public void testList() {
        System.out.println("testListKeys");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "a-b--c-d--k-l--m-n--";
        StringBuilder sb = new StringBuilder();
        //execute
        instance.add("dbmigration.a", "b");
        instance.add("dbmigration.c", "d");
        instance.add("dbmigration.k", "l");
        instance.add("dbmigration.m", "n");
        List<ConfigurationEntry> result = instance.list();
        List<String> list2 = new ArrayList<>();
        for (ConfigurationEntry ce : result) {
            list2.add(ce.getKey() + "_" + ce.getValue());
        }
        //assert
        assertEquals(4, result.size());
        assertEquals(true, list2.contains("dbmigration.a_b"));
        assertEquals(true, list2.contains("dbmigration.c_d"));
        assertEquals(true, list2.contains("dbmigration.k_l"));
        assertEquals(true, list2.contains("dbmigration.m_n"));
        assertEquals(false, list2.contains("dbmigration.o_p"));
    }

    /**
     * Test of getDatasourceJdbcUrl method, of class Configuration.
     */
    @Test
    public void testGetDatasourceJdbcUrl() {
        System.out.println("testGetDatasourceJdbcUrl");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.add(ConfigurationKeys.DATASOURCE_JDBC_URL, "test");
        //assert
        assertEquals("test", instance.getDatasourceJdbcUrl());
    }

    /**
     * Test of setDatasourceJdbcUrl method, of class Configuration.
     */
    @Test
    public void testSetDatasourceJdbcUrl() {
        System.out.println("testGetDatasourceJdbcUrl");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.setDatasourceJdbcUrl("test");
        //assert
        assertEquals(expectedResult, instance.get(ConfigurationKeys.DATASOURCE_JDBC_URL));
    }

    /**
     * Test of getDatasourceUser method, of class Configuration.
     */
    @Test
    public void testGetDatasourceUser() {
        System.out.println("testGetDatasourceUser");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.add(ConfigurationKeys.DATASOURCE_USER, "test");
        //assert
        assertEquals(expectedResult, instance.getDatasourceUser());
    }

    /**
     * Test of setDatasourceUuser method, of class Configuration.
     */
    @Test
    public void testSetDatasourceUser() {
        System.out.println("testGetDatasourceJdbcUrl");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.setDatasourceUser("test");
        //assert
        assertEquals(expectedResult, instance.get(ConfigurationKeys.DATASOURCE_USER));
    }

    /**
     * Test of getDatasourcePassword method, of class Configuration.
     */
    @Test
    public void testGetDatasourcePassword() {
        System.out.println("testGetDatasourcePassword");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.add(ConfigurationKeys.DATASOURCE_PASSWORD, "test");
        //assert
        assertEquals(expectedResult, instance.getDatasourcePassword());
    }

    /**
     * Test of setDatasourcePassword method, of class Configuration.
     */
    @Test
    public void testSetDatasourcePassword() {
        System.out.println("testSetDatasourcePassword");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.setDatasourcePassword("test");
        //assert
        assertEquals(expectedResult, instance.get(ConfigurationKeys.DATASOURCE_PASSWORD));
    }

    /**
     * Test of getName method, of class Configuration.
     */
    @Test
    public void testGetName() {
        System.out.println("testGetName");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.add(ConfigurationKeys.NAME, "test");
        //assert
        assertEquals(expectedResult, instance.getName());
    }

    /**
     * Test of setName method, of class Configuration.
     */
    @Test
    public void testSetName() {
        System.out.println("testSetName");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.setName("test");
        //assert
        assertEquals(expectedResult, instance.get(ConfigurationKeys.NAME));
    }

    /**
     * Test of getSqlDialect method, of class Configuration.
     */
    @Test
    public void testGetSqlDialect() {
        System.out.println("testGetSqlDialect");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.add(ConfigurationKeys.SQL_DIALECT, "test");
        //assert
        assertEquals(expectedResult, instance.getSqlDialect());
    }

    /**
     * Test of setSqlDialect method, of class Configuration.
     */
    @Test
    public void testSetSqlDialect() {
        System.out.println("testSetSqlDialect");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.setSqlDialect("test");
        //assert
        assertEquals(expectedResult, instance.get(ConfigurationKeys.SQL_DIALECT));
    }

    /**
     * Test of getSqlDialectImplClass method, of class Configuration.
     */
    @Test
    public void testGetSqlDialectImplClass() {
        System.out.println("testGetSqlDialectImplClass");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.add(ConfigurationKeys.SQL_DIALECT_IMPL_CLASS, "test");
        //assert
        assertEquals(expectedResult, instance.getSqlDialectImplClass());
    }

    /**
     * Test of setSqlDialectImplClass method, of class Configuration.
     */
    @Test
    public void testSetSqlDialectImplClass() {
        System.out.println("testSetSqlDialectImplClass");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.setSqlDialectImplClass("test");
        //assert
        assertEquals(expectedResult, instance.get(ConfigurationKeys.SQL_DIALECT_IMPL_CLASS));
    }

    /**
     * Test of getSqlMigrationsDirectory method, of class Configuration.
     */
    @Test
    public void testGetSqlMigrationsDirectory() {
        System.out.println("testGetSqlMigrationsDirectory");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.add(ConfigurationKeys.SQL_MIGRATIONS_DIRECTORY, "test");
        //assert
        assertEquals(expectedResult, instance.getSqlMigrationsDirectory());
    }

    /**
     * Test of setSqlMigrationsDirectory method, of class Configuration.
     */
    @Test
    public void testSetSqlMigrationsDirectory() {
        System.out.println("testSetSqlMigrationsDirectory");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.setSqlMigrationsDirectory("test");
        //assert
        assertEquals(expectedResult, instance.get(ConfigurationKeys.SQL_MIGRATIONS_DIRECTORY));
    }
    /**
     * Test of getSqlMigrationsDirectory method, of class Configuration.
     */
    @Test
    public void testGetSqlMigrationsClass() {
        System.out.println("testGetSqlMigrationsClass");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "com.johnny.asuperapp.ASuperAppMain";
        //execute
        instance.add(ConfigurationKeys.SQL_MIGRATIONS_CLASS, "com.johnny.asuperapp.ASuperAppMain");
        //assert
        assertEquals(expectedResult, instance.getSqlMigrationsClass());
    }

    /**
     * Test of setSqlMigrationsDirectory method, of class Configuration.
     */
    @Test
    public void testSetSqlMigrationsClass() {
        System.out.println("testSetSqlMigrationsClass");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "com.johnny.asuperapp.ASuperAppMain";
        //execute
        instance.setSqlMigrationsClass("com.johnny.asuperapp.ASuperAppMain");
        //assert
        assertEquals(expectedResult, instance.get(ConfigurationKeys.SQL_MIGRATIONS_CLASS));
    }
    /**
     * Test of getInstalledBy method, of class Configuration.
     */
    @Test
    public void testGetInstalledBy() {
        System.out.println("testGetInstalledBy");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.add(ConfigurationKeys.INSTALLED_BY, "test");
        //assert
        assertEquals(expectedResult, instance.getInstalledBy());
    }

    /**
     * Test of setInstalledBy method, of class Configuration.
     */
    @Test
    public void testSetInstalledBy() {
        System.out.println("testSetInstalledBy");
        //prepare
        Configuration instance = new Configuration();
        String expectedResult = "test";
        //execute
        instance.setInstalledBy("test");
        //assert
        assertEquals(expectedResult, instance.get(ConfigurationKeys.INSTALLED_BY));
    }

    /**
     * Test of validateKey method, of class Configuration.
     */
    @Test
    public void testValidateKey() {
        System.out.println("testValidateKey");
        //prepare
        Configuration instance = new Configuration();
        //execute
        Configuration.validateKey("dbmigration.hello");
        //assert 
    }

    /**
     * Test of validateKey method, of class Configuration.
     */
    @Test
    public void testValidateKey2() {
        System.out.println("testValidateKey2");
        //prepare
        //execute
        Throwable exception = assertThrows(DBMigrationException.class, () -> Configuration.validateKey("hello"));
        assertEquals("Key does not start with prefix dbmigration.", exception.getMessage());
        //assert 
    }

    /**
     * Test of startsKeyWithPrefix method, of class Configuration.
     */
    @Test
    public void testStartsKeyWithPrefix() {
        System.out.println("testStartsKeyWithPrefix");
        //prepare
        boolean expectedResult = true;
        //execute
        boolean result = Configuration.startsKeyWithPrefix("dbmigration.hello");
        //assert
        assertEquals(expectedResult, result);
    }
    /**
     * Test of startsKeyWithPrefix method, of class Configuration.
     */
    @Test
    public void testStartsKeyWithPrefix2() {
        System.out.println("testStartsKeyWithPrefix2");
        //prepare
        boolean expectedResult = false;
        //execute
        boolean result = Configuration.startsKeyWithPrefix("hello");
        //assert
        assertEquals(expectedResult, result);
    }

    /**
     * Test of prependPrefixIfNeeded method, of class Configuration.
     */
    @Test
    public void testPrependPrefixIfNeeded() {
        System.out.println("testPrependPrefixIfNeeded");
        //prepare
        String expectedResult = "dbmigration.hello";
        //execute
        String result = Configuration.prependPrefixIfNeeded("hello");
        //assert
        assertEquals(expectedResult, result);
    }

    /**
     * Test of prependPrefixIfNeeded method, of class Configuration.
     */
    @Test
    public void testPrependPrefixIfNeeded2() {
        System.out.println("testPrependPrefixIfNeeded2");
        //prepare
        String expectedResult = "dbmigration.hello";
        //execute
        String result = Configuration.prependPrefixIfNeeded("dbmigration.hello");
        //assert
        assertEquals(expectedResult, result);
    }

    /**
     * Test of addAndAddPrefixIfNeeded method, of class Configuration.
     */
    @Test
    public void testAddAndAddPrefixIfNeeded_ConfigurationEntry() {
        System.out.println("testAddAndAddPrefixIfNeeded_ConfigurationEntry");
        //prepare
        ConfigurationEntry ce = new ConfigurationEntry("a", "p");
        Configuration conf = new Configuration();
        //execute
        conf.addAndAddPrefixIfNeeded(ce);
        //assert
        assertTrue(conf.has("dbmigration.a"));
        assertEquals("p", conf.get("dbmigration.a"));
    }

    /**
     * Test of addAndAddPrefixIfNeeded method, of class Configuration.
     */
    @Test
    public void testAddAndAddPrefixIfNeeded_String_String() {
        System.out.println("testAddAndAddPrefixIfNeeded_String_String");
        //prepare
        Configuration conf = new Configuration();
        //execute
        conf.addAndAddPrefixIfNeeded("a", "p");
        //assert
        assertTrue(conf.has("dbmigration.a"));
        assertEquals("p", conf.get("dbmigration.a"));
    }
}
