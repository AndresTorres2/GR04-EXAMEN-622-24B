<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <title>Registrar Bus</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/indexRegAct.css">
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

<div class="container">

    <div class="button-container">
        <a class="button" href="${pageContext.request.contextPath}/GestionServlet?action=gestionBuses">Volver a la lista de buses</a>
    </div>
    <h1>Registrar Bus</h1>

    <form class="styled-form" action="${pageContext.request.contextPath}/GestionServlet?action=guardarBus" method="post">
        <input type="hidden" name="action" value="registrarBus" />

        <label for="busId">Número de Bus:</label><br/>
        <input type="text" id="busId" name="busId" required oninput="validateInput(this)"/><br/>

        <label for="capacidad">Capacidad:</label><br/>
        <input type="number" id="capacidad" name="capacidad" required  oninput="validateInput(this)"/><br/>

        <input class="button" type="submit" value="Registrar Bus" />
    </form>

</div>





</body>
</html>
