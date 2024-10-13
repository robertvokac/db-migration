///////////////////////////////////////////////////////////////////////////////////////////////
// db-migration: A database schema versioning tool.
// Copyright (C) 2021-2022 the original author or authors.
//
// This program is free software: you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation, either version 3
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program. If not, see 
// <https://www.gnu.org/licenses/> or write to the Free Software
// Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
///////////////////////////////////////////////////////////////////////////////////////////////

package com.robertvokac.dbmigration.core.resources;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.robertvokac.dbmigration.core.main.DBMigrationException;
import com.robertvokac.dbmigration.core.utils.DBMigrationUtils;

/**
 *
 * @author <a href="mailto:robertvokac@robertvokac.com">Robert Vokac</a>
 * @since 0.1.0
 */
@EqualsAndHashCode
@ToString
public class SqlFileName implements Comparable<SqlFileName> {

    private static final Logger LOG = LogManager.getLogger(SqlFileName.class);

    private static final String FILE = "File ";

    @Getter
    private final String fileName;
    @Getter
    private final String version;
    @Getter
    private final String description;
    private final SqlFileVersion sqlFileVersion;

    public SqlFileName(String fileNameIn) {
        this.fileName = fileNameIn;
        if (!fileName.endsWith(".sql")) {
            final String msg = FILE + fileName + " does not end with \".sql\".";
            throw new DBMigrationException(msg);
        }
        //Example: V2_1__First_Changes.sql
        String tmpFileName = fileName.replace(".sql", "");
        //Example: V2_1__First_Changes
        if (!fileName.startsWith("V")) {
            final String msg = FILE + fileName + " does not start with \"V\".";
            throw new DBMigrationException(msg);
        }
        if (!fileName.contains("__")) {
            final String msg = FILE + fileName + " does not contain \"__\".";
            throw new DBMigrationException(msg);
        }
        tmpFileName = tmpFileName.substring(1);
        //Example: 2_1__First_Changes
        String[] array = tmpFileName.split("__");

        String tmpVersion = array[0];
        String[] tmpVersionArray = tmpVersion.split("_");
        StringBuilder tmpVersionStringBuilder = new StringBuilder();
        for (int i = 0; i < tmpVersionArray.length; i++) {
            String subVersion = tmpVersionArray[i];
            tmpVersionStringBuilder.append(Integer.valueOf(subVersion));
            boolean lastIndex = i == (tmpVersionArray.length - 1);
            if (!lastIndex) {
                tmpVersionStringBuilder.append(".");
            }
        }
        String tmpDescription = array[1];
        this.version = tmpVersionStringBuilder.toString();
        this.description = tmpDescription.replace("_", " ");
        this.sqlFileVersion = new SqlFileVersion(this.version);
    }

    @Override
    public int compareTo(SqlFileName o) {
        int result = this.sqlFileVersion.compareTo(o.sqlFileVersion);
        
        if (result == 0 && !this.getFileName().equals(o.getFileName())) {
            String msg = DBMigrationUtils
                    .formatString(
                            "Cannot compare instances of SqlFileName with same version and different file names: {}, {}",
                            this.toString(),
                            o.toString()
                    );
            LOG.error(msg);
            throw new DBMigrationException(msg);

        }
        return result;
    }

}
