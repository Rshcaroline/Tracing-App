<?php
/**
 * Created by PhpStorm.
 * User: 汪励颢
 * Date: 2017/2/19
 * Time: 0:37
 */
require_once ("connection.php");
//header('Content-Type:text/html;charset=utf-8');
define('ROOT',dirname(__FILE__).'/');




$fileArray = $_FILES['file'];
//var_dump($fileArray);
$owner=(int)$_POST['owner'];
$x=(double)$_POST['Lat'];
$y=(double)$_POST['Lon'];
$name=$_POST['name'];
$type=(int)$_POST['type'];
$id=time()."_".mt_rand();
$count = count($_FILES['file']['name']);
$text=$_POST['text'];
$con->query("insert into item(owner, Lat, Lon, id, num, text,type,name) values('$owner','$x','$y','$id','$count','$text','$type','$name');");
//
$flag=true;
for ($i = 0; $i < $count; $i++) {

    $tmpfile = $_FILES['file']['tmp_name'][$i];
     $dstfile = ROOT."img/items/".$id."_".$i.".jpg";

    if (!move_uploaded_file($tmpfile, $dstfile)) {

        $flag=false;
        break;
    }
}
if($flag){
    echo "发表成功";
}
else echo  $_FILES['file']['error'];


