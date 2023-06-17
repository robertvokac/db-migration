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

package org.nanoboot.dbmigration.core.main;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.logging.log4j.LogManager;
import org.nanoboot.dbmigration.core.configuration.Configuration;
import org.nanoboot.dbmigration.core.configuration.ConfigurationEntry;
import org.nanoboot.dbmigration.core.configuration.ConfigurationKeys;
import org.nanoboot.dbmigration.core.entity.DBMigrationSchemaHistory;
import org.nanoboot.dbmigration.core.resources.SqlFile;
import org.nanoboot.dbmigration.core.persistence.api.DBMigrationPersistence;
import org.nanoboot.dbmigration.core.query.DBMigrationSchemaHistoryTable;
import org.nanoboot.dbmigration.core.resources.FileSystemSqlFileProvider;
import org.nanoboot.dbmigration.core.resources.ResourcesSqlFileProvider;
import org.nanoboot.dbmigration.core.resources.SqlFileVersion;
import org.nanoboot.powerframework.time.duration.StopWatch;
import org.nanoboot.powerframework.time.moment.UniversalDateTime;
import org.nanoboot.powerframework.time.utils.TimeUnit;

/**
 *
 * @author <a href="mailto:robertvokac@nanoboot.org">Robert Vokac</a>
 * @since 0.1.0
 */
public class DBMigration {

    private static final org.apache.logging.log4j.Logger LOG = LogManager.getLogger(DBMigration.class);

    private void info() {
        LOG.info("*** Target : info : START ***");
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static class FluentConfiguration {

        private final Configuration configuration = new Configuration();

        public DBMigration load() {
            return new DBMigration(configuration);
        }

        public FluentConfiguration dataSource(String jdbcUrl) {
            return dataSource(jdbcUrl, null, null);
        }

        public FluentConfiguration dataSource(String jdbcUrl, String user, String password) {
            configuration.add(ConfigurationKeys.DATASOURCE_JDBC_URL, jdbcUrl);
            configuration.add(ConfigurationKeys.DATASOURCE_USER, user);
            configuration.add(ConfigurationKeys.DATASOURCE_PASSWORD, password);
            return this;
        }

        public FluentConfiguration name(String name) {
            configuration.add(ConfigurationKeys.NAME, name);
            return this;
        }

        public FluentConfiguration sqlDialect(String sqlDialect, String sqlDialectImplClass) {
            configuration.add(ConfigurationKeys.SQL_DIALECT, sqlDialect);
            configuration.add(ConfigurationKeys.SQL_DIALECT_IMPL_CLASS, sqlDialectImplClass);
            return this;
        }

        public FluentConfiguration sqlMigrationsDirectory(String sqlMigrationsDirectory) {
            configuration.add(ConfigurationKeys.SQL_MIGRATIONS_DIRECTORY, sqlMigrationsDirectory);
            return this;
        }

        public FluentConfiguration sqlMigrationsClass(String sqlMigrationsClass) {
            configuration.add(ConfigurationKeys.SQL_MIGRATIONS_CLASS, sqlMigrationsClass);
            return this;
        }

        public FluentConfiguration installedBy(String installedBy) {
            configuration.add(ConfigurationKeys.INSTALLED_BY, installedBy);
            return this;
        }

    }
    private final Configuration configuration;
    private final DBMigrationPersistence persistence;

    //validate -datasource.jdbc-url=jdbc:sqlite:/home/robertvokac/Desktop/test.sqlite3 -name=testDev -sql-dialect=sqlite -sql-migrations-directory=/home/robertvokac/Desktop/testDev/ -installed-by=robertvokac -sql-dialect-impl-class=org.nanoboot.dbmigration.persistence.impl.sqlite.DBMigrationPersistenceSqliteImpl
    public static void main(String[] args) {
        LOG.info("*** DB Migration ***");
        LOG.info("DB Migration main static method has just started.");
        //Flyway flyway = Flyway.configure().dataSource("jdbc:h2:file:./target/foobar", "sa", null).load();
        Configuration configuration = new Configuration();
        if (args.length == 0) {
            final String msg = "There must be at least one argument, but none was defined.";
            LOG.error(msg);
            throw new DBMigrationException(msg);
        }
        String target = args[0];
        DBMigrationTarget.valueOf(target.toUpperCase());
        LOG.info("Requested target = {}", target);
        for (int i = 1; i < args.length; i++) {
            if (args.length == 1) {
                break;
            }
            String arg = args[i];;
            LOG.debug("Found arg \"" + arg + "\"");
            configuration.addAndAddPrefixIfNeeded(ConfigurationEntry.ofArgument(arg));
        }
        DBMigration dBMigration = new DBMigration(configuration);
        //info -datasource.jdbc-url=jdbc:sqlite:/home/robertvokac/Desktop/test.sqlite3 -name=testDev -sql-dialect=sqlite -sql-migrations-directory=/home/robertvokac/Desktop/testDev/ -installed-by=robertvokac -sql-dialect-impl-class=org.nanoboot.dbmigration.core.persistence.impl.sqlite.DBMigrationPersistenceSqliteImpl
//        DBMigration dBMigration = DBMigration.configure()
//                .dataSource("jdbc:sqlite:/home/robertvokac/Desktop/test.sqlite3")
//                .name("testDev")
//                .sqlDialect("sqlite", "org.nanoboot.dbmigration.persistence.impl.sqlite.DBMigrationPersistenceSqliteImpl")
//                .sqlMigrationsDirectory("/home/robertvokac/Desktop/testDev/")
//                .installedBy("robertvokac")
//                .load();
        LOG.info("Going to execute target (and all its predecessors): {}", target);
        switch (target) {
            case "test":
                dBMigration.test();
                break;
            case "info":
                dBMigration.info();
                break;
            case "validate":
                dBMigration.validate();
                break;
            case "migrate":
                dBMigration.migrate();
                break;
            case "repair":
                dBMigration.repair();
                break;
            default:
                throw new DBMigrationException("Unknown target " + target);
        }
        //dBMigration.repair();
        //dBMigration.migrate();

    }

    public static FluentConfiguration configure() {
        return new FluentConfiguration();
    }

    public DBMigration(Configuration configurationIn) {
        this.configuration = configurationIn;
        try {
            this.persistence = loadPersistence();
            this.persistence.setConfiguration(configurationIn);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
            ex.printStackTrace();
            throw new DBMigrationException(ex);
        }
        init();
    }

    private DBMigrationPersistence loadPersistence()
            throws ClassNotFoundException, NoSuchMethodException, InstantiationException,
            IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Class clazz;
        clazz = Class.forName(this.configuration.getSqlDialectImplClass());

        Constructor constr = clazz.getConstructor();
        DBMigrationPersistence persistenceTemp = (DBMigrationPersistence) constr.newInstance();
        return persistenceTemp;
    }

    private void init() {

    }

    public boolean validate() {
        test();

        LOG.info(" ");
        LOG.info("*** Target : validate : START ***");

        try {

            boolean doesSchemaTableExist = persistence.doesMigrationSchemaHistoryTableExist();
            LOG.debug("Schema table exists = {}", doesSchemaTableExist);
            if (!doesSchemaTableExist) {
                LOG.info("Going to create schema table {}", DBMigrationSchemaHistoryTable.TABLE_NAME);
                persistence.createMigrationSchemaHistoryTable();
            }
            List<SqlFile> sqlFiles = loadSqlFiles();
            int nr = 0;
            for (SqlFile sf : sqlFiles) {
                nr++;
                LOG.info("Found sql file to be applied: #{}: {}", nr, sf.getFileName());
                LOG.debug("This sql file {} has sha1 hash: {}", sf.getFileName(), sf.calculateHash());

            }
            List<DBMigrationSchemaHistory> migrations = persistence.listMigrations(this.configuration.getName());
            LOG.info("Listing migrations for migration group with name: {}", this.configuration.getName());
            if (migrations.isEmpty()) {
                LOG.warn("There is no migration for this group in Migration Schema table history.");
            }
            for (DBMigrationSchemaHistory m : migrations) {
                LOG.info("Migration:");
                LOG.info("\t{} ", m);

            }
            Set<String> versions = new HashSet<>();

            Map<String, DBMigrationSchemaHistory> migrationsMap = new HashMap<>();
            Map<String, SqlFile> sqlFilesMap = new HashMap<>();
            for (DBMigrationSchemaHistory m : migrations) {
                versions.add(m.getVersion());
                migrationsMap.put(m.getVersion(), m);
            }
            for (SqlFile sf : sqlFiles) {
                versions.add(sf.getSqlFileName().getVersion());
                sqlFilesMap.put(sf.getSqlFileName().getVersion(), sf);
            }
            for (DBMigrationSchemaHistory m : migrations) {
                if (!sqlFilesMap.containsKey(m.getVersion())) {
                    DBMigrationException e = new DBMigrationException("There is script " + m.getScript() + " only in database but not in migration directory/jar");
                    LOG.error(e.getMessage());
                    throw e;
                }
                if (!m.isSuccess()) {
                    DBMigrationException e = new DBMigrationException("Migration " + m.getScript() + " has failure. Validation failed.");
                    LOG.error(e.getMessage());
                    throw e;
                }
            }

            for (int i = 0; i < migrations.size(); i++) {
                DBMigrationSchemaHistory migration = migrations.get(i);
                SqlFile sqlFile = sqlFiles.get(i);
                if (!migration.getVersion().equals(sqlFile.getSqlFileName().getVersion())) {
                    DBMigrationException e = new DBMigrationException("There is an additional sql file in migration directory " + sqlFile.getFileName() + "." + migration.getScript() + " was expected.");
                    LOG.error(e.getMessage());
                    throw e;
                }
                String sqlFileHash = sqlFile.calculateHash();
                String migrationHash = migration.getHash();
                String previousMigrationHash;
                if (i == 0) {
                    previousMigrationHash = "";
                } else {
                    DBMigrationSchemaHistory previousMigration = migrations.get(i - 1);
                    previousMigrationHash = previousMigration.getHash();
                }
                String expectedMigrationHash = DBMigrationSchemaHistory.calculateHash(previousMigrationHash, sqlFileHash);
                if (!migrationHash.equals(expectedMigrationHash)) {
                    final String msg = "Migration hash \"" + migrationHash + "\" is not that one, which was expected. Something is wrong (script " + migration.getScript() + "): sql file SHA-1 hash or migration SHA-1 hash.";
                    DBMigrationException e = new DBMigrationException(msg);
                    LOG.error(e.getMessage());
                    throw e;
                }

            }
            List<SqlFile> notYetMigratedSqlFiles = new ArrayList<>();
            for (String version : versions) {
                if (!migrationsMap.containsKey(version)) {
                    notYetMigratedSqlFiles.add(sqlFilesMap.get(version));
                }
            }
            LOG.info("Going to check, which SQL scripts were not yet migrated:");
            LOG.info("Result: all SQL scripts were already migrated. There is nothing to migrate.");
            for (SqlFile sqlFile : sqlFiles) {
                if (notYetMigratedSqlFiles.contains(sqlFile)) {
                    LOG.warn("Following SQL script was not yet migrated: {}", sqlFile.getFileName());
                }
            }

        } catch (Exception e) {
            DBMigrationException dBMigrationException = new DBMigrationException("Target validate failed while running", e);
            LOG.error(dBMigrationException);
            throw dBMigrationException;
        }

        return true;
    }

    private List<SqlFile> loadSqlFiles() {
        List<SqlFile> sqlFiles;
        LOG.info("Going to load sql migrations files to be applied:");
        String sqlMigrationsDirectory = configuration.getSqlMigrationsDirectory();
        if (sqlMigrationsDirectory == null) {
            LOG.info("Sql migrations files from a jar file will be used.");
            String path = ResourcesSqlFileProvider
                    .createPath(
                            configuration.getSqlDialect(),
                            configuration.getName());
            Class clazz;
            String className = configuration.getSqlMigrationsClass();
            try {
                LOG.debug("Going to create class object for: {}", className);
                clazz = Class.forName(className);
                LOG.debug("Creating class {} was success", className);
            } catch (ClassNotFoundException ex) {
                LOG.error("Creating class {}  failed: {}", className, ex.getMessage());
                Logger.getLogger(DBMigration.class.getName()).log(Level.SEVERE, null, ex);
                throw new DBMigrationException(ex);
            }
            sqlFiles = new ResourcesSqlFileProvider().provide(path, clazz);

        } else {
            LOG.info("Sql migrations files from a directory file will be used.");
            sqlFiles = new FileSystemSqlFileProvider().provide(sqlMigrationsDirectory);
        }
        Collections.sort(sqlFiles);
        return sqlFiles;
    }

    public MigrationResult migrate() {
        validate();
        LOG.info(" ");
        LOG.info("*** Target : migrate : START ***");

        try {

            List<SqlFile> sqlFiles = loadSqlFiles();
            List<DBMigrationSchemaHistory> migrations = persistence.listMigrations(this.configuration.getName());

            List<SqlFileVersion> sqlFileVersions = new ArrayList<>();

            Map<String, DBMigrationSchemaHistory> migrationsMap = new HashMap<>();
            Map<String, SqlFile> sqlFilesMap = new HashMap<>();
            for (DBMigrationSchemaHistory m : migrations) {
                sqlFileVersions.add(new SqlFileVersion(m.getVersion()));
                migrationsMap.put(m.getVersion(), m);
            }
            for (SqlFile sf : sqlFiles) {
                sqlFileVersions.add(new SqlFileVersion(sf.getSqlFileName().getVersion()));
                sqlFilesMap.put(sf.getSqlFileName().getVersion(), sf);
            }

            Collections.sort(sqlFileVersions);

            int countOfExecutedMigrations = 0;
            for (SqlFileVersion sqlFileVersion : sqlFileVersions) {
                String version = sqlFileVersion.getVersion();
                if (!migrationsMap.containsKey(version)) {
                    countOfExecutedMigrations++;
                    LOG.info("Going to migrate SQL script {}.", sqlFilesMap.get(version).getFileName());
                    SqlFile sqlFile = sqlFilesMap.get(version);
                    DBMigrationSchemaHistory newMigration = new DBMigrationSchemaHistory();
                    newMigration.setId(UUID.randomUUID());
                    newMigration.setMigrationGroup(configuration.getName());
                    DBMigrationSchemaHistory previousMigration = migrations.isEmpty() ? null : migrations.get(migrations.size() - 1);

                    int newInstalledRank = previousMigration == null ? 1 : previousMigration.getInstalledRank() + 1;
                    LOG.debug("previousMigration is {} and new rank is {}", previousMigration, newInstalledRank);
                    newMigration.setInstalledRank(newInstalledRank);
                    newMigration.setVersion(sqlFile.getSqlFileName().getVersion());
                    newMigration.setDescription(sqlFile.getSqlFileName().getDescription());
                    newMigration.setType(MigrationType.SQL);
                    newMigration.setScript(sqlFile.getFileName());
                    String newHash = DBMigrationSchemaHistory.calculateHash(previousMigration == null ? "" : previousMigration.getHash(), sqlFile.calculateHash());
                    newMigration.setHash(newHash);
                    newMigration.setInstalledBy(configuration.getInstalledBy());
                    newMigration.setInstalledOn(UniversalDateTime.now().toString());
                    newMigration.setExecutionTime(456);
                    newMigration.setSuccess(true);

                    String sql = sqlFile.getContent();
                    StopWatch stopWatch = new StopWatch();
                    stopWatch.start();
                    String result = persistence.executeSql(sql);
                    stopWatch.stop();
                    boolean success = result == null;
                    newMigration.setExecutionTime((int) stopWatch.getCurrentDuration().toTotal(TimeUnit.MILLISECOND));
                    newMigration.setSuccess(success);

                    if (success) {
                        LOG.info("Migration of script " + newMigration.getScript() + " Finished. Result: SUCCESS.");
                    } else {
                        LOG.error("Migration of script {} Finished. Result: FAILURE", newMigration.getScript());
                        LOG.error("Migration of script {} with sql content: \n", newMigration.getScript(),  sql);
                        LOG.error("Migration of script : " + "Error details: {}", result);
                    }
                    persistence.addMigration(newMigration);
                    migrations.add(newMigration);
                    if (!success) {
                        throw new DBMigrationException("INFO : Migration of script " + newMigration.getScript() + " Finished. Result: FAILURE");
                    }

                }
            }
            LOG.info("Count of successfully executed migrations is {}", countOfExecutedMigrations);
            return MigrationResult.SUCCESS;
        } catch (Exception e) {
            DBMigrationException dBMigrationException = new DBMigrationException("Target migrate failed while running", e);
            LOG.error(dBMigrationException);
            throw dBMigrationException;
        }

    }

    public RepairResult repair() {
        test();
        LOG.info(" ");
        LOG.info("*** Target : repair : START ***");

        try {
            List<DBMigrationSchemaHistory> migrations = persistence.listMigrations(this.configuration.getName());
            List<DBMigrationSchemaHistory> failedMigrationsToBeRemoved = new ArrayList<>();
            for (DBMigrationSchemaHistory m : migrations) {
                if (!m.isSuccess()) {
                    failedMigrationsToBeRemoved.add(m);
                }
            }
            for (DBMigrationSchemaHistory m : failedMigrationsToBeRemoved) {
                LOG.info("Going to remove migration: " + m.toString());
                persistence.removeMigration(m.getMigrationGroup(), m.getVersion());
            }
            migrate();
        } catch (Exception e) {
            DBMigrationException dBMigrationException = new DBMigrationException("Target repair failed while running", e);
            LOG.error(dBMigrationException);
            throw dBMigrationException;
        }

        return RepairResult.SUCCESS;
    }

    public boolean test() {
        LOG.info(" ");
        LOG.info("*** Target : test : START ***");
        try {
            persistence.test();
        } catch (Exception e) {
            DBMigrationException dBMigrationException = new DBMigrationException("Target test failed while running", e);
            LOG.error(dBMigrationException);
            throw dBMigrationException;
        }
        return true;
    }

}
