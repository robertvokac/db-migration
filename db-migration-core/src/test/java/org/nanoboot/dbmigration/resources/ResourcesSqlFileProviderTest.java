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

package org.nanoboot.dbmigration.core.resources;

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
public class ResourcesSqlFileProviderTest {
    
    public ResourcesSqlFileProviderTest() {
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
     * Test of createPath method, of class ResourcesSqlFileProvider.
     */
    @Test
    public void testCreatePath() {
        System.out.println("createPath");
        //prepare
        String sqlDialect = "sqlite";
        String nameOfMigrations = "testa";
        String expResult = "db_migrations/sqlite/testa";
        //test
        String result = ResourcesSqlFileProvider.createPath(sqlDialect, nameOfMigrations);
        //assert
        assertEquals(expResult, result);
    }

    /**
     * Test of provide method, of class ResourcesSqlFileProvider.
     */
    @Test
    public void testProvide_String() {
        System.out.println("provide(String)");
        //prepare
        String path = ResourcesSqlFileProvider.createPath("sqlite", "test");
        Class clazz = ResourcesSqlFileProvider.class;
        ResourcesSqlFileProvider instance = new ResourcesSqlFileProvider();
        List<SqlFile> expResult = null;
        //execute
        Throwable exception = assertThrows(UnsupportedOperationException.class, () -> instance.provide(path));
        
        //assert
    }
    
    /**
     * Test of provide method, of class ResourcesSqlFileProvider.
     */
    @Test
    public void testProvide_String_Class() {
        System.out.println("provide(String,Class)");
        //prepare
        String path = ResourcesSqlFileProvider.createPath("sqlite", "test");
        Class clazz = org.nanoboot.dbmigration.test.jar.DBMigrationTestJarDummyClass.class;
        ResourcesSqlFileProvider instance = new ResourcesSqlFileProvider();
        List<SqlFile> expResult = null;
        //execute
        try{
        List<SqlFile> result = instance.provide(path,clazz);
        }catch(Exception e) {
            System.err.println("###"+e.getMessage());
            throw e;
        }
        //assert
        //assertEquals(12, result.size());
        //assertEquals(expResult, result);
    }
    
}
