<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();
$orderid= $_POST["orderid"];

$querry = mysqli_query($conn,"select driver.driverID,driver.FullName,driver.phone,driver.NumberPlate,driver.DriverLicense 
from order_customer_tracking inner JOIN driver on order_customer_tracking.drverid = driverID where order_customer_tracking.OrderNumber=$orderid;");

while ($row = mysqli_fetch_assoc($querry)) {
$emparray[] = $row;
}
echo json_encode(array( "status" => "true","message" => "Data fetched successfully!","dmitri" => $emparray) );
?>

