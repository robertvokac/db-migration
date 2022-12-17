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

package org.nanoboot.dbmigration.core.resources;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author <a href="mailto:robertvokac@nanoboot.org">Robert Vokac</a>
 * @since 0.1.0
 */
@EqualsAndHashCode
public class SqlFileVersion implements Comparable<SqlFileVersion> {

    private static final Logger LOG = LogManager.getLogger(SqlFileVersion.class);
    @Getter
    private final String version;

    private final int[] subversions;

    public SqlFileVersion(String version) {
        this.version = version;
        String[] versionArray = version.split("\\.");
        subversions = new int[versionArray.length];
        for (int i = 0; i < versionArray.length; i++) {
            subversions[i] = Integer.valueOf(versionArray[i]);
        }
    }

    private int getSubversion(int index) {
        if (index >= subversions.length) {
            return 0;
        }
        return this.subversions[index];
    }

    private int getSubversionsCount() {
        return subversions.length;
    }

    @Override
    public int compareTo(SqlFileVersion o) {
        int maxSubVersionCount = Math.max(this.getSubversionsCount(), o.getSubversionsCount());
        int result = 0;

        LOG.trace("this.version={}", this.version);
        LOG.trace("o.version={}", o.version);
        for (int i = 0; i < maxSubVersionCount; i++) {
            Integer i1 = this.getSubversion(i);
            Integer i2 = o.getSubversion(i);
            LOG.trace("this.getSubversion({})=", i1);
            LOG.trace("o.getSubversion({})=", i2);
            int tempResult = i1.compareTo(i2);
            if (tempResult != 0) {
                result = tempResult;
                break;
            }
        }
        return result;
    }
    
}
