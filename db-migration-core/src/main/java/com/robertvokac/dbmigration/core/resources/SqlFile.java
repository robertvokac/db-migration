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

package com.robertvokac.dbmigration.core.resources;

import lombok.Data;
import com.robertvokac.dbmigration.core.utils.DBMigrationUtils;

/**
 *
 * @author <a href="mailto:robertvokac@robertvokac.com">Robert Vokac</a>
 * @since 0.1.0
 */
@Data
public class SqlFile implements Comparable<SqlFile> {

    private SqlFileName sqlFileName;
    private String fileName;
    private String content;

    public SqlFile(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
        this.sqlFileName = new SqlFileName(fileName);
    }

    public String calculateHash() {
        return DBMigrationUtils.hashSha1(content);
    }

    @Override
    public int compareTo(SqlFile o) {
        return this.getSqlFileName().compareTo(o.getSqlFileName());
    }
}
