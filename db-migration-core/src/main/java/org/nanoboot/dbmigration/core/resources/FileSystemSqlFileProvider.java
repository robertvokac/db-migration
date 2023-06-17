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
import java.util.ArrayList;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nanoboot.dbmigration.core.main.DBMigrationException;
import static org.nanoboot.dbmigration.core.utils.DBMigrationUtils.readTextFromFile;

/**
 *
 * @author <a href="mailto:robertvokac@nanoboot.org">Robert Vokac</a>
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
