 <?php
/**
 * Created by PhpStorm.
 * User: 汪励颢
 * Date: 2017/2/28
 * Time: 13:14
 */
 require_once ('connection.php');
 define('ROOT',dirname(__FILE__).'/');
 error_reporting(E_ALL);

 ini_set('display_errors', 'On');
 $name=$con->real_escape_string($_POST["name"]);
 $pass=$con->real_escape_string($_POST["pass"]);
 $result=$con->query("SELECT * FROM `user`  WHERE `name`= '$name'");
if( $row=$result->fetch_assoc()){
 echo "用户名已存在";
}
 else{
  //$id=$_POST['id'];
  $tmpfile = $_FILES['file']['tmp_name'];

  //$dstfile = ROOT."img/icon/".$id."jpg";

  //$result2=$con->query("insert into user(name,pass) values('$name','$pass');");
  if($result2=$con->query("insert into user(name,pass) values('$name','$pass');")){
  $result3=$con->query("SELECT * FROM `user`  WHERE `name`= '$name'");
   $row2=$result3->fetch_assoc();
   $id=$row2['id'];
   $dstfile = ROOT."img/usericon/".$id.".jpg";

   if(move_uploaded_file($tmpfile, $dstfile)){
        echo "用户注册成功";
   }
   else echo "服务器炸裂1";

  }
  else echo "服务器炸裂2";
 }

