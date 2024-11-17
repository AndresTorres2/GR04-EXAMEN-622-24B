<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
  <title>Gestión de Reservas</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
  <style>
    #searchInput {
      padding: 10px;
      width: 300px;
      margin: 1.5rem 0;
      border: 1px solid var(--white-300);
      border-radius: 6px;
    }

    #searchInput::placeholder {
      color: #a0a0a0;
    }
  </style>
  <script>
    function searchReservas() {
      const input = document.getElementById('searchInput').value.toLowerCase();
      const rows = document.querySelectorAll('tbody tr');
      rows.forEach(row => {
        const studentName = row.cells[1].textContent.toLowerCase();
        row.style.display = studentName.includes(input) ? '' : 'none';
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
<div class="container">
  <h1 style="margin-top: 1.5rem;">Gestión de Reservas</h1>

  <div style="display: flex; justify-content: space-between; margin-bottom: 1.5rem;">
    <a href="${pageContext.request.contextPath}/View/Administrador/dashboardAdmin.jsp">
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24">
        <path fill="currentColor" d="m9 18l-6-6l6-6l1.4 1.4L6.8 11H21v2H6.8l3.6 3.6z"/>
      </svg>
      <p>Volver al Dashboard</p>
    </a>
    <a class="button" href="${pageContext.request.contextPath}/GestionServlet?action=nuevaReserva">
      <p>Agregar nueva reserva</p>
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"><path fill="currentColor" d="M11 13H5v-2h6V5h2v6h6v2h-6v6h-2z"/></svg>
    </a>
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
      <th>Acciones</th>
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
          <a class="red-button" href="${pageContext.request.contextPath}/GestionServlet?action=eliminarReserva&reservaId=${reserva.id}"
             onclick="return confirm('¿Estás seguro de que deseas eliminar esta reserva?');">Eliminar</a>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>
