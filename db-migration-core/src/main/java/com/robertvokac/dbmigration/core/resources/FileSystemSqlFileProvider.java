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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.robertvokac.dbmigration.core.main.DBMigrationException;
import static com.robertvokac.dbmigration.core.utils.DBMigrationUtils.readTextFromFile;

/**
 *
 * @author <a href="mailto:robertvokac@robertvokac.com">Robert Vokac</a>
 * @since 0.1.0
 */
public final class FileSystemSqlFileProvider implements SqlFileProvider {

    private static final Logger LOG = LogManager.getLogger(FileSystemSqlFileProvider.class);
    @Override
    public List<SqlFile> provide(String path, Class clazz) {
        return loadSqlFilesFromPath(path);
    }

    private List<SqlFile> loadSqlFilesFromPath(String sqlMigrationsDirectory) {
        List<SqlFile> sqlFiles = new ArrayList<>();
        for (File f : new File(sqlMigrationsDirectory).listFiles()) {
            if (f.isDirectory()) {
                final String msg = "File \"" + f.getAbsolutePath() + "\" is directory, sqlMigrationsDirectory cannot contain any directory.";
                LOG.error(msg);
                throw new DBMigrationException(msg);
            }

            SqlFile sqlFile = new SqlFile(f.getName(), readTextFromFile(f.getAbsolutePath()));
            sqlFiles.add(sqlFile);
        }
        return sqlFiles;
    }

}
