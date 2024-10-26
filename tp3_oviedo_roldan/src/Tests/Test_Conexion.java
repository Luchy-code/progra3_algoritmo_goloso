package Tests;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ConexionBD.Conexion;
import Model.Offer;

public class Test_Conexion {

    private Conexion conexion;

    @BeforeEach
    void setUp() {
        conexion = new Conexion();
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (var conn = Conexion.getConnection();
             var stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM Inscriciones WHERE dni = 'testDNI'");
        }
    }

    @Test
    void testGetConnection() {
        try (var conn = Conexion.getConnection()) {
            assertNotNull(conn);
            assertTrue(!conn.isClosed(), "La conexión debería estar activa");
        } catch (SQLException e) {
            fail("Error al establecer la conexión: " + e.getMessage());
        }
    }

    @Test
    void testInsertDataIntoDatabase() {
        try {
            conexion.insertDataIntoDatabase("testDNI", "Argentin@", "08:00", "16:00", 200, "2024-10-01", "2024-10-10");
            assertTrue(conexion.existeOferta("testDNI", "2024-10-01", "2024-10-10", "08:00", "16:00", 200),
                    "La oferta debería existir después de la inserción");
        } catch (SQLException e) {
            fail("Error al insertar datos: " + e.getMessage());
        }
    }

    @Test
    void testExisteOferta() {
        try {
            conexion.insertDataIntoDatabase("testDNI", "Argentin@", "08:00", "16:00", 200, "2024-10-01", "2024-10-10");
            boolean existe = conexion.existeOferta("testDNI", "2024-10-01", "2024-10-10", "08:00", "16:00", 200);
            assertTrue(existe, "La oferta debería existir en la base de datos");
        } catch (SQLException e) {
            fail("Error al verificar existencia de oferta: " + e.getMessage());
        }
    }

    @Test
    void testGetAllInscripciones() {
        try {
            conexion.insertDataIntoDatabase("testDNI", "Argentin@", "08:00", "16:00", 200, "2024-10-01", "2024-10-10");
            List<Offer> inscripciones = conexion.getAllInscripciones();
            assertNotNull(inscripciones);
            assertTrue(inscripciones.stream().anyMatch(o -> o.getDni().equals("testDNI")),
                    "Debería existir una inscripción con DNI 'testDNI'");
        } catch (SQLException e) {
            fail("Error al obtener inscripciones: " + e.getMessage());
        }
    }
}
