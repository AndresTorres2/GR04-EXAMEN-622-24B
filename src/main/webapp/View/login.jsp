<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login PoliBus</title>
    <style>
        body {
            background: #100f0f;
            color: #d3d3d3;
            font-family: Arial, sans-serif;
            padding: 4rem;
            margin: 0;
            box-sizing: border-box;
        }

        input[type="submit"] {
            text-decoration: none;
            padding: 10px 20px;
            background-color: #48578e;
            color: white;
            border-radius: 5px;
            display: inline-block;
        }

        input[type="submit"]:hover {
            background-color: #71a8df;
        }

        .error-message {
            color: red;
            margin-top: 10px;
        }
    </style>
</head>
<body>
<h2>Login PoliBus</h2>

<form action="${pageContext.request.contextPath}/GestionServlet?action=validarUsuario" method="post">
    <label for="email">Usuario:</label>
    <input type="text" id="email" name="email" required><br><br>

    <label for="password">Contraseña:</label>
    <input type="password" id="password" name="password" required><br><br>

    <input type="submit" value="Iniciar Sesión" />
</form>

<c:if test="${not empty error}">
    <div class="error-message">${error}</div>
</c:if>

</body>
</html>
