<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
  <title>Lista de Viajes</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/indexAdministrador.css">
  <style>
  .container {
    max-width: 1400px;
    margin: auto;
    padding: 2rem;
    border-radius: 12px;
    backdrop-filter: blur(8px);
    border: 1px solid #7e7e7e;
    background-color: rgba(255, 255, 255, 0.05);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
  }
  </style>
</head>
<body>
<c:if test="${not empty errorMessage}">
  <script type="text/javascript">
    alert("${errorMessage}");
  </script>
</c:if>

<div class="container">
  <h1>Lista de Viajes</h1>

  <div class="nav-links">
    <a class="button" href="${pageContext.request.contextPath}/View/Administrador/dashboardAdmin.jsp">Volver al Dashboard</a>
    <a class="button" href="${pageContext.request.contextPath}/GestionServlet?action=nuevoViaje">Agregar Nuevo Viaje</a>
  </div>

  <table>
    <thead>
      <tr>
        <th>ID</th>
        <th>Conductor</th>
        <th>Bus</th>
        <th>Fecha</th>
        <th>Hora de Salida</th>
        <th>Ruta</th>
        <th>Recorrido</th>
        <th>Jornada</th>
        <th>Asientos Ocupados</th>
        <th>Acciones</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="viaje" items="${viajes}">
        <tr>
          <td>${viaje.id}</td>
          <td>${viaje.conductor.nombre} ${viaje.conductor.apellido}</td>
          <td>${viaje.bus.busId}</td>
          <td>${viaje.fecha}</td>
          <td>${viaje.horaDeSalida}</td>
          <td>Desde: ${viaje.ruta.origen} hasta ${viaje.ruta.destino}</td>
          <td>
            <c:forEach var="calle" items="${viaje.ruta.calles}" varStatus="status">
              ${calle.nombre}<c:if test="${!status.last}">, </c:if>
            </c:forEach>
          </td>
          <td>${viaje.jornada}</td>
          <td>${viaje.asientosOcupados}</td>
          <td>
            <a href="${pageContext.request.contextPath}/GestionServlet?action=formActualizarViaje&viajeId=${viaje.id}">Actualizar</a> |
            <a href="${pageContext.request.contextPath}/GestionServlet?action=eliminarViaje&viajeId=${viaje.id}"
               onclick="return confirm('¿Estás seguro de que deseas eliminar este viaje?');">Eliminar</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

</body>
</html>
