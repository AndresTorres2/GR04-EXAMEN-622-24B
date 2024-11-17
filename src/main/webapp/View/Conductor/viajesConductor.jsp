<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<html>
<head>
    <title>Lista de viajes del Conductor</title>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
</head>
<body>
<div>

    <h1 style="margin-top: 2.5rem">Lista de Viajes del Conductor</h1>


<div class="card-container">
    <c:if test="${empty viajes}">
        <p>No hay viajes programados en este momento.</p>
    </c:if>
    <c:forEach var="viaje" items="${viajes}">
        <div class="card">
            <div>
                <p><strong>Fecha:</strong> ${viaje.fecha}</p>
                <p><strong>Hora de Salida:</strong> ${viaje.horaDeSalida}</p>
                <p><strong>Ruta:</strong> ${viaje.ruta.origen} ➔ ${viaje.ruta.destino}</p>
                <p><strong>Asientos ocupados:</strong> ${viaje.asientosOcupados}/${viaje.bus.capacidad}</p>
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
