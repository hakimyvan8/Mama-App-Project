
<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();

$userid = $_POST["userid"];
$id = $_POST["id"];
$driverId =$_POST["driverId"];
$FeedBack =$_POST["FeedBack"];

$receiverName =$_POST["receiverName"];
$receiverPhone =$_POST["receiverPhone"];

$senderName =$_POST["senderName"];
$senderNumber =$_POST["senderNumber"];

$querry = $conn->prepare("INSERT INTO messages(from_id,to_id,sentat,receiverName,ordernumber,receiverPhone,originName,originPhone)
 VALUES('$userid','$driverId',now(),'$receiverName','$id','$receiverPhone','$senderName','$senderNumber')");
$querry->execute();




$stmtupdateorderlist = $conn->prepare("select id from messages where ordernumber='$id'");
$stmtupdateorderlist->execute();
$stmtupdateorderlist->bind_result($msg_id);
$stmtupdateorderlist->fetch();
$stmtupdateorderlist->close();


$querr = $conn->prepare("INSERT INTO msgcontent(msg_id,ReceiverName,SenderName,from_id,to_id,msgcont,sentat,receivernumber,sendernumber) VALUES ('$msg_id','$receiverName','$senderName',$userid,'$driverId','$FeedBack',now(),'$receiverPhone','$senderNumber')");
$querr->execute();


$responses["message"] = "done!";
echo json_encode($responses);

?>

