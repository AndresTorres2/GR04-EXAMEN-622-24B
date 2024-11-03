<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Crear Nueva Reserva</title>
    <style>
        body {
            background: #100f0f;
            color: #d3d3d3;
            font-family: Arial, sans-serif;
            padding: 4rem;
            margin: 0;
            box-sizing: border-box;
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

        table {
            border-collapse: collapse;
            margin-bottom: 1.5rem;
            width: 100%;
        }

        th, td, tr {
            padding: 0.5rem 1rem;
            border: 1px solid #d3d3d3;
        }

        select, button {
            padding: 10px;
            margin: 10px 0;
            border: 1px solid #d3d3d3;
            border-radius: 5px;
            background: #1c1c1c;
            color: white;
        }
    </style>
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


<div style="margin-bottom: 2rem;">
    <a href="${pageContext.request.contextPath}/View/gestionReservas.jsp">Volver al Gestion de Reservas</a>
</div>

<form action="${pageContext.request.contextPath}/GestionServlet?action=crearReserva" method="post">
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
                    <button type="submit" name="viajeId" value="${viaje.id}">Reservar</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</form>
</body>
</html>
