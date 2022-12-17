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

package org.nanoboot.dbmigration.core.persistence.impl.sqlite;

import java.sql.Connection;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.nanoboot.dbmigration.core.entity.DBMigrationSchemaHistory;
import org.nanoboot.dbmigration.core.persistence.impl.base.DBMigrationPersistenceBase;
import org.nanoboot.dbmigration.core.query.DBMigrationSchemaHistoryTable;
import org.nanoboot.dbmigration.core.persistence.api.DBMigrationPersistence;

/**
 *
 * @author <a href="mailto:robertvokac@nanoboot.org">Robert Vokac</a>
 * @since 0.1.0
 */
public class DBMigrationPersistenceSqliteImpl extends DBMigrationPersistenceBase implements DBMigrationPersistence {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(DBMigrationPersistenceSqliteImpl.class);
    private static final String SQLITE = "sqlite";
    private static final String SQL_CREATE_TABLE_DB_MIGRATION_SCHEMA_HISTORY
            = """
            CREATE TABLE "DB_MIGRATION_SCHEMA_HISTORY" (
                "ID" TEXT NOT NULL,
                "MIGRATION_GROUP" TEXT NOT NULL,
                "INSTALLED_RANK" INTEGER NOT NULL,
                "VERSION" TEXT NOT NULL,
                "DESCRIPTION" TEXT NOT NULL,
                "TYPE" TEXT NOT NULL,
                "SCRIPT" TEXT NOT NULL,
                "HASH" TEXT NOT NULL UNIQUE,
                "INSTALLED_BY" TEXT NOT NULL,
                "INSTALLED_ON" TEXT NOT NULL,
                "EXECUTION_TIME" INTEGER NOT NULL,
                "SUCCESS" INTEGER NOT NULL,
                PRIMARY KEY("ID"),
                CONSTRAINT "DB_MIGRATION_SCHEMA_HISTORY_UNIQUE_CONSTRAINT_MIGRATION_GROUP_AND_INSTALLED_RANK" UNIQUE("MIGRATION_GROUP","INSTALLED_RANK"),
                CONSTRAINT "DB_MIGRATION_SCHEMA_HISTORY_UNIQUE_CONSTRAINT_MIGRATION_GROUP_AND_VERSION" UNIQUE("MIGRATION_GROUP","VERSION")
            );
            """;
    private static final String SQL_LIST_MIGRATIONS
            = "SELECT * FROM " + DBMigrationSchemaHistoryTable.TABLE_NAME
            + " WHERE " + DBMigrationSchemaHistoryTable.MIGRATION_GROUP
            + " = ? ORDER BY " + DBMigrationSchemaHistoryTable.VERSION;
    private static final String SQL_INSERT_MIGRATION
            = "INSERT INTO " + DBMigrationSchemaHistoryTable.TABLE_NAME
            + " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

    private static final String SQL_DELETE_MIGRATION
            = "DELETE FROM " + DBMigrationSchemaHistoryTable.TABLE_NAME
            + " WHERE " + DBMigrationSchemaHistoryTable.MIGRATION_GROUP + " = ? AND "
            + DBMigrationSchemaHistoryTable.VERSION + " = ? ";

    @Override
    public String getSqlDialect() {
        return SQLITE;
    }

    @Override
    public List<Integer> listInstalledRanks(String migrationGroup) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> listVersions(String migrationGroup) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DBMigrationSchemaHistory getMigrationByInstalledRank(int installedRank) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DBMigrationSchemaHistory getMigrationByVersion(int installedRank) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateMigration(DBMigrationSchemaHistory migration) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean test() {
        Connection conn = createConnection();
        int testResult = selectOneInt(conn, SELECT_1__2_AS_ONE_PLUS_TWO_IS, "ONE_PLUS_TWO_IS");
        LOG.info("Test: " + SELECT_1__2_AS_ONE_PLUS_TWO_IS + " returned: " + testResult);
        LOG.info("doesTableExist(TEST_TABLE)=" + doesTableExist(conn, "TEST_TABLE"));
        LOG.info("doesTableExist(DB_MIGRATION_SCHEMA_HISTORY)=" + doesTableExist(conn, "DB_MIGRATION_SCHEMA_HISTORY"));
        LOG.info("doesTableExist(DB_MIGRATION_SCHEMA_HISTORY2)=" + doesTableExist(conn, "DB_MIGRATION_SCHEMA_HISTORY2"));
        closeConnection(conn);
        return true;
    }
    private static final String SELECT_1__2_AS_ONE_PLUS_TWO_IS = "select (1 + 2) as ONE_PLUS_TWO_IS";

    @Override
    public List<String> listMigrationGroups() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getCreateMigrationsTableSql() {
        return SQL_CREATE_TABLE_DB_MIGRATION_SCHEMA_HISTORY;
    }

    @Override
    public String getDeleteMigrationPreparedSql() {
        return SQL_DELETE_MIGRATION;
    }

    @Override
    public String getInsertMigrationPreparedSql() {
        return SQL_INSERT_MIGRATION;
    }

    @Override
    public String getListMigrationsPreparedSql() {
        return SQL_LIST_MIGRATIONS;
    }

}
