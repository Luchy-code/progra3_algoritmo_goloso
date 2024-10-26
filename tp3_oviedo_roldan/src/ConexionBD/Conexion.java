package ConexionBD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import Model.Offer;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/ofertas?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException ex) {
            throw new SQLException("Error al conectar con la base de datos", ex);
        }
    }

    public void insertDataIntoDatabase(String dni, String nacionalidad, String in, String out, int dinero, String fechaInscripcion, String fechaEvento) throws SQLException {
    	String nac = nacionalidad.equals("Argentin@") ? "Si" : "No";
        Connection conn = null;

        String sql = "INSERT INTO Inscriciones (dni, argentino, fechaInscripcion, fechaEvento, hora_in, hora_out, oferta) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            conn = getConnection();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                stmt.setString(1, dni);
                stmt.setString(2, nac);
                stmt.setString(3, fechaInscripcion);
                stmt.setString(4, fechaEvento);
                stmt.setString(5, in);
                stmt.setString(6, out);
                stmt.setInt(7, dinero);

                int rowsInserted = stmt.executeUpdate();

                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(null, "Registro insertado exitosamente.");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error al insertar los datos: " + ex.getMessage());
            throw new SQLException("Error al insertar los datos en la base de datos", ex);
        } finally {
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.err.println("Error al cerrar la conexión: " + ex.getMessage());
                }
            }
        }
    }

    public boolean existeOferta(String dni, String fechaInscripcion, String fechaEvento, String hora_in, String hora_out, int oferta) throws SQLException {
        return contarRegistros(dni, fechaInscripcion, fechaEvento, hora_in, hora_out, oferta) > 0;
    }

    private int contarRegistros(String dni, String fechaInscripcion, String fechaEvento, String hora_in, String hora_out, int oferta) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Inscriciones WHERE dni = ? AND fechaInscripcion = ? AND fechaEvento = ? AND hora_in = ? AND hora_out = ? AND oferta = ?";
        int count = 0;

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, dni);
            stmt.setString(2, fechaInscripcion);
            stmt.setString(3, fechaEvento);
            stmt.setString(4, hora_in);
            stmt.setString(5, hora_out);
            stmt.setInt(6, oferta);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error al contar registros: " + ex.getMessage());
            throw new SQLException("Error al verificar la existencia de la oferta", ex);
        }
        return count;
    }

    public List<Offer> getAllInscripciones() throws SQLException {
        List<Offer> inscripciones = new ArrayList<>();
        String sql = "SELECT dni, argentino, fechaInscripcion, fechaEvento, hora_in, hora_out, oferta FROM Inscriciones";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String dni = rs.getString("dni");
                String argentino = rs.getString("argentino");
                String fechaInscripcion = rs.getString("fechaInscripcion");
                String fechaEvento = rs.getString("fechaEvento");
                String horaIn = rs.getString("hora_in");
                String horaOut = rs.getString("hora_out");
                int oferta = rs.getInt("oferta");

                Offer of = new Offer();
                of.collecting(dni, argentino, horaIn, horaOut, oferta, fechaInscripcion, fechaEvento);
                inscripciones.add(of);
            }
        } catch (SQLException ex) {
            System.err.println("Error al obtener inscripciones: " + ex.getMessage());
            throw new SQLException("Error al obtener todas las inscripciones", ex);
        }
        return inscripciones;
    }
}
