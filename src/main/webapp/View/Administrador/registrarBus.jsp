<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <title>Registrar Bus</title>
    <script>
        function validateInput(input) {
            input.value = input.value.replace(/[^0-9]/g, '');
        }
    </script>
</head>
<body>
<c:if test="${not empty errorMessage}">
    <script type="text/javascript">
        alert("${errorMessage}");
    </script>
</c:if>
<h1>Registrar Bus</h1>

<form action="${pageContext.request.contextPath}/GestionServlet?action=guardarBus" method="post">
    <input type="hidden" name="action" value="registrarBus" />

    <label for="busId">Número de Bus:</label><br/>
    <input type="text" id="busId" name="busId" required oninput="validateInput(this)"/><br/><br/>

    <label for="capacidad">Capacidad:</label><br/>
    <input type="number" id="capacidad" name="capacidad" required  oninput="validateInput(this)"/><br/><br/>



    <input type="submit" value="Registrar Bus" />
</form>

<a href="${pageContext.request.contextPath}/GestionServlet?action=gestionBuses">Volver a la lista de buses</a>

</body>
</html>
