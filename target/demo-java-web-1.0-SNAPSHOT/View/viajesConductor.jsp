<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>Lista de viajes del Conductor</title>
    <style>
        body {
            background: #100f0f;
            color: #d3d3d3;
            font-family: Arial, sans-serif;
            padding: 0 4rem;
            margin: 0;
            box-sizing: border-box;
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
    <h1>Lista de Viajes del Conductor</h1>
</div>

<div class="card-container">
    <c:forEach var="viaje" items="${viajes}">
        <div class="card">
            <div>
                <h3>Viaje Programado</h3>
                <p><strong>Fecha:</strong> ${viaje.fecha}</p>
                <p><strong>Hora de Salida:</strong> ${viaje.horaDeSalida}</p>
                <p><strong>Ruta:</strong> ${viaje.ruta.origen} ➔ ${viaje.ruta.destino}</p>
            </div>
            <a class="menu-item" href="${pageContext.request.contextPath}/GestionServlet?action=consultarViajesDetallesConductor&viajeId=${viaje.id}">
                Ver detalles
            </a>

        </div>
    </c:forEach>
</div>
</body>
</html>
