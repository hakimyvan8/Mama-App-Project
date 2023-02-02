<?php

require_once 'db_connect.php';
$db = new DB_Connect();
$con = $db->connect();

if(isTheseParametersAvailable(array('phone', 'password'))){

//getting values
    $phone = $_POST['phone'];
    $password = $_POST['password'];
    $response = array();
//creating the query
    $stmt = $con->prepare("SELECT  admin_id, admin_name, admin_email, admin_image, admin_contact, admin_country, admin_job, admin_about FROM admins WHERE admin_contact = ? AND admin_pass = ? AND admin_job = 'Dispatch Manager'");
    $stmt->bind_param("ss",$phone, $password);

    $stmt->execute();

    $stmt->store_result();

//if the user exist with given credentials

    if($stmt->num_rows > 0){

        $stmt->bind_result($adminId, $adminName, $adminEmail, $adminImage, $adminContact, $adminCountry, $adminJob, $adminAbout);
        $stmt->fetch();
        $user = array(

            'adminId'=>$adminId,
            'adminName'=>$adminName,
            'adminEmail'=>$adminEmail,
            'adminImage'=>$adminImage,
            'adminContact'=>$adminContact,
            'adminCountry'=>$adminCountry,
            'adminJob' =>$adminJob,
            'adminAbout' =>$adminAbout
        );

        $response['error'] = false;
        $response['message'] = 'Login successfull';
        $response['dispatch'] = $user;
        echo json_encode($response);
    }else{
//if the user not found
        $response['error'] = true;
        $response['message'] = 'Invalid phone or password';
        echo json_encode($response);
    }
}
echo json_encode($response);
function isTheseParametersAvailable($params){

    foreach($params as $param){
        if(!isset($_POST[$param])){
            return false;
        }
    }
    return true;
}
?>