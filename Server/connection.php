<?php
$conn_hostname = "localhost";
$conn_database = "Tracing";
$conn_username = "root";
$conn_password = "123456";
date_default_timezone_set('PRC');


$con=mysqli_connect($conn_hostname, $conn_username, $conn_password,$conn_database);
$con->query('SET NAMES UTF8');