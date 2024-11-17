<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <link rel="icon" type="image/png" href="${pageContext.request.contextPath}/assets/epn.png">
    <title>Registrar Conductor</title>

    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/index.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/form.css">
    <script type="text/javascript">

        function validarNombreApellido(input) {
            input.value = input.value.replace(/[^A-Za-zÁÉÍÓÚáéíóúñÑüÜ \n]/g, '');
        }

        function validarTelefono(input) {
            input.value = input.value.replace(/[^0-9]/g, '');
        }
    </script>
</head>
<body>
<a style="position:absolute; top: 2rem; left: 2rem" href="${pageContext.request.contextPath}/GestionServlet?action=gestionConductores">
    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24">
        <path fill="currentColor" d="m9 18l-6-6l6-6l1.4 1.4L6.8 11H21v2H6.8l3.6 3.6z"/>
    </svg>
    <p>Volver a la lista de conductores</p></a>
    <form action="${pageContext.request.contextPath}/GestionServlet?action=guardarConductor" method="post">
        <input type="hidden" name="action" value="registrarConductor" />
        <h1 >Registrar Conductor</h1>
        <div class="form-section">
            <label for="nombre">Nombre:</label>
            <input type="text" id="nombre" name="nombre" oninput="validarNombreApellido(this)" required/>
        </div>
        <div class="form-section">
            <label for="apellido">Apellido:</label>
            <input type="text" id="apellido" name="apellido" oninput="validarNombreApellido(this)" required/>
        </div>
        <div class="form-section">
            <label for="email">Email:</label>
            <input type="email" id="email" name="email" required/>
        </div>
        <div class="form-section">
            <label for="contrasena">Contraseña:</label>
            <input type="password" id="contrasena" name="contrasena" required/>
        </div>
        <div class="form-section">
            <label for="telefono">Teléfono:</label>
            <input type="text" id="telefono" name="telefono" oninput="validarTelefono(this)" required />
        </div>

        <input class="blue-button"  type="submit" value="Registrar Conductor" />
    </form>



</body>
</html>
