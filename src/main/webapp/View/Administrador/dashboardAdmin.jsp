<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
  <title>Menu Administrador - PoliBus</title>
  <style>
    body {
      background: url("${pageContext.request.contextPath}/assets/BannerPolibus720.jpg");
      color: #d3d3d3;
      font-family: Arial, sans-serif;
      padding: 4rem;
      margin: 0;
      box-sizing: border-box;
      position: relative;
    }

    body::before {
      content: "";
      position: absolute;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      background-color: rgba(0, 0, 0, 0.85);
      z-index: -1;
      pointer-events: none;
    }

    .dashboard-container {
      width: 80%;
      margin: auto;
      padding: 2rem;
      border-radius: 12px;
      backdrop-filter: blur(8px);
      border: 1px solid #7e7e7e;
      text-align: center;
    }

    h1 {
      font-size: 24px;
      margin-bottom: 1.5rem;
      color: #d3d3d3;
    }

    .menu {
      display: flex;
      justify-content: space-around;
      gap: 1rem;
      flex-wrap: wrap;
    }

    .menu-item {
      background-color: #48578e;
      padding: 15px 20px;
      width: 18%;
      text-align: center;
      border-radius: 8px;
      color: white;
      text-decoration: none;
      font-size: 16px;
      transition: background-color 0.3s ease, transform 0.3s ease;
      cursor: pointer;
      border: 1px solid transparent;
    }

    .menu-item:hover {
      background-color: #71a8df;
      border: 1px solid #7e7e7e;
      transform: scale(1.05);
    }

    .logout-button {
      background-color: #a94442;
      padding: 15px 20px;
      text-align: center;
      width: 15%;
      border-radius: 8px;
      color: white;
      text-decoration: none;
      font-size: 16px;
      margin-top: 20px;
      transition: background-color 0.3s ease, transform 0.3s ease;
      cursor: pointer;
      display: inline-block;
      border: 1px solid transparent;
    }

    .logout-button:hover {
      background-color: #d9534f;
      border: 1px solid #7e7e7e;
      transform: scale(1.05);
    }
  </style>
</head>
<body>
<div class="dashboard-container">
  <h1>Menu Administrador - PoliBus</h1>
  <div class="menu">
    <a class="menu-item" href="${pageContext.request.contextPath}/GestionServlet?action=gestionConductores">Gestion de Conductores</a>
    <a class="menu-item" href="${pageContext.request.contextPath}/GestionServlet?action=gestionBuses">Gestion de Buses</a>
    <a class="menu-item" href="${pageContext.request.contextPath}/GestionServlet?action=gestionRutas">Gestion de Rutas</a>
    <a class="menu-item" href="${pageContext.request.contextPath}/GestionServlet?action=gestionViajes">Gestion de Viajes</a>
    <a class="menu-item" href="${pageContext.request.contextPath}/GestionServlet?action=gestionReservas">Gestion de Reservas</a>
  </div>
  <a class="logout-button" href="${pageContext.request.contextPath}/GestionServlet?action=cerrarSesion">Cerrar Sesión</a>
</div>
</body>
</html>
