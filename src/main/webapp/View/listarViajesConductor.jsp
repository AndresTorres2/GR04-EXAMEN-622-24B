<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- Created by IntelliJ IDEA. User: Tut-Dem Date: 31/10/2024 Time: 12:57 To
change this template use File | Settings | File Templates. --%>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
  <head>
    <title>Viajes Programados para el Conductor</title>
    <style>
      body {
        background: #100f0f;
        color: #d3d3d3;
        font-family: Arial, sans-serif;
        padding: 0 4rem;
        margin: 0;
        box-sizing: border-box;
      }

      .header {
        padding: 20px 0;
      }

      .card-container {
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        justify-content: center;
        gap: 20px;
        width: 100%;
      }

      .card {
        display: flex;
        width: 100%;
        box-sizing: border-box;
        justify-content: space-between;
        align-items: center;
        border-bottom: 1px solid #222222;
        padding: 2rem 0;
      }

      .card-container .card:last-child {
        border-bottom: none;
      }

      .card h3 {
        margin: 0 0 10px 0;
      }

      .card p {
        margin: 5px 0;
      }

      a {
        text-decoration: none;
        padding: 10px 20px;
        background-color: #48578e;
        color: white;
        border-radius: 5px;
        display: inline-block;
      }

      a:hover {
        background-color: #71a8df;
      }
    </style>
  </head>
  <body>
    <div class="header">
      <h1>Mis Viajes Programados</h1>
    </div>
    <c:choose>
      <c:when test="${not empty viajesConductor}">
        <div class="card-container">
          <c:forEach var="viaje" items="${viajesConductor}">
            <div class="card">
              <div>
                <h3>Fecha: ${viaje.fecha}</h3>
                <p>Hora de Salida: ${viaje.horaDeSalida}</p>
                <p>Ruta: ${viaje.ruta.origen} - ${viaje.ruta.destino}</p>
              </div>
              <a
                href="${pageContext.request.contextPath}/ViajeServlet?ruta=verDetalles&viajeId=${viaje.id}"
                >Ver Detalle</a
              >
            </div>
          </c:forEach>
        </div>
      </c:when>
      <c:otherwise>
        <p>No tienes viajes programados.</p>
      </c:otherwise>
    </c:choose>
  </body>
</html>
