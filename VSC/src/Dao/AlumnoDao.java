package Dao;

import Conexion.ConexionBD;
import Models.Alumno;
import Models.Estado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class AlumnoDao {

    public void registrar(Alumno alumno) throws SQLException {
        String sql = "INSERT INTO alumno (legajo, nombre, apellido, estado, prioridad) " +
                     "VALUES (?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, alumno.getLegajo());
            sentencia.setString(2, alumno.getNombre());
            sentencia.setString(3, alumno.getApellido());
            sentencia.setString(4, alumno.getEstado().name());
            sentencia.setBoolean(5, alumno.getPrioridad());
            sentencia.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new SQLException("El legajo " + alumno.getLegajo() +
                    " ya está registrado.", e);
            }
            throw e;
        }
    }

    public void inscribir(int codigoComision, int legajo) throws SQLException {
        String sql = "INSERT INTO comision_alumno (codigo_comision, legajo_alumno) VALUES (?, ?)";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, codigoComision);
            sentencia.setInt(2, legajo);
            sentencia.executeUpdate();
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new SQLException("El alumno ya está inscripto en esa comisión.", e);
            }
            throw e;
        }
    }

    public Alumno buscarPorLegajo(int legajo) throws SQLException {
        String sql = "SELECT legajo, nombre, apellido, estado, prioridad FROM alumno WHERE legajo = ?";
        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {
            sentencia.setInt(1, legajo);
            try (ResultSet resultado = sentencia.executeQuery()) {
                return resultado.next() ? leerAlumno(resultado) : null;
            }
        }
    }

    private Alumno leerAlumno(ResultSet resultado) throws SQLException {
        Alumno alumno = new Alumno(resultado.getString("nombre"),
            resultado.getString("apellido"), resultado.getInt("legajo"));
        String estado = resultado.getString("estado");
        if (estado != null) {
            alumno.setEstado(Estado.valueOf(estado));
        }
        alumno.setPrioridad(resultado.getBoolean("prioridad"));
        return alumno;
    }

    public ArrayList<Alumno> listarPorComision(int codigoComision)
            throws SQLException {

        String sql =
            "SELECT a.legajo, a.nombre, a.apellido, a.estado, a.prioridad " +
            "FROM alumno a " +
            "JOIN comision_alumno ca ON a.legajo = ca.legajo_alumno " +
            "WHERE ca.codigo_comision = ? " +
            "ORDER BY a.legajo";

        ArrayList<Alumno> alumnos = new ArrayList<>();

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, codigoComision);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    alumnos.add(leerAlumno(resultado));
                }
            }
        }

        return alumnos;
    }

    public void cambiarPrioridad(int legajo, boolean prioridad)
            throws SQLException {

        String sql = "UPDATE alumno SET prioridad = ? WHERE legajo = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setBoolean(1, prioridad);
            sentencia.setInt(2, legajo);

            if (sentencia.executeUpdate() == 0) {
                throw new SQLException(
                    "No existe un alumno con legajo " + legajo
                );
            }
        }
    }

    // Da de baja a un alumno de una comision (todo o nada)
    public void darDeBaja(int codigoComision, int legajo) throws SQLException {

        String[] sentencias = {
            "DELETE FROM turno WHERE codigo_comision = ? " +
            "AND legajo_alumno = ? AND estado = 'PENDIENTE'",
            "DELETE FROM grupo_alumno WHERE codigo_comision = ? " +
            "AND legajo_alumno = ?",
            "DELETE FROM comision_alumno WHERE codigo_comision = ? " +
            "AND legajo_alumno = ?"
        };

        try (Connection conexion = ConexionBD.conectar()) {
            conexion.setAutoCommit(false);

            try {
                for (String sql : sentencias) {
                    try (PreparedStatement sentencia =
                            conexion.prepareStatement(sql)) {
                        sentencia.setInt(1, codigoComision);
                        sentencia.setInt(2, legajo);
                        sentencia.executeUpdate();
                    }
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
}