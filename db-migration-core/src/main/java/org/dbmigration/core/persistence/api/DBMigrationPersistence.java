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

package org.nanoboot.dbmigration.core.persistence.api;

import java.util.List;
import org.nanoboot.dbmigration.core.configuration.Configuration;
import org.nanoboot.dbmigration.core.entity.DBMigrationSchemaHistory;

/**
 *
 * @author <a href="mailto:robertvokac@nanoboot.org">Robert Vokac</a>
 * @since 0.1.0
 */
public interface DBMigrationPersistence {
    public String getSqlDialect();
    public boolean doesMigrationSchemaHistoryTableExist();
    public void createMigrationSchemaHistoryTable();
    public String executeSql(String sql);
    public void addMigration(DBMigrationSchemaHistory migration);
    public void removeMigration(String migrationGroup, String version);
    public List<DBMigrationSchemaHistory> listMigrations(String migrationGroup);
    public List<Integer> listInstalledRanks(String migrationGroup);
    public List<String> listVersions(String migrationGroup);
    public List<String> listMigrationGroups();
    public DBMigrationSchemaHistory getMigrationByInstalledRank(int installedRank);
    public DBMigrationSchemaHistory getMigrationByVersion(int version);
    public void updateMigration(DBMigrationSchemaHistory migration);
    public boolean test();
    public void setConfiguration(Configuration configurationIn);
    public Configuration getConfiguration();
}
