INSERT INTO USUARIOS(APELLIDO, DNI, EMAIL, IMG_PERFIL, NOMBRE, NUM_TELEFONO, PASSWORD, SEXO)
VALUES
('Pepoles', '31213467', 'pepe@pepe.com', 'img.jpg', 'Pepe', '111111', 'asdkfjalsdjfadf', 'M');


INSERT INTO USUARIOS_GUIA(ID, PUNTUACION, IMG_CREDENCIAL, IDIOMAS)
VALUES
(1, 0, null, null);

INSERT INTO SERVICIO_GUIA(NOMBRE, DESCRIPCION, TIPO_SERVICIO, PRECIO, GUIA_ID, CUPO)
VALUES
    ('Museos en Italia', '5 dias para recorrer todos los museos de Italia', 1, 123.12, 1,3),
    ('Musica de cuba', 'Los mejores artistas cubanos, salsa y habanos', 1, 66, 1,5),
    ( 'Alfajores Argentina', 'La mejor cata de alfajores', 1, 1000, 1,10);