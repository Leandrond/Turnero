
package Views;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import Controllers.GestionTurnero;
import Models.Alumno;
import Models.Comision;
import Models.DefensaParcial;
import Models.Docente;
import Models.Estado;
import Models.ExamenFinal;
import Models.Turno;
import Models.Materia;
import Models.Modalidad;
import Models.PresentacionTPI;
import Models.Turnero;
import Models.Turno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

public class Main {

    static Scanner teclado = new Scanner(System.in);
    static int leerEntero(String mensaje) {
        while (true) {
        System.out.print(mensaje);

            try {
                return Integer.parseInt(teclado.nextLine().trim());
        }       catch (NumberFormatException e) {
            System.out.println("Ingrese un numero entero valido.");
        }
    }
}

    static Modalidad leerModalidad() {
        while (true) {
            System.out.println("\nMODALIDAD DE EVALUACION");
            System.out.println("1. Individual");
            System.out.println("2. Grupal");
            System.out.println("3. General");

            int opcion = leerEntero("Modalidad: ");
            switch (opcion) {
                case 1:
                    return Modalidad.INDIVIDUAL;
                case 2:
                    return Modalidad.GRUPAL;
                case 3:
                    return Modalidad.GENERAL;
                default:
                    System.out.println("Opcion incorrecta.");
            }
        }
    }

    static String leerTexto(String mensaje) {
        while (true) {
            System.out.print(mensaje);
            String texto = teclado.nextLine().trim();
            if (!texto.isEmpty()) {
                return texto;
            }
            System.out.println("El dato no puede quedar vacio.");
        }
    }
    public static void main(String[] args) {

        GestionTurnero gestion = new GestionTurnero();
        registrarDocente(gestion);

        int opcion;
        do {
            System.out.println("\nTURNERO");
            System.out.println("1. Comisiones");
            System.out.println("2. Instancias evaluativas");
            System.out.println("0. Salir");

            opcion = leerEntero("Opcion: ");
            switch (opcion) {
                case 1:
                    menuComisiones(gestion);
                    break;

                case 2:
                    menuEvaluaciones(gestion);
                    break;

                case 0:
                    System.out.println("Programa finalizado.");
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
            }

        } while (opcion != 0);

        teclado.close();
    }

static void registrarDocente(GestionTurnero gestion) {
    if (!gestion.tieneDocenteRegistrado()) {

        System.out.println("\nREGISTRO DEL DOCENTE");

        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String legajo = leerTexto("Legajo: ");

        gestion.registrarDocente(nombre, apellido, legajo);

        System.out.println("Docente registrado correctamente.");
    }
}
    static void menuComisiones(GestionTurnero gestion) {

        int opcion;

        do {
            System.out.println("\nCOMISIONES");
            System.out.println("1. Crear comision");
            System.out.println("2. Ver comisiones");
            System.out.println("3. Administrar alumnos");
            System.out.println("4. Administrar grupos");
            System.out.println("5. Prioridades de alumnos");
            System.out.println("0. Volver");

            opcion = leerEntero("Opcion: ");

            switch (opcion) {
                case 1:
                    crearComision(gestion);
                    break;

                case 2:
                    listarComisiones(gestion);
                    break;

                case 3:
                    menuAlumnos(gestion);
                    break;

                case 4:
                    menuGrupos(gestion);
                    break;

                case 5:
                    administrarPrioridades(gestion);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
            }

        } while (opcion != 0);
    }
    static void crearComision(GestionTurnero gestion) {
        int codigo = leerEntero("Codigo: ");
        String nombre = leerTexto("Nombre: ");
        String materia = leerTexto("Materia: ");

         Docente docenteActual = gestion.getDocenteActual();
         Materia nuevaMateria = new Materia(materia, docenteActual);
         gestion.crearComision(codigo, nombre, nuevaMateria);
    }
    static void listarComisiones(GestionTurnero gestion) {
        gestion.listarComisiones();
    }
    static void menuAlumnos(GestionTurnero gestion) {

        int codigo = leerEntero("Codigo de comision: ");
        int opcion;

        do {
            System.out.println("\nALUMNOS");
            System.out.println("1. Agregar alumno");
            System.out.println("2. Ver alumnos");
            System.out.println("3. Buscar alumno");
            System.out.println("4. Dar de baja alumno");
            System.out.println("0. Volver");

            opcion = leerEntero("Opcion: ");

            switch (opcion) {
                case 1:
                    agregarAlumno(gestion, codigo);
                    break;

                case 2:
                    listarAlumnos(gestion, codigo);
                    break;

                case 3:
                    buscarAlumno(gestion, codigo);
                    break;

                case 4:
                    darDeBajaAlumno(gestion, codigo);
                    break;

                case 0:
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
            }

        } while (opcion != 0);
    }

    static void agregarAlumno(GestionTurnero gestion, int codigo) {
        int legajo = leerEntero("Legajo del alumno: ");
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        gestion.agregarAlumno(codigo, legajo, nombre, apellido);
    }
    static void listarAlumnos(GestionTurnero gestion, int codigo) {

         gestion.listarAlumnos(codigo);
    }
    static void buscarAlumno(GestionTurnero gestion, int codigo) {

        int legajo = leerEntero("Legajo: ");

         gestion.buscarAlumno(codigo, legajo);
    }
    static void darDeBajaAlumno(GestionTurnero gestion, int codigo) {

        int legajo = leerEntero("Legajo: ");

         gestion.darDeBajaAlumno(codigo, legajo);
    }
    static void menuGrupos(GestionTurnero gestion) {

        int codigo = leerEntero("Codigo de comision: ");
        int opcion;

        do {
            System.out.println("\nGRUPOS");
            System.out.println("1. Crear grupo");
            System.out.println("2. Agregar alumno a grupo");
            System.out.println("3. Quitar alumno de grupo");
            System.out.println("4. Ver grupos");
            System.out.println("5. Cambiar cupo maximo de grupo");
            System.out.println("0. Volver");

            opcion = leerEntero("Opcion: ");

            switch (opcion) {
                case 1:
                    crearGrupo(gestion);
                    break;

                case 2:
                    agregarAlumnoAGrupo(gestion, codigo);
                    break;

                case 3:
                    quitarAlumnoDeGrupo(gestion, codigo);
                    break;

                case 4:
                    listarGrupos(gestion, codigo);
                    break;
                case 5:
                    cambiarCupoMaximo(gestion, codigo);
                    break;  

                case 0:
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
            }

        } while (opcion != 0);
    }
    static void crearGrupo(GestionTurnero gestion) {
        System.out.println("\nCREAR GRUPO");
        System.out.println("\nCodigo de comision: ");
        int codigo = leerEntero("Codigo de comision: ");
        System.out.println("\nNumero de grupo: ");
        int numero = leerEntero("Numero de grupo: ");
        System.out.println("\nCupo maximo: ");
        int cupo = leerEntero("Cupo: ");

         gestion.crearGrupo(codigo, numero, cupo);
    }
    static void cambiarCupoMaximo(GestionTurnero gestion, int codigo) {
        System.out.println("\nCAMBIAR CUPO MAXIMO DE GRUPO");
        System.out.println("\nCodigo de Grupo: ");
        int numero = leerEntero("Numero de grupo: ");
        System.out.println("\nNuevo cupo maximo: ");
        int cupo = leerEntero("Cupo: ");

         gestion.cambiarCupoMaximo(codigo, numero, cupo);
    }
    static void agregarAlumnoAGrupo(GestionTurnero gestion, int codigo) {
        System.out.println("\nAGREGAR ALUMNO A GRUPO");
        System.out.println("\nCodigo de grupo: ");
        int numero = leerEntero("Numero de grupo: ");
        System.out.println("\nLegajo del alumno: ");
        int legajo = leerEntero("Legajo: ");

         gestion.agregarAlumnoAGrupo(codigo, numero, legajo);
    }
    static void quitarAlumnoDeGrupo(GestionTurnero gestion, int codigo) {
        System.out.println("\nQUITAR ALUMNO DE GRUPO");
        System.out.println("\n Codigo de grupo: ");
        int numero = leerEntero("Numero de grupo: ");
        System.out.println("\n Legajo del alumno: ");
        int legajo = leerEntero("Legajo: ");

         gestion.quitarAlumnoDeGrupo(codigo, numero, legajo);
    }
    static void listarGrupos(GestionTurnero gestion, int codigo) {

         gestion.listarGrupos(codigo);
    }
    static void administrarPrioridades(GestionTurnero gestion) {
        System.out.println("\nADMINISTRAR PRIORIDADES DE ALUMNOS");
        System.out.println("\nCodigo de comision: ");
        int codigo = leerEntero("Codigo de comision: ");
        System.out.println("\nLegajo del alumno: ");
        int legajo = leerEntero("Legajo: ");

        System.out.println("1. Dar prioridad");
        System.out.println("2. Quitar prioridad");

        int opcion = leerEntero("Opcion: ");

         if (opcion == 1) {
             gestion.cambiarPrioridad(codigo, legajo, true);
         } else if (opcion == 2) {
             gestion.cambiarPrioridad(codigo, legajo, false);
         } else {
             System.out.println("Opcion incorrecta.");
         }
    }
    
static void menuEvaluaciones(GestionTurnero gestion) {

    int opcion;

    do {
        System.out.println("\nINSTANCIAS EVALUATIVAS");
        System.out.println("1. Crear instancia");
        System.out.println("2. Ver instancias");
        System.out.println("3. Iniciar evaluacion");
        System.out.println("4. Cambiar orden de turnos");
        System.out.println("5. Modificar fecha");
        System.out.println("6. Modificar hora");
        System.out.println("0. Volver");

        opcion = leerEntero("Opcion: ");

        switch (opcion) {

            case 1:
                crearInstancia(gestion);
                break;

            case 2:
                listarInstancias(gestion);
                break;

            case 3:
                ejecutarEvaluacion(gestion);
                break;

            case 4:
                cambiarOrdenTurnos(gestion);
                break;

            case 5:
                modificarFechaInstancia(gestion);
                break;

            case 6:
                modificarHoraInstancia(gestion);
                break;

            case 0:
                break;

            default:
                System.out.println("MAL!!!.");
        }

    } while (opcion != 0);
}

static void crearInstancia(GestionTurnero gestion) {

    System.out.println("\nCREAR INSTANCIA EVALUATIVA");
    int codigoComision = leerEntero("Codigo de comision: ");

    Comision comisionEncontrada =
        gestion.buscarComisionPorCodigo(codigoComision);

    if (comisionEncontrada == null) {
        System.out.println("No se encontro la comision.");
        return;
    }
    System.out.println("\n1. Defensa parcial");
    System.out.println("2. Examen final");
    System.out.println("3. Presentacion TPI");

    int tipo = leerEntero("Tipo de instancia: ");

    if (tipo < 1 || tipo > 3) {
        System.out.println("Tipo de instancia no valido.");
        return;
    }

    Modalidad modalidad = leerModalidad();
    String fecha = leerTexto("Fecha (AAAA-MM-DD): ");
    String hora = leerTexto("Hora (HH:MM): ");

    double duracionEstimada;
    int codigoDeInstancia;

    try {
        LocalDate.parse(fecha);
        LocalTime.parse(hora);

        duracionEstimada = Double.parseDouble(
            leerTexto("Duracion estimada en minutos: ")
        );

        if (!Double.isFinite(duracionEstimada) || duracionEstimada < 1 || duracionEstimada > 1440) {
            System.out.println("Ingresa una duracion entre 1 y 1440 minutos.");
            return;
        }

    } catch (java.time.format.DateTimeParseException |
             NumberFormatException e) {
        System.out.println("Fecha, hora o duracion no valida.");
        return;
    }

    codigoDeInstancia = leerEntero("Codigo de instancia: ");
    Docente docente = gestion.getDocenteActual();

    if (docente == null) {
        System.out.println("No hay un docente registrado.");
        return;
    }
    Turnero turnero = new Turnero();

    boolean registrada;
    switch (tipo) {

        case 1:
            registrada = gestion.crearDefensaParcial(
                comisionEncontrada,
                fecha,
                hora,
                duracionEstimada,
                codigoDeInstancia,
                docente,
                turnero,
                modalidad
            );
            break;

        case 2:
            registrada = gestion.crearExamenFinal(
                comisionEncontrada,
                fecha,
                hora,
                duracionEstimada,
                codigoDeInstancia,
                docente,
                turnero,
                modalidad
            );
            break;

        case 3:
            registrada = gestion.crearPresentacionTPI(
                comisionEncontrada,
                fecha,
                hora,
                duracionEstimada,
                codigoDeInstancia,
                docente,
                turnero,
                modalidad
            );
            break;
        default:
            return;
    }

    if (!registrada) {
        return;
    }

    System.out.println("Instancia evaluativa registrada.");
    System.out.println("Modalidad seleccionada: " + modalidad);
}

static void modificarFechaInstancia(GestionTurnero gestion) {

    System.out.println("\nMODIFICAR FECHA DE INSTANCIA");

    int codigoComision = leerEntero("Codigo de comision: ");
    int codigoInstancia = leerEntero("Codigo de instancia: ");

    String nuevaFecha = leerTexto("Nueva fecha (AAAA-MM-DD): ");

    try {
        LocalDate.parse(nuevaFecha);
        gestion.modificarFechaInstancia(
            codigoComision,
            codigoInstancia,
            nuevaFecha
        );

    } catch (java.time.format.DateTimeParseException e) {
        System.out.println("Formato de fecha no valido.");
    }
}

static void modificarHoraInstancia(GestionTurnero gestion) {

    System.out.println("\nMODIFICAR HORA DE INSTANCIA");

    int codigoComision = leerEntero("Codigo de comision: ");
    int codigoInstancia = leerEntero("Codigo de instancia: ");

    String nuevaHora = leerTexto("Nueva hora (HH:MM): ");

    try {
        LocalTime.parse(nuevaHora);
        gestion.modificarHoraInstancia(
            codigoComision,
            codigoInstancia,
            nuevaHora
        );

    } catch (java.time.format.DateTimeParseException e) {
        System.out.println("Formato de hora no valido.");
    }
}
    static void listarInstancias(GestionTurnero gestion) {

        int codigo = leerEntero("Codigo de comision: ");

         gestion.listarInstanciasEvaluativas(codigo);
    }

static void ejecutarEvaluacion(GestionTurnero gestion) {

    System.out.println("\nEJECUTAR EVALUACION");

    int codigoComision = leerEntero("Codigo de comision: ");
    int codigoInstancia = leerEntero("Codigo de instancia: ");
    Turnero turnero = gestion.obtenerTurnero(
        codigoComision, codigoInstancia
    );

    if (turnero == null) {
        System.out.println("No se encontro el turnero.");
        return;
    }
    if (turnero.getListaTurno().isEmpty()) {
        System.out.println("Esta instancia no tiene turnos cargados.");
        return;
    }

    int opcion;

    do {
        Turno turnoActual = turnero.iniciarOContinuar();

        if (turnoActual == null) {
            System.out.println("Evaluacion finalizada.");
            break;
        }

        System.out.println("\nTURNO ACTUAL");
        System.out.println("Numero: " + turnoActual.getNumero());
        System.out.println("Horario: " + turnoActual.getFechaHora());
        if (turnoActual.getAlumno() != null) {

            System.out.println(
                "Alumno: " + turnoActual.getAlumno().getNombreCompleto()
                    + " (legajo: " + turnoActual.getAlumno().getLegajo() + ")"
            );

        } else if (turnoActual.getGrupo() != null) {

            System.out.println(
                "Grupo: " + turnoActual.getGrupo().getNumero()
            );
            System.out.println("Integrantes:");
            for (Alumno alumno : turnoActual.getGrupo().getlistaAlumnos()) {
                System.out.println("- " + alumno.getNombreCompleto());
            }

        } else if (turnoActual.getComision() != null) {
            System.out.println("Comision: " + turnoActual.getComision().getNombre());
        }

        System.out.println("\nMENU EVALUACION");
        System.out.println("1. Finalizar turno y continuar");
        System.out.println("2. Marcar como ausente");
        System.out.println("0. Volver");

        opcion = leerEntero("Opcion: ");

        switch (opcion) {

            case 1:
                gestion.siguienteTurno(
                    codigoComision,
                    codigoInstancia
                );
                break;

            case 2:
                gestion.ponerAusente(codigoComision, codigoInstancia);
                break;

            case 0:
                System.out.println("Volviendo al menu.");
                break;

            default:
                System.out.println("Opcion incorrecta.");
        }

    } while (opcion != 0);
}
    static void cambiarOrdenTurnos(GestionTurnero gestion) {

        System.out.println("\nCAMBIAR ORDEN DE TURNOS");
        System.out.println("\nCodigo de comision: ");
        int codigoComision = leerEntero("Codigo de comision: ");
        System.out.println("\nCodigo de instancia: ");
        int codigoInstancia = leerEntero("Codigo de instancia: ");
        System.out.println("\nCodigo de turno: ");
        Turnero turnero = gestion.obtenerTurnero(codigoComision, codigoInstancia);
        if (turnero == null || turnero.getListaTurno().isEmpty()) {
            System.out.println("No hay turnos para mostrar.");
            return;
        }

        for (int i = 0; i < turnero.getListaTurno().size(); i++) {
            Turno turno = turnero.getListaTurno().get(i);
            String participante;
            if (turno.getAlumno() != null) {
                participante = turno.getAlumno().getNombreCompleto();
            } else if (turno.getGrupo() != null) {
                participante = "Grupo " + turno.getGrupo().getNumero();
            } else {
                participante = turno.getComision().getNombre();
            }
            System.out.println("Posicion " + (i + 1) + " | Turno " + turno.getNumero()
                    + " | " + participante + " | " + turno.getEstado());
        }

        int codigoTurno = leerEntero("Numero de turno: ");
        System.out.println("\nNueva posicion: ");
        int nuevaPosicion = leerEntero("Nueva posicion: ");

        gestion.cambiarOrdenTurno(codigoComision, codigoInstancia, codigoTurno, nuevaPosicion - 1);

    }
}

