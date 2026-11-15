package Dao;
import Models.Turno;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
}}