<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <title>Crear Nueva Reserva</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/indexRegAct.css">
    <script>
        function searchViajes() {
            const input = document.getElementById('rutaSearchInput').value.toLowerCase();
            const rows = document.querySelectorAll('tbody tr');
            rows.forEach(row => {
                const rutaOrigen = row.cells[4].textContent.toLowerCase();
                const rutaDestino = row.cells[4].textContent.toLowerCase();
                if (rutaOrigen.includes(input) || rutaDestino.includes(input)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        }
    </script>
</head>
<body>
<h1>Nueva Reserva</h1>


<div class="button-container">
    <a class="button"href="${pageContext.request.contextPath}/GestionServlet?action=gestionReservas">Volver al Gestion de Reservas</a>
</div>

<form class="tablaReserva" action="${pageContext.request.contextPath}/GestionServlet?action=crearReserva" method="post">
    <label for="estudianteSelect">Seleccionar Estudiante:</label>
    <select id="estudianteSelect" name="estudianteId" required>
        <option value="">Seleccione un estudiante</option>
        <c:forEach var="estudiante" items="${estudiantes}">
            <option value="${estudiante.id}">${estudiante.nombre} ${estudiante.apellido}</option>
        </c:forEach>
    </select>

    <label for="rutaSearchInput">Buscar por Ruta:</label>
    <input type="text" id="rutaSearchInput" onkeyup="searchViajes()" placeholder="Buscar por origen o destino...">

    <!-- Tabla de viajes -->
    <h2>Viajes Disponibles</h2>
    <table>
        <thead>
        <tr>
            <th>Jornada</th>
            <th>Hora de Salida</th>
            <th>Fecha</th>
            <th>Asientos Ocupados</th>
            <th>Ruta</th>
            <th>Acciones</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="viaje" items="${viajes}">
            <tr>
                <td>${viaje.jornada}</td>
                <td>${viaje.horaDeSalida}</td>
                <td>${viaje.fecha} </td>
                <td>${viaje.asientosOcupados}</td>
                <td>${viaje.ruta.origen} ➜ ${viaje.ruta.destino}</td>
                <td>
                    <div class="reserva-container ">
                        <button class="buttonR" type="submit" name="viajeId" value="${viaje.id}">Reservar</button>
                    </div>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</form>

</body>
</html>
