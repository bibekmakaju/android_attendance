<?php
$servername = "localhost";
$username = "username";
$password = "password";

// Create connection
$conn = new mysqli("localhost", "root", "","attend");

// Check connection
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
} 
// echo "Connected successfully";
$sql = "SELECT * FROM student";
$result = $conn->query($sql);
$json = array();
if(mysqli_num_rows($result)){
    echo '{"student":[';

    $first = true;

    while($row=mysqli_fetch_assoc($result)){
        //  cast results to specific data types

        if($first) {
            $first = false;
        } else {
            echo ',';
        }
        echo json_encode($row);
    }
    echo ']}';
} else {

}

?>