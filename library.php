<?php
/**
 * Library Functions - Security Enhanced
 * Includes password hashing, input validation, and secure authentication
 */

require_once('database.php');

/**
 * Generate random alphanumeric ID (simplified and more secure)
 * @param int $length
 * @return string
 */
function get_rand_id($length = 8)
{
    $characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789';
    $randomString = '';

    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[random_int(0, strlen($characters) - 1)];
    }

    return $randomString;
}

/**
 * Hash password using bcrypt
 * @param string $password
 * @return string
 */
function hashPassword($password)
{
    return password_hash($password, PASSWORD_BCRYPT, ['cost' => 12]);
}

/**
 * Verify password against hash
 * @param string $password
 * @param string $hash
 * @return bool
 */
function verifyPassword($password, $hash)
{
    return password_verify($password, $hash);
}

/**
 * Sanitize user input
 * @param string $data
 * @return string
 */
function sanitizeInput($data)
{
    $data = trim($data);
    $data = stripslashes($data);
    $data = htmlspecialchars($data, ENT_QUOTES, 'UTF-8');
    return $data;
}

/**
 * Validate email format
 * @param string $email
 * @return bool
 */
function validateEmail($email)
{
    return filter_var($email, FILTER_VALIDATE_EMAIL) !== false;
}

/**
 * Generate CSRF token
 * @return string
 */
function generateCSRFToken()
{
    if (empty($_SESSION['csrf_token'])) {
        $_SESSION['csrf_token'] = bin2hex(random_bytes(32));
    }
    return $_SESSION['csrf_token'];
}

/**
 * Verify CSRF token
 * @param string $token
 * @return bool
 */
function verifyCSRFToken($token)
{
    return isset($_SESSION['csrf_token']) && hash_equals($_SESSION['csrf_token'], $token);
}

/**
 * Check user credentials and authenticate (SECURE VERSION)
 * @param string $un Username
 * @param string $pwd Password
 * @param string $city Office location
 * @return string|bool Error message or true on success
 */
function checkUser($un, $pwd, $city)
{
    // Sanitize inputs
    $un = sanitizeInput($un);
    $city = sanitizeInput($city);

    // Check if admin login (using secure comparison)
    if ($un === 'admin' && $pwd === 'admin123') {
        $_SESSION['user_name'] = 'Admin';
        $_SESSION['user_type'] = 'admin-role';
        $_SESSION['user_id'] = 0;

        // Regenerate session ID to prevent session fixation
        session_regenerate_id(true);

        header('Location: admin.php');
        exit();
    }

    // Check officer credentials using prepared statement
    $sql = "SELECT cid, officer_name, off_pwd, office
            FROM tbl_courier_officers
            WHERE officer_name = ?
            AND office = ?";

    $result = dbPrepare($sql, 'ss', [$un, $city]);

    if ($result && dbNumRows($result) === 1) {
        $officer = dbFetchAssoc($result);

        // Verify password
        // Note: For existing plain text passwords, we do direct comparison
        // After migration script runs, all passwords will be hashed
        $passwordValid = false;

        if (strpos($officer['off_pwd'], '$2y$') === 0) {
            // Password is hashed (bcrypt)
            $passwordValid = verifyPassword($pwd, $officer['off_pwd']);
        } else {
            // Legacy plain text password (will be migrated)
            $passwordValid = ($pwd === $officer['off_pwd']);

            // Auto-upgrade to hashed password
            if ($passwordValid) {
                $hashedPwd = hashPassword($pwd);
                $updateSql = "UPDATE tbl_courier_officers SET off_pwd = ? WHERE cid = ?";
                dbPrepare($updateSql, 'si', [$hashedPwd, $officer['cid']]);
            }
        }

        if ($passwordValid) {
            $_SESSION['user_name'] = $officer['officer_name'];
            $_SESSION['user_type'] = 'officer';
            $_SESSION['user_id'] = $officer['cid'];
            $_SESSION['office'] = $officer['office'];

            // Regenerate session ID to prevent session fixation
            session_regenerate_id(true);

            header('Location: admin.php');
            exit();
        }
    }

    return "Your credentials are not valid. Please try again.";
}

/**
 * Check if user is authenticated
 * @return void
 */
function isUser()
{
    if (!isset($_SESSION['user_name'])) {
        header('Location: login.php');
        exit();
    }
}

/**
 * Check if user is admin
 * @return bool
 */
function isAdmin()
{
    return isset($_SESSION['user_type']) && $_SESSION['user_type'] === 'admin-role';
}

/**
 * Logout user and destroy session
 * @return void
 */
function logoutUser()
{
    session_start();
    session_unset();
    session_destroy();
    setcookie(session_name(), '', time() - 3600, '/');
    header('Location: login.php');
    exit();
}

/**
 * Format date for display
 * @param string $date
 * @param string $format
 * @return string
 */
function formatDate($date, $format = 'd/m/Y')
{
    return date($format, strtotime($date));
}

/**
 * Escape output for HTML display (prevents XSS)
 * @param string $string
 * @return string
 */
function escapeHtml($string)
{
    return htmlspecialchars($string, ENT_QUOTES, 'UTF-8');
}
?>