<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setHeader("Expires", "0");
%>

<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <title>Detalles de la Reserva</title>
    <style>
        body{
            display: flex;
            align-items: center;
            height: 100vh;
            gap: 1.5rem;
            justify-content: space-between;

        }
    </style>
    <link rel="stylesheet" href="https://unpkg.com/leaflet@1.2.0/dist/leaflet.css"/>
    <link rel="stylesheet" href="https://unpkg.com/leaflet-routing-machine@latest/dist/leaflet-routing-machine.css"/>
</head>
<body>
<div style="width: 100%">
    <h1>Detalles de la Reserva</h1>
    <h3>${reserva.viaje.ruta.origen} ➜ ${reserva.viaje.ruta.destino}</h3>
    <p><strong>Horario:</strong> ${reserva.viaje.horaDeSalida} (${reserva.viaje.jornada})</p>
    <p><strong>Bus:</strong> #${reserva.viaje.bus.busId}</p>
    <p><strong>Fecha de viaje:</strong> <fmt:formatDate value="${reserva.viaje.fecha}"
                                                        pattern="EEEE"/>, ${reserva.viaje.fecha}</p>
    <p><strong>Fecha de la reserva:</strong> <fmt:formatDate value="${reserva.fecha}" pattern="EEEE"/>, ${reserva.fecha}
    </p>
    <strong>Recorrido:</strong>
    <ul>
        <c:forEach var="calle" items="${reserva.viaje.ruta.calles}">
            <li>${calle.nombre}</li>
        </c:forEach>
    </ul>
    <p><strong>Ubicacion del conductor:</strong></p>
    <p id="coordenadas-conductor">Esperando ubicación del conductor...</p>


    <div style="display: flex; justify-content: space-between; align-items: center; margin: 1.5rem 0;">
        <button id="add-waypoint" style="align-items: center; display: block;" onclick="habilitarAgregarParada()">Establecer Parada</button>
        <a class="red-button"
           href="${pageContext.request.contextPath}/ReservarAsientoServlet?action=cancelarReserva&reservaId=${reserva.id}"
           onclick="return confirm('¿Está seguro de que desea cancelar la reserva?');">
            Cancelar Reserva
        </a>
    </div>
    <a href="javascript:history.back();" class="tab">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24">
            <path fill="currentColor" d="m9 18l-6-6l6-6l1.4 1.4L6.8 11H21v2H6.8l3.6 3.6z"/>
        </svg>
        <p>Regresar a Reservas</p>
    </a>
</div>
<div>
   <div id="map"></div>
   <div id="map-leyenda">
       <div class="map-leyenda-item">
           <img src="${pageContext.request.contextPath}/assets/markerIcon.png">
           <p>Punto de referencia en la ruta</p>
       </div>
       <div class="map-leyenda-item">
           <img src="${pageContext.request.contextPath}/assets/paradaIcon.png">
           <p>Su parada seleccionada</p>
       </div>
       <div class="map-leyenda-item">
           <img src="${pageContext.request.contextPath}/assets/busIcon.png">
           <p>Polibus</p>
       </div>
   </div>

</div>

<script src="https://unpkg.com/leaflet@1.2.0/dist/leaflet.js"></script>
<script src="https://unpkg.com/leaflet-routing-machine@latest/dist/leaflet-routing-machine.js"></script>
<script>
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

    var routingControl = L.Routing.control({
        waypoints: waypoints,
        routeWhileDragging: false,
        draggableWaypoints: false,
        addWaypoints: false,
        autoRoute: false,
        editable: false,
    }).addTo(map);

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
    var paradaMarker;
    var agregarParada = false;
    const reservaId = ${reserva.id};

    function actualizarParada(latitud, longitud) {
        fetch('${pageContext.request.contextPath}/GestionServlet?action=gestionarParada', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                latitud: latitud,
                longitud: longitud,
                reservaId: reservaId,
            })
        })
            .then(response => response.json())
            .then(data => {
                if (data.success) {
                    alert("Parada establecida correctamente.");
                    obtenerParadaActualizada();
                } else {
                    alert("Hubo un error al agregar la parada.");
                }
            })
            .catch(error => console.error("Error al guardar la parada:", error));
    }

    function obtenerParadaActualizada() {
        fetch('${pageContext.request.contextPath}/ReservarAsientoServlet?action=mostrarParadasDeReserva&reservaId=' + reservaId + '&timestamp=' + new Date().getTime())
            .then(response => response.json())
            .then(data => {
                if (data.latitud && data.longitud) {
                    if (paradaMarker) {
                        map.removeLayer(paradaMarker);
                    }

                    paradaMarker = L.marker([data.latitud, data.longitud], { icon: paradaIcon }).addTo(map)
                        .bindPopup("Parada asignada a esta reserva.")
                        .openPopup();

                    console.log("Nueva parada: "+data.latitud+" CON "+data.longitud);
                } else {
                    console.error("No se encontró la parada para esta reserva.");
                }
            })
            .catch(error => console.error("Error al obtener la parada actualizada:", error));
    }

    // Obtener la parada al cargar el mapa
    obtenerParadaActualizada();

    // Manejar el clic en el mapa para agregar la parada
    map.on('click', function (e) {
        if (agregarParada) {
            if (paradaMarker) {
                map.removeLayer(paradaMarker);
            }

            // Crear el marcador para la nueva parada
            paradaMarker = L.marker([e.latlng.lat, e.latlng.lng], { icon: paradaIcon }).addTo(map)
                .bindPopup("Su parada está aquí.").openPopup();

            // Obtener las coordenadas y actualizar la parada en el backend
            const latitud = e.latlng.lat;
            const longitud = e.latlng.lng;

            if (latitud && longitud) {
                actualizarParada(latitud, longitud); // Actualizar parada y obtener la última parada
            } else {
                alert("Datos inválidos: latitud o longitud faltantes.");
            }

            agregarParada = false;
            document.getElementById('add-waypoint').textContent = "Reestablecer Parada";
        }
    });

    // Función para habilitar el agregado de parada
    function habilitarAgregarParada() {
        agregarParada = true;
        document.getElementById('add-waypoint').textContent = "Haga clic en el mapa para agregar una parada";
    }

</script>




</body>
</html>
