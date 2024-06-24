INSERT INTO USUARIOS(APELLIDO, DNI, EMAIL, IMG_PERFIL, NOMBRE, NUM_TELEFONO, PASSWORD, SEXO, ESTADO)
VALUES
('Pepoles', '31213467', 'pepe@pepe.com', 'img.jpg', 'Pepe', '111111', '$2a$04$5Wq2Pg/IMyz5J/zr2Bkbe.mE3pZIo8GS5kCZ2/2efZGfHvLqrb0Om ', 'M', 'ACTIVO'),
('Repo', '41255543', 'turista@gmail.com', 'img.jpg', 'Nico', '1438273634','$2a$10$u7UC0QdskYSCNFYNsmfwd.4SlPX8vQkrqQHl6Bb0y.G4u37B5pH8.', 'M', 'ACTIVO');

INSERT INTO USUARIOS_GUIA(ID, PUNTUACION, IMG_CREDENCIAL, IDIOMAS)
VALUES
(1, 0, 'IMG', ARRAY [0]);

INSERT INTO SERVICIO_GUIA(NOMBRE, DESCRIPCION, TIPO_SERVICIO, PRECIO, GUIA_ID, CUPO)
VALUES
    ('Museos en Italia', '5 dias para recorrer todos los museos de Italia', 1, 123.12, 1,3),
    ('Musica de cuba', 'Los mejores artistas cubanos, salsa y habanos', 1, 66, 1,5),
    ( 'Alfajores Argentina', 'La mejor cata de alfajores', 1, 1000, 1,10);