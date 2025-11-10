<?php
/**
 * Password Migration Script
 * This script updates all plain text passwords to bcrypt hashed passwords
 *
 * IMPORTANT: Run this script ONCE after upgrading the system
 * Access: http://localhost:8888/Courier-Management-System/migrate_passwords.php
 */

define('APP_ACCESS', true);
require_once('database.php');
require_once('library.php');

// Security: Disable this script after migration
// Set this to false after running the migration
$MIGRATION_ENABLED = true;

if (!$MIGRATION_ENABLED) {
    die('Migration has been disabled. If you need to run it again, edit migrate_passwords.php');
}

echo "<h2>Password Migration Script</h2>";
echo "<p>Starting migration process...</p>";

// First, update the database schema to support longer password hashes
echo "<h3>Step 1: Updating Database Schema</h3>";

try {
    // Update tbl_courier_officers password field to support bcrypt hashes (60 chars)
    $alterSql = "ALTER TABLE tbl_courier_officers
                 MODIFY COLUMN off_pwd VARCHAR(255) NOT NULL";
    dbQuery($alterSql);
    echo "<p style='color: green;'>✓ Database schema updated successfully</p>";
} catch (Exception $e) {
    echo "<p style='color: orange;'>⚠ Schema may already be updated or error occurred: " . $e->getMessage() . "</p>";
}

// Migrate existing passwords
echo "<h3>Step 2: Hashing Existing Passwords</h3>";

$sql = "SELECT cid, officer_name, off_pwd FROM tbl_courier_officers";
$result = dbQuery($sql);

$updated = 0;
$skipped = 0;

echo "<ul>";
while ($officer = dbFetchAssoc($result)) {
    // Check if password is already hashed
    if (strpos($officer['off_pwd'], '$2y$') === 0) {
        echo "<li>" . escapeHtml($officer['officer_name']) . " - Already hashed (skipped)</li>";
        $skipped++;
        continue;
    }

    // Hash the plain text password
    $hashedPassword = hashPassword($officer['off_pwd']);

    // Update the database
    $updateSql = "UPDATE tbl_courier_officers SET off_pwd = ? WHERE cid = ?";
    dbPrepare($updateSql, 'si', [$hashedPassword, $officer['cid']]);

    echo "<li style='color: green;'>✓ " . escapeHtml($officer['officer_name']) . " - Password hashed successfully</li>";
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
$result = dbQuery($sql);

while ($officer = dbFetchAssoc($result)) {
    echo "<tr>";
    echo "<td>" . escapeHtml($officer['officer_name']) . "</td>";
    echo "<td>" . escapeHtml($officer['office']) . "</td>";
    echo "<td>Password remains the same (now securely hashed)</td>";
    echo "</tr>";
}
echo "</table>";

echo "<p style='background: #fff3cd; padding: 10px; border: 1px solid #ffc107;'>";
echo "<strong>IMPORTANT:</strong> Set <code>\$MIGRATION_ENABLED = false;</code> in this file to disable this script after migration.";
echo "</p>";

echo "<p><a href='login.php'>Go to Login Page</a></p>";
?>
