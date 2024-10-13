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

package com.robertvokac.dbmigration.core.entity;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import lombok.Data;
import lombok.ToString;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.robertvokac.dbmigration.core.main.DBMigrationException;
import com.robertvokac.dbmigration.core.main.MigrationType;
import com.robertvokac.dbmigration.core.utils.DBMigrationUtils;

/**
 *
 * @author <a href="mailto:robertvokac@robertvokac.com">Robert Vokac</a>
 * @since 0.1.0
 */
@Data
@ToString
public class DBMigrationSchemaHistory implements Comparable<DBMigrationSchemaHistory> {

    private static final Logger LOG = LogManager.getLogger(DBMigrationSchemaHistory.class);
    /**
     * UUID of migration.
     */
    private UUID id;
    /**
     * Migration group.
     */
    private String migrationGroup;
    /**
     * Order number.
     */
    private int installedRank;
    /**
     * Version. examples: 1, 1.0, 4.3.5
     */
    private String version;
    /**
     * Description extracted from script name.
     */
    private String description;
    /**
     * Migration type: SQL or Java
     */
    private MigrationType type;
    /**
     * Script name
     */
    private String script;
    /**
     * Hash.
     */
    private String hash;
    /**
     * Installation was done by.
     */
    private String installedBy;
    /**
     * Installation date and time.
     */
    private String installedOn;
    /**
     * Installation time.
     */
    private int executionTime;
    /**
     * Result. If success, then true, otherwise false (failure).
     */
    //in milliseconds
    private boolean success;

    public static String calculateHash(String hashOfPreviousMigration, String hashOfScriptContentOfThisMigration) {
        return DBMigrationUtils.hashSha1(hashOfPreviousMigration + hashOfScriptContentOfThisMigration);
    }

    @Override
    public int compareTo(DBMigrationSchemaHistory o) {
        if (!this.getMigrationGroup().equals(o.getMigrationGroup())) {
            String msg = "Cannot compare, if migration groups are different: this.getMigrationGroup()={}, o.getMigrationGroup()={}";
            LOG.error(msg, this.getMigrationGroup(), o.getMigrationGroup());
            throw new DBMigrationException(DBMigrationUtils.formatString(msg, this.getMigrationGroup(), o.getMigrationGroup()));
        }
        return Integer.valueOf(this.getInstalledRank()).compareTo(Integer.valueOf(o.getInstalledRank()));
    }
    
}
