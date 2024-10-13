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

package com.robertvokac.dbmigration.core.configuration;

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
public class ConfigurationEntryTest {
    
    public ConfigurationEntryTest() {
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
     * Test of ofArgument method, of class ConfigurationEntry.
     */
    @Test
    public void testOfArgument() {
        System.out.println("ofArgument");
        //prepare
        String arg = "name=Johnny";
        //execute
        //assert
        Throwable exception = assertThrows(DBMigrationException.class, () -> ConfigurationEntry.ofArgument(arg));
        assertEquals("Arguments must start with - since second position: " + arg, exception.getMessage());
    }
    /**
     * Test of ofArgument method, of class ConfigurationEntry.
     */
    @Test
    public void testOfArgument2() {
        System.out.println("ofArgument 2");
        //prepare
        String arg = "-nameJohnny";
        //execute
        //assert
        Throwable exception = assertThrows(DBMigrationException.class, () -> ConfigurationEntry.ofArgument(arg));
        assertEquals("Array is " + "1"+ ", but 2 was expected.", exception.getMessage());
    }
    /**
     * Test of ofArgument method, of class ConfigurationEntry.
     */
    @Test
    public void testOfArgument3() {
        System.out.println("ofArgument 3");
        //prepare
        String arg = "-name=Johnny";
        String keyExpected = "name";
        String valueExpected = "Johnny";
        //execute
        ConfigurationEntry ce = ConfigurationEntry.ofArgument(arg);
        //assert
        assertEquals(keyExpected, ce.getKey());
        assertEquals(valueExpected, ce.getValue());
    }

  
}
