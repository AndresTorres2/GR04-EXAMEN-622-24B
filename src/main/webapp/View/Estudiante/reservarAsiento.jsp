<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
    <title>Reservar Asiento</title>
</head>
<body>
<div>

    <h1>Reservar Asiento</h1>
    <c:if test="${not empty error}">
        <p style="color: red;">${error}</p>
    </c:if>
    <c:if test="${empty viajesList}">
        <p>Por el momento no existen viajes para la Semana.</p>
        <div style="display: flex; justify-content: space-between; margin-top: 1.5rem">
            <div>
                <a href="${pageContext.request.contextPath}/ViajeServlet?ruta=seleccionarJornada&jornada">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24">
                        <path fill="currentColor" d="m9 18l-6-6l6-6l1.4 1.4L6.8 11H21v2H6.8l3.6 3.6z"/>
                    </svg>
                    <p>
                        Volver a los detalles del viaje
                    </p>
                </a>
            </div>
        </div>
    </c:if>

<c:if test="${not empty viajesList}">
    <form action="${pageContext.request.contextPath}/ReservarAsientoServlet?action=guardarReserva" method="post" >
        <table>
            <thead>
            <tr>
                <th>Día</th>
                <th>Fecha</th>
                <th>Asientos Ocupados</th>
                <th>Capacidad</th>
                <th>Seleccionar</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="viaje" items="${viajesList}">
                <tr>

                    <td>
                        <fmt:formatDate value="${viaje.fecha}" pattern="EEEE"/>
                    </td>
                    <td>
                        <fmt:formatDate value="${viaje.fecha}" pattern="dd/MM/yyyy"/>
                    </td>
                    <td>${viaje.asientosOcupados}</td>
                    <td>${viaje.bus.capacidad}</td>
                    <td>
                        <input type="checkbox" name="idsViajesSeleccionados" value="${viaje.id}">
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <div style="display: flex; justify-content: space-between; margin-top: 1.5rem">
            <div>
                <a href="${pageContext.request.contextPath}/ViajeServlet?ruta=seleccionarJornada&jornada">
                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24">
                        <path fill="currentColor" d="m9 18l-6-6l6-6l1.4 1.4L6.8 11H21v2H6.8l3.6 3.6z"/>
                    </svg>
                    <p>
                        Volver a los detalles del viaje
                    </p>
                </a>
            </div>
            <button type="submit">Realizar Reserva</button>
        </div>
    </form>
</c:if>

</div>
</body>
</html>
