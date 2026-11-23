package Dao;

import Conexion.ConexionBD;
import Models.Docente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DocenteDao {

    public void registrar(Docente docente) throws SQLException {

        
        String sql =
            "INSERT INTO docente (legajo, nombre, apellido) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
            PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, docente.getLegajo());
            sentencia.setString(2, docente.getNombre());
            sentencia.setString(3, docente.getApellido());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new SQLException("El legajo " + docente.getLegajo() +
                    " ya está registrado.", e);
            }
            throw e;
        }
    }

    public void listarDocentes() throws SQLException {
        String sql =
            "SELECT legajo, nombre, apellido FROM docente ORDER BY legajo";

        try (Connection conexion = ConexionBD.conectar();
            Statement sentencia = conexion.createStatement();
            ResultSet resultado = sentencia.executeQuery(sql)) {

            System.out.println("\n--- LISTADO DE DOCENTES ---");

            while (resultado.next()) {
                System.out.println(
                    "Legajo: " + resultado.getString("legajo") +
                    " - " + resultado.getString("nombre") +
                    " " + resultado.getString("apellido")
                );
            }
        }
    }

    public Docente buscarPorLegajo(String legajoBuscado) throws SQLException {
        String sql =
            "SELECT legajo, nombre, apellido " +
            "FROM docente WHERE legajo = ?";

        try (Connection conexion = ConexionBD.conectar();
            PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, legajoBuscado);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return new Docente(
                        resultado.getString("nombre"),
                        resultado.getString("apellido"),
                        resultado.getString("legajo")
                    );
                }
            }
        }

        return null;
    }
}