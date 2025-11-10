#!/bin/bash

# Courier Management System - Database Setup Script
# This script sets up the database for macOS/MAMP

echo "=================================="
echo "Courier Management System v2.0"
echo "Database Setup Script"
echo "=================================="
echo ""

# MAMP MySQL path
MYSQL_PATH="/Applications/MAMP/Library/bin/mysql80/bin/mysql"

# Check if MAMP MySQL exists
if [ ! -f "$MYSQL_PATH" ]; then
    echo "ERROR: MAMP MySQL not found at $MYSQL_PATH"
    echo "Please install MAMP or update the MYSQL_PATH in this script"
    exit 1
fi

echo "Found MySQL at: $MYSQL_PATH"
echo ""

# Ask user what they want to do
echo "What would you like to do?"
echo "1) Fresh install (drops existing database - WARNING: loses all data)"
echo "2) Update existing database (keeps your data, just updates schema)"
echo "3) Cancel"
echo ""
read -p "Enter choice [1-3]: " choice

case $choice in
    1)
        echo ""
        echo "WARNING: This will DELETE all existing data in courier_db!"
        read -p "Are you sure? Type 'YES' to confirm: " confirm
        if [ "$confirm" != "YES" ]; then
            echo "Cancelled."
            exit 0
        fi

        echo ""
        echo "Dropping and recreating database..."
        $MYSQL_PATH -u root -p -e "DROP DATABASE IF EXISTS courier_db; CREATE DATABASE courier_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

        echo "Importing database schema and sample data..."
        $MYSQL_PATH -u root -p courier_db < database/courier_db.sql

        echo ""
        echo "✓ Fresh installation complete!"
        echo ""
        echo "NEXT STEPS:"
        echo "1. Go to: http://localhost:8888/Courier-Management-System/migrate_passwords.php"
        echo "2. Run the password migration"
        echo "3. Then login at: http://localhost:8888/Courier-Management-System/login.php"
        ;;

    2)
        echo ""
        echo "Updating database schema to support password hashing..."
        $MYSQL_PATH -u root -p courier_db -e "ALTER TABLE tbl_courier_officers MODIFY COLUMN off_pwd VARCHAR(255) NOT NULL;"

        echo ""
        echo "✓ Schema updated successfully!"
        echo ""
        echo "NEXT STEPS:"
        echo "1. Go to: http://localhost:8888/Courier-Management-System/migrate_passwords.php"
        echo "2. Run the password migration to hash existing passwords"
        echo "3. Then you can login normally"
        ;;

    3)
        echo "Cancelled."
        exit 0
        ;;

    *)
        echo "Invalid choice. Exiting."
        exit 1
        ;;
esac

echo ""
echo "=================================="
echo "Setup complete!"
echo "=================================="
