<?php
session_start();

require_once ('includes/db.php'); 

$result=array();
$userid = $_POST["userid"];
$response['error'] = false;
$response['message'] = 'processing data';

$cart = $con->prepare("SELECT id,ordernumber,to_id,from_id,sentat,receiverName,receiverPhone,originName,originPhone FROM messages WHERE to_id = $userid OR from_id = $userid");
$cart ->execute();
$cart->bind_result($id,$ordernumber,$to_id,$from_id,$date,$receiverName,$receiverPhone,$originName,$originPhone);
$enquriy = array();



while($cart->fetch()){

    $temp = array();

    $temp['id'] = $id;
    $temp['ordernumber'] = $ordernumber;
    $temp['to_id'] = $to_id;
    $temp['from_id'] = $from_id;
    $temp['date'] = $date;
    $temp['receiverName'] = $receiverName;
    $temp['receiverPhone'] = $receiverPhone;
    $temp['originName'] = $originName;
    $temp['originPhone'] = $originPhone;
    
    array_push($enquriy,$temp);
}


$responses["enqlist"] = $enquriy;
echo json_encode($responses);

?>