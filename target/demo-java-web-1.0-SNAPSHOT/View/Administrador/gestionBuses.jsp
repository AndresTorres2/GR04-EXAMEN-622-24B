<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
  <title>Gestión de Buses</title>
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
<c:if test="${not empty errorMessage}">
  <script type="text/javascript">
    alert("${errorMessage}");
  </script>
</c:if>
<h1>Gestión de Buses</h1>
<div style="display: flex; margin: 1.5rem 0; justify-content: space-between">
  <a href="${pageContext.request.contextPath}/View/Administrador/dashboardAdmin.jsp">Volver al Dashboard</a>
  <a href="${pageContext.request.contextPath}/GestionServlet?action=nuevoBus">Agregar nuevo bus</a>
</div>
<table>
  <thead>
  <tr>
    <th>Bus #</th>
    <th>Capacidad</th>
    <th></th>
    <th></th>
  </tr>
  </thead>
  <tbody>
  <c:forEach var="bus" items="${buses}">
    <tr>
      <td>${bus.busId}</td>
      <td>${bus.capacidad}</td>
      <td>
        <a href="${pageContext.request.contextPath}/GestionServlet?action=actualizarBus&busId=${bus.busId}">Actualizar</a>

      </td>
      <td><a href="${pageContext.request.contextPath}/GestionServlet?action=eliminarBus&busId=${bus.busId}" onclick="return confirm('¿Estás seguro de que deseas eliminar este bus?');">Eliminar</a></td>
    </tr>
  </c:forEach>
  </tbody>
</table>

</body>
</html>
