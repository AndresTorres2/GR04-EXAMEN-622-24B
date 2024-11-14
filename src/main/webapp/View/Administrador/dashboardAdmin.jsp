<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<html>
<head>
  <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
  <title>Menu Administrador - PoliBus</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/indexDashboardAdmin.css">
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
