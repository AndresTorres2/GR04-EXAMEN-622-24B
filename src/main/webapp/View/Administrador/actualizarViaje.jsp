<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <title>Actualizar Viaje</title>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/css/select2.min.css" rel="stylesheet" />
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/form.css">
</head>
<body>
<a style="position:absolute; top: 2rem; left: 2rem" href="${pageContext.request.contextPath}/GestionServlet?action=gestionViajes">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24">
        <path fill="currentColor" d="m9 18l-6-6l6-6l1.4 1.4L6.8 11H21v2H6.8l3.6 3.6z"/>
    </svg>
    <p>Volver a la lista de Viajes</p>
</a>

<form action="${pageContext.request.contextPath}/GestionServlet?action=actualizarViaje" method="post">
    <h1>Actualizar Viaje</h1>
    <div class="form-section">
        <input type="hidden" name="viajeId" value="${viaje.id}" />
    </div>

    <div class="form-section">
        <label for="busId">Bus:</label>
        <select name="busId" id="busId">
            <c:forEach var="bus" items="${buses}">
                <option value="${bus.busId}" ${bus.busId == viaje.bus.busId ? 'selected' : ''}>
                        ${bus.busId}
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-section">
        <label for="rutaId">Ruta:</label>
        <select name="rutaId" id="rutaId">
            <c:forEach var="ruta" items="${rutas}">
                <option value="${ruta.id}" ${ruta.id == viaje.ruta.id ? 'selected' : ''}>
                        ${ruta.origen} - ${ruta.destino}
                </option>
            </c:forEach>
        </select>
    </div>

    <div class="form-section">
        <label for="fecha">Fecha:</label>
        <input type="date" name="fecha" id="fecha" value="${viaje.fecha}" required />
    </div>

    <div class="form-section">
        <label for="horaDeSalida">Hora de Salida:</label>
        <input type="time" name="horaDeSalida" id="horaDeSalida" value="${viaje.horaDeSalida}" required />
    </div>

    <div class="form-section">
        <label for="jornada">Jornada:</label>
        <select name="jornada" id="jornada" required>
            <option value="">Seleccione una jornada</option>
            <option value="Matutina" ${viaje.jornada == 'matutina' ? 'selected' : ''}>Matutina</option>
            <option value="Vespertina" ${viaje.jornada == 'vespertino' ? 'selected' : ''}>Vespertino</option>
        </select>
    </div>

    <div class="form-section">
        <label for="conductor">Conductor:</label>
        <select id="conductor" name="conductorId" required>
            <option value="">Selecciona un conductor</option>
            <c:forEach var="conductor" items="${conductores}">
                <option value="${conductor.id}" ${conductor.id == viaje.conductor.id ? 'selected' : ''}>
                        ${conductor.nombre} ${conductor.apellido}
                </option>
            </c:forEach>
        </select>
    </div>

    <input class="blue-button" type="submit" value="Actualizar Viaje" />
</form>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/js/select2.min.js"></script>
<script>
    $(document).ready(function() {
        $('#busId').select2({
            placeholder: "Selecciona bus",
            allowClear: true
        });
        $('#rutaId').select2({
            placeholder: "Selecciona ruta",
            allowClear: true
        });
        $('#jornada').select2({
            placeholder: "Selecciona jornada",
            allowClear: true
        });
        $('#conductor').select2({
            placeholder: "Selecciona conductor",
            allowClear: true
        });
    });
</script>
</body>
</html>
