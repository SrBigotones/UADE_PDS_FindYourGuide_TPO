INSERT INTO USUARIOS(APELLIDO, DNI, EMAIL, IMG_PERFIL, NOMBRE, NUM_TELEFONO, PASSWORD, SEXO, ESTADO)
VALUES
('Pepoles', '31213467', 'pepe@pepe.com', 'img.jpg', 'Pepe', '111111', '$2a$04$5Wq2Pg/IMyz5J/zr2Bkbe.mE3pZIo8GS5kCZ2/2efZGfHvLqrb0Om', 'M', 'ACTIVO'),
('Repo', '41255543', 'turista@gmail.com', 'img.jpg', 'Nico', '1438273634','$2a$04$5Wq2Pg/IMyz5J/zr2Bkbe.mE3pZIo8GS5kCZ2/2efZGfHvLqrb0Om', 'M', 'ACTIVO');

INSERT INTO USUARIOS_GUIA(ID, PUNTUACION, IMG_CREDENCIAL, IDIOMAS)
VALUES
(1, 0, 'IMG', ARRAY [0]);




INSERT INTO TIPO_TROFEO(ID,DESCRIPCION_TROFEO,NOMBRE_TROFEO)
VALUES
    (0,'El guia recibio mas de 10 reseñas con una puntuacion promedio superior a 4.5!','Trofeo al Exito'),
    (1,'El usuario escribio mas de 10 reseñas!','Trofeo a la resenia');

INSERT INTO CIUDAD_PAIS(CIUDAD, PAIS)
VALUES
('GENOVA', 'ITALIA'),
('BUENOS_AIRES', 'ARGETNINA'),
('CUBA', 'CUBA');



INSERT INTO SERVICIO_GUIA(NOMBRE, DESCRIPCION, TIPO_SERVICIO, PRECIO, GUIA_ID, CUPO, CIUDAD_PAIS_ID)
VALUES
    ('Museos en Italia', '5 dias para recorrer todos los museos de Italia', 1, 123.12, 1,3,1),
    ('Musica de cuba', 'Los mejores artistas cubanos, salsa y habanos', 1, 66, 1,5,3),
    ('Alfajores Argentina', 'La mejor cata de alfajores', 1, 1000, 1,10,2);
