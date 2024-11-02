<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Lista de Viajes</title>
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
    table{
      border-collapse: collapse;
      margin-bottom: 1.5rem;
      width: 100%;

    }

    th, td, tr{
      padding: 0.5rem 1rem;
      border: 1px solid #d3d3d3;
    }

  </style>
</head>
<body>
<h1>Lista de Viajes</h1>
<div style="display: flex; margin: 1.5rem 0; justify-content: space-between">
  <a href="${pageContext.request.contextPath}/View/dashboardAdmin.jsp">Volver al Dashboard</a>
  <a href="${pageContext.request.contextPath}/GestionServlet?action=nuevoViaje">Agregar Nuevo Viaje</a>
</div>
<table border="1">
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
    <th></th>
    <th></th>
  </tr>
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
        <a href="${pageContext.request.contextPath}/GestionServlet?action=formActualizarViaje&viajeId=${viaje.id}">Actualizar</a>
      </td>
      <td>
        <a href="${pageContext.request.contextPath}/GestionServlet?action=eliminarViaje&viajeId=${viaje.id}"
           onclick="return confirm('¿Estás seguro de que deseas eliminar este viaje?');">Eliminar</a>
      </td>
    </tr>
  </c:forEach>
</table>



</body>
</html>
