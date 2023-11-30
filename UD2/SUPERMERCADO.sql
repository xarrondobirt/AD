-- 1.Crea un tipo objeto para almacenar direcciones postales con dos campos, la dirección y el código postal
CREATE OR REPLACE TYPE DIRECCION_POSTAL_TYPE AS OBJECT(
DIRECCION VARCHAR2(45),
CODIGO_POSTAL VARCHAR2(6)
);
/
-- 2.Crea un tipo objeto contacto para almacenar un número de teléfono y un email
CREATE OR REPLACE TYPE CONTACTO_TYPE AS OBJECT(
NUMERO_TELEFONO NUMBER(9),
EMAIL VARCHAR2(45)
);
/
-- 3.Crea un tipo objeto persona con los campos id, nombre, apellido, dirección y contacto. Después, crea un subtipo cliente con otro campo adicional llamado número de pedidos
CREATE OR REPLACE TYPE PERSONA_TYPE AS OBJECT(
ID NUMBER,
NOMBRE VARCHAR2(45),
APELLIDO VARCHAR2(45),
DIRECCION VARCHAR2(45),
CONTACTO CONTACTO_TYPE
)
NOT FINAL;
/
CREATE OR REPLACE TYPE CLIENTE_TYPE UNDER PERSONA_TYPE(
NUMERO_PEDIDOS NUMBER
);
/
-- 4.Crea un tipo objeto articulo con los campos id, nombre, descripción, precio y porcentaje de iva. Después crea un tipo tabla de objetos para registros de artículos.
CREATE OR REPLACE TYPE ARTICULO_TYPE AS OBJECT(
ID NUMBER,
NOMBRE VARCHAR2(45),
DESCRIPCION VARCHAR2(45),
PRECIO NUMBER(10,2),
PORCENTAJE_IVA NUMBER(5,2)
);
/
CREATE OR REPLACE TYPE ARTICULO_TABLE AS TABLE OF ARTICULO_TYPE;
/
-- 5.Crea un tipo objeto para la lista de la compra y otro para su detalle.
CREATE OR REPLACE TYPE DETALLE_LISTA_COMPRA_TYPE AS OBJECT(
ARTICULOS ARTICULO_TABLE,
CANTIDAD NUMBER,
MEMBER FUNCTION TOTAL RETURN NUMBER
);
/
CREATE OR REPLACE TYPE DETALLE_LISTA_COMPRA_TABLE AS TABLE OF DETALLE_LISTA_COMPRA_TYPE;
/
CREATE OR REPLACE TYPE LISTA_COMPRA_TYPE AS OBJECT(
ID NUMBER,
FECHA DATE,
CLIENTE REF CLIENTE_TYPE,
DETALLES DETALLE_LISTA_COMPRA_TABLE
);
/
-- 6.Crea ahora el cuerpo del tipo lista de la compra para definir el método total.
CREATE OR REPLACE TYPE BODY DETALLE_LISTA_COMPRA_TYPE AS
MEMBER FUNCTION TOTAL RETURN NUMBER IS
TOTAL NUMBER:=0;
BEGIN
FOR i IN 1..ARTICULOS.COUNT LOOP
TOTAL := TOTAL + (ARTICULOS(i).PRECIO + (ARTICULOS(i).PRECIO * ARTICULOS(i).PORCENTAJE_IVA / 100)) * CANTIDAD;
END LOOP;
RETURN TOTAL;
END;
END;
/
-- 7.Crea una tabla de clientes e inserta uno.
CREATE TABLE CLIENTES OF CLIENTE_TYPE;
/
INSERT INTO CLIENTES
VALUES (CLIENTE_TYPE(1, 'Xabier', 'Arrondo', 'Falsa 123', CONTACTO_TYPE(666000111, 'xarrondo@birt.eus'), 5));
/
-- 8.Crea una tabla para las listas de la compra e inserta una lista de la compra con un detalle de dos artículos para el cliente insertado anteriormente:
CREATE TABLE COMPRAS OF LISTA_COMPRA_TYPE
NESTED TABLE DETALLES STORE AS DETALLES_NEST_TABLE
(NESTED TABLE ARTICULOS STORE AS ARTICULOS_NEST_TABLE)
/
INSERT INTO COMPRAS
SELECT
    LISTA_COMPRA_TYPE(1, SYSDATE, REF(C), DETALLE_LISTA_COMPRA_TABLE(
        DETALLE_LISTA_COMPRA_TYPE(
            ARTICULO_TABLE(
                ARTICULO_TYPE(1, 'Teclado', 'Periféricos', 20, 21),
                ARTICULO_TYPE(2, 'Lenovo', 'PC', 2000, 21)
            ),
            2
        )
    ))
FROM CLIENTES C
WHERE C.ID = 1;
/
-- 9.Muestra con una select los datos de la lista de la compra
SELECT * FROM COMPRAS;
/

