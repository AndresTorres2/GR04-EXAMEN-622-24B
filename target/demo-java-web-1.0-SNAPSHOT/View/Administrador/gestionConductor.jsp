<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
  <title>Gestión de Conductores</title>

  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">

</head>
<body>
<c:if test="${not empty errorMessage}">
  <script type="text/javascript">
    alert("${errorMessage}");
  </script>
</c:if>

<div class="container">
  <h1 style="margin-top: 1.5rem">Gestión de Conductores</h1>

  <div style="display: flex; width: 100%; justify-content: space-between">
    <a href="${pageContext.request.contextPath}/View/Administrador/dashboardAdmin.jsp">
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24">
        <path fill="currentColor" d="m9 18l-6-6l6-6l1.4 1.4L6.8 11H21v2H6.8l3.6 3.6z"/>
      </svg>
      <p>
        Volver al Dashboard
      </p>
    </a>
    <a class="button" href="${pageContext.request.contextPath}/GestionServlet?action=nuevoConductor">
      <p>Agregar nuevo conductor</p>
      <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24"><path fill="currentColor" d="M11 13H5v-2h6V5h2v6h6v2h-6v6h-2z"/></svg>
    </a>
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
          <td >
            <div style="display: flex; gap: 1.5rem; width: 100%; justify-content: center">
              <a href="${pageContext.request.contextPath}/GestionServlet?action=actualizarConductorForm&conductorId=${conductor.id}">
                Actualizar
              </a>
              <a  class="red-button" href="${pageContext.request.contextPath}/GestionServlet?action=eliminarConductor&conductorId=${conductor.id}" onclick="return confirm('¿Estás seguro de que deseas eliminar este conductor?');">Eliminar</a>
            </div>
             </td>

        </tr>
      </c:forEach>
    </tbody>
  </table>
</div>

</body>
</html>
