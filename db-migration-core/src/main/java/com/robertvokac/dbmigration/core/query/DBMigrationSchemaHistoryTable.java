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

package com.robertvokac.dbmigration.core.query;

/**
 *
 * @author <a href="mailto:robertvokac@robertvokac.com">Robert Vokac</a>
 * @since 0.1.0
 */
public class DBMigrationSchemaHistoryTable {

    public DBMigrationSchemaHistoryTable() {
        //No instantiation needed.
    }

    public static final String TABLE_NAME = "DB_MIGRATION_SCHEMA_HISTORY";

    public static final String ID = "ID";
    public static final String MIGRATION_GROUP = "MIGRATION_GROUP";
    public static final String INSTALLED_RANK = "INSTALLED_RANK";
    public static final String VERSION = "VERSION";
    public static final String DESCRIPTION = "DESCRIPTION";

    public static final String TYPE = "TYPE";
    public static final String SCRIPT = "SCRIPT";
    public static final String HASH = "HASH";
    public static final String INSTALLED_BY = "INSTALLED_BY";
    public static final String INSTALLED_ON = "INSTALLED_ON";

    public static final String EXECUTION_TIME = "EXECUTION_TIME";
    public static final String SUCCESS = "SUCCESS";

}
