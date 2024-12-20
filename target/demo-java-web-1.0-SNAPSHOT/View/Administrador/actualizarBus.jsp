<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
  <title>Actualizar Bus</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/form.css">
  <script type="text/javascript">
    function validarCapacidad(input) {
      input.value = input.value.replace(/[^0-9]/g, '');
    }
  </script>
</head>
<body>
<a style="position:absolute; top: 2rem; left: 2rem" href="${pageContext.request.contextPath}/GestionServlet?action=gestionBuses">
  <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24">
    <path fill="currentColor" d="m9 18l-6-6l6-6l1.4 1.4L6.8 11H21v2H6.8l3.6 3.6z"/>
  </svg>
  <p>Volver a la lista de buses</p>
</a>

<form action="${pageContext.request.contextPath}/GestionServlet?action=busActualizado" method="post">
  <h1>Actualizar Bus</h1>
  <div class="form-section">
    <input type="hidden" name="action" value="actualizarBus" />
    <input type="hidden" name="busId" value="${bus.busId}" />
  </div>
  <div class="form-section">
    <label for="busIdDisplay">Número de Bus:</label>
    <input type="text" id="busIdDisplay" name="busIdDisplay" value="${bus.busId}" disabled/>
  </div>

  <div class="form-section">
    <label for="capacidad">Capacidad:</label>
    <input type="number" id="capacidad" name="capacidad" value="${bus.capacidad}" required oninput="validarCapacidad(this)" />
  </div>

  <input class="blue-button"  type="submit" value="Actualizar Bus" />
</form>

</body>
</html>
