<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Gestión de Conductores</title>
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
            width: 100%;
            border-collapse: collapse;
            margin-bottom: 1.5rem;
        }

        th, td, tr{
            padding: 0.5rem 1rem;
            border: 1px solid #d3d3d3;
        }

    </style>
</head>
<body>
<h1>Gestión de Conductores</h1>
<div style="display: flex; margin: 1.5rem 0; justify-content: space-between">
    <a href="${pageContext.request.contextPath}/View/dashboardAdmin.jsp">Volver al Dashboard</a>
    <a href="${pageContext.request.contextPath}/GestionServlet?action=nuevoConductor">Agregar nuevo conductor</a>
</div>
<table>
    <thead>
    <tr>
        <th>Nombre</th>
        <th>Apellido</th>
        <th>Email</th>
        <th>Teléfono</th>
        <th>Acciones</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="conductor" items="${conductores}">
        <tr>
            <td>${conductor.nombre}</td>
            <td>${conductor.apellido}</td>
            <td>${conductor.email}</td>
            <td>${conductor.phone}</td>
            <td>
                <a href="${pageContext.request.contextPath}/GestionServlet?action=eliminarConductor&conductorId=${conductor.id}" onclick="return confirm('¿Estás seguro de que deseas eliminar este conductor?');">Eliminar</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
