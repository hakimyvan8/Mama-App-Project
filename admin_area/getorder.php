<?php
require_once 'includes/db.php';
require_once 'Customer/db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();

    $user = '7';

    $getcart = $con->prepare("select id from orders where userId = $user");
    $getcart->execute();
    $getcart->bind_result($orderid);
    $getcart->fetch();
    $getcart->close();
    $count = $conn->prepare("select count(*)from order_item where orderId=12 ");
    $count->bind_param('ss',$orderid);

    $count ->execute();
    $count ->bind_result($counter);

    $count->fetch();



$responses["count"] = $counter;
echo json_encode($responses);
















