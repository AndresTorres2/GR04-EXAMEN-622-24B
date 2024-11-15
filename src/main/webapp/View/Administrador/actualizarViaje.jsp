<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <title>Actualizar Viaje</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/css/select2.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/indexRegAct.css">
</head>
<body>
<div class="container">

    <div class="button-container">
        <a class="button" href="${pageContext.request.contextPath}/GestionServlet?action=gestionViajes">Volver a la lista de viajes</a>
    </div>
    <h1>Actualizar Viaje</h1>

    <form class="styled-form" action="${pageContext.request.contextPath}/GestionServlet?action=actualizarViaje" method="post">
        <input type="hidden" name="viajeId" value="${viaje.id}" />

        <label for="busId">Bus:</label>
        <select name="busId" id="busId">
            <c:forEach var="bus" items="${buses}">
                <option value="${bus.busId}" ${bus.busId == viaje.bus.busId ? 'selected' : ''}>
                        ${bus.busId}
                </option>
            </c:forEach>
        </select><br/>

        <label for="rutaId">Ruta:</label>
        <select name="rutaId" id="rutaId">
            <c:forEach var="ruta" items="${rutas}">
                <option value="${ruta.id}" ${ruta.id == viaje.ruta.id ? 'selected' : ''}>
                        ${ruta.origen} - ${ruta.destino}
                </option>
            </c:forEach>
        </select><br/>

        <label for="fecha">Fecha:</label>
        <input type="date" name="fecha" id="fecha" value="${viaje.fecha}" required /><br/>

        <label for="horaDeSalida">Hora de Salida:</label>
        <input type="time" name="horaDeSalida" id="horaDeSalida" value="${viaje.horaDeSalida}" required /><br/>

        <label for="jornada">Jornada:</label><br/>
        <select name="jornada" id="jornada" required>
            <option value="">Seleccione una jornada</option>
            <option value="Matutina" ${viaje.jornada == 'matutina' ? 'selected' : ''}>matutina</option>
            <option value="Vespertina" ${viaje.jornada == 'vespertino' ? 'selected' : ''}>vespertino</option>
        </select><br>

        <label for="conductor">Conductor:</label><br/>
        <select id="conductor" name="conductorId" required>
            <option value="">Selecciona un conductor</option>
            <c:forEach var="conductor" items="${conductores}">
                <option value="${conductor.id}" ${conductor.id == viaje.conductor.id ? 'selected' : ''}>
                        ${conductor.nombre} ${conductor.apellido}
                </option>
            </c:forEach>
        </select><br/>

        <input class="button" type="submit" value="Actualizar Viaje" />
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
