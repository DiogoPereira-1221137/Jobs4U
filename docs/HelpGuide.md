# Help Guide

## JAVA_HOME error

*MavenReportException: Error while generating Javadoc: Unable to find javadoc command: The environment variable JAVA_HOME is not correctly set.*

To resolve this issue follow these steps:
1. Go to your java jdk folder (for this go to Local Disk (:C), then open the folder Program, look for the Java folder and open it)
2. Now copy the path to the java jdk folder (make sure that the version is correct - we are using the jdk-19)
3. Open the search for applications (click on windowns button) and type "environment variable" and enter
4. Click on the Environment Variables button (It will open another window)
5. Click on the New option bellow the System Variables
6. In Variable name type "%JAVA_HOME%" and Variable value paste the path to the java jdk folder and click OK

if you have another issue check [help video](https://youtu.be/eDjkU9Qq_i4?feature=shared).


## Manven error

*mvn command is not recognized as an internal or external command.*

To resolve this issue follow these steps:
1. Go to your maven folder (for this go to Local Disk (:C), then open the folder Program, look for the Java folder and open it)
2. Now copy the path to the maven folder (like apache-maven-3.2.3)
3. Open the search for aplications (clice on widowns button) and type "environment variable" and enter
4. Click on the Environment Variables button (It will opne another window)
5. Click on path and then click Edit option bellow the System Variables (It will opne another window)
6. Paste the path to the maven folder and click OK

if you have another issue check [help video](https://youtu.be/eDjkU9Qq_i4?feature=shared).

## running runApp

*O sistema não conseguiu localizar o caminho especificado*

To resolve this issue you need to make sure that you have the .jar (in the target folder) in the app.
If this is not the case, run the following commands on cmd/console (ensure that you are on the project folder (sem4pi-23-24-2dl3) before executing the command).

```
cd scripts
```
```
buildApp.bat or buildApp.sh (.bat for Windows and .sh Linux)
```

Then type in the number corresponding to the app you want to build.


