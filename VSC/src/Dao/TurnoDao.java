package Dao;
import Conexion.ConexionBD;
import Models.Alumno;
import Models.Comision;
import Models.Estado;
import Models.Grupo;
import Models.Instancias.InstanciaEvaluativa;
import Models.Turno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TurnoDao {

    public void registrar(
        Connection conexion, Turno turno, int codigoComision, int posicion)
        throws SQLException {

    if (turno == null || turno.getInstancia() == null ||
        turno.getFecha() == null || turno.getHora() == null ||
        turno.getEstado() == null) {
        throw new IllegalArgumentException(
            "El turno debe tener instancia, fecha, hora y estado."
        );
    }

    String tipoObjetivo;
    Integer legajoAlumno = null;
    Integer numeroGrupo = null;

    if (turno.getAlumno() != null &&
        turno.getGrupo() == null &&
        turno.getComision() == null) {
        tipoObjetivo = "ALUMNO";
        legajoAlumno = turno.getAlumno().getLegajo();
    } else if (turno.getAlumno() == null &&
               turno.getGrupo() != null &&
               turno.getComision() == null) {
        tipoObjetivo = "GRUPO";
        numeroGrupo = turno.getGrupo().getNumero();
    } else if (turno.getAlumno() == null &&
               turno.getGrupo() == null &&
               turno.getComision() != null) {
        tipoObjetivo = "COMISION";
    } else {
        throw new IllegalArgumentException(
            "El turno debe corresponder a un alumno, grupo o comisión."
        );
    }

    String sql =
        "INSERT INTO turno " +
        "(numero, posicion, codigo_comision, codigo_instancia, " +
        "tipo_objetivo, legajo_alumno, numero_grupo, fecha, hora, estado, nota) " +
        "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement sentencia = conexion.prepareStatement(sql)) {
        sentencia.setInt(1, turno.getNumero());
        sentencia.setInt(2, posicion);
        sentencia.setInt(3, codigoComision);
        sentencia.setInt(4, turno.getInstancia().getCodigoDeInstancia());
        sentencia.setString(5, tipoObjetivo);

        if (legajoAlumno == null) {
            sentencia.setNull(6, java.sql.Types.INTEGER);
        } else {
            sentencia.setInt(6, legajoAlumno);
        }

        if (numeroGrupo == null) {
            sentencia.setNull(7, java.sql.Types.INTEGER);
        } else {
            sentencia.setInt(7, numeroGrupo);
        }

        sentencia.setDate(8, java.sql.Date.valueOf(turno.getFecha()));
        sentencia.setTime(9, java.sql.Time.valueOf(turno.getHora()));
        sentencia.setString(10, turno.getEstado().name());

        if (turno.getNota() == null) {
            sentencia.setNull(11, java.sql.Types.INTEGER);
        } else {
            sentencia.setInt(11, turno.getNota());
        }

        sentencia.executeUpdate();
    }
}

    // Reemplaza todos los turnos de una instancia por los que estan en memoria
    public void reemplazarTurnos(
            int codigoComision, int codigoInstancia, List<Turno> turnos)
            throws SQLException {

        String borrar =
            "DELETE FROM turno " +
            "WHERE codigo_comision = ? AND codigo_instancia = ?";

        try (Connection conexion = ConexionBD.conectar()) {
            conexion.setAutoCommit(false); // Los cambios se confirman todos juntos al final

            try {
                try (PreparedStatement sentencia =
                        conexion.prepareStatement(borrar)) {
                    sentencia.setInt(1, codigoComision);
                    sentencia.setInt(2, codigoInstancia);
                    sentencia.executeUpdate(); // Borra los turnos viejos de esa instancia
                }

                for (int posicion = 0; posicion < turnos.size(); posicion++) {
                    registrar(
                        conexion, turnos.get(posicion), codigoComision, posicion
                    ); // Vuelve a guardar cada turno con su posicion actual
                }

                conexion.commit(); // Confirma todo
            } catch (SQLException | RuntimeException e) {
                try {
                    conexion.rollback(); // Si algo falla deshace todo y la base queda como estaba
                } catch (SQLException errorRollback) {
                    e.addSuppressed(errorRollback);
                }
                throw e;
            }
        }
    }

    // Lee de la base los turnos de una instancia en el orden guardado
    public ArrayList<Turno> cargarPorInstancia(
            Comision comision, InstanciaEvaluativa instancia)
            throws SQLException {

        String sql =
            "SELECT numero, tipo_objetivo, legajo_alumno, numero_grupo, " +
            "fecha, hora, estado, nota " +
            "FROM turno " +
            "WHERE codigo_comision = ? AND codigo_instancia = ? " +
            "ORDER BY posicion";

        ArrayList<Turno> turnos = new ArrayList<>();

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, comision.getCodigoComision());
            sentencia.setInt(2, instancia.getCodigoDeInstancia());

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    java.time.LocalDate fecha =
                        resultado.getDate("fecha").toLocalDate();
                    java.time.LocalTime hora =
                        resultado.getTime("hora").toLocalTime();
                    String tipoObjetivo = resultado.getString("tipo_objetivo");

                    Turno turno = null;

                    if (tipoObjetivo.equals("ALUMNO")) {
                        Alumno alumno = comision.buscarAlumnoPorLegajo(
                            resultado.getInt("legajo_alumno")
                        );
                        if (alumno != null) {
                            turno = new Turno(instancia, alumno, fecha, hora);
                        }
                    } else if (tipoObjetivo.equals("GRUPO")) {
                        int numeroGrupo = resultado.getInt("numero_grupo");
                        for (Grupo grupo : comision.getListaGrupos()) {
                            if (grupo.getNumero() == numeroGrupo) {
                                turno = new Turno(instancia, grupo, fecha, hora);
                                break;
                            }
                        }
                    } else {
                        turno = new Turno(instancia, comision, fecha, hora);
                    }

                    if (turno != null) {
                        int nota = resultado.getInt("nota");
                        Integer notaTurno = resultado.wasNull() ? null : nota;

                        turno.restaurar(
                            resultado.getInt("numero"),
                            Estado.valueOf(resultado.getString("estado")),
                            notaTurno
                        ); // Deja el turno como estaba guardado
                        turnos.add(turno);
                    }
                }
            }
        }

        return turnos;
    }
}