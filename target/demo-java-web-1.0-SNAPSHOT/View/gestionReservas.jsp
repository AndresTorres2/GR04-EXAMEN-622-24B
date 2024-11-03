<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <title>Gestión de Reservas</title>
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

    #searchInput {
      padding: 10px;
      width: 200px;
      margin-bottom: 1.5rem;
      border: 1px solid #d3d3d3;
      border-radius: 5px;
      background: #1c1c1c;
      color: white;
    }
  </style>
  <script>
    function searchReservas() {
      const input = document.getElementById('searchInput').value.toLowerCase();
      const rows = document.querySelectorAll('tbody tr');
      rows.forEach(row => {
        const studentName = row.cells[1].textContent.toLowerCase();
        if (studentName.includes(input)) {
          row.style.display = '';
        } else {
          row.style.display = 'none';
        }
      });
    }
    window.onload = function() {
      const mensaje = "${mensaje}";
      if (mensaje) {
        alert(mensaje);
      }
    };
  </script>

</head>
<body>
<h1>Gestión de Reservas</h1>

<div style="display: flex; margin: 1.5rem 0; justify-content: space-between">
  <a href="${pageContext.request.contextPath}/View/dashboardAdmin.jsp">Volver al Dashboard</a>
  <a href="${pageContext.request.contextPath}/GestionServlet?action=nuevaReserva">Agregar nueva reserva</a>
</div>


<input type="text" id="searchInput" placeholder="Buscar por nombre del estudiante" onkeyup="searchReservas()">

<table>
  <thead>
  <tr>
    <th>Fecha de Reserva</th>
    <th>Estudiante</th>
    <th>Bus</th>
    <th>Ruta</th>
    <th>Horario de Salida</th>

    <th></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="reserva" items="${reservas}">
    <tr>
      <td>${reserva.fecha}</td>
      <td>${reserva.estudiante.nombre} ${reserva.estudiante.apellido}</td>
      <td>${reserva.viaje.bus.busId}</td>
      <td>${reserva.viaje.ruta.origen} ➜ ${reserva.viaje.ruta.destino}</td>
      <td>${reserva.viaje.horaDeSalida}</td>
      <td>
        <a href="${pageContext.request.contextPath}/GestionServlet?action=eliminarReserva&reservaId=${reserva.id}" onclick="return confirm('¿Estás seguro de que deseas eliminar esta reserva?');">Eliminar</a>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</body>
</html>
