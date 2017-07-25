<?php
require_once ('connection.php');
header('Content-type: text/json');
$Lat=(double)$_POST['Lat'];
$Lon=(double)$_POST['Lon'];
$r=(double)$_POST['r'];
$minLat=$Lat-$r;
$maxLat=$Lat+$r;
$minLon=$Lon-$r;
$maxLon=$Lon+$r;

$result=$con->query("SELECT * FROM `item`  WHERE `Lat`< '$maxLat' AND `Lat` >'$minLat' And `Lon`<'$maxLon' And `Lon`>'$minLon' ORDER BY `time`DESC");
while($row = $result -> fetch_object()){
$arr[]=$row;
}

    echo json_encode($arr);



