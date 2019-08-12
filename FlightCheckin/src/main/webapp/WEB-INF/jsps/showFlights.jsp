<!DOCTYPE html>
<html>
    <head>
        <title>Flights</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
    </head>

    <body>
        <div>
            <p class="id">The ID is </p>
            <p class="numberOfBags">No. of bags is </p>
        </div>
    </body>

    <script>
        $(document).ready(function () {
            $.ajax({
                url: "http://localhost:8080/flightReservation/reservations/3"
            }).then(function (data, status, jqxhr) {
                $('.id').append(data.id);
                $('.numberOfBags').append(data.numberOfBags);
                console.log(jqxhr);
            });
        });
    </script>
</html>