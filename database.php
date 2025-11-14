<?php
/**
 * Database Connection and Helper Functions
 * Modernized to use mysqli with prepared statements support
 */

// Enable error reporting for development (disable in production)
error_reporting(E_ALL);
ini_set('display_errors', 1);

// Database connection config
$dbHost = 'localhost';
$dbUser = 'root';
$dbPass = '';  // Change to 'root' for MAMP
$dbName = 'courier_db';

// Create mysqli connection
$dbConn = mysqli_connect($dbHost, $dbUser, $dbPass, $dbName);

// Check connection
if (!$dbConn) {
    die('MySQL connect failed: ' . mysqli_connect_error());
}

// Set charset to prevent SQL injection via encoding
mysqli_set_charset($dbConn, 'utf8mb4');

/**
 * Execute a query (use for SELECT, UPDATE, DELETE)
 * @param string $sql
 * @return mysqli_result|bool
 */
function dbQuery($sql)
{
    global $dbConn;
    $result = mysqli_query($dbConn, $sql);

    if (!$result) {
        error_log('MySQL Error: ' . mysqli_error($dbConn));
        die('Database query failed: ' . mysqli_error($dbConn));
    }

    return $result;
}

/**
 * Execute a prepared statement (SECURE - prevents SQL injection)
 * @param string $sql SQL with ? placeholders
 * @param string $types Parameter types (e.g., 'ssi' for string, string, int)
 * @param array $params Array of parameters
 * @return mysqli_result|bool
 */
function dbPrepare($sql, $types, $params)
{
    global $dbConn;
    $stmt = mysqli_prepare($dbConn, $sql);

    if (!$stmt) {
        error_log('MySQL Prepare Error: ' . mysqli_error($dbConn));
        die('Database prepare failed: ' . mysqli_error($dbConn));
    }

    if (!empty($params)) {
        mysqli_stmt_bind_param($stmt, $types, ...$params);
    }

    mysqli_stmt_execute($stmt);

    $result = mysqli_stmt_get_result($stmt);
    if (!$result) {
        $result = $stmt; // For INSERT/UPDATE/DELETE
    }

    return $result;
}

/**
 * Get number of affected rows from last operation
 * @return int
 */
function dbAffectedRows()
{
    global $dbConn;
    return mysqli_affected_rows($dbConn);
}

/**
 * Fetch array from result
 * @param mysqli_result $result
 * @param int $resultType
 * @return array|null
 */
function dbFetchArray($result, $resultType = MYSQLI_NUM)
{
    if (!$result) return null;
    return mysqli_fetch_array($result, $resultType);
}

/**
 * Fetch associative array from result
 * @param mysqli_result $result
 * @return array|null
 */
function dbFetchAssoc($result)
{
    if (!$result) return null;
    return mysqli_fetch_assoc($result);
}

/**
 * Fetch row from result
 * @param mysqli_result $result
 * @return array|null
 */
function dbFetchRow($result)
{
    if (!$result) return null;
    return mysqli_fetch_row($result);
}

/**
 * Free result memory
 * @param mysqli_result $result
 * @return void
 */
function dbFreeResult($result)
{
    if ($result && $result instanceof mysqli_result) {
        mysqli_free_result($result);
    }
}

/**
 * Get number of rows in result
 * @param mysqli_result $result
 * @return int
 */
function dbNumRows($result)
{
    if (!$result) return 0;
    return mysqli_num_rows($result);
}

/**
 * Select database
 * @param string $dbName
 * @return bool
 */
function dbSelect($dbName)
{
    global $dbConn;
    return mysqli_select_db($dbConn, $dbName);
}

/**
 * Get last inserted ID
 * @return int
 */
function dbInsertId()
{
    global $dbConn;
    return mysqli_insert_id($dbConn);
}

/**
 * Escape string for SQL (legacy support - use dbPrepare instead)
 * @param string $string
 * @return string
 */
function dbEscape($string)
{
    global $dbConn;
    return mysqli_real_escape_string($dbConn, $string);
}

/**
 * Close database connection
 * @return void
 */
function dbClose()
{
    global $dbConn;
    if ($dbConn) {
        mysqli_close($dbConn);
    }
}

// Register shutdown function to close connection
register_shutdown_function('dbClose');
?>