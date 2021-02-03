<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@page import="java.sql.*"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/sql" prefix="sql"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page import="java.util.*"%>
<%@ page import="javax.servlet.http.*,javax.servlet.*"%>
<%@page import="pl.nzc.DBUtils"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
	integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
	crossorigin="anonymous">
<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js"
	integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN"
	crossorigin="anonymous"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js"
	integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
	integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
	crossorigin="anonymous"></script>
<script>
	function sh() {
		document.getElementById("tab").style.visibility = "visible";
	}
	function hi() {
		document.getElementById("tab").style.visibility = "hidden";
	}
	function sh2() {
		document.getElementById("tab2").style.visibility = "visible";
	}
	function hi2() {
		document.getElementById("tab2").style.visibility = "hidden";
	}
</script>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<div class="container">
		<div class="row" style="font-size: 50px">
			<b>Hello: ${userName}</b>
			<a href="logout">
				<button type="button" class="btn btn-success">Logout</button>
			</a>
		</div>
		<div class="row">
			<a href="changePassword.jsp">
				<button type="button" class="btn btn-warning">Change
					password</button>
			</a>

		</div>
		<div class="row">
			<div class="container" style="background-color: grey">
				<b style="font-size: 30px">Passwords</b> <input type="button"
					class="btn btn-secondary" value="Show table" onclick="sh();">
				<input type="button" class="btn btn-secondary" value="Hide table"
					onclick="hi();"><br>
				<%
					try {
					String userName = DBUtils.getLoginedUser(session);
					DriverManager.registerDriver(new org.h2.Driver());
					Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
					String sql = "Select id, PASSWORD, startdate, enddate from passwordstory where users_LOGIN =?";
					PreparedStatement pstm = conn.prepareStatement(sql);
					pstm.setString(1, userName);
					ResultSet rs = pstm.executeQuery();
				%><table class="table" border=1
					style="visibility: visible; border-color: white; color: white"
					id="tab">
					<thead>
						<tr>
							<th scope="col">ID</th>
							<th scope="col">Password</th>
							<th scope="col">Start Date</th>
							<th scope="col">End Date</th>
						</tr>
					</thead>
					<tbody>
						<%
							while (rs.next()) {
						%>
						<tr>
							<td><%=rs.getString("id")%></td>
							<td><%=rs.getString("PASSWORD")%></td>
							<td><%=rs.getString("startdate")%></td>
							<td><%=rs.getString("enddate")%></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
				<br>
				<%
					} catch (Exception e) {
				out.print(e.getMessage());
				}
				%><br>
			</div>
		</div>
		<div class="row">
			<div class="container" style="background-color: #A9A9A9">
				<b style="font-size: 30px">Activity</b> <input
					type="button" class="btn btn-secondary" value="Show table"
					onclick="sh2();"> <input type="button"
					class="btn btn-secondary" value="Hide table" onclick="hi2();">
				<%
					try {
					String userName = DBUtils.getLoginedUser(session);
					DriverManager.registerDriver(new org.h2.Driver());
					Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
					String sql = "Select id, description, login, logout from loginstory where users_LOGIN =?";
					PreparedStatement pstm = conn.prepareStatement(sql);
					pstm.setString(1, userName);
					ResultSet rs = pstm.executeQuery();
				%><table class="table" border=1
					style="border-color: white; color: white" id="tab2">
					<thead>
						<tr>
							<th scope="col">ID</th>
							<th scope="col">Description</th>
							<th scope="col">Login time</th>
							<th scope="col">Logout time</th>
						</tr>
					</thead>
					<tbody>
						<%
							while (rs.next()) {
						%>
						<tr>
							<td><%=rs.getString("id")%></td>
							<td><%=rs.getString("description")%></td>
							<td><%=rs.getString("login")%></td>
							<td><%=rs.getString("logout")%></td>
						</tr>
						<%
							}
						%>
					</tbody>
				</table>
				<br>
				<%
					} catch (Exception e) {
				out.print(e.getMessage());
				}
				%><br>
			</div>
		</div>
	</div>
</body>
</html>