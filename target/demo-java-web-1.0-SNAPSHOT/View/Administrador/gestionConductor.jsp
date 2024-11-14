<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
  <title>Gestión de Conductores</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/indexGestionConductor.css">
</head>
<body>
<c:if test="${not empty errorMessage}">
  <script type="text/javascript">
    alert("${errorMessage}");
  </script>
</c:if>

<div class="container">
  <h1>Gestión de Conductores</h1>

  <div class="nav-links">
    <a class="button" href="${pageContext.request.contextPath}/View/Administrador/dashboardAdmin.jsp">Volver al Dashboard</a>
    <a class="button" href="${pageContext.request.contextPath}/GestionServlet?action=nuevoConductor">Agregar nuevo conductor</a>
  </div>

  <table>
    <thead>
      <tr>
        <th>Nombre</th>
        <th>Apellido</th>
        <th>Email</th>
        <th>Teléfono</th>
        <th>Acciones</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="conductor" items="${conductores}">
        <tr>
          <td>${conductor.nombre}</td>
          <td>${conductor.apellido}</td>
          <td>${conductor.email}</td>
          <td>${conductor.phone}</td>
          <td>
            <a href="${pageContext.request.contextPath}/GestionServlet?action=actualizarConductorForm&conductorId=${conductor.id}">Actualizar</a> |
            <a href="${pageContext.request.contextPath}/GestionServlet?action=eliminarConductor&conductorId=${conductor.id}" onclick="return confirm('¿Estás seguro de que deseas eliminar este conductor?');">Eliminar</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

</body>
</html>
