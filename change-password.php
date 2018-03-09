<?php
session_start();
require_once('database.php');
require_once('library.php');

isUser();
?>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html><head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Courier / Cargo Tracking Script in PHP - Ver 0.97</title>
<link href="css/mystyle.css" rel="stylesheet" type="text/css">
<link href="css/style.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style2 {color: #FFFFFF}
-->
</style>

<script language="JavaScript">
<!--
function Check_form() {


// Check Password 
		if ( signupForm.txtp.value == "" )
		{
			alert( "Kindly enter a password." );
			signupForm.txtp.focus( );
			return false;
		}

		if ( signupForm.txtp.value.length < 4 )
		{
			alert( "Password must be atleast 4 characters." );	
			signupForm.txtp.focus( );
			return false;
		}


		if ( signupForm.txtp.value.length > 20 )
		{
		alert( "Password must be Max 20 characters." );	
		signupForm.txtp.focus( );
		return false;
		}

		if ( signupForm.txtcp.value == "" )
		{
			alert( "Kindly enter a confirm password." );
			signupForm.txtcp.focus( );
			return false;
		}

		if ( signupForm. txtp.value != signupForm. txtcp.value )
		{
			alert( "Password and Confirm password must be same." );
			signupForm.txtp.value = "";
			signupForm.txtcp.value = "";

			signupForm.txtp.focus( );

			return false;
		}

		tmpPass = signupForm.txtp.value;

		goodPasswd = 1;

		for( var idx=0; idx< tmpPass.length; idx++ )
		{
			ch = tmpPass.charAt(idx);

			if( !((ch>='a') && (ch<='z')) && !((ch>='A') && (ch<='Z')) && !((ch>=0) && (ch <=9)) )
			{
				goodPasswd = 0;
				break;
			}
		}

		if( goodPasswd ==0 )
		{
			alert( "Password must contains only letters and digits." );
			signupForm. txtp.value="";
			signupForm. txtcp.value="";
			signupForm. txtp.focus();
			return false;
		}

	

return true;
}

//-->
</script>

</head>

<body>
<table border="0" cellpadding="0" cellspacing="0" align="center" width="900">
  <tbody><tr>
    <td width="900">
	
<?php include("header.php"); ?>
	</td>
  </tr>
  
  <tr>
    <td bgcolor="#FFFFFF"><table border="0" cellpadding="1" cellspacing="1" align="center" width="98%">
      <tbody><tr>
        <td class="Partext1">&nbsp;</td>
      </tr>
      
      <tr>
        <td class="Partext1">&nbsp;</td>
        </tr>
      
      <tr>
        <td height="25"><div class="headtext13" align="center"><strong>Change Admin Password </strong></div></td>
        </tr>
      <tr>
        <td height="25"><div class="redtext" align="center"></div></td>
      </tr>
      <tr>
        <td><div align="center">
           <form name="signupForm" id="signupForm" method="post" action="process.php?action=change-pass" onSubmit="return Check_form();"> 
            <table border="0" cellpadding="0" cellspacing="0" align="center" width="60%">
              <tbody><tr>
                <td width="18"><img src="images/boxtopleftcorner.gif" alt="" height="13" width="18"></td>
                <td background="images/boxtopBG.gif" width="734"></td>
                <td width="18"><img src="images/boxtoprightcorner.gif" alt="" height="13" width="18"></td>
              </tr>
              <tr>
                <td background="images/boxleftBG.gif"></td>
                <td><table border="0" cellpadding="0" cellspacing="0" align="center" width="98%">
                    <tbody><tr>
                      <td colspan="2" height="4"></td>
                    </tr>
                    <tr>
                      <td height="18"><table class="newtext" border="0" cellpadding="2" cellspacing="2" align="center" width="100%">
                        <tbody><tr>
                          <td>&nbsp;</td>
                          <td>&nbsp;</td>
                        </tr>
                        <tr>
                          <td width="40%"><div align="right">New Password  : </div></td>
                          <td width="60%"><div align="left">
                              <input name="txtp" id="txtp" type="password">
                          </div></td>
                        </tr>
                        <tr>
                          <td><div align="right">Confirm Password : </div></td>
                          <td><div align="left">
                              <input name="txtcp" id="txtcp" type="password">
                          </div></td>
                        </tr>
                        <tr>
                          <td>&nbsp;</td>
                          <td><div align="left">
                              <input name="Submit" value="Submit" type="submit">
                          </div></td>
                        </tr>
                      </tbody></table></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                </tbody></table></td>
                <td background="images/boxrightBG.gif"></td>
              </tr>
              <tr>
                <td width="18"><img src="images/boxbtmleftcorner.gif" alt="" height="12" width="18"></td>
                <td background="images/boxbtmBG.gif" width="734"></td>
                <td width="18"><img src="images/boxbtmrightcorner.gif" alt="" height="12" width="18"></td>
              </tr>
            </tbody></table>
            </form>
        </div></td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
      <tr>
        <td>&nbsp;</td>
      </tr>
    </tbody></table>      </td>
  </tr>
  <tr>
    <td><table border="0" cellpadding="0" cellspacing="0" align="center" width="900">
  <tbody><tr>
    <td bgcolor="#2284d5" height="40" width="476">&nbsp;</td>
    <td bgcolor="#2284d5" width="304"><div align="right"></div></td>
  </tr>
</tbody></table>
</td>
  </tr>
</tbody></table>


</body></html>