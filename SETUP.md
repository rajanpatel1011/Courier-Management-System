# Courier Management System - Setup Guide

## Version 2.0 - Modernized & Secure

This guide will help you set up the modernized Courier Management System on your MacBook or any development environment.

---

## 🚀 What's New in Version 2.0

### Security Improvements
- ✅ **mysqli** instead of deprecated `mysql_*` functions
- ✅ **Prepared statements** to prevent SQL injection
- ✅ **Bcrypt password hashing** (PASSWORD_BCRYPT)
- ✅ **Session security** (CSRF protection, session fixation prevention)
- ✅ **XSS prevention** with input/output sanitization
- ✅ **Security headers** (X-Frame-Options, XSS-Protection, etc.)

### Code Quality
- ✅ Modern PHP 7.4+ compatible
- ✅ Proper error handling and logging
- ✅ Code documentation with PHPDoc
- ✅ Separated configuration (config.php)
- ✅ .htaccess security rules

---

## 📋 Prerequisites

### Required Software
- **PHP**: 7.4 or higher
- **MySQL**: 5.7 or higher
- **Apache**: 2.4 or higher
- **Web Server**: MAMP, XAMPP, WAMP, or built-in PHP server

### MacBook Setup Options

#### Option 1: MAMP (Recommended)
- Download from: https://www.mamp.info/
- Includes: Apache, MySQL, PHP pre-configured
- Easy GUI interface

#### Option 2: Homebrew
```bash
brew install php mysql apache2
```

---

## 🔧 Installation Steps

### Step 1: Clone/Download the Project

```bash
# If using git
git clone <repository-url>

# Or download and extract the ZIP file
```

### Step 2: Move to Web Root

**For MAMP:**
```bash
cp -r Courier-Management-System /Applications/MAMP/htdocs/
```

**For XAMPP (macOS):**
```bash
cp -r Courier-Management-System /Applications/XAMPP/htdocs/
```

**For built-in PHP server:**
```bash
cd Courier-Management-System
php -S localhost:8000
```

### Step 3: Configure Database

1. **Start MySQL** (via MAMP/XAMPP or manually):
   ```bash
   # If using Homebrew
   brew services start mysql
   ```

2. **Create Database:**
   ```sql
   CREATE DATABASE courier_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
   ```

3. **Import SQL File:**

   **Via phpMyAdmin:**
   - Go to: http://localhost:8888/phpMyAdmin
   - Select `courier_db` database
   - Click Import → Choose `database/courier_db.sql`
   - Click Go

   **Via Command Line:**
   ```bash
   mysql -u root -p courier_db < database/courier_db.sql
   ```

### Step 4: Configure Application

Edit `database.php` (lines 12-15):

```php
// For MAMP
$dbHost = 'localhost';
$dbUser = 'root';
$dbPass = 'root';  // MAMP default password
$dbName = 'courier_db';

// For Homebrew/custom MySQL
$dbPass = 'your_mysql_password';
```

### Step 5: Run Password Migration

**IMPORTANT:** This step hashes all existing passwords securely.

1. Open browser: `http://localhost:8888/Courier-Management-System/migrate_passwords.php`
2. Wait for migration to complete
3. After successful migration, edit `migrate_passwords.php` and set:
   ```php
   $MIGRATION_ENABLED = false;
   ```

### Step 6: Access the Application

**Homepage:**
```
http://localhost:8888/Courier-Management-System/
```

**Admin Login:**
```
http://localhost:8888/Courier-Management-System/login.php
```

**Track Package:**
```
http://localhost:8888/Courier-Management-System/track-status.php
```

---

## 🔐 Default Login Credentials

### Admin Account
- **Username:** `admin`
- **Password:** `admin123`

### Manager Accounts

| Username | Password | Office |
|----------|----------|--------|
| kapil | kapil | Fast Courier - Jalgaon |
| ashraf | ashraf | Fast Courier - Aurangabad |
| sunil | sunil | Fast Courier - Pune |

**⚠️ IMPORTANT:** Change these passwords after first login!

---

## 🧪 Testing the Application

### 1. Test Login
- Go to login page
- Try logging in with admin credentials
- Try logging in with a manager account

### 2. Test Courier Management
- Add a new courier
- Search for existing couriers
- Update courier status
- Track courier by consignment number

### 3. Test Security
- Try SQL injection in search fields (should be blocked)
- Check if passwords are hashed in database
- Verify session timeout works

---

## 🐛 Troubleshooting

### Issue: "mysqli_connect(): Connection refused"
**Solution:**
- Make sure MySQL is running
- Check database credentials in `database.php`
- For MAMP: Verify port is 3306 or 8889

### Issue: "Call to undefined function mysqli_connect()"
**Solution:**
- Enable mysqli extension in php.ini:
  ```ini
  extension=mysqli
  ```
- Restart Apache

### Issue: Blank page / No errors shown
**Solution:**
- Enable error reporting in `database.php`:
  ```php
  error_reporting(E_ALL);
  ini_set('display_errors', 1);
  ```

### Issue: Password migration fails
**Solution:**
- Check if database user has ALTER permissions
- Run SQL manually:
  ```sql
  ALTER TABLE tbl_courier_officers MODIFY COLUMN off_pwd VARCHAR(255);
  ```

### Issue: Session timeout too short
**Solution:**
- Edit `config.php` (line 18):
  ```php
  define('SESSION_LIFETIME', 7200); // 2 hours
  ```

---

## 📁 Project Structure

```
Courier-Management-System/
├── config.php              # Configuration settings
├── database.php            # Database connection (mysqli)
├── library.php             # Helper functions & security
├── migrate_passwords.php   # Password migration script
├── logout.php              # Logout handler
├── .htaccess              # Apache security rules
├── index.php              # Homepage
├── login.php              # Login page
├── admin.php              # Admin dashboard
├── add-courier.php        # Add new courier
├── courier-list.php       # View all couriers
├── track-status.php       # Track courier by number
├── database/
│   └── courier_db.sql     # Database schema & sample data
├── css/                   # Stylesheets
├── images/                # Images and uploads
└── SETUP.md              # This file
```

---

## 🔒 Security Best Practices

### For Development
1. Keep `ENVIRONMENT` set to `'development'` in `config.php`
2. Use strong passwords even in development
3. Don't commit real passwords to git

### For Production
1. **Change `ENVIRONMENT` to `'production'`** in `config.php`
2. **Use HTTPS** (uncomment HTTPS redirect in `.htaccess`)
3. **Change all default passwords**
4. **Disable error display** (already done in production mode)
5. **Set restrictive file permissions:**
   ```bash
   chmod 644 *.php
   chmod 755 images/
   chmod 600 config.php
   ```
6. **Create logs directory:**
   ```bash
   mkdir logs
   chmod 777 logs
   ```

---

## 🚀 Deployment Checklist

- [ ] Change all default passwords
- [ ] Set `ENVIRONMENT = 'production'` in config.php
- [ ] Enable HTTPS and force SSL
- [ ] Update database credentials
- [ ] Disable migrate_passwords.php
- [ ] Set proper file permissions
- [ ] Test all functionality
- [ ] Set up database backups
- [ ] Configure error logging
- [ ] Review .htaccess security rules

---

## 📝 Additional Notes

### Database Schema Changes
The modernization includes an automatic schema update:
- `tbl_courier_officers.off_pwd` changed from VARCHAR(40) to VARCHAR(255)

This allows storing bcrypt hashes (60 characters).

### Backward Compatibility
The system automatically detects and upgrades plain text passwords to hashed versions on login. This ensures smooth transition without data loss.

### Session Management
- Sessions expire after 1 hour of inactivity (configurable)
- Session IDs are regenerated every 30 minutes
- Session fixation attacks are prevented
- HttpOnly and SameSite cookies are enforced

---

## 🤝 Support

If you encounter issues:
1. Check the Troubleshooting section above
2. Verify all prerequisites are installed
3. Check PHP error logs
4. Ensure MySQL is running

---

## 📄 License

This is an educational project. Use at your own risk in production environments.

---

**Version:** 2.0
**Last Updated:** 2025
**PHP Version:** 7.4+
**MySQL Version:** 5.7+
