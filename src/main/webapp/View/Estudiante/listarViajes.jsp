<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
    <title>PoliBuses</title>
</head>
<body>
    <c:if test="${not empty successMessage}">
        <script type="text/javascript">
            alert('${successMessage}');
        </script>
    </c:if>

    <div style="width: 100%; margin-left: -4rem; display: flex; align-items: center; justify-content: space-between; background-color: var(--dark-blue); padding: 2rem 4rem;">
        <img style="width: 182px; height: 100px" src="${pageContext.request.contextPath}/assets/epnlogo.svg" alt="epn logo">
       <div style="display: flex; align-items: center; gap: 1.5rem">
           <a style="margin: 2rem 0"  href="${pageContext.request.contextPath}/ReservarAsientoServlet?action=consultarReservas">Consultar Reservas</a>
           <a href="${pageContext.request.contextPath}/GestionServlet?action=cerrarSesion">
               <p>Cerrar Sesión</p>
               <svg viewBox="0 0 24 24" height="16" width="16"><path fill="currentColor" d="M5 21q-.825 0-1.412-.587T3 19V5q0-.825.588-1.412T5 3h6q.425 0 .713.288T12 4t-.288.713T11 5H5v14h6q.425 0 .713.288T12 20t-.288.713T11 21zm12.175-8H10q-.425 0-.712-.288T9 12t.288-.712T10 11h7.175L15.3 9.125q-.275-.275-.275-.675t.275-.7t.7-.313t.725.288L20.3 11.3q.3.3.3.7t-.3.7l-3.575 3.575q-.3.3-.712.288t-.713-.313q-.275-.3-.262-.712t.287-.688z"></path></svg>
           </a>
       </div>
    </div>


    <div style="display: flex; justify-content: space-between; margin-top: 1rem;">
        <h1>Viajes Polibus </h1>

        <div style="display: flex; gap: 1.5rem; align-items: center">
            <span>Seleccione la jornada</span>
            <div class="button-group">
                <a href="${pageContext.request.contextPath}/ViajeServlet?ruta=seleccionarJornada&jornada=matutina">Matutina</a>
                <a href="${pageContext.request.contextPath}/ViajeServlet?ruta=seleccionarJornada&jornada=vespertino">Vespertina</a>
            </div>
        </div>
    </div>

    <div class="card-container">
        <c:forEach var="viaje" items="${viajes}">
            <div class="card">
               <div>
                   <h3>${viaje[1]} ➜ ${viaje[2]}</h3>
                   <p>Hora de Salida: ${viaje[0]}</p>
               </div>
                <a href="${pageContext.request.contextPath}/ViajeServlet?ruta=verDetalles&ids=${viaje[3]}">Ver detalles</a>
            </div>
        </c:forEach>
    </div>

    <c:if test="${empty viajes}">
        <p>No se encontraron viajes. Seleccione una jornada.</p>
    </c:if>

</body>
</html>
