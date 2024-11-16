@echo off
REM Deleting .jar and .class files in all folders and subfolders
del /s /q *.jar
del /s /q *.class
del /s /q *.jar*
del /s /q *.class*

echo.
echo -----
echo Downloading SQLite Adapter:
echo -----
curl -O https://repo1.maven.org/maven2/org/xerial/sqlite-jdbc/3.47.0.0/sqlite-jdbc-3.47.0.0.jar

echo.
echo -----
echo Downloading JUnit:
echo -----
curl -O https://repo1.maven.org/maven2/org/junit/platform/junit-platform-console-standalone/1.9.3/junit-platform-console-standalone-1.9.3.jar
curl -O https://repo1.maven.org/maven2/org/hamcrest/hamcrest-core/1.3/hamcrest-core-1.3.jar

echo.
echo -----
echo Downloading Checkstyle:
echo -----
curl -L -O https://github.com/checkstyle/checkstyle/releases/download/checkstyle-10.18.2/checkstyle-10.18.2-all.jar

echo.
echo -----
echo Making local copy of database
echo -----
copy users.dat usersBackup.dat

echo.
echo -----
echo Running Checkstyle Checks:
echo -----
java -jar checkstyle-10.18.2-all.jar -c checkstyle.xml */*.java
java -jar checkstyle-10.18.2-all.jar -c checkstyle.xml *.java

echo.
echo -----
echo Checking for Compilation Errors:
echo -----
javac -cp "junit-platform-console-standalone-1.9.3.jar;hamcrest-core-1.3.jar;sqlite-jdbc-3.47.0.0.jar;." *.java
@REM java -cp "junit-platform-console-standalone-1.9.3.jar;hamcrest-core-1.3.jar;sqlite-jdbc-3.47.0.0.jar;." RunLocalTest


echo.
echo -----
echo Running Local Tests:
echo -----
java -cp "junit-platform-console-standalone-1.9.3.jar;hamcrest-core-1.3.jar;sqlite-jdbc-3.47.0.0.jar;." RunLocalTest

echo.
echo -----
echo Resetting local copy of database
echo -----
move /y usersBackup.dat users.dat

REM Clean up .jar and .class files after testing
del /s /q *.jar
del /s /q *.class

