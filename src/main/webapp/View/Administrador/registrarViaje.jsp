<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <title>Registrar Viaje</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/css/select2.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/indexRegAct.css">
</head>
<body>
<div class="container">

    <div class="button-container">
        <a class="button" href="${pageContext.request.contextPath}/GestionServlet?action=gestionViajes">Volver a la lista de viajes</a>
    </div>
    <h1>Registrar Nuevo Viaje</h1>

    <form class="styled-form" action="${pageContext.request.contextPath}/GestionServlet?action=guardarViaje" method="post">
        <label for="busId">Bus:</label><br/>
        <select name="busId" id="busId">
            <c:forEach var="bus" items="${buses}">
                <option value="${bus.busId}">${bus.busId}</option>
            </c:forEach>
        </select><br/>

        <label for="rutaId">Ruta:</label><br/>
        <select name="rutaId" id="rutaId">
            <c:forEach var="ruta" items="${rutas}">
                <option value="${ruta.id}">${ruta.origen} - ${ruta.destino}</option>
            </c:forEach>
        </select><br/>

        <label for="fecha">Fecha:</label><br/>
        <input type="date" name="fecha" id="fecha" required><br/>

        <label for="horaDeSalida">Hora de Salida:</label><br/>
        <input type="time" name="horaDeSalida" id="horaDeSalida" required><br/>

        <label for="jornada">Jornada:</label><br/>
        <select name="jornada" id="jornada" required>
            <option value="">Seleccione una jornada</option>
            <option value="matutina">Matutina</option>
            <option value="vespertino">Vespertina</option>
        </select><br/>

        <label for="conductor">Conductor:</label><br/>
        <select id="conductor" name="conductorId" required>
            <option value="">Selecciona un conductor</option>
            <c:forEach var="conductor" items="${conductores}">
                <option value="${conductor.id}">${conductor.nombre} ${conductor.apellido}</option>
            </c:forEach>
        </select><br/>

        <input class="button" type="submit"value="Agregar Viaje" />
    </form>

</div>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/js/select2.min.js"></script>
<script>
    $(document).ready(function() {
        $('#busId').select2({
            placeholder: "Selecciona bus",
            allowClear: true
        });
    });
    $(document).ready(function() {
        $('#rutaId').select2({
            placeholder: "Selecciona ruta",
            allowClear: true
        });
    });
    $(document).ready(function() {
        $('#jornada').select2({
            placeholder: "Selecciona jornada",
            allowClear: true
        });
    });
    $(document).ready(function() {
        $('#conductor').select2({
            placeholder: "Selecciona conductor",
            allowClear: true
        });
    });
</script>
</body>
</html>
