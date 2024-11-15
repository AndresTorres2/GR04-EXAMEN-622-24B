<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <title>Login PoliBus</title>
    <style>
        body {
            background: url("${pageContext.request.contextPath}/assets/epnbackground.webp");
            color: #d3d3d3;
            font-family: Arial, sans-serif;
            padding: 4rem;
            margin: 0;
            box-sizing: border-box;
        }
        body::before {
            content: "";
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.85);
            z-index: -1;
            pointer-events: none;
        }
        input {
            text-align: left !important;
        }

        input:-webkit-autofill {
            -webkit-box-shadow: 0 0 0px 1000px #242424 inset !important; /** nota: se necesita una manera de hacer esto transparente **/
            -webkit-text-fill-color: #d3d3d3 !important;
            transition: background-color 5000s ease-in-out 0s;
        }

        input:-webkit-autofill:focus,
        input:-webkit-autofill:active {
            -webkit-box-shadow: 0 0 0px 1000px #242424 inset !important;
            -webkit-text-fill-color: #d3d3d3 !important;
            transition: background-color 5000s ease-in-out 0s;
        }

        body > div{
            margin-top: 100px;
            margin-left: 1100px;
            width: fit-content;
            text-align: center;
        }
        form{
            backdrop-filter: blur(8px);
            border-radius: 12px;
            border: 1px #7e7e7e solid;
            padding: 3.25rem 5rem;
        }
        form > div{
            width: 100%;
            display: flex;
            gap: 1.5rem;
            justify-content: space-between;
        }
        input[type="submit"] {
            all: unset;
            padding: 10px 20px;
            background-color: #48578e;
            color: white;
            border-radius: 5px;
            display: inline-block;
            cursor: pointer;
        }

        input[type="text"], input[type="number"], input[type="password"] {
           all:unset;
            border-bottom: 1px white solid;
            justify-content: normal;
        }

            input[type="submit"]:hover {
            background-color: #71a8df;
        }

        .error-message {
            color: #bf4141;
            margin-top: 10px;
        }
        .logo-container {
            text-align: center;
            margin-bottom: 50px;
            margin-left: 80px;
        }

        .logo-container img {
            width: 150px;
            height: auto;
        }
    </style>
</head>
<body>
<div>
    <form action="${pageContext.request.contextPath}/GestionServlet?action=validarUsuario" method="post">
        <div class="logo-container">
            <img src="${pageContext.request.contextPath}/assets/epn.png" alt="EPN Logo">
        </div>
        <h2>Iniciar sesión</h2>
        <div>
            <label for="email">Correo</label>
            <input type="text" id="email" name="email" required>
        </div>
        <br><br>
       <div>
           <label for="password">Contraseña</label>
           <input type="password" id="password" name="password" required>
       </div>
       <br><br>
        <input type="submit" value="Acceder" />
    </form>

    <c:if test="${not empty error}">
        <div class="error-message">${error}</div>
    </c:if>

</div>
</body>
</html>
