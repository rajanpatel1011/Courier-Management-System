# Courier Management System - v2.0 (Modernized)

A secure, modern courier and package tracking system built with PHP and MySQL.

## 📦 Features

### For Customers
- Track packages using consignment numbers
- View real-time package status
- Check delivery history

### For Managers
- Add new couriers/packages
- Update courier status
- Manage customer details
- View courier lists and reports

### For Administrators
- Add new branches and offices
- Manage manager accounts
- Full system access

## 🔒 Security Features (v2.0)

- ✅ **mysqli** with prepared statements (SQL injection prevention)
- ✅ **Bcrypt password hashing** (strong encryption)
- ✅ **CSRF protection** on all forms
- ✅ **XSS prevention** (input/output sanitization)
- ✅ **Session security** (fixation prevention, timeout)
- ✅ **Security headers** (X-Frame-Options, XSS-Protection)
- ✅ **Input validation** and sanitization
- ✅ **Secure authentication** with automatic password upgrades

## 🚀 Quick Start

### 1. Install MAMP (for MacBook)
Download from: https://www.mamp.info/

### 2. Setup Database
```bash
# Import the SQL file
mysql -u root -p courier_db < database/courier_db.sql
```

### 3. Configure
Edit `database.php`:
```php
$dbPass = 'root';  // MAMP default
```

### 4. Run Migration
Visit: `http://localhost:8888/Courier-Management-System/migrate_passwords.php`

### 5. Access
- **Homepage**: http://localhost:8888/Courier-Management-System/
- **Login**: http://localhost:8888/Courier-Management-System/login.php

## 🔐 Default Credentials

**Admin:**
- Username: `admin`
- Password: `admin123`

**Managers:**
- Username: `kapil` | Password: `kapil`
- Username: `ashraf` | Password: `ashraf`
- Username: `sunil` | Password: `sunil`

## 📚 Documentation

See [SETUP.md](SETUP.md) for detailed installation and configuration instructions.

## 🛠️ Tech Stack

- **PHP**: 7.4+
- **MySQL**: 5.7+
- **Apache**: 2.4+
- **Security**: mysqli, Bcrypt, CSRF tokens

## 📋 Requirements

- PHP 7.4 or higher
- MySQL 5.7 or higher
- Apache web server
- PHP extensions: mysqli, session

## 🎯 Version History

### v2.0 (2025) - Modernization Update
- Migrated from deprecated mysql_* to mysqli
- Added password hashing with bcrypt
- Implemented prepared statements
- Added comprehensive security features
- Improved code structure and documentation

### v1.0 (2011) - Original Version
- Basic courier management functionality
- Plain text authentication
- Legacy mysql_* functions

## 📄 License

Educational project - Use with caution in production.

## 🤝 Contributing

This is a modernized version of an educational project. Feel free to fork and improve!

---

**For full setup instructions, troubleshooting, and security best practices, see [SETUP.md](SETUP.md)**
