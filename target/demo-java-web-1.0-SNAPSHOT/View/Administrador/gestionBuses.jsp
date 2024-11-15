<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
  <title>Gestión de Buses</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/indexAdministrador.css">
</head>
<body>
<c:if test="${not empty errorMessage}">
  <script type="text/javascript">
    alert("${errorMessage}");
  </script>
</c:if>

<div class="container">
  <h1>Gestión de Buses</h1>

  <div class="nav-links">
    <a class="button" href="${pageContext.request.contextPath}/View/Administrador/dashboardAdmin.jsp">Volver al Dashboard</a>
    <a class="button" href="${pageContext.request.contextPath}/GestionServlet?action=nuevoBus">Agregar nuevo bus</a>
  </div>

  <table>
    <thead>
      <tr>
        <th>Bus #</th>
        <th>Capacidad</th>
        <th>Acciones</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="bus" items="${buses}">
        <tr>
          <td>${bus.busId}</td>
          <td>${bus.capacidad}</td>
          <td>
            <a href="${pageContext.request.contextPath}/GestionServlet?action=actualizarBus&busId=${bus.busId}">Actualizar</a> |
            <a href="${pageContext.request.contextPath}/GestionServlet?action=eliminarBus&busId=${bus.busId}" onclick="return confirm('¿Estás seguro de que deseas eliminar este bus?');">Eliminar</a>
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

</body>
</html>
