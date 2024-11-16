<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/consultarReservas.css">

  <title>Consultar Reservas</title>

</head>
<body>
<div class="container">
  <h1>Consultar Reservas</h1>

  <%
    String diaSeleccionado = request.getParameter("dia");
  %>

  <div class="tabs">
    <a class="tab <%= "1".equals(diaSeleccionado) ? "active" : "" %>"
       href="${pageContext.request.contextPath}/ReservarAsientoServlet?action=verReservasDia&dia=1">Lunes</a>
    <a class="tab <%= "2".equals(diaSeleccionado) ? "active" : "" %>"
       href="${pageContext.request.contextPath}/ReservarAsientoServlet?action=verReservasDia&dia=2">Martes</a>
    <a class="tab <%= "3".equals(diaSeleccionado) ? "active" : "" %>"
       href="${pageContext.request.contextPath}/ReservarAsientoServlet?action=verReservasDia&dia=3">Miércoles</a>
    <a class="tab <%= "4".equals(diaSeleccionado) ? "active" : "" %>"
       href="${pageContext.request.contextPath}/ReservarAsientoServlet?action=verReservasDia&dia=4">Jueves</a>
    <a class="tab <%= "5".equals(diaSeleccionado) ? "active" : "" %>"
       href="${pageContext.request.contextPath}/ReservarAsientoServlet?action=verReservasDia&dia=5">Viernes</a>
  </div>


</div>
  <c:set var="diaAnterior" value="" />
  <div class="card-container">
    <c:forEach var="reserva" items="${reservas}">
      <c:set var="diaReserva">
        <fmt:formatDate value="${reserva.viaje.fecha}" pattern="EEEE"/>
      </c:set>
      
      <div class="card">
         <div>
           <p><b>Ruta:</b>${reserva.viaje.ruta.origen} ➜ ${reserva.viaje.ruta.destino}</p>
           <p><b>Horario:</b> ${reserva.viaje.horaDeSalida} (${reserva.viaje.jornada})</p>
           <p><strong>Bus:</strong> #${reserva.viaje.bus.busId}</p>
         </div>
        <a  href="${pageContext.request.contextPath}/ReservarAsientoServlet?action=detalleReserva&reservaId=${reserva.id}">Ver detalles</a>
      </div>
    </c:forEach>
  </div>

  <a href="${pageContext.request.contextPath}/View/Estudiante/listarViajes.jsp" class="botton">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24">
      <path fill="currentColor" d="m9 18l-6-6l6-6l1.4 1.4L6.8 11H21v2H6.8l3.6 3.6z"/>
    </svg>
  <p>
    Regresar a la lista de viajes
  </p></a>
</div>
</body>
</html>
