package Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {

    private static final String USUARIO = "root"; // Usuario
    private static final String CLAVE = "123456"; // Contraseña

    private static final String URL_SERVIDOR =
    "jdbc:mysql://localhost:3306";

    private static final String URL_BASE =
    "jdbc:mysql://localhost:3306/turnero_db";

        // hay dos conecciones a la base de datos
        // la primera conecta a MySQL
        // y la otra crea la base de datos si no existe
    public static Connection conectarServidor() throws SQLException {
        return DriverManager.getConnection(URL_SERVIDOR, USUARIO, CLAVE);
    }

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL_BASE, USUARIO, CLAVE);
    }
}


// Agregar al main.java:
//
//
//    import Conexion.InicializadorBD;
//    import java.sql.SQLException;
//
//y antes que nada en el primer metodo agregar esto
//
//    try {
//        InicializadorBD.crearBD();
//    } catch (SQLException e) {
//        System.err.println(
//            "No se pudo preparar la base de datos: " + e.getMessage()
//        );
//        return;
//    }
// para docente
// DocenteDao docenteDao = new DocenteDao();
// docenteDao.registrar(docente);

//         AGREGAR EN Materia
//         private int idMateria;

        // public int getIdMateria() {
        //     return idMateria;
        // }

        // public void setIdMateria(int idMateria) {
        //     this.idMateria = idMateria;
        // }


        