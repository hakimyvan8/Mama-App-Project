<?php
session_start();

require_once ('includes/db.php'); 

$result=array();
$user_id = $_POST["userid"];
$response['error'] = false;
$response['message'] = 'processing data';

$cart = $con->prepare("SELECT id,status,createdAt,firstName,province,country,city,grandTotal,mobile,paymentsecreat,paymentStatus FROM orders WHERE userId = $user_id");
$cart ->execute();
$cart->bind_result($orderid,$status,$date,$firstname,$province,$country,$city,$grandTotal,$mobile,$paymentsecreat,$paymentStatus);
$products = array();



while($cart->fetch()){

    $temp = array();

    $temp['orderid'] = $orderid;
    $temp['status'] = $status;
    $temp['date'] = $date;
    $temp['firstname'] = $firstname;
    $temp['province'] = $province;
    $temp['country'] = $country;
    $temp['city'] = $city;
    $temp['grandTotal'] = $grandTotal;
    $temp['mobile'] = $mobile;
    $temp['transcode'] = $paymentsecreat;
    $temp['paymentStatus'] = $paymentStatus;
    
    array_push($products,$temp);
}


$responses["products"] = $products;
echo json_encode($responses);

?>