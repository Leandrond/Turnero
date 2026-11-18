package Dao;

import Conexion.ConexionBD;
import Models.Comision;
import Models.Docente;
import Models.Materia;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ComisionDao {

    public void registrarConMateria(Comision comision, String legajoDocente)
            throws SQLException {

        String insertarMateria =
            "INSERT INTO materia (nombre, representante_legajo) VALUES (?, ?)";

        String insertarComision =
            "INSERT INTO comision " +
            "(codigo_comision, nombre, id_materia, legajo_docente) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar()) {
            conexion.setAutoCommit(false);

            try {
                int idMateria;

                try (PreparedStatement sentencia = conexion.prepareStatement(
                        insertarMateria, PreparedStatement.RETURN_GENERATED_KEYS)) {
                    sentencia.setString(1, comision.getMateria().getNombre());
                    sentencia.setString(2, legajoDocente);
                    sentencia.executeUpdate();

                    try (ResultSet claves = sentencia.getGeneratedKeys()) {
                        if (!claves.next()) {
                            throw new SQLException("No se recibió el ID de la materia.");
                        }
                        idMateria = claves.getInt(1);
                    }
                }

                comision.getMateria().setIdMateria(idMateria);

                try (PreparedStatement sentencia =
                        conexion.prepareStatement(insertarComision)) {
                    sentencia.setInt(1, comision.getCodigoComision());
                    sentencia.setString(2, comision.getNombre());
                    sentencia.setInt(3, idMateria);
                    sentencia.setString(4, legajoDocente);
                    sentencia.executeUpdate();
                }

                conexion.commit();
            } catch (SQLException e) {
                try {
                    conexion.rollback();
                } catch (SQLException errorRollback) {
                    e.addSuppressed(errorRollback);
                }
                throw e;
            }
        }
    }

    public ArrayList<Comision> listarPorDocente(Docente docente)
            throws SQLException {

        String sql =
            "SELECT c.codigo_comision, c.nombre, " +
            "m.id_materia, m.nombre AS nombre_materia " +
            "FROM comision c " +
            "JOIN materia m ON c.id_materia = m.id_materia " +
            "WHERE c.legajo_docente = ? " +
            "ORDER BY c.codigo_comision";

        ArrayList<Comision> comisiones = new ArrayList<>();

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, docente.getLegajo());

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    Materia materia = new Materia(
                        resultado.getString("nombre_materia"),
                        docente
                    );
                    materia.setIdMateria(resultado.getInt("id_materia"));

                    Comision comision = new Comision(
                        resultado.getInt("codigo_comision"),
                        resultado.getString("nombre"),
                        materia
                    );
                    comisiones.add(comision);
                }
            }
        }

        return comisiones;
    }
}