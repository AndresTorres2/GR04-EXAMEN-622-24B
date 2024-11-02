<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <title>Lista de Pasajeros</title>
    <style>
        body {
            background: #100f0f;
            color: #dcdcdc;
            font-family: Arial, sans-serif;
        }

        .container {
            display: flex;
            flex-direction: column;
            align-items: center;
        }

        h1 {
            margin-bottom: 20px;
        }

        .pasajero {
            background-color: #1c1c1c;
            padding: 20px;
            border: 1px solid #dcdcdc;
            margin-bottom: 15px;
            width: 80%;
        }

        a {
            text-decoration: none;
            color: #dcdcdc;
        }

        .header {
            padding: 20px 0;
            text-align: center;
        }

        .button-group {
            display: flex;
            gap: 0.75rem;
        }
        .card-container {
            display: flex;
            flex-direction: column;
            flex-wrap: wrap;
            justify-content: center;
            gap: 20px;
            width: 100%;
        }

        .card {
            display: flex;
            width: 100%;
            box-sizing: border-box;
            justify-content: space-between;
            align-items: center;
            border-bottom: 1px solid #222222;
            padding: 2rem 0;
        }

        .card-container .card:last-child {
            border-bottom: none;
        }

        .card h3 {
            margin: 0 0 10px 0;
        }

        .card p {
            margin: 5px 0;
        }

        a {
            text-decoration: none;
            padding: 10px 20px;
            background-color: #48578e;
            color: white;
            border-radius: 5px;
            display: inline-block;
        }

        a:hover {
            background-color: #71a8df;
        }
    </style>
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
        <a href="${pageContext.request.contextPath}/GestionServlet?action=consultarViajesConductor&conductorId=${viaje.conductor.id}">Volver a Mis Viajes</a>
    </div>

</div>
</body>
</html>
