<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();
$chatid=$_POST["chatid"];

$querry = mysqli_query($conn,"select id,msg_id,ReceiverName,SenderName,from_id,to_id,msgcont,
sentat,receivernumber,sendernumber from msgcontent where msg_id=$chatid;");

while ($row = mysqli_fetch_assoc($querry)) {
$emparray[] = $row;
}
echo json_encode(array( "status" => "true","message" => "Data fetched successfully!","data" => $emparray) );
?>

