<?php
/**
 * Process Handler - Modernized with Prepared Statements
 * Handles all form submissions with proper security
 */

// Start session
session_start();

require_once('database.php');
require_once('library.php');

$action = isset($_GET['action']) ? $_GET['action'] : '';

switch($action) {
	case 'add-cons':
		addCons();
	break;

	case 'delivered':
		markDelivered();
	break;

	case 'add-office':
		addNewOffice();
	break;

	case 'add-manager':
		addManager();
	break;

	case 'update-status':
		updateStatus();
	break;

	case 'change-pass':
		changePass();
	break;

	case 'logOut':
		logoutUser(); // Use the secure logout function
	break;

	default:
		die('Invalid action');
}

/**
 * Add new courier/consignment - SECURE VERSION
 */
function addCons(){
	// Sanitize all inputs
	$Shippername = sanitizeInput($_POST['Shippername']);
	$Shipperphone = sanitizeInput($_POST['Shipperphone']);
	$Shipperaddress = sanitizeInput($_POST['Shipperaddress']);

	$Receivername = sanitizeInput($_POST['Receivername']);
	$Receiverphone = sanitizeInput($_POST['Receiverphone']);
	$Receiveraddress = sanitizeInput($_POST['Receiveraddress']);

	$ConsignmentNo = sanitizeInput($_POST['ConsignmentNo']);
	$Shiptype = sanitizeInput($_POST['Shiptype']);
	$Weight = (float)$_POST['Weight'];
	$Invoiceno = sanitizeInput($_POST['Invoiceno']);
	$Qnty = (int)$_POST['Qnty'];

	$Bookingmode = sanitizeInput($_POST['Bookingmode']);
	$Totalfreight = sanitizeInput($_POST['Totalfreight']);
	$Mode = sanitizeInput($_POST['Mode']);

	$Packupdate = sanitizeInput($_POST['Packupdate']);
	$Pickuptime = sanitizeInput($_POST['Pickuptime']);
	$status = sanitizeInput($_POST['status']);
	$Comments = sanitizeInput($_POST['Comments']);

	// Use prepared statement to prevent SQL injection
	$sql = "INSERT INTO tbl_courier (cons_no, ship_name, phone, s_add, rev_name, r_phone, r_add, type, weight, invice_no, qty, book_mode, freight, mode, pick_date, pick_time, status, comments, book_date)
			VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, NOW())";

	dbPrepare($sql, 'ssssssssdsisssssss', [
		$ConsignmentNo,
		$Shippername,
		$Shipperphone,
		$Shipperaddress,
		$Receivername,
		$Receiverphone,
		$Receiveraddress,
		$Shiptype,
		$Weight,
		$Invoiceno,
		$Qnty,
		$Bookingmode,
		$Totalfreight,
		$Mode,
		$Packupdate,
		$Pickuptime,
		$status,
		$Comments
	]);

	header('Location: courier-add-success.php');
	exit();
}

/**
 * Mark courier as delivered - Already secure (uses int cast)
 */
function markDelivered() {
	$cid = (int)$_GET['cid'];

	$sql = "UPDATE tbl_courier SET status = ? WHERE cid = ?";
	dbPrepare($sql, 'si', ['Delivered', $cid]);

	header('Location: delivered-success.php');
	exit();
}

/**
 * Add new office - SECURE VERSION
 */
function addNewOffice() {
	$OfficeName = sanitizeInput($_POST['OfficeName']);
	$OfficeAddress = sanitizeInput($_POST['OfficeAddress']);
	$City = sanitizeInput($_POST['City']);
	$PhoneNo = sanitizeInput($_POST['PhoneNo']);
	$OfficeTiming = sanitizeInput($_POST['OfficeTiming']);
	$ContactPerson = sanitizeInput($_POST['ContactPerson']);

	$sql = "INSERT INTO tbl_offices (off_name, address, city, ph_no, office_time, contact_person)
			VALUES (?, ?, ?, ?, ?, ?)";

	dbPrepare($sql, 'ssssss', [
		$OfficeName,
		$OfficeAddress,
		$City,
		$PhoneNo,
		$OfficeTiming,
		$ContactPerson
	]);

	header('Location: office-add-success.php');
	exit();
}

/**
 * Add new manager - SECURE VERSION with password hashing
 */
function addManager() {
	$ManagerName = sanitizeInput($_POST['ManagerName']);
	$Password = $_POST['Password']; // Don't sanitize passwords
	$Address = sanitizeInput($_POST['Address']);
	$Email = sanitizeInput($_POST['Email']);
	$PhoneNo = sanitizeInput($_POST['PhoneNo']);
	$OfficeName = sanitizeInput($_POST['OfficeName']);

	// Hash the password before storing
	$HashedPassword = hashPassword($Password);

	// Validate email
	if (!validateEmail($Email)) {
		die('Invalid email address');
	}

	$sql = "INSERT INTO tbl_courier_officers (officer_name, off_pwd, address, email, ph_no, office, reg_date)
			VALUES (?, ?, ?, ?, ?, ?, NOW())";

	dbPrepare($sql, 'ssssss', [
		$ManagerName,
		$HashedPassword,
		$Address,
		$Email,
		$PhoneNo,
		$OfficeName
	]);

	header('Location: manager-add-success.php');
	exit();
}

/**
 * Update courier status - SECURE VERSION
 */
function updateStatus() {
	$OfficeName = sanitizeInput($_POST['OfficeName']);
	$status = sanitizeInput($_POST['status']);
	$comments = sanitizeInput($_POST['comments']);
	$cid = (int)$_POST['cid'];
	$cons_no = sanitizeInput($_POST['cons_no']);

	// Insert tracking record
	$sql = "INSERT INTO tbl_courier_track (cid, cons_no, current_city, status, comments, bk_time)
			VALUES (?, ?, ?, ?, ?, NOW())";

	dbPrepare($sql, 'issss', [
		$cid,
		$cons_no,
		$OfficeName,
		$status,
		$comments
	]);

	// Update courier status
	$sql_1 = "UPDATE tbl_courier
				SET status = ?
				WHERE cid = ?
				AND cons_no = ?";

	dbPrepare($sql_1, 'sis', [
		$status,
		$cid,
		$cons_no
	]);

	header('Location: update-success.php');
	exit();
}

/**
 * Change password - SECURE VERSION with password hashing
 */
function changePass() {
	if (!isset($_SESSION['user_id'])) {
		die('Unauthorized');
	}

	$oldPassword = $_POST['old_password'];
	$newPassword = $_POST['new_password'];
	$confirmPassword = $_POST['confirm_password'];
	$userId = (int)$_SESSION['user_id'];

	// Verify new passwords match
	if ($newPassword !== $confirmPassword) {
		die('New passwords do not match');
	}

	// Get current password hash
	$sql = "SELECT off_pwd FROM tbl_courier_officers WHERE cid = ?";
	$result = dbPrepare($sql, 'i', [$userId]);
	$user = dbFetchAssoc($result);

	if (!$user) {
		die('User not found');
	}

	// Verify old password
	if (!verifyPassword($oldPassword, $user['off_pwd'])) {
		die('Current password is incorrect');
	}

	// Hash and update new password
	$hashedPassword = hashPassword($newPassword);
	$updateSql = "UPDATE tbl_courier_officers SET off_pwd = ? WHERE cid = ?";
	dbPrepare($updateSql, 'si', [$hashedPassword, $userId]);

	header('Location: admin.php?msg=password_changed');
	exit();
}

?>