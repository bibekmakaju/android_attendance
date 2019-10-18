
<?php 
include 'connect.php'; 
	$receiver_data=$_POST['array'];
	$new_array=json_decode($receiver_data,true);

foreach($new_array as $row)
{
	$crn =$row['crn'];
	$course_id=$row['courseid'];
	$date1=$row['date'];
	$present=$row['present'];
	//update if date exits

// 	$sql ="UPDATE attendrec SET present WHERE date='date'";
// 	if ($result = $conn->query($sql)>0) {
		
// 	}
// 	else {

// }
	$sql ="INSERT INTO attendrec (crn, course_id, present, date) VALUES ('$crn', '$course_id', '$present','$date1')";

	$result = $conn->query($sql);
	if ($result) {

    echo "true";
} else {
    echo "false";
}
}

?>

