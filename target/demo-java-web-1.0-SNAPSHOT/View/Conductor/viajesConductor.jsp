<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>Lista de viajes del Conductor</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <style>
        body {
            background: url("${pageContext.request.contextPath}/assets/BannerPolibus720.jpg") no-repeat center center fixed;
            background-size: cover;
            color: #d3d3d3;
            font-family: Arial, sans-serif;
            padding: 0 4rem;
            margin: 0;
            box-sizing: border-box;
        }

        .dashboard-container {
            width: 80%;
            margin: auto;
            padding: 2rem;
            border-radius: 12px;
            backdrop-filter: blur(8px);
            border: 1px solid #7e7e7e;
            text-align: center;
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
            background-color: rgba(0, 0, 0, 0.75); /* Fondo semitransparente más oscuro */
            border-radius: 8px;
            backdrop-filter: blur(5px); /* Difuminado */
        }

        .card-container .card:last-child {
            border-bottom: none;
        }

        .card h3 {
            margin: 0 0 10px 0;
            color: #ffffff; /* Texto blanco para mayor contraste */
            font-weight: bold; /* Texto en negrita */
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.8); /* Sombra para mejorar legibilidad */
        }

        .card p {
            margin: 5px 0;
            color: #ffffff; /* Texto blanco para mayor contraste */
            font-weight: bold; /* Texto en negrita */
            text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.8); /* Sombra para mejorar legibilidad */
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

        .menu-item {
            margin-top: 20px;
        }
    </style>
</head>
<body>
<div class="dashboard-container">
<div class="header">
    <h1>Lista de Viajes del Conductor</h1>
</div>

<div class="card-container">
    <c:if test="${empty viajes}">
        <p>No hay viajes programados en este momento.</p>
    </c:if>
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
<a href="javascript:history.back();" class="menu-item">Regresar</a>
</div>
</body>
</html>
