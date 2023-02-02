package com.igor.mamba;


public class URLs {
    public static final String IP ="192.168.79.31";
    public static final String ROOT_URL = "http://"+IP+"/admin_area/Customer/";
    public static final String URL_REGISTER = ROOT_URL+"register.php";
    public static final String URL_LOGIN= ROOT_URL+"login.php";
    public static final String URL_LOGINDRIVER= ROOT_URL+"driverlogin.php";
    public static final String URL_RESSETPASS= "http://"+IP+"/admin_area/resetpassword.php";
    public static final String URL_STATUS= ROOT_URL+"statuschecker.php";
    public static final String URL_ADDTOCART= ROOT_URL+"cart.php";
    public static final String URL_SUPPLIERLOGIN= ROOT_URL+"loginSUPPLIER.php";
    public static final String URL_DISPATCHERLOGIN= ROOT_URL+"loginDispatch.php";
    public static final String URL_ADDTOORDER= ROOT_URL+"orderitem.php";
    public static final String postdata = ROOT_URL+"updateadress.php" ;
    public static final String getdefaultlocations =" http://"+URLs.IP+"/admin_area/Customer/getshippingdetails.php";
    public static final String PURCHASEDITEMS = "http://"+URLs.IP+"/admin_area/Customer/loadorderitems.php";
    public static final String FEEDBACK = "http://"+URLs.IP+"/admin_area/Customer/feedback.php";
    public static final String FEEDBACKREPLY = "http://"+URLs.IP+"/admin_area/Customer/feedbackreply.php";
    public static final String FEEDBACKDRIVER = "http://"+URLs.IP+"/admin_area/Customer/feedbackDriver.php";
    public static final String DRIVERDETAILS = "http://"+URLs.IP+"/admin_area/Customer/loadorderdriver.php";
    public static final String FINANCEDETAILS = "http://"+URLs.IP+"/admin_area/Customer/populateadmins.php";
    public static final String CHATDETAILS = "http://"+URLs.IP+"/admin_area/Customer/loadchats.php";
    public static final String DISPATCHDETAILS = "http://"+URLs.IP+"/admin_area/Customer/populateDispatch.php";
    public static final String ADMINDETAILS = "http://"+URLs.IP+"/admin_area/Customer/populateSuper.php";
    public static String driver  = ROOT_URL+"driverorder.php";
    public static String accept   =ROOT_URL+"acceptdriver.php";
    public static String confirm   =ROOT_URL+"confirmorder.php";
    public static String getdeliveredorder  = ROOT_URL+"getcompletedelivery.php";
    public static String getenrouteorder  = ROOT_URL+"getdenrouteorder.php";
}
