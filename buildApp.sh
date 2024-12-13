#!/bin/bash

# Store the current directory
current_dir=$(pwd)

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
        1)
            app="jobs4u.backoffice"
            ;;
        2)
            app="jobs4u.candidate"
            ;;
        3)
            app="jobs4u.customer"
            ;;
        4)
            app="jobs4u.fileBot"
            ;;
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

    cd "../$app" || {
        echo "Failed to change directory. Please check if the directory exists."
        read -n 1 -s -r -p "Press any key to continue..."
        menu
    }

    mvn clean package
    mvn verify package

    # Change back to the original directory
    cd "$current_dir"

    menu
}

menu
