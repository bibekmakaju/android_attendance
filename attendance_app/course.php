<?php
include 'connect.php'; 
// echo "Connected successfully";
$sql = "SELECT * FROM course";
$result = $conn->query($sql);
$json = array();
if(mysqli_num_rows($result)){
    echo '{"course":[';

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