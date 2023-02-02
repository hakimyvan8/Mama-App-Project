
<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();

$userid = $_POST["userid"];
$msgid = $_POST["msgid"];
$driverId =$_POST["driverId"];
$FeedBack =$_POST["FeedBack"];

if($userid)
$querr = $conn->prepare("INSERT INTO msgcontent(msg_id,from_id,to_id,msgcont,sentat)
 VALUES ('$msgid','$userid','$driverId','$FeedBack',now())");
$querr->execute();


$responses["message"] = "done!";
echo json_encode($responses);

?>

