
<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();

$userid = $_POST["userid"];
$msgid = $_POST["msgid"];
$driverId =$_POST["driverId"];
$FeedBack =$_POST["FeedBack"];

$receiverName =$_POST["receiverName"];
$receiverPhone =$_POST["receiverPhone"];

$senderName =$_POST["senderName"];
$senderNumber =$_POST["senderNumber"];

$querr = $conn->prepare("INSERT INTO msgcontent(msg_id,ReceiverName,SenderName,from_id,to_id,msgcont,sentat,receivernumber,sendernumber)
 VALUES ('$msgid','$receiverName','$senderName',$userid,'$driverId','$FeedBack',now(),'$receiverPhone','$senderNumber')");
$querr->execute();


$responses["message"] = "done!";
echo json_encode($responses);

?>

