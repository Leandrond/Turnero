package Dao;
import Conexion.ConexionBD;
import Models.Docente;
import Models.Instancias.InstanciaEvaluativa ;
import Models.Turno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class InstanciaEvaluativaDao {


    public void registrar(
            InstanciaEvaluativa instancia,
            int codigoComision,
            List<Docente> docentes,
            List<Turno> turnos) throws SQLException {

        if (docentes == null || docentes.isEmpty()) {
            throw new IllegalArgumentException(
                "La instancia debe tener al menos un docente."
            );
        }

        String insertarInstancia =
            "INSERT INTO instancia_evaluativa " +
            "(codigo_comision, codigo_instancia, tipo, modalidad, fecha, " +
            "hora_inicio, duracion_estimada) VALUES (?, ?, ?, ?, ?, ?, ?)";

        String asociarDocente =
            "INSERT INTO instancia_docente " +
            "(codigo_comision, codigo_instancia, legajo_docente) " +
            "VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar()) {
            conexion.setAutoCommit(false);

            try {
                try (PreparedStatement sentencia =
                        conexion.prepareStatement(insertarInstancia)) {
                    sentencia.setInt(1, codigoComision);
                    sentencia.setInt(2, instancia.getCodigoDeInstancia());
                    sentencia.setString(3, instancia.getTipo());
                    sentencia.setString(4, instancia.getModalidad().name());
                    sentencia.setDate(
                        5, java.sql.Date.valueOf(instancia.getFecha())
                    );
                    sentencia.setTime(
                        6, java.sql.Time.valueOf(instancia.getHoraInicio())
                    );
                    sentencia.setDouble(7, instancia.getDuracionEstimada());
                    sentencia.executeUpdate();
                }

                try (PreparedStatement sentencia =
                        conexion.prepareStatement(asociarDocente)) {
                    for (Docente docente : docentes) {
                        sentencia.setInt(1, codigoComision);
                        sentencia.setInt(2, instancia.getCodigoDeInstancia());
                        sentencia.setString(3, docente.getLegajo());
                        sentencia.addBatch();
                    }
                    sentencia.executeBatch();
                }

                TurnoDao turnoDao = new TurnoDao();
                for (int posicion = 0; posicion < turnos.size(); posicion++) {
                    turnoDao.registrar(
                        conexion, turnos.get(posicion), codigoComision, posicion
                    );
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



    public void listarPorComision(int codigoComision) throws SQLException {
        String sql =
            "SELECT codigo_instancia, tipo, fecha, hora_inicio, " +
            "duracion_estimada " +
            "FROM instancia_evaluativa " +
            "WHERE codigo_comision = ? " +
            "ORDER BY fecha, hora_inicio";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, codigoComision);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    System.out.println(
                        "Código: " + resultado.getInt("codigo_instancia") +
                        " - Tipo: " + resultado.getString("tipo") +
                        " - Fecha: " + resultado.getDate("fecha") +
                        " - Hora: " + resultado.getTime("hora_inicio") +
                        " - Duración: " +
                        resultado.getDouble("duracion_estimada")
                    );
                }
            }
        }
    }
}