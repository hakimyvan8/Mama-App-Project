<?php
session_start();

require_once ('includes/db.php'); 

$result=array();
$response['error'] = false;
$response['message'] = 'processing data';

$cart = $con->prepare("SELECT id,orderid,to_id,from_id,msg,sentat,receiverName,receiverPhone,senderName,senderNumber FROM messages WHERE to_id = $user_id");
$cart ->execute();
$cart->bind_result($id,$orderId,$to_id,$from_id,$msg,$date,$receiverName,$receiverPhone,$senderName,$senderNumber);
$enquriy = array();



while($cart->fetch()){

    $temp = array();

    $temp['id'] = $id;
    $temp['orderId'] = $orderId;
    $temp['to_id'] = $to_id;
    $temp['from_id'] = $from_id;
    $temp['msg'] = $msg;
    $temp['date'] = $date;
    $temp['receiverName'] = $receiverName;
    $temp['receiverPhone'] = $receiverPhone;
    $temp['senderName'] = $senderName;
    $temp['senderNumber'] = $senderNumber;
    
    array_push($enquriy,$temp);
}


$responses["enqlistII"] = $enquriy;
echo json_encode($responses);

?>