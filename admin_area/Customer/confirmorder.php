




<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();
$orderid=  $_POST["orderid"];
$id  = $_POST["driverid"];

$get = $conn->prepare("SELECT Tracking_id,OrderNumber,status,customer_id,location FROM order_customer_tracking WHERE OrderNumber=$orderid");
$get->execute();
$get->bind_result($Tracking_id,$OrderNumber,$status,$customer_id,$location);
$get->store_result();
$get->fetch();

 $get->close();
$run_product = $conn->prepare("UPDATE order_customer_tracking set Tracking_id='$Tracking_id' ,status= 4 ,customer_id='$customer_id' ,location='$location' WHERE OrderNumber='$orderid'");
$run_product->execute();

 if($run_product->execute()){
     $update =$conn->prepare("UPDATE orders set status = 4 WHERE id = $orderid");
     $update->execute();
 }

echo json_encode(array( "status" => "true","message" => "Delivery Completed") );
?>