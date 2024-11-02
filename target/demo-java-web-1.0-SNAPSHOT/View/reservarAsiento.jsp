<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<!DOCTYPE html>
<html>
<head>
    <title>Reservar Asiento</title>
    <style>
        body {
            background: #100f0f;
            color: #dcdcdc;
            font-family: Arial, sans-serif;
        }
        .container {
            width: 60%;
            margin: auto;
            padding: 20px;
            background: #1c1c1c;
            border-radius: 10px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.5);
        }
        h1, h2 {
            text-align: center;
            margin-bottom: 20px;
        }
        table {
            width: 100%;
            margin-top: 20px;
            border-collapse: collapse;
        }
        table, th, td {
            border: 1px solid #dcdcdc;
        }
        th, td {
            padding: 10px;
            text-align: center;
        }
        th {
            background-color: #48578e;
        }
        td {
            background-color: #2e2e2e;
        }
        button {
            display: block;
            width: 100%;
            padding: 10px;
            background-color: #48578e;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 18px;
            cursor: pointer;
        }
        button:hover {
            background-color: #71a8df;
        }
        .back-link {
            text-align: center;
            margin-top: 20px;
        }
        .back-link a {
            color: #71a8df;
            text-decoration: none;
        }
        .back-link a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="container">
    <h1>Reservar Asiento</h1>

    <!-- Mostrar la información de los viajes -->
    <form action="${pageContext.request.contextPath}/ReservarAsientoServlet?action=guardarReserva" method="post">
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
                        <!-- Calcula el día de la semana basado en la fecha del viaje -->
                        <fmt:formatDate value="${viaje.fecha}" pattern="EEEE"/>
                    </td>
                    <td>
                        <!-- Muestra la fecha en formato día/mes/año -->
                        <fmt:formatDate value="${viaje.fecha}" pattern="dd/MM/yyyy"/>
                    </td>
                    <td>${viaje.asientosOcupados}</td>
                    <td>${viaje.bus.capacidad}</td>
                    <td>
                        <!-- Checkbox para seleccionar este viaje -->
                        <input type="checkbox" name="idsViajesSeleccionados" value="${viaje.id}">
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <button type="submit">Realizar Reserva</button>
    </form>

    <div class="back-link">
        <a href="${pageContext.request.contextPath}/ViajeServlet?ruta=seleccionarJornada&jornada">Volver a la lista de buses</a>
    </div>
</div>
</body>
</html>
