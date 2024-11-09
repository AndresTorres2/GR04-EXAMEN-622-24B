<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
<head>
  <title>Actualizar Ruta</title>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/css/select2.min.css" rel="stylesheet" />
</head>
<body>
<h1>Actualizar Ruta</h1>
<form action="${pageContext.request.contextPath}/GestionServlet?action=actualizarRuta" method="post">
  <input type="hidden" name="rutaId" value="${ruta.id}" /> <!-- ID de la ruta a actualizar -->

  <label for="origen">Origen:</label>
  <input type="text" id="origen" name="origen" value="${ruta.origen}" required /><br/><br/>

  <label for="destino">Destino:</label>
  <input type="text" id="destino" name="destino" value="${ruta.destino}" required /><br/><br/>

  <label for="calles">Selecciona Calles:</label><br/>
  <select name="calles" id="calles" multiple>
    <c:forEach var="calle" items="${ruta.calles}">
      <option value="${calle.id}"
      <c:if test="${fn:contains(ruta.calles, calle)}">selected</c:if>
      >${calle.nombre}</option>
    </c:forEach>
  </select>
  <br/><br/>

  <input type="submit" value="Actualizar Ruta" />
</form>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/js/select2.min.js"></script>
<script>
  $(document).ready(function() {
    $('#calles').select2({
      placeholder: "Selecciona calles",
      allowClear: true
    });
  });
</script>
</body>
</html>
