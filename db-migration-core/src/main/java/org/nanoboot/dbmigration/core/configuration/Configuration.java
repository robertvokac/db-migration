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

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.nanoboot.dbmigration.core.main.DBMigrationException;

/**
 *
 * @author <a href="mailto:robertvokac@nanoboot.org">Robert Vokac</a>
 * @since 0.1.0
 */
public class Configuration {

    private static final Logger LOG = LogManager.getLogger(Configuration.class);

    private Map<String, String> map = new HashMap<>();

    public static void validateKey(String key) {
        if (!startsKeyWithPrefix(key)) {
            String msg = "Key does not start with prefix " + ConfigurationKeys.PROPERTIES_PREFIX;
            LOG.error(msg);
            throw new DBMigrationException(msg);
        }
    }

    public static boolean startsKeyWithPrefix(String key) {
        return key.startsWith(ConfigurationKeys.PROPERTIES_PREFIX);
    }

    public static String prependPrefixIfNeeded(String key) {
        if (!startsKeyWithPrefix(key)) {
            return ConfigurationKeys.PROPERTIES_PREFIX + key;
        }
        return key;
    }

    public void add(ConfigurationEntry configurationEntry) {
        add(configurationEntry.getKey(), configurationEntry.getValue());
    }

    public void add(String key, String value) {
        validateKey(key);
        if (value == null) {
            //nothing to do
            LOG.debug("Trying to add key {} with value. Skipping. Null values are skipped.", key);
            return;
        }
        LOG.debug("Added key: \"{}\" with value \"{}\".", key, value);
        this.map.put(key, value);
    }

    public void addAndAddPrefixIfNeeded(ConfigurationEntry configurationEntry) {
        configurationEntry.setKey(prependPrefixIfNeeded(configurationEntry.getKey()));
        add(configurationEntry);
    }

    public void addAndAddPrefixIfNeeded(String key, String value) {
        add(prependPrefixIfNeeded(key), value);
    }

    public String get(String key) {
        validateKey(key);
        return getMandatory(key);
    }

    public String getMandatory(String key) {
        if (!has(key)) {
            final String msg = "There is no key: " + key;
            LOG.error(msg);
            throw new DBMigrationException(msg);
        }
        return map.get(key);
    }

    public String getOptional(String key) {
        validateKey(key);

        if (!has(key)) {
            final String msg = "There is no key: " + key;
            LOG.warn(msg);

            return null;
        }
        return map.get(key);
    }

    public boolean has(String key) {
        validateKey(key);
        return map.containsKey(key);
    }

    public void remove(String key) {
        validateKey(key);
        map.remove(key);
    }

    public List<String> listKeys() {
        List<String> list = new ArrayList<>();
        for (String key : map.keySet()) {
            list.add(key);
        }
        return list;
    }

    public List<ConfigurationEntry> list() {
        List<ConfigurationEntry> list = new ArrayList<>();
        for (String key : map.keySet()) {
            String value = get(key);
            list.add(new ConfigurationEntry(key, value));
        }
        return list;
    }

    public String getDatasourceJdbcUrl() {
        return get(ConfigurationKeys.DATASOURCE_JDBC_URL);
    }

    public void setDatasourceJdbcUrl(String datasourceJdbcUrl) {
        add(ConfigurationKeys.DATASOURCE_JDBC_URL, datasourceJdbcUrl);
    }

    public String getDatasourceUser() {
        return getOptional(ConfigurationKeys.DATASOURCE_USER);
    }

    public void setDatasourceUser(String datasourceUser) {
        add(ConfigurationKeys.DATASOURCE_USER, datasourceUser);
    }

    public String getDatasourcePassword() {
        return getOptional(ConfigurationKeys.DATASOURCE_PASSWORD);
    }

    public void setDatasourcePassword(String datasourcePassword) {
        add(ConfigurationKeys.DATASOURCE_PASSWORD, datasourcePassword);
    }

    public String getName() {
        return get(ConfigurationKeys.NAME);
    }

    public void setName(String name) {
        add(ConfigurationKeys.NAME, name);
    }

    public String getSqlDialect() {
        return get(ConfigurationKeys.SQL_DIALECT);
    }

    public void setSqlDialect(String sqlDialect) {
        add(ConfigurationKeys.SQL_DIALECT, sqlDialect);
    }

    public String getSqlDialectImplClass() {
        return get(ConfigurationKeys.SQL_DIALECT_IMPL_CLASS);
    }

    public void setSqlDialectImplClass(String sqlDialectImplClass) {
        add(ConfigurationKeys.SQL_DIALECT_IMPL_CLASS, sqlDialectImplClass);
    }

    public String getSqlMigrationsDirectory() {
        return getOptional(ConfigurationKeys.SQL_MIGRATIONS_DIRECTORY);
    }

    public void setSqlMigrationsDirectory(String sqlMigrationsDirectory) {
        add(ConfigurationKeys.SQL_MIGRATIONS_DIRECTORY, sqlMigrationsDirectory);
    }

    public String getSqlMigrationsClass() {
        return getOptional(ConfigurationKeys.SQL_MIGRATIONS_CLASS);
    }

    public void setSqlMigrationsClass(String sqlMigrationsClass) {
        add(ConfigurationKeys.SQL_MIGRATIONS_CLASS, sqlMigrationsClass);
    }

    public String getInstalledBy() {
        return get(ConfigurationKeys.INSTALLED_BY);
    }

    public void setInstalledBy(String installedBy) {
        add(ConfigurationKeys.INSTALLED_BY, installedBy);
    }
}
