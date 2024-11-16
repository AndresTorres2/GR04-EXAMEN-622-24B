<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/login.css">
    <title>Login PoliBus</title>
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
