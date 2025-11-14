<?php
/**
 * Password Migration Script
 * This script updates all plain text passwords to bcrypt hashed passwords
 *
 * IMPORTANT: Run this script ONCE after upgrading the system
 * Access: http://localhost:8888/Courier-Management-System/migrate_passwords.php
 */

// Security: Disable this script after migration
// Set this to false after running the migration
$MIGRATION_ENABLED = true;

if (!$MIGRATION_ENABLED) {
    die('Migration has been disabled. If you need to run it again, edit migrate_passwords.php');
}

// Disable error display temporarily to avoid showing connection errors
error_reporting(E_ALL);
ini_set('display_errors', 0);

// Database configuration
$dbHost = 'localhost';
$dbUser = 'root';
$dbPass = 'root';  // Change if needed
$dbName = 'courier_db';

// Connect to database
$dbConn = @mysqli_connect($dbHost, $dbUser, $dbPass, $dbName);

if (!$dbConn) {
    die("<h2>Database Connection Error</h2><p style='color: red;'>Could not connect to database: " . mysqli_connect_error() . "</p><p>Please check your database credentials in migrate_passwords.php (lines 20-23)</p>");
}

mysqli_set_charset($dbConn, 'utf8mb4');

echo "<!DOCTYPE html><html><head><meta charset='UTF-8'><title>Password Migration</title></head><body>";
echo "<h2>Password Migration Script</h2>";
echo "<p>Starting migration process...</p>";

// First, update the database schema to support longer password hashes
echo "<h3>Step 1: Updating Database Schema</h3>";

$alterSql = "ALTER TABLE tbl_courier_officers MODIFY COLUMN off_pwd VARCHAR(255) NOT NULL";
$alterResult = @mysqli_query($dbConn, $alterSql);

if ($alterResult) {
    echo "<p style='color: green;'>✓ Database schema updated successfully</p>";
} else {
    // Check if error is because column is already correct size
    $error = mysqli_error($dbConn);
    if (strpos($error, 'Duplicate column name') !== false || strpos($error, 'check the manual') !== false) {
        echo "<p style='color: orange;'>⚠ Schema may already be updated</p>";
    } else {
        echo "<p style='color: orange;'>⚠ Schema update note: " . htmlspecialchars($error) . "</p>";
    }
}

// Migrate existing passwords
echo "<h3>Step 2: Hashing Existing Passwords</h3>";

$sql = "SELECT cid, officer_name, off_pwd FROM tbl_courier_officers";
$result = mysqli_query($dbConn, $sql);

if (!$result) {
    die("<p style='color: red;'>Error fetching officers: " . htmlspecialchars(mysqli_error($dbConn)) . "</p>");
}

$updated = 0;
$skipped = 0;

echo "<ul>";
while ($officer = mysqli_fetch_assoc($result)) {
    // Check if password is already hashed
    if (strpos($officer['off_pwd'], '$2y$') === 0) {
        echo "<li>" . htmlspecialchars($officer['officer_name']) . " - Already hashed (skipped)</li>";
        $skipped++;
        continue;
    }

    // Hash the plain text password using bcrypt
    $hashedPassword = password_hash($officer['off_pwd'], PASSWORD_BCRYPT, ['cost' => 12]);

    // Update the database using prepared statement
    $stmt = mysqli_prepare($dbConn, "UPDATE tbl_courier_officers SET off_pwd = ? WHERE cid = ?");
    mysqli_stmt_bind_param($stmt, 'si', $hashedPassword, $officer['cid']);
    mysqli_stmt_execute($stmt);

    echo "<li style='color: green;'>✓ " . htmlspecialchars($officer['officer_name']) . " - Password hashed successfully</li>";
    $updated++;
}
echo "</ul>";

echo "<h3>Migration Summary</h3>";
echo "<p>Passwords updated: <strong>$updated</strong></p>";
echo "<p>Already hashed (skipped): <strong>$skipped</strong></p>";
echo "<p style='color: green; font-weight: bold;'>✓ Migration completed successfully!</p>";

echo "<hr>";
echo "<h3>Current Officer Login Credentials</h3>";
echo "<table border='1' cellpadding='10' style='border-collapse: collapse;'>";
echo "<tr><th>Username</th><th>Office</th><th>Note</th></tr>";

$sql = "SELECT officer_name, office FROM tbl_courier_officers";
$result = mysqli_query($dbConn, $sql);

while ($officer = mysqli_fetch_assoc($result)) {
    echo "<tr>";
    echo "<td>" . htmlspecialchars($officer['officer_name']) . "</td>";
    echo "<td>" . htmlspecialchars($officer['office']) . "</td>";
    echo "<td>Password remains the same (now securely hashed)</td>";
    echo "</tr>";
}
echo "</table>";

echo "<p style='background: #fff3cd; padding: 10px; border: 1px solid #ffc107;'>";
echo "<strong>IMPORTANT:</strong> Set <code>\$MIGRATION_ENABLED = false;</code> in this file (line 12) to disable this script after migration.";
echo "</p>";

echo "<p><a href='login.php'>Go to Login Page</a></p>";
echo "</body></html>";

// Close connection
mysqli_close($dbConn);
?>
