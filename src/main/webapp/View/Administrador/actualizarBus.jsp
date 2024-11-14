<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
  <title>Actualizar Bus</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/indexRegAct.css">
    <script>
        function validateInput(input) {
            input.value = input.value.replace(/[^0-9]/g, '');
        }
    </script>
</head>
<body>
<div class="container">

  <div class="button-container">
    <a class="button" href="${pageContext.request.contextPath}/GestionServlet?action=gestionBuses">Volver a la lista de buses</a>
  </div>

  <h1>Actualizar Bus</h1>

  <form class="styled-form" action="${pageContext.request.contextPath}/GestionServlet?action=busActualizado" method="post">
    <input type="hidden" name="action" value="actualizarBus" />
    <input type="hidden" name="busId" value="${bus.busId}" />

    <label for="busIdDisplay">Número de Bus:</label><br/>
    <input type="text" id="busIdDisplay" name="busIdDisplay" value="${bus.busId}" disabled/><br/>
    <label for="capacidad">Capacidad:</label><br/>
    <input type="number" id="capacidad" name="capacidad" value="${bus.capacidad}" required oninput="validateInput(this)"/><br/>

    <input class="button" type="submit" value="Actualizar Bus" />
  </form>

</div>

</body>
</html>
