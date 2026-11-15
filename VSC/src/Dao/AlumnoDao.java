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

    public void registrarEnComision(Alumno alumno, int codigoComision)
            throws SQLException {

        String buscarAlumno =
            "SELECT 1 FROM alumno WHERE legajo = ?";

        String insertarAlumno =
            "INSERT INTO alumno " +
            "(legajo, nombre, apellido, estado, prioridad) " +
            "VALUES (?, ?, ?, ?, ?)";

        String asociarAlumno =
            "INSERT INTO comision_alumno (codigo_comision, legajo_alumno) " +
            "VALUES (?, ?)";

        try (Connection conexion = ConexionBD.conectar()) {
            conexion.setAutoCommit(false);

            try {
                boolean existe;

                try (PreparedStatement sentencia =
                        conexion.prepareStatement(buscarAlumno)) {
                    sentencia.setInt(1, alumno.getLegajo());

                    try (ResultSet resultado = sentencia.executeQuery()) {
                        existe = resultado.next();
                    }
                }

                if (!existe) {
                    try (PreparedStatement sentencia =
                            conexion.prepareStatement(insertarAlumno)) {
                        sentencia.setInt(1, alumno.getLegajo());
                        sentencia.setString(2, alumno.getNombre());
                        sentencia.setString(3, alumno.getApellido());
                        sentencia.setString(4, alumno.getEstado().name());
                        sentencia.setBoolean(5, alumno.getPrioridad());
                        sentencia.executeUpdate();
                    }
                }

                try (PreparedStatement sentencia =
                        conexion.prepareStatement(asociarAlumno)) {
                    sentencia.setInt(1, codigoComision);
                    sentencia.setInt(2, alumno.getLegajo());
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
                    Alumno alumno = new Alumno(
                        resultado.getString("nombre"),
                        resultado.getString("apellido"),
                        resultado.getInt("legajo")
                    );

                    String estado = resultado.getString("estado");
                    if (estado != null) {
                        alumno.setEstado(Estado.valueOf(estado));
                    }

                    alumno.setPrioridad(resultado.getBoolean("prioridad"));
                    alumnos.add(alumno);
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
}