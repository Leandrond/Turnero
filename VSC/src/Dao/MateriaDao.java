package Dao;

import Conexion.ConexionBD;
import Models.Docente;
import Models.Materia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MateriaDao {

    public void registrar(Materia materia) throws SQLException {
        String sql =
            "INSERT INTO materia (nombre, representante_legajo) VALUES (?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(
                 sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            sentencia.setString(1, materia.getNombre());
            sentencia.setString(
                2,
                materia.getRepresentanteCatedra().getLegajo()
            );

            sentencia.executeUpdate();

            try (ResultSet claves = sentencia.getGeneratedKeys()) {
                if (claves.next()) {
                    materia.setIdMateria(claves.getInt(1));
                } else {
                    throw new SQLException("No se recibió el ID de la materia.");
                }
            }
        }
    }

    public Materia buscarPorId(int idMateria) throws SQLException {
        String sql =
            "SELECT m.id_materia, m.nombre, " +
            "d.legajo, d.nombre AS nombre_docente, " +
            "d.apellido AS apellido_docente " +
            "FROM materia m " +
            "JOIN docente d ON m.representante_legajo = d.legajo " +
            "WHERE m.id_materia = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idMateria);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    Docente docente = new Docente(
                        resultado.getString("nombre_docente"),
                        resultado.getString("apellido_docente"),
                        resultado.getString("legajo")
                    );

                    Materia materia = new Materia(
                        resultado.getString("nombre"),
                        docente
                    );
                    materia.setIdMateria(resultado.getInt("id_materia"));
                    return materia;
                }
            }
        }

        return null;
    }
}