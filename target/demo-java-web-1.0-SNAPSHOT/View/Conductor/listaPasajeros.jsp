<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
    <title>Lista de Pasajeros</title>
</head>
<body>
<div class="header">
    <h1>Lista de Pasajeros para el Viaje #${viaje.id}</h1>
</div>
<div class="container">

    <c:if test="${not empty pasajeros}">
        <c:forEach var="pasajero" items="${pasajeros}">
            <div class="pasajero">
                <p><strong>Nombre:</strong> ${pasajero.nombre} ${pasajero.apellido}</p>
                <p><strong>ID de Estudiante:</strong> ${pasajero.id}</p>
                <p><strong>Correo:</strong> ${pasajero.email}</p>
            </div>
        </c:forEach>
    </c:if>

    <c:if test="${empty pasajeros}">
        <p>No hay pasajeros registrados para este viaje.</p>
    </c:if>

    <div class="button-group">
        <a href="javascript:history.back();" class="menu-item">Regresar</a>
    </div>

</div>
</body>
</html>
