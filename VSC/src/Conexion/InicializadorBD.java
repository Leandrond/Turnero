package Conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class InicializadorBD {
public static void crearBD() throws SQLException {
    // creamos la base de datos si no existe
    try (Connection conexion = ConexionBD.conectarServidor();
        Statement sentencia = conexion.createStatement()) {
        sentencia.executeUpdate(
            "CREATE DATABASE IF NOT EXISTS turnero_db "
        );
    }
    // creamos todas las tablas si no existen
    try (Connection conexion = ConexionBD.conectar()) {
        ejecutarDDL(conexion,
            "CREATE TABLE IF NOT EXISTS docente (" +
            "legajo VARCHAR(30) PRIMARY KEY, " +
            "nombre VARCHAR(100) NOT NULL, " +
            "apellido VARCHAR(100) NOT NULL" +
            ") ENGINE=InnoDB");

        ejecutarDDL(conexion,
            "CREATE TABLE IF NOT EXISTS materia (" +
            "id_materia INT AUTO_INCREMENT PRIMARY KEY, " +
            "nombre VARCHAR(120) NOT NULL, " +
            "representante_legajo VARCHAR(30) NOT NULL, " +
            "FOREIGN KEY (representante_legajo) REFERENCES docente(legajo)" +
            ") ENGINE=InnoDB");

        ejecutarDDL(conexion,
            "CREATE TABLE IF NOT EXISTS comision (" +
            "codigo_comision INT PRIMARY KEY, " +
            "nombre VARCHAR(100) NOT NULL, " +
            "id_materia INT NOT NULL, " +
            "legajo_docente VARCHAR(30) NOT NULL, " +
            "FOREIGN KEY (id_materia) REFERENCES materia(id_materia), " +
            "FOREIGN KEY (legajo_docente) REFERENCES docente(legajo)" +
            ") ENGINE=InnoDB");

        ejecutarDDL(conexion,
            "CREATE TABLE IF NOT EXISTS alumno (" +
            "legajo INT PRIMARY KEY, " +
            "estado VARCHAR(20), " +
            "prioridad BOOLEAN NOT NULL DEFAULT FALSE" +
            ") ENGINE=InnoDB");

        ejecutarDDL(conexion,
            "CREATE TABLE IF NOT EXISTS comision_alumno (" +
            "codigo_comision INT NOT NULL, " +
            "legajo_alumno INT NOT NULL, " +
            "PRIMARY KEY (codigo_comision, legajo_alumno), " +
            "FOREIGN KEY (codigo_comision) REFERENCES comision(codigo_comision), " +
            "FOREIGN KEY (legajo_alumno) REFERENCES alumno(legajo)" +
            ") ENGINE=InnoDB");

        ejecutarDDL(conexion,
            "CREATE TABLE IF NOT EXISTS grupo (" +
            "codigo_comision INT NOT NULL, " +
            "numero_grupo INT NOT NULL, " +
            "cupo_maximo INT NOT NULL, " +
            "PRIMARY KEY (codigo_comision, numero_grupo), " +
            "FOREIGN KEY (codigo_comision) REFERENCES comision(codigo_comision)" +
            ") ENGINE=InnoDB");

        ejecutarDDL(conexion,
            "CREATE TABLE IF NOT EXISTS grupo_alumno (" +
            "codigo_comision INT NOT NULL, " +
            "numero_grupo INT NOT NULL, " +
            "legajo_alumno INT NOT NULL, " +
            "PRIMARY KEY (codigo_comision, numero_grupo, legajo_alumno), " +
            "FOREIGN KEY (codigo_comision, numero_grupo) " +
            "REFERENCES grupo(codigo_comision, numero_grupo), " +
            "FOREIGN KEY (codigo_comision, legajo_alumno) " +
            "REFERENCES comision_alumno(codigo_comision, legajo_alumno)" +
            ") ENGINE=InnoDB");

        ejecutarDDL(conexion,
            "CREATE TABLE IF NOT EXISTS instancia_evaluativa (" +
            "codigo_comision INT NOT NULL, " +
            "codigo_instancia INT NOT NULL, " +
            "tipo VARCHAR(40) NOT NULL, " +
            "fecha DATE NOT NULL, " +
            "hora_inicio TIME NOT NULL, " +
            "duracion_estimada DECIMAL(8,2) NOT NULL, " +
            "PRIMARY KEY (codigo_comision, codigo_instancia), " +
            "FOREIGN KEY (codigo_comision) REFERENCES comision(codigo_comision)" +
            ") ENGINE=InnoDB");

        ejecutarDDL(conexion,
            "CREATE TABLE IF NOT EXISTS instancia_docente (" +
            "codigo_comision INT NOT NULL, " +
            "codigo_instancia INT NOT NULL, " +
            "legajo_docente VARCHAR(30) NOT NULL, " +
            "PRIMARY KEY (codigo_comision, codigo_instancia, legajo_docente), " +
            "FOREIGN KEY (codigo_comision, codigo_instancia) " +
            "REFERENCES instancia_evaluativa(codigo_comision, codigo_instancia), " +
            "FOREIGN KEY (legajo_docente) REFERENCES docente(legajo)" +
            ") ENGINE=InnoDB");

        ejecutarDDL(conexion,
            "CREATE TABLE IF NOT EXISTS turno (" +
            "id_turno BIGINT AUTO_INCREMENT PRIMARY KEY, " +
            "numero INT NOT NULL, " +
            "posicion INT NOT NULL, " +
            "codigo_comision INT NOT NULL, " +
            "codigo_instancia INT NOT NULL, " +
            "tipo_objetivo VARCHAR(20) NOT NULL, " +
            "legajo_alumno INT NULL, " +
            "numero_grupo INT NULL, " +
            "fecha DATE NOT NULL, " +
            "hora TIME NOT NULL, " +
            "estado VARCHAR(20) NOT NULL, " +
            "UNIQUE (codigo_comision, codigo_instancia, posicion), " +
            "FOREIGN KEY (codigo_comision, codigo_instancia) " +
            "REFERENCES instancia_evaluativa(codigo_comision, codigo_instancia), " +
            "FOREIGN KEY (codigo_comision, legajo_alumno) " +
            "REFERENCES comision_alumno(codigo_comision, legajo_alumno), " +
            "FOREIGN KEY (codigo_comision, numero_grupo) " +
            "REFERENCES grupo(codigo_comision, numero_grupo), " +
            "CHECK (" +
            "(tipo_objetivo = 'ALUMNO' AND legajo_alumno IS NOT NULL AND numero_grupo IS NULL) OR " +
            "(tipo_objetivo = 'GRUPO' AND legajo_alumno IS NULL AND numero_grupo IS NOT NULL) OR " +
            "(tipo_objetivo = 'COMISION' AND legajo_alumno IS NULL AND numero_grupo IS NULL)" +
            ")" +
            ") ENGINE=InnoDB");
    }

    System.out.println("Base de datos y tablas disponibles.");
}

private static void ejecutarDDL(Connection conexion, String sql)
        throws SQLException {
    try (Statement sentencia = conexion.createStatement()) {
        sentencia.executeUpdate(sql);
    }
}
}