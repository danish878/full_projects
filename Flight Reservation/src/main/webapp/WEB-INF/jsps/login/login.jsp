<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>Login</title>
    </head>
    <body>
        <h1>Login</h1>
        <div style="color:red">${msg}</div>
        <form action="login" method="post">
    <pre>
        Username: <input type="text" name="email"/>
        Password: <input type="password" name="password"/>
        <input type="submit" value="Login"/>
    </pre>
        </form>
    </body>
</html>