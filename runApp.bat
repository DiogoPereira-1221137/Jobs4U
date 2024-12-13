@echo off

:menu
cls
echo Provide the application number as an argument:
echo 1. jobs4u.backoffice
echo 2. jobs4u.candidate
echo 3. jobs4u.customer
echo 4. jobs4u.fileBot
echo 5. jobs4u.server
echo 6. Exit

set /p choice=Choose:


if "%choice%"=="1" (
    SET BASE_CP=jobs4u.app.backoffice.console/target/jobs4u.app.backoffice.console-0.1.0.jar;jobs4u.app.backoffice.console/target/dependency/*;
    set "app=lapr4.jobs4u.app.backoffice.console.Backoffice"
)

if "%choice%"=="2" (
    SET BASE_CP=jobs4u.app.candidate.console/target/jobs4u.app.candidate.console-0.1.0.jar;jobs4u.app.candidate.console/target/dependency/*;
    set "app=lapr4.jobs4u.app.candidate.console.CandidateApp"
)

if "%choice%"=="3" (
    SET BASE_CP=jobs4u.app.customer.console/target/jobs4u.app.customer.console-0.1.0.jar;jobs4u.app.customer.console/target/dependency/*;
    set "app=lapr4.jobs4u.app.customer.console.CustomerApp"
)

if "%choice%"=="4" (
    SET BASE_CP=jobs4u.fileBot/target/jobs4u.fileBot-0.1.0.jar;jobs4u.fileBot/target/dependency/*;
    set "app=hello.HelloWorld4"
)

if "%choice%"=="5" (
    SET BASE_CP=jobs4u.app.server.console/target/jobs4u.app.server.console-0.1.0.jar;jobs4u.app.server.console/target/dependency/*;
    set "app=lapr4.jobs4u.app.server.console.FollowUpServer"
)


if "%choice%"=="6" (
    echo Exiting...
    exit /b
)

if "%app%"=="" (
    echo Invalid choice. Please enter a valid option.
    pause
    goto menu
)

echo Executing %app%...
java -cp "%BASE_CP%" "%app%"

REM Check the error level
if errorlevel 1 (
    echo An error occurred during execution.
    echo Please check if the class exists.
) else (
    echo Execution completed successfully.
)
