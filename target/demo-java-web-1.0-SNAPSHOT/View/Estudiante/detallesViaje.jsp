<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
    <title>Detalles del Bus</title>

    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.2.0/dist/leaflet.css"/>
    <link rel="stylesheet" href="https://unpkg.com/leaflet-routing-machine@latest/dist/leaflet-routing-machine.css"/>
    <style>
        body{
            display: flex;
            align-items: center;
            height: 100vh;
            gap: 1.5rem;
            justify-content: space-between;
        }
    </style>
</head>
<body>
<div style="width: 100%">
    <h1>Detalles del Viaje</h1>
    <h3>${viaje.ruta.origen} ➜ ${viaje.ruta.destino}</h3>
    <p><strong>Horario:</strong> ${viaje.horaDeSalida} (${viaje.jornada})</p>
    <p><strong>Recorrido:</strong></p>
    <ul>
        <c:forEach var="calle" items="${viaje.ruta.calles}">
            <li>${calle.nombre}</li>
        </c:forEach>
    </ul>
    <div style="display: flex; justify-content: space-between; align-items: center; margin: 1.5rem 0;  gap: 1.5rem; width: 100%">
        <a href="${pageContext.request.contextPath}/View/Estudiante/listarViajes.jsp">
            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24">
                <path fill="currentColor" d="m9 18l-6-6l6-6l1.4 1.4L6.8 11H21v2H6.8l3.6 3.6z"/>
            </svg>
            <p>
                Regresar a la lista de viajes
            </p>
        </a>

        <a href="${pageContext.request.contextPath}/ReservarAsientoServlet?action=formularioReserva&idsViaje=${idViajes}">
            Reservar asiento</a>
    </div>

</div>
<div id="map"></div>


<script src="https://unpkg.com/leaflet@1.2.0/dist/leaflet.js"></script>
<script src="https://unpkg.com/leaflet-routing-machine@latest/dist/leaflet-routing-machine.js"></script>
<script>
    var map = L.map('map').setView([-0.210194, -78.489326], 13);

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 20,
    }).addTo(map);

    var waypoints = [];

    <c:forEach var="calle" items="${callesYCoordenadas}">
    var latitud = ${calle[2]};
    var longitud = ${calle[3]};
    waypoints.push(L.latLng(latitud, longitud));
    L.marker([latitud, longitud]).addTo(map).bindPopup('${calle[1]}');
    </c:forEach>

    L.Routing.control({
        waypoints: waypoints,
        routeWhileDragging: true
    }).addTo(map);
</script>


</body>
</html>
