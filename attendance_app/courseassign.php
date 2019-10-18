<?php
include 'connect.php';
// echo "Connected successfully";
$id =$_GET["id"];
$batch_ad=$_GET["batch"];
// $sql = "SELECT * FROM courseassign WHERE course_id='$id'";
$sql="SELECT c.* FROM student as s JOIN courseassign as c on c.crn=s.crn WHERE c.course_id='$id' AND s.batch_ad='$batch_ad'";
$result = $conn->query($sql);
$json = array();
if(mysqli_num_rows($result)){
    echo '{"courseassign":[';

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