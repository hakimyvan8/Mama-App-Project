<?php

include("securityStock.php")

?>

<nav class="navbar navbar-inverse navbar-fixed-top" ><!-- navbar navbar-inverse navbar-fixed-top Starts -->

<div class="navbar-header" ><!-- navbar-header Starts -->

<button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse" ><!-- navbar-ex1-collapse Starts -->


<span class="sr-only" >Toggle Navigation</span>

<span class="icon-bar" ></span>

<span class="icon-bar" ></span>

<span class="icon-bar" ></span>


</button><!-- navbar-ex1-collapse Ends -->

<a class="navbar-brand" href="indexSTOCK.php?dashboardStock" >Stock Manager Admin Panel</a>


</div><!-- navbar-header Ends -->

<ul class="nav navbar-right top-nav" ><!-- nav navbar-right top-nav Starts -->

<li class="dropdown" ><!-- dropdown Starts -->

<a href="#" class="dropdown-toggle" data-toggle="dropdown" ><!-- dropdown-toggle Starts -->

<i class="fa fa-user" ></i>

<?php echo $admin_name; ?> <b class="caret" ></b>


</a><!-- dropdown-toggle Ends -->

<ul class="dropdown-menu" ><!-- dropdown-menu Starts -->

<li><!-- li Starts -->

<a href="indexSTOCK.php?user_profile=<?php echo $admin_id; ?>" >

<i class="fa fa-fw fa-user" ></i> Profile


</a>

</li><!-- li Ends -->

<li><!-- li Starts -->

<a href="indexSTOCK.php?view_products" >

<i class="fa fa-fw fa-envelope" ></i> Products 

<span class="badge" ><?php echo $count_products; ?></span>


</a>

</li><!-- li Ends -->

<li><!-- li Starts -->

<a href="indexSTOCK.php?view_pendingCustomers" >

<i class="fa fa-fw fa-gear" ></i> Customers

<span class="badge" ><?php echo $count_customers; ?></span>


</a>

</li><!-- li Ends -->


<li><!-- li Starts -->

<a href="indexSTOCK.php?view_p_cats" >

<i class="fa fa-fw fa-gear" ></i> Product Categories

<span class="badge" ><?php echo $count_p_categories; ?></span>


</a>

</li><!-- li Ends -->

<li class="divider"></li>

<li><!-- li Starts -->

<a href="logout.php">

<i class="fa fa-fw fa-power-off"> </i> Log Out

</a>

</li><!-- li Ends -->

</ul><!-- dropdown-menu Ends -->




</li><!-- dropdown Ends -->


</ul><!-- nav navbar-right top-nav Ends -->

<div class="collapse navbar-collapse navbar-ex1-collapse"><!-- collapse navbar-collapse navbar-ex1-collapse Starts -->

<ul class="nav navbar-nav side-nav"><!-- nav navbar-nav side-nav Starts -->

<li><!-- li Starts -->

<a href="indexSTOCK.php?dashboardStock">

<i class="fa fa-fw fa-dashboard"></i> Dashboard

</a>

</li><!-- li Ends -->

<li><!-- Products li Starts -->

<a href="#" data-toggle="collapse" data-target="#products">

<i class="fa fa-fw fa-table"></i> Products

<i class="fa fa-fw fa-caret-down"></i>


</a>

<ul id="products" class="collapse">

<li>
<a href="indexSTOCK.php?insert_product"> Insert Products </a>
</li>

<li>
<a href="indexSTOCK.php?view_products"> View Instore Products </a>
</li>
    <li>
        <a href="indexSTOCK.php?view_Outproducts"> View Out of Stock Products </a>
    </li>

</ul>

</li><!-- Products li Ends -->


<li><!-- li Starts -->

<a href="#" data-toggle="collapse" data-target="#p_cat">

<i class="fa fa-fw fa-pencil"></i> Products Categories

<i class="fa fa-fw fa-caret-down"></i>


</a>

<ul id="p_cat" class="collapse">

<li>
<a href="indexSTOCK.php?insert_p_cat"> Insert Product Category </a>
</li>

<li>
<a href="indexSTOCK.php?view_p_cats"> View Products Categories </a>
</li>


</ul>

</li><!-- li Ends -->


<li><!-- li Starts -->

<a href="logout.php">

<i class="fa fa-fw fa-power-off"></i> Log Out

</a>

</li><!-- li Ends -->

</ul><!-- nav navbar-nav side-nav Ends -->

</div><!-- collapse navbar-collapse navbar-ex1-collapse Ends -->

</nav><!-- navbar navbar-inverse navbar-fixed-top Ends -->

<?php ?>