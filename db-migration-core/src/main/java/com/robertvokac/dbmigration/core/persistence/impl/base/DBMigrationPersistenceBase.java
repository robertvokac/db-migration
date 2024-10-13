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

package com.robertvokac.dbmigration.core.persistence.impl.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.robertvokac.dbmigration.core.configuration.Configuration;
import com.robertvokac.dbmigration.core.entity.DBMigrationSchemaHistory;
import com.robertvokac.dbmigration.core.main.DBMigrationException;
import com.robertvokac.dbmigration.core.main.MigrationType;
import com.robertvokac.dbmigration.core.persistence.api.DBMigrationPersistence;
import com.robertvokac.dbmigration.core.persistence.impl.sqlite.DBMigrationPersistenceSqliteImpl;
import com.robertvokac.dbmigration.core.query.DBMigrationSchemaHistoryTable;

/**
 *
 * @author <a href="mailto:robertvokac@robertvokac.com">Robert Vokac</a>
 * @since 0.1.0
 */
public abstract class DBMigrationPersistenceBase implements DBMigrationPersistence {

    private static final Logger LOG = LogManager.getLogger(DBMigrationPersistenceBase.class);
    private static final String SELECT__FROM = "select * from ";
    private Configuration configuration;

    @Override
    public void setConfiguration(Configuration configurationIn) {
        this.configuration = configurationIn;
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }
//https://www.sqlitetutorial.net/sqlite-java/

    protected Connection createConnection() {
        String jdbcUrl = getConfiguration().getDatasourceJdbcUrl();
        if (!getSqlDialect().equals("sqlite")) {
            String user = getConfiguration().getDatasourceUser();
            String password = getConfiguration().getDatasourcePassword();
        }
        Connection conn = null;
        try {
            //"jdbc:sqlite:C:/sqlite/db/chinook.db"
            String url = jdbcUrl;
            conn = DriverManager.getConnection(url);

            LOG.debug("Connection to database has been established.");

        } catch (SQLException e) {
            LOG.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return conn;
    }

    protected void closeConnection(Connection conn) {
        try {
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException ex) {
            LOG.error(ex.getMessage());
        }
    }

    protected int selectOneInt(Connection conn, String sql, String columnName) {

        try ( Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                return rs.getInt(columnName);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        final String msg = "There is no such record: " + sql;
        LOG.error(msg);
        throw new DBMigrationException(msg);
    }

    protected String selectOneString(Connection conn, String sql, String columnName) {

        try ( Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                return rs.getString(columnName);
            }
        } catch (SQLException e) {
            LOG.error(e.getMessage());
        }
        final String msg = "There is no such record: " + sql;
        LOG.error(msg);
        throw new DBMigrationException(msg);
    }

    @Override
    public boolean doesMigrationSchemaHistoryTableExist() {
        return doesTableExist(createConnection(), DBMigrationSchemaHistoryTable.TABLE_NAME);
    }

    public boolean doesTableExist(Connection conn, String tableName) {
        String sql = SELECT__FROM + tableName;
        try ( Statement stmt = conn.createStatement();  ResultSet rs = stmt.executeQuery(sql)) {
            return true;
        } catch (SQLException e) {
            LOG.error(e);
        }
        return false;
    }

    @Override
    public String executeSql(String sql) {
        try ( Connection conn = createConnection();) {
            conn.setAutoCommit(false);
            String[] sqls = sql.split(SQL_STATEMENT_SEPARATOR);
            try ( Statement stmt = conn.createStatement()) {
                for (String oneSql : sqls) {
                    if(oneSql.isBlank()) {
                        continue;
                    }
                    System.err.println("Going to execute sql:\n"+oneSql);
                    stmt.execute(oneSql);
                }
                conn.commit();
            } catch (SQLException ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
            return null;
        } catch (SQLException e) {
            LOG.error(e);
            return e == null ? "NullPointerException may happened." : e.getMessage();
        }
    }
    private static final String SQL_STATEMENT_SEPARATOR = ";";

    @Override
    public void createMigrationSchemaHistoryTable() {
        String result = executeSql(getCreateMigrationsTableSql());
        if (result != null) {
            final String msg = "Creating table " + DBMigrationSchemaHistoryTable.TABLE_NAME + " failed: " + result;
            throw new DBMigrationException(msg);
        }
    }

    public abstract String getCreateMigrationsTableSql();

    @Override
    public void removeMigration(String migrationGroup, String version) {
        LOG.info("Going to remove migration: {},{}", migrationGroup, version);
        try ( PreparedStatement preparedStatement = createPreparedStatementForRemoveMigration(createConnection(), migrationGroup, version);) {
            LOG.debug("removeMigration preparedStatement.toString()::{}", preparedStatement.toString());
            boolean result = preparedStatement.execute();

        } catch (SQLException e) {
            String msg = "Deleting migration failed: " + migrationGroup + ", " + version + "\n \n" + e.getMessage();
            LOG.error(msg);
            throw new DBMigrationException(msg);
        }
    }

    private PreparedStatement createPreparedStatementForRemoveMigration(Connection createConnection, String migrationGroup, String version) throws SQLException {
        PreparedStatement preparedStatement = createConnection.prepareStatement(getDeleteMigrationPreparedSql());
        int i = 0;
        preparedStatement.setString(++i, migrationGroup);
        preparedStatement.setString(++i, version);
        return preparedStatement;

    }

    public abstract String getDeleteMigrationPreparedSql();

    @Override
    public void addMigration(DBMigrationSchemaHistory migration) {
        LOG.info(" ");;
        LOG.info("* Executing next SQL migration file *");;
        LOG.info("Going to add new migration to database:");
        LOG.info("{}", migration);

        try ( PreparedStatement preparedStatement = createPreparedStatementForInsertMigration(createConnection(), migration);) {
            LOG.debug("addMigration preparedStatement.toString()::{}", preparedStatement.toString());
            boolean result = preparedStatement.execute();

        } catch (SQLException e) {
            String msg = "Inserting new migration failed: " + migration.toString() + "\n \n" + e.getMessage();
            LOG.error(msg);
            throw new DBMigrationException(msg);
        }

    }

    private PreparedStatement createPreparedStatementForInsertMigration(Connection createConnection, DBMigrationSchemaHistory migration) throws SQLException {
        PreparedStatement preparedStatement = createConnection().prepareStatement(getInsertMigrationPreparedSql());
        int i = 0;
        preparedStatement.setString(++i, migration.getId().toString());
        preparedStatement.setString(++i, migration.getMigrationGroup());
        preparedStatement.setInt(++i, migration.getInstalledRank());
        preparedStatement.setString(++i, migration.getVersion());
        preparedStatement.setString(++i, migration.getDescription());

        preparedStatement.setString(++i, migration.getType().name());
        preparedStatement.setString(++i, migration.getScript());
        preparedStatement.setString(++i, migration.getHash());
        preparedStatement.setString(++i, migration.getInstalledBy());
        preparedStatement.setString(++i, migration.getInstalledOn());

        preparedStatement.setInt(++i, migration.getExecutionTime());
        preparedStatement.setInt(++i, (migration.isSuccess() ? 1 : 0));
        return preparedStatement;
    }

    public abstract String getInsertMigrationPreparedSql();

    @Override
    public List<DBMigrationSchemaHistory> listMigrations(String migrationGroup) {
        LOG.info("Listing migrations for group = {}", migrationGroup);
        List<DBMigrationSchemaHistory> list = new ArrayList<>();
        String sql = null;
        try ( PreparedStatement preparedStatement = createPreparedStatementForListMigrations(createConnection(), migrationGroup);  ResultSet rs = preparedStatement.executeQuery();) {

            sql = preparedStatement.toString();
            LOG.debug("listMigrations preparedStatement sql = {}", sql.toString());
            while (rs.next()) {
                DBMigrationSchemaHistory migration = loadMigrationsFromResultSet(rs);
                list.add(migration);
            }
            Collections.sort(list);
            return list;
        } catch (SQLException e) {
            LOG.error(e);
        }
        final String msg = "There is no such record: " + sql;
        LOG.error(msg);
        throw new DBMigrationException(msg);

    }

    private DBMigrationSchemaHistory loadMigrationsFromResultSet(ResultSet rs) {
        DBMigrationSchemaHistory migration = new DBMigrationSchemaHistory();

        try {
            migration.setId(UUID.fromString(rs.getString(DBMigrationSchemaHistoryTable.ID)));
            migration.setMigrationGroup(rs.getString(DBMigrationSchemaHistoryTable.MIGRATION_GROUP));
            migration.setInstalledRank(rs.getInt(DBMigrationSchemaHistoryTable.INSTALLED_RANK));
            migration.setVersion(rs.getString(DBMigrationSchemaHistoryTable.VERSION));
            migration.setDescription(rs.getString(DBMigrationSchemaHistoryTable.DESCRIPTION));

            migration.setType(MigrationType.valueOf(rs.getString(DBMigrationSchemaHistoryTable.TYPE)));
            migration.setScript(rs.getString(DBMigrationSchemaHistoryTable.SCRIPT));
            migration.setHash(rs.getString(DBMigrationSchemaHistoryTable.HASH));
            migration.setInstalledBy(rs.getString(DBMigrationSchemaHistoryTable.INSTALLED_BY));
            migration.setInstalledOn(rs.getString(DBMigrationSchemaHistoryTable.INSTALLED_ON));

            migration.setExecutionTime(rs.getInt(DBMigrationSchemaHistoryTable.EXECUTION_TIME));
            migration.setSuccess(rs.getInt(DBMigrationSchemaHistoryTable.SUCCESS) != 0);

            return migration;
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(DBMigrationPersistenceSqliteImpl.class.getName()).log(Level.SEVERE, null, ex);
            throw new DBMigrationException(ex);
        }

    }

    private PreparedStatement createPreparedStatementForListMigrations(Connection createConnection, String migrationGroup) throws SQLException {
        PreparedStatement preparedStatement = createConnection.prepareStatement(getListMigrationsPreparedSql());
        preparedStatement.setString(1, migrationGroup);
        return preparedStatement;
    }

    public abstract String getListMigrationsPreparedSql();
}
