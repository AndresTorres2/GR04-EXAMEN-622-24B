<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
    <title>Compartir Ubicación del Conductor</title>
    <style>
        body{
            display: flex;
            gap: 1.5rem;
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
    </style>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.2.0/dist/leaflet.css"/>
    <link rel="stylesheet" href="https://unpkg.com/leaflet-routing-machine@latest/dist/leaflet-routing-machine.css"/>
</head>
<body>
<div class="card-container">
    <div class="card">
        <div>
            <h3>Viaje Programado</h3>
            <p><strong>Fecha:</strong> ${viaje.fecha}</p>
            <p><strong>Hora de Salida:</strong> ${viaje.horaDeSalida}</p>
            <p><strong>Jornada:</strong> ${viaje.jornada}</p>
            <p><strong>Número del Bus:</strong> ${viaje.bus.busId}</p>
            <p><strong>Asientos Ocupados:</strong> ${viaje.asientosOcupados}</p>
            <p><strong>Ruta:</strong> ${viaje.ruta.origen} ➔ ${viaje.ruta.destino}</p>
        </div>
    </div>
    <div class="button-group">
        <a class="menu-item" href="javascript:void(0)"  onclick="iniciarCompartirUbicacion(${viaje.id})">Iniciar y Compartir Ubicación</a>
        <a href="${pageContext.request.contextPath}/GestionServlet?action=notificarPasajeros&viajeId=${viaje.id}">Notificar Pasajeros</a>
        <a href="${pageContext.request.contextPath}/ViajeServlet?ruta=verPasajeros&viajeId=${viaje.id}">Ver listado de Pasajeros</a>
    </div>
    <a href="javascript:history.back();" class="menu-item">Regresar</a>
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
    var waypointAvailable = false;

    <c:forEach var="calle" items="${callesYCoordenadas}">
    var latitud = ${calle[2]};
    var longitud = ${calle[3]};
    waypoints.push(L.latLng(latitud, longitud));
    L.marker([latitud, longitud]).addTo(map).bindPopup('${calle[1]}');
    </c:forEach>

    var routingControl = L.Routing.control({
        waypoints: waypoints,
        routeWhileDragging: false,
        draggableWaypoints: false,
        autoRoute: false,
        editable: false,
    }).addTo(map);

    routingControl.getPlan().on('click', function (e) {
        e.preventDefault();
    });

    routingControl.route();

    var paradaIcon = L.icon({
        iconUrl: "${pageContext.request.contextPath}/assets/paradaIcon.png",
        iconSize: [25, 41],
    });

    var busIcon = L.icon({
        iconUrl: "${pageContext.request.contextPath}/assets/busIcon.png",
        iconSize: [40, 40],
    });

    var busMarker;


    function actualizarUbicacionBus() {
        fetch('${pageContext.request.contextPath}/GestionServlet?action=obtenerUbicacion')
            .then(response => response.text())
            .then(data => {
                if (data === "Ubicación no disponible") {
                    document.getElementById('coordenadas-conductor').textContent = data;
                    if (busMarker) {
                        map.removeLayer(busMarker);
                        busMarker = null;
                    }
                } else {
                    const [latitud, longitud] = data.split(",");
                    document.getElementById('coordenadas-conductor').textContent =
                        "Latitud: " + latitud + ", Longitud: " + longitud;

                    if (busMarker) {
                        map.removeLayer(busMarker);
                    }

                    busMarker = L.marker([latitud, longitud], {icon: busIcon}).addTo(map);
                    map.setView([latitud, longitud], 13);
                }
            })
            .catch(error => console.error("Error al obtener la ubicación:", error));
    }

    setInterval(actualizarUbicacionBus, 5000);
</script>
<script>
    function iniciarCompartirUbicacion(viajeId) {
        if (navigator.geolocation) {
            ubicacionIntervalo = setInterval(() => {
                navigator.geolocation.getCurrentPosition(function (position) {
                    const latitud = position.coords.latitude;
                    const longitud = position.coords.longitude;
                    console.log("latitud: ", latitud);
                    console.log("longitud: ", longitud);

                    fetch('${pageContext.request.contextPath}/GestionServlet?action=compartirUbicacion&viajeId=' + viajeId + '&latitud=' + latitud + '&longitud=' + longitud, {
                        method: 'POST'
                    })
                        .then(response => {
                            if (!response.ok) {
                                console.error("Error al compartir la ubicación:", response.statusText);
                            }
                        })
                        .catch(error => console.error("Error de red:", error));
                });
            }, 5000);
        } else {
            alert("La geolocalización no está soportada en este navegador.");
        }
    }
</script>
</body>
</html>
