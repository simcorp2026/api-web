package com.programacion.api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import jakarta.inject.Inject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import com.programacion.api.repository.IProductoRepository;
import com.programacion.api.repository.modelo.Producto;

/**
 * Prueba de integración del flujo de ventas y seguridad contra PostgreSQL real (Testcontainers).
 * Requiere Docker. Se ejecuta con: ./mvnw verify -DskipITs=false
 * El admin inicial usa la contraseña de %test.app.admin.password (application.properties).
 */
@QuarkusTest
@QuarkusTestResource(PostgresTestResource.class)
class VentaResourceIT {

    private static final String ADMIN_PASSWORD = "test-admin-123";

    @Inject
    IProductoRepository productoRepository;

    @Test
    void registrarVentaDescuentaStockYDevuelve201() {
        Integer productoId = crearProducto(5.0, 10.0);
        String body = """
                {"cedulaCliente":"0102030405","detalles":[{"productoId":%d,"cantidad":2}]}
                """.formatted(productoId);

        given().auth().oauth2(tokenAdmin()).contentType(ContentType.JSON).body(body)
                .when().post("/ventas")
                .then().statusCode(201)
                .body("totalVenta", closeTo(23.0, 0.001))
                .body("detalles", hasSize(1));

        assertStock(productoId, 3.0);
    }

    @Test
    void stockInsuficienteDevuelve409YNoDescuentaStock() {
        Integer productoId = crearProducto(1.0, 10.0);
        String body = """
                {"cedulaCliente":"0102030405","detalles":[{"productoId":%d,"cantidad":5}]}
                """.formatted(productoId);

        given().auth().oauth2(tokenAdmin()).contentType(ContentType.JSON).body(body)
                .when().post("/ventas")
                .then().statusCode(409)
                .body("mensaje", containsString("Stock insuficiente"));

        assertStock(productoId, 1.0);
    }

    @Test
    void cedulaInvalidaDevuelve400() {
        String body = """
                {"cedulaCliente":"abc","detalles":[{"productoId":1,"cantidad":1}]}
                """;

        given().auth().oauth2(tokenAdmin()).contentType(ContentType.JSON).body(body)
                .when().post("/ventas")
                .then().statusCode(400);
    }

    @Test
    void ventaInexistenteDevuelve404() {
        given().auth().oauth2(tokenAdmin())
                .when().get("/ventas/999999999")
                .then().statusCode(404)
                .body("mensaje", is("Venta no encontrada: 999999999"));
    }

    @Test
    void sinTokenDevuelve401() {
        given().when().get("/ventas")
                .then().statusCode(401);
    }

    @Test
    void loginConClaveIncorrectaDevuelve401() {
        given().contentType(ContentType.JSON)
                .body("{\"username\":\"admin\",\"password\":\"incorrecta\"}")
                .when().post("/auth/login")
                .then().statusCode(401);
    }

    @Test
    void vendedorNoPuedeCrearProductosNiUsuarios() {
        String vendedor = "vend-" + UUID.randomUUID().toString().substring(0, 8);
        given().auth().oauth2(tokenAdmin()).contentType(ContentType.JSON)
                .body("{\"username\":\"" + vendedor + "\",\"password\":\"clave-segura-1\",\"rol\":\"VENDEDOR\"}")
                .when().post("/usuarios")
                .then().statusCode(201);

        String tokenVendedor = token(vendedor, "clave-segura-1");

        given().auth().oauth2(tokenVendedor).contentType(ContentType.JSON)
                .body("{\"codigoBarras\":\"X\",\"categoria\":\"X\",\"stock\":1,\"precio\":1}")
                .when().post("/productos")
                .then().statusCode(403);

        given().auth().oauth2(tokenVendedor).contentType(ContentType.JSON)
                .body("{\"username\":\"otro\",\"password\":\"clave-segura-1\",\"rol\":\"ADMIN\"}")
                .when().post("/usuarios")
                .then().statusCode(403);
    }

    private String tokenAdmin() {
        return token("admin", ADMIN_PASSWORD);
    }

    private String token(String username, String password) {
        return given().contentType(ContentType.JSON)
                .body("{\"username\":\"" + username + "\",\"password\":\"" + password + "\"}")
                .when().post("/auth/login")
                .then().statusCode(200)
                .extract().path("accessToken");
    }

    Integer crearProducto(double stock, double precio) {
        Producto p = new Producto(null, "IT-" + UUID.randomUUID(), "Test", stock, precio);
        productoRepository.insertar(p);
        return p.getId();
    }

    private void assertStock(Integer productoId, double esperado) {
        Producto p = productoRepository.seleccionar(productoId);
        Assertions.assertEquals(esperado, p.getStock(), 0.001);
    }
}
