<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <title>Menú Principal Conductor</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
</head>
<body>
    <div style="width: 100%; margin-left: -4rem; display: flex; align-items: center; justify-content: space-between; background-color: var(--dark-blue); padding: 2rem 4rem;">
        <img style="width: 182px; height: 100px" src="${pageContext.request.contextPath}/assets/epnlogo.svg" alt="epn logo">
            <a href="${pageContext.request.contextPath}/GestionServlet?action=cerrarSesion">
                <p>Cerrar Sesión</p>
                <svg viewBox="0 0 24 24" height="16" width="16"><path fill="currentColor" d="M5 21q-.825 0-1.412-.587T3 19V5q0-.825.588-1.412T5 3h6q.425 0 .713.288T12 4t-.288.713T11 5H5v14h6q.425 0 .713.288T12 20t-.288.713T11 21zm12.175-8H10q-.425 0-.712-.288T9 12t.288-.712T10 11h7.175L15.3 9.125q-.275-.275-.275-.675t.275-.7t.7-.313t.725.288L20.3 11.3q.3.3.3.7t-.3.7l-3.575 3.575q-.3.3-.712.288t-.713-.313q-.275-.3-.262-.712t.287-.688z"></path></svg>
            </a>
    </div>
    <h1 style="margin-top: 2rem">Menú Principal del Conductor</h1>
    <p>Bienvenido/a, ${conductor.nombre} ${conductor.apellido}</p>
    <br>
    <div class="menu">
        <a class="menu-item" href="${pageContext.request.contextPath}/GestionServlet?action=consultarViajesConductor&conductorId=${conductor.id}">Ver mis Viajes</a>
    </div>

</body>
</html>
