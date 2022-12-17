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

import lombok.AllArgsConstructor;
import lombok.Data;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nanoboot.dbmigration.core.main.DBMigrationException;

/**
 *
 * @author <a href="mailto:robertvokac@nanoboot.org">Robert Vokac</a>
 * @since 0.1.0
 */
@Data
@AllArgsConstructor
public class ConfigurationEntry {

    private static final Logger LOG = LogManager.getLogger(ConfigurationEntry.class);

    private String key;
    private String value;

    public static ConfigurationEntry ofArgument(String arg) {
        if (!arg.startsWith("-")) {
            String msg = "Arguments must start with - since second position: " + arg;
            LOG.error(msg);
            throw new DBMigrationException(msg);
        }
        String tmpArg = arg.substring(1);
        String[] array = tmpArg.split("=", 2);
        if (array.length != 2) {
            String msg = "Array is " + array.length + ", but 2 was expected.";
            LOG.error(msg);
            throw new DBMigrationException(msg);
        }
        String key = array[0];
        String value = array[1];

        return new ConfigurationEntry(key, value);
    }
}
