<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Menú Principal Conductor</title>
    <style>
        body {
            background: #100f0f;
            color: #d3d3d3;
            font-family: Arial, sans-serif;
            padding: 4rem;
            margin: 0;
            box-sizing: border-box;
        }

        .dashboard-container {
            width: 80%;
            margin: 0 auto;
            padding: 20px;
            border-radius: 8px;
        }
        h1 {
            text-align: center;
        }
        .menu {
            display: flex;
            justify-content: space-around;
            margin: 20px 0;
        }
        .menu-item {
            background-color: #48578e;
            padding: 20px;
            width: 20%;
            text-align: center;
            border-radius: 8px;
            color: white;
            text-decoration: none;
            font-size: 18px;
            transition: background-color 0.3s ease;
        }
        .menu-item:hover {
            background-color: #71a8df;
        }

        .logout-button {
            background-color: #a94442;
            padding: 10px 20px;
            color: white;
            border-radius: 5px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            transition: background-color 0.3s ease;
            margin-top: 20px;
        }
        .logout-button:hover {
            background-color: #d9534f;
        }
    </style>
</head>
<body>
<div class="dashboard-container">
    <h1>Menú Principal del Conductor</h1>
    <p>Bienvenido Conductor: ${conductor.nombre} ${conductor.apellido}</p>
    <br>
    <div class="menu">
        <a class="menu-item" href="${pageContext.request.contextPath}/GestionServlet?action=consultarViajesConductor&conductorId=${conductor.id}">Ver mis Viajes</a>
    </div>
    <div style="text-align: right;">
        <a class="logout-button" href="${pageContext.request.contextPath}/GestionServlet?action=cerrarSesion">Cerrar Sesión</a>
    </div>
</div>
</body>
</html>
