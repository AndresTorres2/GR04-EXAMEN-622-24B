<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Gestión de Rutas</title>
    <style>
        body {
            background: #100f0f;
            color: #d3d3d3;
            font-family: Arial, sans-serif;
            padding: 4rem;
            margin: 0;
            box-sizing: border-box;
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
        table{
            border-collapse: collapse;
            margin-bottom: 1.5rem;
            width: 100%;

        }

        th, td, tr{
            padding: 0.5rem 1rem;
            border: 1px solid #d3d3d3;
        }

    </style>
</head>
<body>
<h1>Gestión de Rutas</h1>
<div style="display: flex; margin: 1.5rem 0; justify-content: space-between">
    <a href="${pageContext.request.contextPath}/View/dashboardAdmin.jsp">Volver al Dashboard</a>
    <a href="${pageContext.request.contextPath}/GestionServlet?action=nuevaRuta">Agregar nueva ruta</a>
</div>
<table >
    <thead>
    <tr>
        <th>Origen</th>
        <th>Destino</th>
        <th>Recorrido</th>
        <th></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="ruta" items="${rutas}">
        <tr>
            <td>${ruta.origen}</td>
            <td>${ruta.destino}</td>
            <td>
                <c:forEach var="calle" items="${ruta.calles}" varStatus="status">
                    ${calle.nombre}<c:if test="${!status.last}">, </c:if>
                </c:forEach>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/GestionServlet?action=formActualizarRuta&rutaId=${ruta.id}">Actualizar</a>
            </td>
            <td>
                <a href="${pageContext.request.contextPath}/GestionServlet?action=eliminarRuta&rutaId=${ruta.id}" onclick="return confirm('¿Estás seguro de que deseas eliminar esta ruta?');">Eliminar</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
