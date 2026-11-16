package Dao;
import Conexion.ConexionBD;
import Models.Comision;
import Models.Docente;
import Models.Instancias.DefensaParcial;
import Models.Instancias.ExamenFinal;
import Models.Instancias.InstanciaEvaluativa ;
import Models.Instancias.PresentacionTPI;
import Models.Modalidad;
import Models.Turnero;
import Models.Turno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
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

    // Actualiza la fecha y la hora de inicio de una instancia
    public void actualizarFechaHora(
            int codigoComision, int codigoInstancia,
            LocalDate fecha, LocalTime hora) throws SQLException {

        String sql =
            "UPDATE instancia_evaluativa SET fecha = ?, hora_inicio = ? " +
            "WHERE codigo_comision = ? AND codigo_instancia = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setDate(1, java.sql.Date.valueOf(fecha));
            sentencia.setTime(2, java.sql.Time.valueOf(hora));
            sentencia.setInt(3, codigoComision);
            sentencia.setInt(4, codigoInstancia);
            sentencia.executeUpdate();
        }
    }


    // Lee de la base las instancias de una comision con sus turnos
    public ArrayList<InstanciaEvaluativa> cargarPorComision(
            Comision comision, Docente docente) throws SQLException {

        String sql =
            "SELECT codigo_instancia, tipo, modalidad, fecha, " +
            "hora_inicio, duracion_estimada " +
            "FROM instancia_evaluativa " +
            "WHERE codigo_comision = ? " +
            "ORDER BY codigo_instancia";

        ArrayList<InstanciaEvaluativa> instancias = new ArrayList<>();

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, comision.getCodigoComision());

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    String tipo = resultado.getString("tipo");
                    int codigo = resultado.getInt("codigo_instancia");
                    LocalDate fecha = resultado.getDate("fecha").toLocalDate();
                    LocalTime hora =
                        resultado.getTime("hora_inicio").toLocalTime();
                    double duracion = resultado.getDouble("duracion_estimada");
                    Modalidad modalidad =
                        Modalidad.valueOf(resultado.getString("modalidad"));
                    Turnero turnero = new Turnero();

                    InstanciaEvaluativa instancia;
                    if (tipo.equals("Defensa Parcial")) {
                        instancia = new DefensaParcial(
                            tipo, fecha, hora, duracion, comision,
                            docente, codigo, turnero, modalidad
                        );
                    } else if (tipo.equals("Examen Final")) {
                        instancia = new ExamenFinal(
                            tipo, fecha, hora, duracion, comision,
                            docente, codigo, turnero, modalidad
                        );
                    } else {
                        instancia = new PresentacionTPI(
                            tipo, fecha, hora, duracion, comision,
                            docente, codigo, turnero, modalidad
                        );
                    }

                    instancias.add(instancia);
                }
            }
        }

        // Los turnos se leen despues de cerrar la consulta de instancias
        for (InstanciaEvaluativa instancia : instancias) {
            instancia.getTurnero().getListaTurno().addAll(
                new TurnoDao().cargarPorInstancia(comision, instancia)
            );
        }

        return instancias;
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