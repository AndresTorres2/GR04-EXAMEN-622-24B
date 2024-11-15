<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
  <title>Gestión de Reservas</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/indexAdministrador.css">
  <style>
  .container {
    max-width: 900px;
    margin: auto;
    padding: 2rem;
    border-radius: 12px;
    backdrop-filter: blur(8px);
    border: 1px solid #7e7e7e;
    background-color: rgba(255, 255, 255, 0.05);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  }

  a.button {
    text-decoration: none;
    padding: 10px 20px;
    background-color: #48578e;
    color: white;
    border-radius: 5px;
    display: inline-block;
    transition: background-color 0.3s ease, transform 0.2s ease;
    font-size: 14px;
    border: 1px solid transparent;
  }

  a.button:hover {
    background-color: #71a8df;
    transform: scale(1.05);
  }

  .nav-links {
    display: flex;
    justify-content: space-between;
    margin-bottom: 1.5rem;
  }

  #searchInput {
    padding: 10px;
    width: 200px;
    margin-bottom: 1.5rem;
    border: 1px solid #7e7e7e;
    border-radius: 5px;
    background: #1c1c1c;
    color: white;
  }

  #searchInput::placeholder {
    color: #a0a0a0;
  }

  table {
    width: 100%;
    border-collapse: collapse;
    background-color: rgba(0, 0, 0, 0.5);
    border-radius: 8px;
    overflow: hidden;
    margin-top: 1rem;
  }

  thead {
    background-color: #48578e;
    color: white;
  }

  th, td {
    padding: 0.75rem 1rem;
    border: 1px solid #3a3a3a;
    text-align: left;
    font-size: 14px;
  }

  tbody tr:nth-child(even) {
    background-color: rgba(255, 255, 255, 0.05);
  }

  tbody tr:hover {
    background-color: rgba(255, 255, 255, 0.1);
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
  <h1>Gestión de Reservas</h1>

  <div class="nav-links">
    <a class="button" href="${pageContext.request.contextPath}/View/Administrador/dashboardAdmin.jsp">Volver al Dashboard</a>
    <a class="button" href="${pageContext.request.contextPath}/GestionServlet?action=nuevaReserva">Agregar nueva reserva</a>
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
            <a href="${pageContext.request.contextPath}/GestionServlet?action=eliminarReserva&reservaId=${reserva.id}"
               onclick="return confirm('¿Estás seguro de que deseas eliminar esta reserva?');">Eliminar</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>
</body>
</html>
