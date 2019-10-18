<?php
   include("connect.php");
   
   // $db="attend";
   // if($_SERVER["REQUEST_METHOD"] == "POST") {
      // username and password sent from form 
      // $=$_GET["busid"];
      $myusername =$_GET["user"];
      $mypassword = $_GET["pass"];
      // $myusername ="ersks";
      // $mypassword = "khwopa";

      
      $sql = "SELECT * FROM user WHERE username = '$myusername' and password = '$mypassword'";
      $result = $conn->query($sql);
      if(mysqli_num_rows($result)){
         $row = mysqli_fetch_assoc($result);
     
         
         echo $row["fullname"].",".$row["id"];
      
      }
      else{
       echo "Username or password incorrect";
}
   
?>
