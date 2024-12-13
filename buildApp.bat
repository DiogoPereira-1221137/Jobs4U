@echo off

rem Store the current directory
set "current_dir=%CD%"

:menu
cls
echo Provide the application number as an argument:
echo 1. jobs4u.backoffice
echo 2. jobs4u.candidate
echo 3. jobs4u.customer
echo 4. jobs4u.fileBot
echo 5. Exit

set /p choice=Choose:

if "%choice%"=="1" (
    set "app=jobs4u.backoffice"
)

if "%choice%"=="2" (
    set "app=jobs4u.candidate"
)

if "%choice%"=="3" (
    set "app=jobs4u.customer"
)

if "%choice%"=="4" (
    set "app=jobs4u.fileBot"
)

if "%choice%"=="5" (
    echo Exiting...
    exit /b
)

if "%app%"=="" (
    echo Invalid choice. Please enter a valid option.
    pause
    goto menu
)

cd /d "%app%" || (
    echo Failed to change directory. Please check if the directory exists.
    pause
    goto menu
)

mvn clean package
mvn verify package

rem Change back to the original directory
cd /d "%current_dir%"

goto menu
a