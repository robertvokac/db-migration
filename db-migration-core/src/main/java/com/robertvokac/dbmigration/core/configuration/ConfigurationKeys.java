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

package com.robertvokac.dbmigration.core.configuration;

/**
 *
 * @author <a href="mailto:robertvokac@robertvokac.com">Robert Vokac</a>
 * @since 0.1.0
 */
public class ConfigurationKeys {
    public static final String PROPERTIES_PREFIX = "dbmigration.";
    public static final String DATASOURCE_JDBC_URL = "dbmigration.datasource.jdbc-url";
    public static final String DATASOURCE_USER = "dbmigration.datasource.user";
    public static final String DATASOURCE_PASSWORD = "dbmigration.datasource.password";
    public static final String NAME = "dbmigration.name";
    public static final String SQL_DIALECT = "dbmigration.sql-dialect";
    public static final String SQL_DIALECT_IMPL_CLASS = "dbmigration.sql-dialect-impl-class";
    public static final String SQL_MIGRATIONS_DIRECTORY = "dbmigration.sql-migrations-directory";
    public static final String SQL_MIGRATIONS_CLASS = "dbmigration.sql-migrations-class";
    public static final String INSTALLED_BY = "dbmigration.installed-by";
    private ConfigurationKeys() {
        //Not meant to be instantiated.
    }
}
