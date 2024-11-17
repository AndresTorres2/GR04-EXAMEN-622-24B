-- Conectar a la base de datos
CONNECT 'jdbc:derby:/usr/local/tomcat/polibusfinal;';

-- Insertar datos en la tabla Ubicaciones
INSERT INTO Ubicaciones (latitud, longitud) VALUES
                                                (-0.21189384416842594, -78.4854912),
                                                (-0.233141025061029, -78.50880205767169),
                                                (-0.2415040263736462, -78.51250775767168),
                                                (-0.2518713357923819, -78.52271663862643),
                                                (-0.346586, -78.553517),
                                                (-0.20759386716230427, -78.49684861349246),
                                                (-0.18832689711012612, -78.49213615767171),
                                                (-0.20443232465026515, -78.50024771534338),
                                                (-0.20723744395118912, -78.51300374603017),
                                                (-0.2471528965452066, -78.53531589999999),
                                                (-0.27751, -78.57505),
                                                (-0.13506845432786618, -78.49375151534338),
                                                (0.005493341946105372, -78.4468253052241);

-- Insertar datos en la tabla Calles
INSERT INTO Calles (nombre, ubicacionId) VALUES
                                             ('Ladrón de Guevara', 1),
                                             ('Av. Oriental (antigua)', 2),
                                             ('Av. Napo-Camal', 3),
                                             ('El Recreo', 4),
                                             ('Av. Maldonado hasta la Bomba de Guamaní', 5),
                                             ('Av. Patria', 6),
                                             ('Av. 10 de Agosto', 7),
                                             ('Av. Pérez Guerrero', 8),
                                             ('Túneles', 9),
                                             ('Av. Mariscal Sucre', 10),
                                             ('Chillogallo', 11),
                                             ('Av. La Prensa', 12),
                                             ('Manuel Córdova Galarza hasta San Antonio de Pichincha', 13);

-- Insertar datos en la tabla Rutas
INSERT INTO Rutas (origen, destino) VALUES
                                        ('EPN', 'Bomba Guamaní'),
                                        ('EPN', 'Terminal Quitumbe'),
                                        ('EPN', 'San Antonio de Pichincha');

-- Insertar datos en la tabla Rutas_Calles para la ruta EPN -> Bomba Guamaní
INSERT INTO Rutas_Calles (rutaId, calleId) VALUES
                                               (1, 1),
                                               (1, 2),
                                               (1, 3),
                                               (1, 4),
                                               (1, 5);

-- Insertar datos en la tabla Rutas_Calles para la ruta EPN -> Terminal Quitumbe
INSERT INTO Rutas_Calles (rutaId, calleId) VALUES
                                               (2, 1),
                                               (2, 8),
                                               (2, 9),
                                               (2, 10),
                                               (2, 11);

-- Insertar datos en la tabla Rutas_Calles para la ruta EPN -> San Antonio de Pichincha
INSERT INTO Rutas_Calles (rutaId, calleId) VALUES
                                               (3, 6),
                                               (3, 7),
                                               (3, 12),
                                               (3, 13);

-- Insertar datos en la tabla Buses
INSERT INTO Buses (id, capacidad) VALUES
                                      ('731', 44),
                                      ('780', 44),
                                      ('860', 44),
                                      ('511', 44);

-- Insertar datos en la tabla Usuarios
INSERT INTO Usuarios (tipo_usuario, id, nombres, apellidos, email, contraseña, phone) VALUES
                                                                                          ('U_Administrador', 202135788, 'Santiago', 'Torres', 'santiago.torres@example.com', 'santiago123', '09333333'),
                                                                                          ('U_Estudiante', 202131745, 'Eliath Sebastián', 'Velasco', 'eliath.velasco@example.com', 'eliath123', '09113333'),
                                                                                          ('U_Estudiante', 202110777, 'Linda Amada', 'Uzho Nuñez', 'linda.uzho@epn.edu.ec', 'lindita123', '0999999777'),
                                                                                          ('U_Administrador', 202145678, 'Maria Fernanda', 'Perez', 'maria.perez@example.com', 'maria123', '09444444'),
                                                                                          ('U_Administrador', 202155689, 'Carlos Eduardo', 'Mendoza', 'carlos.mendoza@example.com', 'carlos123', '09555555'),
                                                                                          ('U_Estudiante', 202120345, 'Andres Ignacio', 'Lara', 'andres.lara@example.com', 'andres123', '09222222'),
                                                                                          ('U_Estudiante', 202110456, 'Jessica Paulina', 'Lopez', 'jessica.lopez@example.com', 'jessica123', '09666666'),
                                                                                          ('U_Conductor', 202160111, 'Juan Manuel', 'Garcia', 'juan.garcia@example.com', 'juan123', '09777777'),
                                                                                          ('U_Conductor', 202170222, 'Marcelo Javier', 'Ortiz', 'marcelo.ortiz@example.com', 'marcelo123', '09888888'),
                                                                                          ('U_Conductor', 202180333, 'Rosario Elizabeth', 'Cabrera', 'rosario.cabrera@example.com', 'rosario123', '09100000');

INSERT INTO Viajes (busId, fecha, horaDeSalida, rutaId, jornada, asientosOcupados, conductorId) VALUES
                                                                                                    ('731', '2024-10-14', '19:10:00', 1, 'vespertino', 0, '202160111'),
                                                                                                    ('780', '2024-10-15', '19:10:00', 1, 'vespertino', 0, '202170222'),
                                                                                                    ('731', '2024-10-16', '19:10:00', 1, 'vespertino', 0, '202180333'),
                                                                                                    ('780', '2024-10-17', '19:10:00', 1, 'vespertino', 0, '202170222'),
                                                                                                    ('731', '2024-10-18', '19:10:00', 1, 'vespertino', 0, '202180333'),

                                                                                                    ('511', '2024-10-14', '21:10:00', 2, 'vespertino', 0, '202170222'),
                                                                                                    ('860', '2024-10-15', '21:10:00', 2, 'vespertino', 0, '202180333'),
                                                                                                    ('860', '2024-10-16', '21:10:00', 2, 'vespertino', 0, '202170222'),
                                                                                                    ('860', '2024-10-17', '21:10:00', 2, 'vespertino', 0, '202180333'),
                                                                                                    ('511', '2024-10-18', '21:10:00', 2, 'vespertino', 0, '202170222'),

                                                                                                    ('780', '2024-10-14', '05:10:00', 2, 'matutina', 0, '202180333'),
                                                                                                    ('860', '2024-10-15', '05:10:00', 2, 'matutina', 0, '202170222'),
                                                                                                    ('780', '2024-10-16', '05:10:00', 2, 'matutina', 0, '202160111'),
                                                                                                    ('731', '2024-10-17', '05:10:00', 2, 'matutina', 0, '202180333'),
                                                                                                    ('511', '2024-10-18', '05:10:00', 2, 'matutina', 0, '202160111'),

                                                                                                    ('731', '2024-10-14', '05:10:00', 1, 'matutina', 0, '202180333'),
                                                                                                    ('860', '2024-10-15', '05:10:00', 1, 'matutina', 0, '202170222'),
                                                                                                    ('780', '2024-10-16', '05:10:00', 1, 'matutina', 0, '202180333'),
                                                                                                    ('731', '2024-10-17', '05:10:00', 1, 'matutina', 0, '202170222'),
                                                                                                    ('511', '2024-10-18', '05:10:00', 1, 'matutina', 0, '202160111'),

                                                                                                    ('780', '2024-10-14', '05:10:00', 3, 'matutina', 0, '202170222'),
                                                                                                    ('860', '2024-10-15', '05:10:00', 3, 'matutina', 0, '202180333'),
                                                                                                    ('780', '2024-10-16', '05:10:00', 3, 'matutina', 0, '202170222'),
                                                                                                    ('731', '2024-10-17', '05:10:00', 3, 'matutina', 0, '202160111'),
                                                                                                    ('511', '2024-10-18', '05:10:00', 3, 'matutina', 0, '202170222'),

                                                                                                    ('780', '2024-10-14', '21:10:00', 3, 'vespertino', 0, '202160111'),
                                                                                                    ('860', '2024-10-15', '21:10:00', 3, 'vespertino', 0, '202180333'),
                                                                                                    ('780', '2024-10-16', '21:10:00', 3, 'vespertino', 0, '202160111'),
                                                                                                    ('860', '2024-10-17', '21:10:00', 3, 'vespertino', 0, '202170222'),
                                                                                                    ('511', '2024-10-18', '21:10:00', 3, 'vespertino', 0, '202160111'),

                                                                                                    ('731', '2024-11-18', '19:10:00', 1, 'vespertino', 0, '202170222'),
                                                                                                    ('780', '2024-11-19', '19:10:00', 1, 'vespertino', 0, '202160111'),
                                                                                                    ('731', '2024-11-20', '19:10:00', 1, 'vespertino', 0, '202180333'),
                                                                                                    ('780', '2024-11-21', '19:10:00', 1, 'vespertino', 0, '202160111'),
                                                                                                    ('731', '2024-11-22', '19:10:00', 1, 'vespertino', 0, '202170222'),

                                                                                                    ('511', '2024-11-18', '21:10:00', 2, 'vespertino', 0, '202180333'),
                                                                                                    ('860', '2024-11-19', '21:10:00', 2, 'vespertino', 0, '202170222'),
                                                                                                    ('860', '2024-11-20', '21:10:00', 2, 'vespertino', 0, '202160111'),
                                                                                                    ('860', '2024-11-21', '21:10:00', 2, 'vespertino', 0, '202170222'),
                                                                                                    ('511', '2024-11-22', '21:10:00', 2, 'vespertino', 0, '202160111'),

                                                                                                    ('780', '2024-11-18', '05:10:00', 2, 'matutina', 0, '202170222'),
                                                                                                    ('860', '2024-11-19', '05:10:00', 2, 'matutina', 0, '202160111'),
                                                                                                    ('780', '2024-11-20', '05:10:00', 2, 'matutina', 0, '202170222'),
                                                                                                    ('731', '2024-11-21', '05:10:00', 2, 'matutina', 0, '202180333'),
                                                                                                    ('511', '2024-11-22', '05:10:00', 2, 'matutina', 0, '202170222'),

                                                                                                    ('731', '2024-11-18', '05:10:00', 1, 'matutina', 0, '202160111'),
                                                                                                    ('860', '2024-11-19', '05:10:00', 1, 'matutina', 0, '202170222'),
                                                                                                    ('780', '2024-11-20', '05:10:00', 1, 'matutina', 0, '202180333'),
                                                                                                    ('731', '2024-11-21', '05:10:00', 1, 'matutina', 0, '202170222'),
                                                                                                    ('511', '2024-11-22', '05:10:00', 1, 'matutina', 0, '202160111'),

                                                                                                    ('780', '2024-11-18', '05:10:00', 3, 'matutina', 0, '202170222'),
                                                                                                    ('860', '2024-11-19', '05:10:00', 3, 'matutina', 0, '202160111'),
                                                                                                    ('780', '2024-11-20', '05:10:00', 3, 'matutina', 0, '202170222'),
                                                                                                    ('731', '2024-11-21', '05:10:00', 3, 'matutina', 0, '202160111'),
                                                                                                    ('511', '2024-11-22', '05:10:00', 3, 'matutina', 0, '202180333'),

                                                                                                    ('780', '2024-11-18', '21:10:00', 3, 'vespertino', 0, '202180333'),
                                                                                                    ('860', '2024-11-19', '21:10:00', 3, 'vespertino', 0, '202170222'),
                                                                                                    ('780', '2024-11-20', '21:10:00', 3, 'vespertino', 0, '202160111'),
                                                                                                    ('860', '2024-11-21', '21:10:00', 3, 'vespertino', 0, '202180333'),
                                                                                                    ('511', '2024-11-22', '21:10:00', 3, 'vespertino', 0, '202160111');

