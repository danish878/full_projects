<%--
  Created by IntelliJ IDEA.
  User: Danish
  Date: 5/24/2019
  Time: 4:02 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Reservation Details</title>
    </head>
    <body>
        <h2>Flight Details:</h2>
        <pre>
Airlines: ${reservation.flight.operatingAirlines}
Flight Number: ${reservation.flight.flightNumber}
Departure City: ${reservation.flight.departureCity}
Arrival City: ${reservation.flight.arrivalCity}
Date Of Departure: ${reservation.flight.dateOfDeparture}
Estimated Departure Time: ${reservation.flight.estimatedDepartureTime}

<h2>Passenger Details</h2>
First Name: ${reservation.passenger.firstName}
Last Name: ${reservation.passenger.lastName}
Email: ${reservation.passenger.email}
Phone: ${reservation.passenger.phone}
</pre>
        <form action="completeCheckIn" method="post">
            Number of Bags: <input type="text" name="numberOfBags"/>
            <input type="hidden" name="reservationId" value="${reservation.id}"/>
            <input type="submit" value="Check In"/>
        </form>
    </body>
</html>
