<?php
require_once 'db_connect.php';
$db = new DB_Connect();
$conn = $db->connect();

$querry = mysqli_query($conn,"Select admin_id,admin_contact,admin_job from admins where admin_job='Dispatch Manager'");

while ($row = mysqli_fetch_assoc($querry)) {
$emparray[] = $row;
}
echo json_encode(array( "status" => "true","message" => "Data fetched successfully!","data" => $emparray) );
?>