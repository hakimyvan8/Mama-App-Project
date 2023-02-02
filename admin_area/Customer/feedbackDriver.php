
<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();

$userid = $_POST["userid"];
$id = $_POST["id"];
$custoId =$_POST["custoId"];
$FeedBack =$_POST["FeedBack"];

$receiverName =$_POST["receiverName"];
$receiverPhone =$_POST["receiverPhone"];

$senderName =$_POST["senderName"];
$senderNumber =$_POST["senderNumber"];


$querry = $conn->prepare("INSERT INTO messages(from_id,to_id,msg,sentat,orderid,receiverName,receiverPhone,senderName,senderNumber) VALUES ('$userid','$custoId','$FeedBack',now(),'$id','$receiverName','$receiverPhone','$senderName','$senderNumber')");
if($querry->execute())
{
    $response['error'] = false;
    echo json_encode($response);

}
else{
    $response['error'] = true;
    echo json_encode($response);


}

?>

