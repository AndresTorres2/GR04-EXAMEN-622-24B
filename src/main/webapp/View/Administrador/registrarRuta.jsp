<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <title>Crear Ruta</title>
  <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/css/select2.min.css" rel="stylesheet" />
</head>
<body>
<h1>Crear Ruta</h1>
<form action="${pageContext.request.contextPath}/GestionServlet?action=guardarRuta" method="post">
  <label for="origen">Origen:</label>
  <input type="text" id="origen" name="origen" required /><br/><br/>

  <label for="destino">Destino:</label>
  <input type="text" id="destino" name="destino" required /><br/><br/>

  <label for="calles">Selecciona Calles:</label><br/>
  <select name="calles" id="calles" multiple>
    <c:forEach var="calle" items="${calles}">
      <option value="${calle.id}">${calle.nombre}</option>
    </c:forEach>
  </select><br/><br/>



  <input type="submit" value="Crear Ruta" />
</form>

<!-- Incluye jQuery -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<!-- Incluye CSS de Select2 -->

<!-- Incluye JS de Select2 -->
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
