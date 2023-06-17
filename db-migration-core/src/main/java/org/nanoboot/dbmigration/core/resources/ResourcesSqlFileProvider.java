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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.nanoboot.dbmigration.core.main.DBMigrationException;
import org.nanoboot.dbmigration.core.utils.DBMigrationConstants;

/**
 *
 * @author <a href="mailto:robertvokac@nanoboot.org">Robert Vokac</a>
 * @since 0.1.0
 */
public final class ResourcesSqlFileProvider implements SqlFileProvider {
private static final Logger LOG = LogManager.getLogger(ResourcesSqlFileProvider.class);
    public static String createPath(String sqlDialect, String nameOfMigrations) {
        return DBMigrationConstants.RESOURCES_ROOT_DIRECTORY_NAME + File.separator + sqlDialect + File.separator + nameOfMigrations;
    }

    @Override
    public List<SqlFile> provide(String path) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<SqlFile> provide(String path, Class clazz) {
        return loadSqlFilesFromJarResources(path, clazz);
    }

    private List<SqlFile> loadSqlFilesFromJarResources(String path, Class<?> clazz) {
        try {
            return loadSqlFilesFromJarResourcesWithIOException(path, clazz);
        } catch (IOException ex) {
            LOG.error("Loading sql files from jar failed:",ex);
            throw new DBMigrationException(ex);
        }
    }

    private static List<SqlFile> loadSqlFilesFromJarResourcesWithIOException(String p, Class<?> clazz) throws IOException {
        List<SqlFile> result = new ArrayList<>();
        try {
            List<Path> paths = getPathsFromResourceJar(p, clazz);
            for (Path path : paths) {
                LOG.debug("Path : {}", path);

                String filePathInJAR = path.toString();
                
                if (filePathInJAR.startsWith("/")) {
                    filePathInJAR = filePathInJAR.substring(1, filePathInJAR.length());
                }

                LOG.debug("filePathInJAR : {}", filePathInJAR);
                InputStream is = getFileFromResourceAsStream(filePathInJAR, clazz);
                String text = loadTextFromInputStream(is);
                SqlFile sf = new SqlFile(path.getFileName().toString(), text);
                result.add(sf);
            }

        } catch (URISyntaxException | IOException e) {
            LOG.error(e);
            throw new DBMigrationException(e);
        }
        return result;
    }

    private static InputStream getFileFromResourceAsStream(String fileName, Class<?> clazz) {
        ClassLoader classLoader = clazz.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            LOG.error("File not found! {}",fileName);
            throw new IllegalArgumentException("File not found! " + fileName);
        } else {
            return inputStream;
        }

    }

    private static List<Path> getPathsFromResourceJar(String folder, Class<?> clazz)
            throws URISyntaxException, IOException {
        List<Path> result;

        String jarPath = clazz.getProtectionDomain()
                .getCodeSource()
                .getLocation()
                .toURI()
                .getPath();
        LOG.debug("JAR Path :{}",jarPath);

        URI uri = URI.create("jar:file:" + jarPath);
        try ( FileSystem fs = FileSystems.newFileSystem(uri, Collections.emptyMap());Stream<Path> stream = Files.walk(fs.getPath(folder))) {
            result = stream
                    .filter(Files::isRegularFile)
                    .toList();
        } 

        return result;

    }

    private static String loadTextFromInputStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        try ( InputStreamReader streamReader = new InputStreamReader(is, StandardCharsets.UTF_8);  BufferedReader reader = new BufferedReader(streamReader)) {

            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }
}
