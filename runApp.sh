#!/bin/bash

# Menu function
menu() {
    clear
    echo "Provide the application number as an argument:"
    echo "1. jobs4u.backoffice"
    echo "2. jobs4u.candidate"
    echo "3. jobs4u.customer"
    echo "4. jobs4u.fileBot"
    echo "5. Exit"
    read -p "Choose: " choice

    case $choice in
        1) app="jobs4u.backoffice" ;;
        2) app="jobs4u.candidate" ;;
        3) app="jobs4u.customer" ;;
        4) app="jobs4u.fileBot" ;;
        5)
            echo "Exiting..."
            exit 0
            ;;
        *)
            echo "Invalid choice. Please enter a valid option."
            read -n 1 -s -r -p "Press any key to continue..."
            menu
            ;;
    esac

    if [ -z "$app" ]; then
        echo "Invalid choice. Please enter a valid option."
        read -n 1 -s -r -p "Press any key to continue..."
        menu
    fi

    cd "../$app/target" || {
        echo "Failed to change directory. Please check if the directory exists."
        read -n 1 -s -r -p "Press any key to continue..."
        menu
    }

    if [ -f "${app}-0.1.0.jar" ]; then
        echo "Executing ${app}-0.1.0.jar..."
        java -jar "${app}-0.1.0.jar"
    else
        echo "File ${app}-0.1.0.jar not found. Please call compile.sh (in sem4pi-23-24-2dl3/scripts)"
    fi

    menu
}

menu
