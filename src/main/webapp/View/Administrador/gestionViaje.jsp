<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
  <title>Lista de Viajes</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
</head>
<body>
<c:if test="${not empty errorMessage}">
  <script type="text/javascript">
    alert("${errorMessage}");
  </script>
</c:if>

<div class="container">
  <h1 style="margin-top: 1.5rem">Lista de Viajes</h1>

  <div style="display: flex; width: 100%; justify-content: space-between">
    <a href="${pageContext.request.contextPath}/View/Administrador/dashboardAdmin.jsp">
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24">
        <path fill="currentColor" d="m9 18l-6-6l6-6l1.4 1.4L6.8 11H21v2H6.8l3.6 3.6z"/>
      </svg>
      <p>Volver al Dashboard</p>
    </a>
    <a class="button" href="${pageContext.request.contextPath}/GestionServlet?action=nuevoViaje">
      <p>Agregar Nuevo Viaje</p>
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"><path fill="currentColor" d="M11 13H5v-2h6V5h2v6h6v2h-6v6h-2z"/></svg>
    </a>
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
        <td>${viaje.ruta.origen} ➜ ${viaje.ruta.destino}</td>
        <td>${viaje.jornada}</td>
        <td>${viaje.asientosOcupados}</td>
        <td>
          <div style="display: flex; gap: 1.5rem; justify-content: center;">
            <a href="${pageContext.request.contextPath}/GestionServlet?action=formActualizarViaje&viajeId=${viaje.id}">
              Actualizar
            </a>
            <a  class="red-button" href="${pageContext.request.contextPath}/GestionServlet?action=eliminarViaje&viajeId=${viaje.id}"
               onclick="return confirm('¿Estás seguro de que deseas eliminar este viaje?');">Eliminar</a>
          </div>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</div>

</body>
</html>
