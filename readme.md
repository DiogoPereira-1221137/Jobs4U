# Project Jobs4U

## 1. Description of the Project

Jobs4U is a company specialized in talent acquisition. The company provides recruitment services for job positions in its clients. 
The aim of this project is to develop, in an exploratory way, a solution that allows automating the main activities of the company.

## 2. Planning and Technical Documentation

[Planning and Technical Documentation](docs/readme.md)

## 3. How to Build

To build the apps is necessary to run the following commands on cmd/console (ensure that you are on the project folder (sem4pi-23-24-2dl3) before executing the command).

```
buildApp.bat or buildApp.sh (.bat for Windows and .sh Linux)
```

Then type in the number corresponding to the app you want to build.

****

To build all the apps is necessary to run the following commands on cmd/console (ensure that you are on the project folder (sem4pi-23-24-2dl3) before executing the command).

```
build-all.bat or build-all.sh (.bat for Windows and .sh Linux)
```

if you have any problems please check [Guide help](docs/HelpGuide.md).

## 4. How to Execute Tests

To test all the apps  to run the following command on cmd/console (ensure that you are on the project folder (sem4pi-23-24-2dl3) before executing the command).


```
test.bat or test.sh (.bat for Windows and .sh Linux)
```

if you have any problems please check [Guide help](docs/HelpGuide.md).

## 5. How to Run

To run the apps execute the following command on cmd/console (ensure that you are on the project folder (sem4pi-23-24-2dl3) before executing the command).

```
runApp.bat or runApp.sh (.bat for Windows and .sh Linux)
```
Then type in the number corresponding to the app you want to run.

if you have any problems please check [Guide help](docs/HelpGuide.md).


## 6. How to Install/Deploy into Another Machine (or Virtual Machine)
To generate the .jar file for the applications execute the following command on cmd/console (ensure that you are on the project folder (sem4pi-23-24-2dl3) before executing the command).

```
mvn package
```

if you have any problems please check [Guide help](docs/HelpGuide.md).


## 7. How to Generate PlantUML Diagrams
To generate plantuml diagrams for documentation execute the script (ensure that you are on the project folder (sem4pi-23-24-2dl3) before executing the command).

```
generate-plantuml-diagrams.sh
``` 
The generated .svg files should be generated in the same directory as the original .puml file.

if you have any problems please check [Guide help](docs/HelpGuide.md).


## 8. Generate javadoc for the source code
To generate javadocs for the source code run the following command (ensure that you are on the project folder (sem4pi-23-24-2dl3) before executing the command).
```
mvn javadoc:javadoc
```

if you have any problems please check [Guide help](docs/HelpGuide.md).

## 9. Generate javadoc for the test code
To generate javadocs for the tests run the following command (ensure that you are on the project folder (sem4pi-23-24-2dl3) before executing the command).

```
mvn javadoc:test-javadoc
```

if you have any problems please check [Guide help](docs/HelpGuide.md).


## 10. Generate Jacoco source code coverage report
To generate the test report run the following command (ensure that you are on the project folder (sem4pi-23-24-2dl3) before executing the command).

```
mvn test jacoco:report
```

if you have any problems please check [Guide help](docs/HelpGuide.md).