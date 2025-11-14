<?php
/**
 * Logout Page
 * Destroys user session and redirects to login
 */

session_start();
require_once('library.php');

logoutUser();
?>
