<?php

if(!isset($_SESSION['admin_email'])){

    echo "<script>window.open('login.php','_self')</script>";

}

else {

?>

<?php

if(isset($_GET['edit_itemDetails'])){

$edit_id = $_GET['edit_itemDetails'];

$get_p = "select * from itemsinventory where itemID='$edit_id'";

$run_edit = mysqli_query($con,$get_p);

$row_edit = mysqli_fetch_array($run_edit);

$p_id = $row_edit['itemID'];

$p_title = $row_edit['items'];

$p_category = $row_edit['category'];

$p_Units = $row_edit['quantity'];


}
?>


<!DOCTYPE html>

<html>

<head>

<title> Edit Products </title>


<script src="//cdn.tinymce.com/4/tinymce.min.js"></script>
  <script>tinymce.init({ selector:'#product_desc,#product_features' });</script>

</head>

<body>

<div class="row"><!-- row Starts -->

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<ol class="breadcrumb"><!-- breadcrumb Starts -->

<li class="active">

<i class="fa fa-dashboard"> </i> Dashboard / Edit Products

</li>

</ol><!-- breadcrumb Ends -->

</div><!-- col-lg-12 Ends -->

</div><!-- row Ends -->


<div class="row"><!-- 2 row Starts --> 

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<div class="panel panel-default"><!-- panel panel-default Starts -->

<div class="panel-heading"><!-- panel-heading Starts -->

<h3 class="panel-title">

<i class="fa fa-money fa-fw"></i> Edit Products

</h3>

</div><!-- panel-heading Ends -->

<div class="panel-body"><!-- panel-body Starts -->

<form class="form-horizontal" method="post" enctype="multipart/form-data"><!-- form-horizontal Starts -->



<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Item Title </label>

<div class="col-md-6" >

<input type="text" name="items" class="form-control" required value="<?php echo $p_title; ?>">

</div>

</div><!-- form-group Ends -->




<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" > Item Category </label>

<div class="col-md-6" >

    <input type="text" name="category" class="form-control" required value="<?php echo $p_category; ?>" >

</div>

</div><!-- form-group Ends -->




    <div class="form-group" ><!-- form-group Starts -->

        <label class="col-md-3 control-label" > Item Quantiy </label>

        <div class="col-md-6" >

            <input type="text" name="quantity" class="form-control" required value="<?php echo $p_Units; ?>" >

        </div>

    </div><!-- form-group Ends -->




<div class="form-group" ><!-- form-group Starts -->

<label class="col-md-3 control-label" ></label>

<div class="col-md-6" >

<input type="submit" name="update" value="Update Product" class="btn btn-primary form-control" >

</div>

</div><!-- form-group Ends -->

</form><!-- form-horizontal Ends -->

</div><!-- panel-body Ends -->

</div><!-- panel panel-default Ends -->

</div><!-- col-lg-12 Ends -->

</div><!-- 2 row Ends --> 




</body>

</html>

<?php

if(isset($_POST['update'])){

$items = $_POST['items'];
$category = $_POST['category'];
$quantity = $_POST['quantity'];


$update_product = "update itemsinventory set items='$items',category='$category',quantity='$quantity' where itemID='$p_id'";

$run_product = mysqli_query($con,$update_product);

if($run_product){

echo "<script> alert('Product has been updated successfully') </script>";

echo "<script>window.open('index.php?view_itemCat','_self')</script>";



}

}

?>

<?php } ?>
