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

package org.nanoboot.dbmigration.core.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import org.nanoboot.powerframework.security.hash.locator.HashCalculatorLocator;
import java.security.NoSuchAlgorithmException;
import org.nanoboot.dbmigration.core.main.DBMigrationException;
import org.nanoboot.powerframework.security.hash.locator.HashImpl;

/**
 *
 * @author <a href="mailto:robertvokac@nanoboot.org">Robert Vokac</a>
 * @since 0.1.0
 */
public class DBMigrationUtils {

    private DBMigrationUtils() {
        throw new java.lang.UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
    private static final String STRING_FORMATTING_PLACE_HOLDER = "{}";

    /**
     *
     * @param password
     * @return hash, in case of failure :: and the String representation of that
     * thrown exception
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String hashSha1(String password) {
        return HashCalculatorLocator.locate(HashImpl.SHA_1).hash(password);
    }

    public static String formatString(String string, Object... args) {
        return String.format(string.replace(STRING_FORMATTING_PLACE_HOLDER, STRING_FORMATTING_PLACE_HOLDER2), args);
    }
    private static final String STRING_FORMATTING_PLACE_HOLDER2 = "%s";

    //TODO move to Power Framework
    public static void writeToFile(String path, String text) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
            writer.write(text);
        } catch (IOException e) {
            throw new DBMigrationException(e);
        }
    }
    //TODO move to Power Framework
        public static String readTextFromFile(String path) {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(path))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();

                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                return sb.toString();
            }
        } catch (IOException e) {
            throw new DBMigrationException(e);
        }
    }
}
