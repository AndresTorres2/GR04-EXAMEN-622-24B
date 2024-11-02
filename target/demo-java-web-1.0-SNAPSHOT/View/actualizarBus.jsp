<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <title>Actualizar Bus</title>
</head>
<body>
<h1>Actualizar Bus</h1>

<form action="${pageContext.request.contextPath}/GestionServlet?action=busActualizado" method="post">
  <input type="hidden" name="action" value="actualizarBus" />
  <input type="hidden" name="busId" value="${bus.busId}" />

  <label for="busIdDisplay">Número de Bus:</label><br/>
  <input type="text" id="busIdDisplay" name="busIdDisplay" value="${bus.busId}" disabled/><br/><br/>

  <label for="capacidad">Capacidad:</label><br/>
  <input type="number" id="capacidad" name="capacidad" value="${bus.capacidad}" required/><br/><br/>

 <br/><br/>

  <input type="submit" value="Actualizar Bus" />
</form>

<a href="${pageContext.request.contextPath}/GestionServlet?action=gestionBuses">Volver a la lista de buses</a>

</body>
</html>
