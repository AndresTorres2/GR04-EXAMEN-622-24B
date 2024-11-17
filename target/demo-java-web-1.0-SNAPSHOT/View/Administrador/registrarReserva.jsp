<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <title>Crear Nueva Reserva</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/form.css">
    <script>
        function searchViajes() {
            const input = document.getElementById('rutaSearchInput').value.toLowerCase();
            const rows = document.querySelectorAll('tbody tr');
            rows.forEach(row => {
                const ruta = row.cells[4].textContent.toLowerCase();
                if (ruta.includes(input)) {
                    row.style.display = '';
                } else {
                    row.style.display = 'none';
                }
            });
        }
    </script>
</head>
<body>
<a style="margin: 1.5rem 0; margin-left: -2.5rem " href="${pageContext.request.contextPath}/GestionServlet?action=gestionReservas">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24">
        <path fill="currentColor" d="m9 18l-6-6l6-6l1.4 1.4L6.8 11H21v2H6.8l3.6 3.6z"/>
    </svg>
    Volver a la Gestión de Reservas
</a>
<form class="styled-form" action="${pageContext.request.contextPath}/GestionServlet?action=crearReserva" method="post">
    <h1>Crear Nueva Reserva</h1>
    <div class="form-section">
        <label for="estudianteSelect">Seleccionar estudiante:</label>
        <select id="estudianteSelect" name="estudianteId" required>
            <option value="">Seleccione un estudiante</option>
            <c:forEach var="estudiante" items="${estudiantes}">
                <option value="${estudiante.id}">${estudiante.nombre} ${estudiante.apellido}</option>
            </c:forEach>
        </select>
    </div>

    <div class="form-section">
        <label for="rutaSearchInput">Buscar por ruta:</label>
        <input type="text" id="rutaSearchInput" onkeyup="searchViajes()" placeholder="Buscar por origen o destino...">
    </div>

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
                <td>${viaje.fecha}</td>
                <td>${viaje.asientosOcupados}</td>
                <td>${viaje.ruta.origen} ➜ ${viaje.ruta.destino}</td>
                <td>
                   <button class="blue-button"  type="submit" name="viajeId" value="${viaje.id}">Reservar</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</form>




</body>
</html>
