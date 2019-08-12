<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Completed Reservation</title>
    </head>
    <body>
        <h1>Completed Reservation</h1>
        Airline: ${flight.operatingAirlines}<br>
        Departure City: ${flight.departureCity}<br>
        Arrival City: ${flight.arrivalCity}<br>

        <form action="completedReservation" method="post">
    <pre>
        <h2>Passenger Details:</h2>
        First Name: <input type="text" name="passengerFirstName"/>
        Last Name: <input type="text" name="passengerLastName"/>
        Email: <input type="text" name="passengerEmail"/>
        Phone: <input type="text" name="passengerPhone"/>

        <h2>Card Details:</h2>
        Name on the card: <input type="text" name="nameOnTheCard"/>
        Card No: <input type="text" name="cardNo"/>
        Expiry Date: <input type="text" name="expiryDate"/>
        Security Code: <input type="text" name="securityCode"/>

        <input type="hidden" name="flightId" value="${flight.id}"/>
        <input type="submit" value="Confirm"/>
    </pre>
        </form>
    </body>
</html>