package Dao;

import Conexion.ConexionBD;
import Models.Grupo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GrupoDao {

    public void crear(Grupo grupo, int codigoComision) throws SQLException {
        String sql =
            "INSERT INTO grupo (codigo_comision, numero_grupo, cupo_maximo) " +
            "VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, codigoComision);
            sentencia.setInt(2, grupo.getNumero());
            sentencia.setInt(3, grupo.getCupoMaximo());
            sentencia.executeUpdate();
        }
    }

    public void agregarAlumno(
            int codigoComision, int numeroGrupo, int legajoAlumno)
            throws SQLException {

        String buscarGrupo =
            "SELECT g.cupo_maximo, " +
            "(SELECT COUNT(*) FROM grupo_alumno ga " +
            "WHERE ga.codigo_comision = g.codigo_comision " +
            "AND ga.numero_grupo = g.numero_grupo) AS cantidad_alumnos " +
            "FROM grupo g " +
            "WHERE g.codigo_comision = ? AND g.numero_grupo = ?";

        String agregar =
            "INSERT INTO grupo_alumno " +
            "(codigo_comision, numero_grupo, legajo_alumno) VALUES (?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar()) {
            try (PreparedStatement sentencia =
                     conexion.prepareStatement(buscarGrupo)) {
                sentencia.setInt(1, codigoComision);
                sentencia.setInt(2, numeroGrupo);

                try (ResultSet resultado = sentencia.executeQuery()) {
                    if (!resultado.next()) {
                        throw new SQLException("No se encontró el grupo.");
                    }

                    int cupo = resultado.getInt("cupo_maximo");
                    int cantidad = resultado.getInt("cantidad_alumnos");

                    if (cantidad >= cupo) {
                        throw new SQLException("El grupo ya alcanzó su cupo máximo.");
                    }
                }
            }

            try (PreparedStatement sentencia =
                     conexion.prepareStatement(agregar)) {
                sentencia.setInt(1, codigoComision);
                sentencia.setInt(2, numeroGrupo);
                sentencia.setInt(3, legajoAlumno);
                sentencia.executeUpdate();
            }
        }
    }

    
    public void quitarAlumno(
            int codigoComision, int numeroGrupo, int legajoAlumno)
            throws SQLException {

        String sql =
            "DELETE FROM grupo_alumno " +
            "WHERE codigo_comision = ? " +
            "AND numero_grupo = ? AND legajo_alumno = ?";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, codigoComision);
            sentencia.setInt(2, numeroGrupo);
            sentencia.setInt(3, legajoAlumno);

            if (sentencia.executeUpdate() == 0) {
                throw new SQLException("No se encontró ese alumno en el grupo.");
            }
        }
    }


    public java.util.ArrayList<Grupo> listarPorComision(int codigoComision)
            throws SQLException {

        String sql =
            "SELECT numero_grupo, cupo_maximo " +
            "FROM grupo " +
            "WHERE codigo_comision = ? " +
            "ORDER BY numero_grupo";

        java.util.ArrayList<Grupo> grupos = new java.util.ArrayList<>();

        try (Connection conexion = ConexionBD.conectar();
            PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, codigoComision);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    grupos.add(new Grupo(
                        resultado.getInt("numero_grupo"),
                        resultado.getInt("cupo_maximo")
                    ));
                }
            }
        }

        return grupos;
    }


    public java.util.ArrayList<Integer> listarLegajosIntegrantes(
            int codigoComision, int numeroGrupo) throws SQLException {

        String sql =
            "SELECT legajo_alumno FROM grupo_alumno " +
            "WHERE codigo_comision = ? AND numero_grupo = ? " +
            "ORDER BY legajo_alumno";

        java.util.ArrayList<Integer> legajos = new java.util.ArrayList<>();

        try (Connection conexion = ConexionBD.conectar();
            PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, codigoComision);
            sentencia.setInt(2, numeroGrupo);

            try (ResultSet resultado = sentencia.executeQuery()) {
                while (resultado.next()) {
                    legajos.add(resultado.getInt("legajo_alumno"));
                }
            }
        }

        return legajos;
    }

    public void actualizarCupo(
            int codigoComision, int numeroGrupo, int nuevoCupo)
            throws SQLException {

        String sql =
            "UPDATE grupo SET cupo_maximo = ? " +
            "WHERE codigo_comision = ? AND numero_grupo = ?";

        try (Connection conexion = ConexionBD.conectar();
            PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, nuevoCupo);
            sentencia.setInt(2, codigoComision);
            sentencia.setInt(3, numeroGrupo);

            if (sentencia.executeUpdate() == 0) {
                throw new SQLException("No se encontró el grupo en la base de datos.");
            }
        }
    }
}