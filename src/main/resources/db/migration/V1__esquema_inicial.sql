-- Esquema inicial: productos, ventas, detalles de venta y usuarios.
-- Usa IF NOT EXISTS para poder aplicarse sobre bases que ya tenían estas tablas (creadas por Hibernate).

CREATE SEQUENCE IF NOT EXISTS seq_producto START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS seq_venta START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS seq_detalle START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS seq_usuario START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS producto (
    prod_id              INTEGER PRIMARY KEY,
    prod_codigo_barras   VARCHAR(255),
    prod_categoria       VARCHAR(255),
    prod_stock           DOUBLE PRECISION,
    prod_precio          DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS venta (
    vent_id              INTEGER PRIMARY KEY,
    vent_numero_ventas   INTEGER,
    vent_cedula_cliente  VARCHAR(255),
    vent_total_venta     DOUBLE PRECISION
);

CREATE TABLE IF NOT EXISTS detalle_venta (
    deve_id              INTEGER PRIMARY KEY,
    deve_cantidad        DOUBLE PRECISION,
    deve_precio_unitario DOUBLE PRECISION,
    vent_id              INTEGER NOT NULL REFERENCES venta (vent_id),
    prod_id              INTEGER NOT NULL REFERENCES producto (prod_id)
);

CREATE TABLE IF NOT EXISTS usuario (
    usu_id             INTEGER PRIMARY KEY,
    usu_username       VARCHAR(50) NOT NULL UNIQUE,
    usu_password_hash  VARCHAR(255) NOT NULL,
    usu_rol            VARCHAR(20) NOT NULL
);
