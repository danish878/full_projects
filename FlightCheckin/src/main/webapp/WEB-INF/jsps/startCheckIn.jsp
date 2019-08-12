<%--
  Created by IntelliJ IDEA.
  User: Danish
  Date: 5/24/2019
  Time: 3:50 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Start Check In</title>
    </head>
    <body>
        <h2>Start Check In</h2>
        <form action="startCheckIn" method="post">
            Reservation Id: <input type="text" name="reservationId"/>
            <input type="submit" value="Start Check In"/>
        </form>
    </body>
</html>
