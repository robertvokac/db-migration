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

package com.robertvokac.dbmigration.core.persistence.api;

import java.util.List;
import com.robertvokac.dbmigration.core.configuration.Configuration;
import com.robertvokac.dbmigration.core.entity.DBMigrationSchemaHistory;

/**
 *
 * @author <a href="mailto:robertvokac@robertvokac.com">Robert Vokac</a>
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
