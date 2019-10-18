<?php
include 'connect.php'; 
// echo "Connected successfully";
$lecturer_id =$_GET["user"];

// $lecturer_id ='5';
$sql = "SELECT T2.* ,T1.batch_ad FROM subtolect AS T1  JOIN course AS T2 ON T1.course_id = T2.id WHERE T1.lecturer_id='$lecturer_id'";
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