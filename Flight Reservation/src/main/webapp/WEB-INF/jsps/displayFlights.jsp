<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Flights</title>
    </head>
    <body>
        <h1>Flights</h1>
        <table>
            <tr>
                <td>Airlines</td>
                <td>Departure city</td>
                <td>Arrival city</td>
            </tr>
            <c:forEach items="${flights}" var="flight">
                <tr>
                    <td>${flight.operatingAirlines}</td>
                    <td>${flight.departureCity}</td>
                    <td>${flight.arrivalCity}</td>
                    <td><a href="showCompletedReservation?flightId=${flight.id}">Select</a></td>
                </tr>
            </c:forEach>
        </table>

    </body>
</html>