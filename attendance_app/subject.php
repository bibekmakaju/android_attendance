<?php
include 'connect.php'; 
// echo "Connected successfully";
$myusername =$_GET["user"];

// $myusername ='5';
$sql = "SELECT T2.title,T2.sem_id,T1.lecturer_id FROM subtolect AS T1 INNER JOIN course AS T2 ON T1.course_id = T2.id WHERE T1.lecturer_id='$myusername'";
$result = $conn->query($sql);
$json = array();
if(mysqli_num_rows($result)){
    echo '{"subjects":[';

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