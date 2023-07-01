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

import java.io.File;
import java.util.Collections;
import java.util.List;
import org.nanoboot.dbmigration.core.main.DBMigrationException;
import static org.nanoboot.dbmigration.core.utils.DBMigrationUtils.writeToFile;
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
public class FileSystemSqlFileProviderTest {

    public FileSystemSqlFileProviderTest() {
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
     * Test of provide method, of class FileSystemSqlFileProvider.
     */
    @Test
    public void testProvide() {
        System.out.println("provide");
        //prepare
        File dir = new File("tmp_test_dir_" + ((int) (Math.random() * 1000000)));
        dir.mkdir();
        String dirPath = dir.getAbsolutePath();
        File file1 = new File(dirPath + File.separator + "V000001__create_table_teacher.sql");
        File file2 = new File(dirPath + File.separator + "V000002__create_table_student.sql");
        File file3 = new File(dirPath + File.separator + "V000003__create_table_class.sql");
        writeToFile(file1.getAbsolutePath(), "aaa");
        writeToFile(file2.getAbsolutePath(), "bbb");
        writeToFile(file3.getAbsolutePath(), "ccc");
        Class clazz = null;
        FileSystemSqlFileProvider instance = new FileSystemSqlFileProvider();
        //execute
        List<SqlFile> result = instance.provide(dirPath, clazz);
        file1.delete();
        file2.delete();
        file3.delete();
        dir.delete();
        Collections.sort(result);
        //assert
        assertEquals(3, result.size());
        assertEquals("V000001__create_table_teacher.sql", result.get(0).getFileName());
        assertEquals("V000002__create_table_student.sql", result.get(1).getFileName());
        assertEquals("V000003__create_table_class.sql", result.get(2).getFileName());
    }

    /**
     * Test of provide method, of class FileSystemSqlFileProvider.
     */
    @Test
    public void testProvide2() {
        System.out.println("provide2");
        //prepare
        File dir = new File("tmp_test_dir_" + ((int) (Math.random() * 1000000)));
        dir.mkdir();
        File dir2 = new File(dir.getAbsolutePath() + File.separator + ((int) (Math.random() * 1000000)) + ((int) (Math.random() * 1000000)));
        dir2.mkdir();
        String dirPath = dir.getAbsolutePath();
        File file1 = new File(dirPath + File.separator + "V000001__create_table_teacher.sql");
        writeToFile(file1.getAbsolutePath(), "aaa");
        Class clazz = null;
        FileSystemSqlFileProvider instance = new FileSystemSqlFileProvider();
        //execute
        Throwable exception = assertThrows(DBMigrationException.class, () -> instance.provide(dirPath, clazz));
        file1.delete();
        dir2.delete();
        dir.delete();
        assertEquals(true, exception.getMessage().endsWith("is directory, sqlMigrationsDirectory cannot contain any directory."));
        //assert
    }


}
