<?php

if(!isset($_SESSION['admin_email'])){

echo "<script>window.open('login.php','_self')</script>";

}

else {

?>


<div class="row"><!-- 1 row Starts -->

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<ol class="breadcrumb"><!-- breadcrumb Starts -->

<li class="active">

<i class="fa fa-dashboard"></i> Dashboard / View Demand History


</li>

</ol><!-- breadcrumb Ends -->

</div><!-- col-lg-12 Ends -->

</div><!-- 1 row Ends -->


<div class="row"><!-- 2 row Starts -->

<div class="col-lg-12"><!-- col-lg-12 Starts -->

<div class="panel panel-default"><!-- panel panel-default Starts -->

<div class="panel-heading"><!-- panel-heading Starts -->

<h3 class="panel-title"><!-- panel-title Starts -->

<i class="fa fa-money fa-fw"> </i> View Demand  History <br> <br>


<td> 

<a href="index.php?edit_itemDemand">

<i class="fa fa-plus"></i> Add Item in Demand

</a>

</td>




</h3><!-- panel-title Ends -->

</div><!-- panel-heading Ends -->

<div class="panel-body"><!-- panel-body Starts -->

<div class="table-responsive"><!-- table-responsive Starts -->

<table class="table table-bordered table-hover table-striped"><!-- table table-bordered table-hover table-striped Starts -->

<thead><!-- thead Starts -->

<tr>

<th>Item Id</th>
<th>Item Title</th>
<th>Item Category</th>
<th>Item Quantity</th>
<th>Item Price/ KG</th>
<th>Total Price</th>
<th>Edit Item</th>
<th>Delete Item</th>


</tr>

</thead><!-- thead Ends -->

<tbody><!-- tbody Starts -->

<?php

$i=0;

$get_p_cats = "select * from itemsdemand";

$run_p_cats = mysqli_query($con,$get_p_cats);

while($row_p_cats = mysqli_fetch_array($run_p_cats)){

$p_cat_id = $row_p_cats['itemsD'];

$item_title = $row_p_cats['items'];

$item_category = $row_p_cats['category'];

$quantity = $row_p_cats['quantity'];

$price = $row_p_cats['price'];

$i++;

?>

<tr>

<td> <?php echo $i; ?> </td>

<td> <?php echo $item_title; ?> </td>

<td> <?php echo $item_category; ?> </td>

<td> <?php echo $quantity; ?> KG </td>


<td> 

<a href="index.php?edit_itemDetails=<?php echo $p_cat_id; ?>">

<i class="fa fa-pencil"></i> Edit

</a>

</td>



<td> 

<a href="index.php?delete_itemDetail=<?php echo $p_cat_id; ?>">

<i class="fa fa-trash-o"></i> Delete

</a>

</td>



</tr>

<?php } ?>

</tbody><!-- tbody Ends -->

</table><!-- table table-bordered table-hover table-striped Ends -->

</div><!-- table-responsive Ends -->

</div><!-- panel-body Ends -->

</div><!-- panel panel-default Ends -->

</div><!-- col-lg-12 Ends -->

</div><!-- 2 row Ends -->



<?php } ?>