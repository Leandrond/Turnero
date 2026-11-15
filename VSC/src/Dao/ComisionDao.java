package Dao;

import Conexion.ConexionBD;
import Models.Comision;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ComisionDao {

    public void registrar(Comision comision, String legajoDocente)
            throws SQLException {

        String sql =
            "INSERT INTO comision " +
            "(codigo_comision, nombre, id_materia, legajo_docente) " +
            "VALUES (?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
            PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, comision.getCodigoComision());
            sentencia.setString(2, comision.getNombre());
            sentencia.setInt(3, comision.getMateria().getIdMateria());
            sentencia.setString(4, legajoDocente);

            sentencia.executeUpdate();
        }
    }
}