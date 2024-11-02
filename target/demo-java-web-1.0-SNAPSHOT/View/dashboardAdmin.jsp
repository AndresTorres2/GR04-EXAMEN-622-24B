<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
  <title>Menu Administrador - PoliBus</title>
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
  </style>
</head>
<body>
<div class="dashboard-container">
  <h1>Menu Administrador - PoliBus</h1>
  <div class="menu">
    <a class="menu-item" href="${pageContext.request.contextPath}/GestionServlet?action=gestionConductores">Conductores</a>
    <a class="menu-item" href="${pageContext.request.contextPath}/GestionServlet?action=gestionBuses">Buses</a>
    <a class="menu-item" href="${pageContext.request.contextPath}/GestionServlet?action=gestionRutas">Rutas</a>
    <a class="menu-item" href="${pageContext.request.contextPath}/GestionServlet?action=gestionViajes">Viajes</a>
    <a class="menu-item" href="${pageContext.request.contextPath}/GestionServlet?action=gestionReservas">Reservas</a>
  </div>
</div>
</body>
</html>
