<?php
require_once ('connection.php');

header('Content-type: text/json');
$name=$con->real_escape_string($_POST["name"]);
$pass=$con->real_escape_string($_POST["pass"]);
$result=$con->query("SELECT * FROM `user`  WHERE `name`= '$name' AND `pass` ='$pass'");
$row = $result -> fetch_object();
//while($row = $result -> fetch_object()){
    //$arr[]=$row;
//}
echo json_encode($row);
