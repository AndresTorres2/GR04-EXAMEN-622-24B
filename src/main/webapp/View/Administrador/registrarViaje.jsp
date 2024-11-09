<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registrar Viaje</title>
</head>
<body>
<h2>Registrar Nuevo Viaje</h2>

<form action="${pageContext.request.contextPath}/GestionServlet?action=guardarViaje" method="post">
    <label for="busId">Bus:</label>
    <select name="busId" id="busId">
        <c:forEach var="bus" items="${buses}">
            <option value="${bus.busId}">${bus.busId}</option>
        </c:forEach>
    </select>

    <label for="rutaId">Ruta:</label>
    <select name="rutaId" id="rutaId">
        <c:forEach var="ruta" items="${rutas}">
            <option value="${ruta.id}">${ruta.origen} - ${ruta.destino}</option>
        </c:forEach>
    </select>

    <label for="fecha">Fecha:</label>
    <input type="date" name="fecha" id="fecha" required>

    <label for="horaDeSalida">Hora de Salida:</label>
    <input type="time" name="horaDeSalida" id="horaDeSalida" required>

    <label for="jornada">Jornada:</label>
    <select name="jornada" id="jornada" required>
        <option value="">Seleccione una jornada</option>
        <option value="Matutina">Matutina</option>
        <option value="Vespertina">Vespertina</option>
    </select>

    <label for="conductor">Conductor:</label><br/>
    <select id="conductor" name="conductorId" required>
        <option value="">Selecciona un conductor</option>
        <c:forEach var="conductor" items="${conductores}">
            <option value="${conductor.id}">${conductor.nombre} ${conductor.apellido}</option>
        </c:forEach>
    </select><br/><br/>

    <button type="submit">Agregar Viaje</button>
</form>
</body>
</html>
