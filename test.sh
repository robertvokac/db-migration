./build.sh&& \
cd ./db-migration-core/target&& \
java \
-cp "db-migration-core-0.0.0-SNAPSHOT-jar-with-dependencies.jar:../../db-migration-test-jar/target/db-migration-test-jar-0.0.0-SNAPSHOT.jar" \
--module-path "db-migration-core-0.0.0-SNAPSHOT-jar-with-dependencies.jar:../../db-migration-test-jar/target/db-migration-test-jar-0.0.0-SNAPSHOT.jar" \
com.robertvokac.dbmigration.core.main.DBMigration \
migrate \
-datasource.jdbc-url=jdbc:sqlite:/home/robertvokac/Desktop/test.sqlite3 \
-name=test -sql-dialect=sqlite \
-sql-migrations-directory=/home/robertvokac/Desktop/testDev/ \
-installed-by=robertvokac \
-sql-dialect-impl-class=com.robertvokac.dbmigration.core.persistence.impl.sqlite.DBMigrationPersistenceSqliteImpl \
&&cd ../..

#com.robertvokac.dbmigration.test.jar.DBMigrationTestJarDummyClass
