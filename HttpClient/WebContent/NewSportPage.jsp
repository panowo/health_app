<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>健康打卡</title>
</head>
<body>
<form id="from1" action="NewSportRecord" method="post">
<table>
<tr><td>用户名</td><td><input type="text"  name="ID"></td></tr>
<tr><td>日期</td><td><input type="text"  name="DATE"></td></tr>
<tr><td>时间</td><td><input type="text"  name="TIME"></td></tr>
<tr><td>地点</td><td><input type="text"  name="PLACE"></td></tr>
<tr><td>打卡时间</td><td><input type="text"  name="DUR_TIME"></td></tr>
<tr><td colspan="2" align="center"><input type="submit"  value="运动打卡"/></td></tr>
</table>
</form>
</body>
</html>