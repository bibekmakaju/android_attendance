<?php
include 'connect.php'; 
// echo "Connected successfully";
$lecturer_id =$_GET["user"];

$batch =$_GET["batch"];
$sql = "SELECT s.* FROM student as s JOIN courseassign as c on c.crn=s.crn WHERE c.course_id='$lecturer_id' AND s.batch_ad='$batch'";
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