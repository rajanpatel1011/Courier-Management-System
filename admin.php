<?php
session_start();
require_once('library.php');
isUser();
?>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Courier / Cargo Tracking Script in PHP - Ver 0.97</title>
<link href="css/mystyle.css" rel="stylesheet" type="text/css">
<link href="css/style.css" rel="stylesheet" type="text/css">
</head>
<body>

<?php
include("header.php");
?>

	</td>
  </tr>
  
  <tr>
    <td bgcolor="#FFFFFF"><div align="center"> <br>
        <br>
        <table bgcolor="#ECECEC" border="0" cellpadding="2" cellspacing="2" align="center" width="50%">
        <tbody><tr>
          <td class="Partext1" bgcolor="#EEEEEE"><div align="left"><strong>Courier Management System </strong></div></td>
        </tr>
        <tr>
          <td class="newtext" bgcolor="#FFFFFF"><div align="left"><img src="images/arrow_white.gif" border="0" height="8" width="7">&nbsp;<a href="add-courier.php" class="REDLink">Add Shipment</a></div></td>
        </tr>
                <tr>
          <td class="newtext" bgcolor="#FFFFFF"><div align="left"><img src="images/arrow_white.gif" border="0" height="8" width="7">&nbsp;<a href="courier-list.php" class="REDLink">List Shipment</a></div></td>
        </tr>
        <tr>
          <td class="newtext" bgcolor="#FFFFFF"><div align="left"><img src="images/arrow_white.gif" border="0" height="8" width="7">&nbsp;<a href="report.php" class="REDLink">Report</a></div></td>
        </tr>
        <tr>
          <td class="newtext" bgcolor="#FFFFFF"><div align="left"><img src="images/arrow_white.gif" border="0" height="8" width="7">&nbsp;<a href="process.php?action=logOut" class="REDLink">Logout</a></div></td>
        </tr>
      </tbody></table>
            <br>
          <br>
          <br>      
          <br>
    </div></td>
  </tr>
  <tr>
    <td>
	<table border="0" cellpadding="0" cellspacing="0" align="center" width="900">
  <tbody><tr>
    <td bgcolor="#2284d5" height="40" width="476">&nbsp;</td>
    <td bgcolor="#2284d5" width="304">
	</td>
  </tr>
</tbody></table>
</td>
  </tr>
</tbody></table>


</body></html>