package Conexion;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

class RegistroPersonasBD {

    static void preparar(Connection conexion) throws SQLException {
        try (Statement sentencia = conexion.createStatement()) {
            sentencia.executeUpdate("""
                CREATE TABLE IF NOT EXISTS persona (
                    legajo VARCHAR(30) PRIMARY KEY,
                    tipo ENUM('ALUMNO', 'DOCENTE') NOT NULL,
                    legajo_original VARCHAR(30) NOT NULL
                ) ENGINE=InnoDB DEFAULT CHARACTER SET utf8mb4
                  COLLATE utf8mb4_0900_ai_ci
                """);

            // Un solo INSERT: si hay una colisión, InnoDB revierte toda la carga.
            try {
                sentencia.executeUpdate("""
                    INSERT INTO persona (legajo, tipo, legajo_original)
                    SELECT %s, origen.tipo, origen.legajo_original
                    FROM (
                        SELECT 'ALUMNO' AS tipo, CAST(legajo AS CHAR) AS legajo_original
                        FROM alumno
                        UNION ALL
                        SELECT 'DOCENTE', legajo FROM docente
                    ) AS origen
                    WHERE NOT EXISTS (
                        SELECT 1 FROM persona p
                        WHERE p.legajo = %s AND p.tipo = origen.tipo
                        AND CAST(p.legajo_original AS BINARY) =
                            CAST(origen.legajo_original AS BINARY)
                    )
                    """.formatted(clave("origen.legajo_original"),
                                  clave("origen.legajo_original")));
            } catch (SQLException e) {
                if (e.getErrorCode() == 1062) {
                    throw new SQLException(
                        "Hay legajos duplicados en los datos existentes. " +
                        "Revisá el legajo indicado antes de iniciar Turnero. " +
                        e.getMessage(), e);
                }
                throw e;
            }

            protegerLegajo(sentencia, "alumno", "ALUMNO");
            protegerLegajo(sentencia, "docente", "DOCENTE");
        }
    }

    private static void protegerLegajo(Statement sentencia, String tabla, String tipo)
            throws SQLException {
        // El alta y la reserva del legajo forman parte de la misma sentencia.
        sentencia.executeUpdate("""
            CREATE TRIGGER IF NOT EXISTS registrar_persona_%s
            AFTER INSERT ON %s FOR EACH ROW
            INSERT INTO persona (legajo, tipo, legajo_original)
            VALUES (%s, '%s', NEW.legajo)
            """.formatted(tabla, tabla, clave("NEW.legajo"), tipo));

        sentencia.executeUpdate("""
            CREATE TRIGGER IF NOT EXISTS proteger_legajo_%s
            BEFORE UPDATE ON %s FOR EACH ROW
            BEGIN
                IF CAST(OLD.legajo AS BINARY) <> CAST(NEW.legajo AS BINARY) THEN
                    SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'El legajo no se puede modificar';
                END IF;
            END
            """.formatted(tabla, tabla));
    }

    private static String clave(String legajo) {
        // Un legajo numérico con ceros a la izquierda identifica el mismo número.
        String valor = "TRIM(CONVERT(" + legajo + " USING utf8mb4))";
        return "(CASE WHEN " + valor + " REGEXP '^[+-]?[0-9]+$' THEN " +
               "CAST(CAST(" + valor + " AS DECIMAL(30, 0)) AS CHAR CHARACTER SET utf8mb4) " +
               "ELSE " + valor + " END) COLLATE utf8mb4_0900_ai_ci";
    }
}
