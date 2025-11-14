-- Fresh Installation Script for Courier Management System v2.0
-- This script will drop the existing database and recreate it

-- Drop existing database
DROP DATABASE IF EXISTS courier_db;

-- Create new database with UTF-8 MB4 support
CREATE DATABASE courier_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Use the database
USE courier_db;

-- Now run the courier_db.sql file after this
