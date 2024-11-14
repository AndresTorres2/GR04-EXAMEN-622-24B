<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <title>Menú Principal Conductor</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/conductorDashboard.css">
</head>
<body>
<div class="dashboard-container">
    <h1>Menú Principal del Conductor</h1>
    <p class="welcome-message" >Bienvenido Conductor: ${conductor.nombre} ${conductor.apellido}</p>
    <br>
    <div class="menu">
        <a class="menu-item" href="${pageContext.request.contextPath}/GestionServlet?action=consultarViajesConductor&conductorId=${conductor.id}">Ver mis Viajes</a>
    </div>
    <div class="logout-container">
        <a class="logout-button" href="${pageContext.request.contextPath}/GestionServlet?action=cerrarSesion">Cerrar Sesión</a>
    </div>
</div>
</body>
</html>
