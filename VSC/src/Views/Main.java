
package Views;
// import 
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import Controllers.GestionTurnero;
import Models.Comision;
import Models.DefensaParcial;
import Models.Docente;
import Models.Estado;
import Models.ExamenFinal;
import Models.Turno;
import Models.Materia;
import Models.PresentacionTPI;
import Models.Turnero;
import Models.Turno;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

// Main

public class Main {


    

    static Scanner teclado = new Scanner(System.in);


    // VOLVI A PONER Esto q no se potque lo borraron
    static int leerEntero(String mensaje) {
        while (true) {
        System.out.print(mensaje);

            try {
                return Integer.parseInt(teclado.next());
        }       catch (NumberFormatException e) {
            System.out.println("Ingrese un numero entero valido.");
        }
    }
}

    static String leerTexto(String mensaje) {
    System.out.print(mensaje);
    return teclado.next();
}

    // Arranca el programa y crea el objeto que va a manejar
    // los datos del turnero. Despues muestra el menu principal
    // y lo mantiene abierto hasta que el docente elija salir.
    //los metodos que vean aca y que no esten explicados, y que NO sean de otra clase, 
    //estan explicados en el mismo metodo, arriba de la linea donde se llama.
    public static void main(String[] args) {





        GestionTurnero gestion = new GestionTurnero();

        // Falta conectar el registro del docente.
        // Los metodos que necesita estan explicados en registrarDocente().
        // Cuando esten hechos, se descomenta esta llamada.
        registrarDocente(gestion);

        int opcion;
        do {
            System.out.println("\nTURNERO");
            System.out.println("1. Comisiones");
            System.out.println("2. Instancias evaluativas");
            System.out.println("0. Salir");

            opcion = teclado.nextInt();
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

    // Se ejecuta al arrancar el programa para comprobar
    // si hay un docente registrado. Si no hay ninguno,
    // pide nombre, apellido y legajo para darlo de alta.
    // todavia no lo vimos pero tenemos que meterle algo donde guardar esos datos 
    // sino es un pijaso tener que cargar todo cada vez que se arranca el programa.
    

static void registrarDocente(GestionTurnero gestion) {

    // los metodos de gestionturnero ya estan implementados.
    if (!gestion.tieneDocenteRegistrado()) {

        System.out.println("\nREGISTRO DEL DOCENTE");

        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String legajo = leerTexto("Legajo: ");

        gestion.registrarDocente(nombre, apellido, legajo);

        System.out.println("Docente registrado correctamente.");
    }
}



    // Muestra las opciones para trabajar con las comisiones.
    // Desde aca el docente puede crear comisiones, consultar
    // las que tiene y administrar sus alumnos y grupos.
    // Cada opcion llama al metodo del Main que corresponde.
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

            opcion = teclado.nextInt();

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

    // Pide los datos para crear una comision nueva.
    // La comision tiene que quedar asociada al docente
    // y a la materia que se esta dictando, porque un docente tiene mas de una comision
    // y mas de una materia.
    static void crearComision(GestionTurnero gestion) {
        System.out.println("\nCodigo\nNombre\nMateria");
        int codigo = teclado.nextInt();
        String nombre = teclado.nextLine();
        String materia = teclado.nextLine();
        
        // Tiene que crear la comision y agregarla a las que
        // administra el docente. Tambien tiene que asociarle
        // la materia.
        //
        // Para eso falta que Comision tenga una referencia
        // a Materia y que Docente pueda guardar sus comisiones.
        // Materia ya existe, no hace falta crear otra clase.
        //
        // Cuando esten esas relaciones y el metodo de
        // GestionTurnero, se descomenta la llamada. LISTO

         Docente docenteActual = gestion.getDocenteActual();
         Materia nuevaMateria = new Materia(materia, docenteActual);
         gestion.crearComision(codigo, nombre, nuevaMateria);
    }

    // Muestra las comisiones que tiene cargadas el docente.
    // Sirve para consultar los codigos y saber con cual
    // comision quiere trabajar.
    static void listarComisiones(GestionTurnero gestion) {

        // Tiene que recorrer las comisiones del docente
        // y mostrar el codigo, nombre y materia de cada una.
        // Para hacerlo necesita que Docente tenga una lista
        // de comisiones y un metodo para consultarla.
        //
        gestion.listarComisiones();
    }

    // Muestra las opciones para administrar los alumnos.
    // Primero pide la comision para que todas las operaciones
    // se hagan sobre la lista de alumnos que corresponde.
    static void menuAlumnos(GestionTurnero gestion) {

        int codigo = teclado.nextInt();
        int opcion;

        do {
            System.out.println("\nALUMNOS");
            System.out.println("1. Agregar alumno");
            System.out.println("2. Ver alumnos");
            System.out.println("3. Buscar alumno");
            System.out.println("4. Dar de baja alumno");
            System.out.println("0. Volver");

            opcion = teclado.nextInt();

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

    // Pide el legajo del alumno que se quiere agregar.
    // Ese alumno tiene que quedar dentro de la comision
    // seleccionada, no en una lista general.
    static void agregarAlumno(GestionTurnero gestion, int codigo) {

        int legajo = teclado.nextInt();

        // Falta en GestionTurnero:
        // void agregarAlumno(int codigoComision, int legajo)
        //
        // Tiene que buscar la comision por su codigo,
        // crear el Alumno y agregarlo a esa comision.
        //
        // Comision ya tiene agregarAlumno(Alumno), asi que
        // hay que usar ese metodo y no hacerlo de nuevo.
        // Tambien hay que controlar que no se repita el legajo.
        //
        // Cuando este hecho, se descomenta.LISTO

         gestion.agregarAlumno(codigo, legajo);
    }

    // Muestra todos los alumnos de la comision elegida.
    // Sirve para consultar quienes estan cargados antes
    // de armar los grupos o generar una evaluacion.
    static void listarAlumnos(GestionTurnero gestion, int codigo) {

        
        // Tiene que buscar la comision y recorrer los alumnos
        // que devuelve Comision.getListaAlumnos(), que ya existe.
        // Tiene que mostrar por lo menos el legajo de cada uno.
        //
        // Cuando este hecho, se descomenta.LISTO

         gestion.listarAlumnos(codigo);
    }

    // Busca un alumno por legajo dentro de una comision.
    // Sirve para comprobar si esta cargado y consultar
    // sus datos sin tener que recorrer la lista a mano.
    static void buscarAlumno(GestionTurnero gestion, int codigo) {

        int legajo = teclado.nextInt();

        // Falta en GestionTurnero:
        // void buscarAlumno(int codigoComision, int legajo)
        //
        // Tiene que encontrar la comision y usar
        // Comision.buscarAlumnoPorLegajo(), que ya existe.
        // Despues muestra el alumno o avisa si no lo encontro.
        //
        // Cuando este hecho, se descomenta. LISTO

         gestion.buscarAlumno(codigo, legajo);
    }

    // Da de baja un alumno de la comision seleccionada.
    // Pide el legajo para identificar a quien hay que sacar.
    static void darDeBajaAlumno(GestionTurnero gestion, int codigo) {

        int legajo = teclado.nextInt();

        // Falta en GestionTurnero:
        // void darDeBajaAlumno(int codigoComision, int legajo)
        //
        // Tiene que buscar la comision y llamar a
        // Comision.darDeBajaAlumno(), que ya existe.
        // Tiene que avisar si se elimino o si no estaba cargado.
        //
        // Cuando este hecho, se descomenta.LISTO

         gestion.darDeBajaAlumno(codigo, legajo);
    }

    // Muestra las opciones para trabajar con los grupos.
    // Primero pide la comision porque los grupos se arman
    // con alumnos que ya pertenecen a esa comision.
    static void menuGrupos(GestionTurnero gestion) {

        int codigo = teclado.nextInt();
        int opcion;

        do {
            System.out.println("\nGRUPOS");
            System.out.println("1. Crear grupo");
            System.out.println("2. Agregar alumno a grupo");
            System.out.println("3. Quitar alumno de grupo");
            System.out.println("4. Ver grupos");
            System.out.println("5. Cambiar cupo maximo de grupo");
            System.out.println("0. Volver");

            opcion = teclado.nextInt();

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

    // Crea un grupo dentro de la comision seleccionada.
    // Pide el numero de grupo y el cupo maximo de integrantes.
    static void crearGrupo(GestionTurnero gestion) {
        System.out.println("\nCREAR GRUPO");
        System.out.println("\nCodigo de comision: ");
        int codigo = teclado.nextInt();
        System.out.println("\nNumero de grupo: ");
        int numero = teclado.nextInt();
        System.out.println("\nCupo maximo: ");
        int cupo = teclado.nextInt();

        // Falta en Comision:
        // void agregarGrupo(Grupo grupo)
        //
        // Comision tiene que guardar sus grupos en una lista.
        // El metodo tiene que agregar el grupo a esa lista
        // y evitar que haya dos grupos con el mismo numero.
        //
        // Tambien falta en GestionTurnero:
        // void crearGrupo(int codigoComision, int numero, int cupo)
        //
        // Tiene que encontrar la comision, crear el Grupo
        // y llamar a Comision.agregarGrupo().
        // Grupo ya tiene el constructor con numero y cupo.
        // FALTA UN METODO PARA MODIFICAR EL CUPO POR SI SE EQUIVOCA AL CARGARLO,
        //  O SI NECESITA HACERLO PORQUE DEJA JULI ESE METODO VA EN GESTION TURNERO.
        //
        // Cuando esten los dos metodos, se descomenta. LISTO

         gestion.crearGrupo(codigo, numero, cupo);
    }

    
    // Este metodo pide el numero del grupo y el nuevo cupo maximo.
    // Sirve para que el docente pueda cambiar la cantidad de
    // integrantes permitidos en un grupo de la comision elegida.
    // El Main solamente pide los datos, no modifica el grupo segun el ceo.
    static void cambiarCupoMaximo(GestionTurnero gestion, int codigo) {
        System.out.println("\nCAMBIAR CUPO MAXIMO DE GRUPO");
        System.out.println("\nCodigo de Grupo: ");
        int numero = teclado.nextInt();
        System.out.println("\nNuevo cupo maximo: ");
        int cupo = teclado.nextInt();

        // Falta implementar en la clase GestionTurnero:
        // void cambiarCupoMaximo(int codigoComision, int numeroGrupo, int nuevoCupo)
        //
        // Este metodo tiene que buscar la comision por su codigo
        // y despues buscar el grupo por su numero.
        // Si encuentra el grupo, tiene que llamar al metodo
        // setCupoMaximo() de la clase Grupo, que ya existe.
        //
        // Antes de cambiarlo, tiene que comprobar que el nuevo
        // cupo sea mayor que cero y que no sea menor a la cantidad
        // de alumnos que ya tiene cargados el grupo porque sino para que le van a cambiar el cupo.
        //
        // Si la comision o el grupo no existen, tiene que avisarlo.
        // Cuando el metodo este implementado en GestionTurnero,
        // se puede descomentar esta llamada. LISTO

         gestion.cambiarCupoMaximo(codigo, numero, cupo);
    }


    // Agrega un alumno a un grupo de la comision.
    // El alumno ya tiene que estar cargado en la comision obviamente
    // y el grupo tiene que tener lugar disponible obviamente 2.
    static void agregarAlumnoAGrupo(GestionTurnero gestion, int codigo) {
        System.out.println("\nAGREGAR ALUMNO A GRUPO");
        System.out.println("\nCodigo de grupo: ");
        int numero = teclado.nextInt();
        System.out.println("\nLegajo del alumno: ");
        int legajo = teclado.nextInt();

        // Falta en GestionTurnero:
        // void agregarAlumnoAGrupo(int codigo, int numero, int legajo)
        //
        // Tiene que buscar la comision, el grupo y el alumno.
        // Despues llama a Grupo.agregarAlumno(), que ya existe cre, o deberia, ya no recuerdo
        // Fijense.
        //
        // Grupo ya controla el cupo y que no se agregue
        // dos veces el mismo objeto Alumno.
        // GestionTurnero tiene que avisar si no encontro
        // el grupo o si el alumno no pertenece a la comision.
        //
        // Cuando este hecho, se descomenta. Listo

         gestion.agregarAlumnoAGrupo(codigo, numero, legajo);
    }

    // Saca un alumno de un grupo.
    // Solamente lo saca del grupo, no de la comision.
    // Sirve para cambiar los integrantes sin dar de baja alumnos.
    static void quitarAlumnoDeGrupo(GestionTurnero gestion, int codigo) {
        System.out.println("\nQUITAR ALUMNO DE GRUPO");
        System.out.println("\n Codigo de grupo: ");
        int numero = teclado.nextInt();
        System.out.println("\n Legajo del alumno: ");
        int legajo = teclado.nextInt();

        // Falta en GestionTurnero:
        // void quitarAlumnoDeGrupo(int codigo, int numero, int legajo)
        //
        // Tiene que encontrar el grupo y el alumno
        // y llamar a Grupo.quitarAlumno(), que ya existe.
        // Si el alumno no esta en el grupo, tiene que avisarlo.
        //
        // Cuando este hecho, se descomenta.LISTO

         gestion.quitarAlumnoDeGrupo(codigo, numero, legajo);
    }

    // Muestra los grupos de una comision y sus integrantes.
    // Sirve para revisar como quedaron armados antes
    // de generar los turnos de una presentacion grupal.
    static void listarGrupos(GestionTurnero gestion, int codigo) {

        // Falta en Comision:
        // getListaGrupos() LISTO
        //
        // Tiene que devolver la lista de grupos de esa comision.
        //
        // Tambien falta en GestionTurnero:
        // void listarGrupos(int codigoComision)
        //
        // Tiene que buscar la comision, recorrer sus grupos
        // y mostrar el numero y los integrantes de cada uno.
        // Grupo ya tiene getNumero() y getlistaAlumnos().
        //
        // Cuando este hecho, se descomenta. LISTO

         gestion.listarGrupos(codigo);
    }

    // Permite darle o sacarle la prioridad a un alumno.
    // Esa prioridad queda guardada en el alumno para que
    // se tenga en cuenta cada vez que se armen nuevos turnos.
    // No es lo mismo que mover un turno manualmente.
    static void administrarPrioridades(GestionTurnero gestion) {
        System.out.println("\nADMINISTRAR PRIORIDADES DE ALUMNOS");
        System.out.println("\nCodigo de comision: ");
        int codigo = teclado.nextInt();
        System.out.println("\nLegajo del alumno: ");
        int legajo = teclado.nextInt();

        System.out.println("1. Dar prioridad");
        System.out.println("2. Quitar prioridad");

        int opcion = teclado.nextInt();

        // Falta en Alumno:
        // void setPrioridad(boolean prioridad)
        // boolean getPrioridad()
        //
        // El primero tiene que guardar si el alumno tiene
        // prioridad y el segundo tiene que devolver ese dato.
        //
        // Tambien falta en GestionTurnero:
        // void cambiarPrioridad(int codigo, int legajo, boolean prioridad)
        //
        // Tiene que buscar al alumno de esa comision y cambiar
        // su prioridad usando el metodo de Alumno.
        // Despues Turnero tiene que usar ese dato para ordenar
        // los turnos cuando se cree una instancia evaluativa.
        //
        // Cuando esten esos metodos, se descomenta el bloque. LISTO

         if (opcion == 1) {
             gestion.cambiarPrioridad(codigo, legajo, true);
         } else if (opcion == 2) {
             gestion.cambiarPrioridad(codigo, legajo, false);
         } else {
             System.out.println("Opcion incorrecta.");
         }
    }

    // Muestra las opciones para trabajar con las evaluaciones.
    // Permite crear una instancia, ver las que hay cargadas,
    // iniciar una evaluacion y cambiar el orden de sus turnos.
    
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


    // Pide los datos para programar una instancia evaluativa.
    // Primero se elige la comision y el tipo de examen.
    // Despues se carga la modalidad, fecha, hora de inicio
    // y cuanto tiempo se estima para cada turno.

    















































    // falta de aca para abajo cracks





/*  FALTANTES:
    1-Crear instancia de defensa parcial
    2-Crear instancia de examen final
    3-Crear instancia de presentacion TPI
    4-Crear comision. Diferenciar entre Alumno y Grupos
    5-Modificar la ejecucion de la evaluacion
    6- 
    
*/






    
    static void crearInstancia(GestionTurnero gestion) {
        System.out.println("\nCREAR INSTANCIA EVALUATIVA");
        System.out.println("\nCodigo de comision: ");
        int comision = teclado.nextInt();

static void crearInstancia(GestionTurnero gestion) {

    System.out.println("\nCREAR INSTANCIA EVALUATIVA");

    // aca buscamos la comision donde se realizara la evaluacion.
    int codigoComision = leerEntero("Codigo de comision: ");

    Comision comisionEncontrada =
        gestion.buscarComisionPorCodigo(codigoComision);

    if (comisionEncontrada == null) {
        System.out.println("No se encontro la comision.");
        return;
    }

    // aca seleccionamos el tipo de instancia evaluativa.
    System.out.println("\n1. Defensa parcial");
    System.out.println("2. Examen final");
    System.out.println("3. Presentacion TPI");

    int tipo = leerEntero("Tipo de instancia: ");

    if (tipo < 1 || tipo > 3) {
        System.out.println("Tipo de instancia no valido.");
        return;
    }

    // aca seleccionamos la modalidad de la evaluacion.
    System.out.println("\nMODALIDAD DE EVALUACION");
    System.out.println("1. Individual");
    System.out.println("2. Grupal");
    System.out.println("3. General");

    int modalidad = leerEntero("Modalidad: ");

    if (modalidad < 1 || modalidad > 3) {
        System.out.println("Modalidad no valida.");
        return;
    }

    // aca pedimos los datos necesarios para crear la instancia q no se porque estan en string asi q despues diganme.
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

        if (duracionEstimada <= 0) {
            System.out.println("La duracion debe ser mayor a cero.");
            return;
        }

    } catch (java.time.format.DateTimeParseException |
             NumberFormatException e) {
        System.out.println("Fecha, hora o duracion no valida.");
        return;
    }

    codigoDeInstancia = leerEntero("Codigo de instancia: ");

    // aca recuperamos el docente que esta registrado.
    Docente docente = gestion.getDocenteActual();

    if (docente == null) {
        System.out.println("No hay un docente registrado.");
        return;
    }

    // aca creamos el turnero que se asociara a la instancia.
    Turnero turnero = new Turnero();

    // PENDIENTE GESTIONTURNERO:
    // Los metodos de creacion no reciben la modalidad.
    // hay q adaptarlos para generar los turnos 
    // Individual: un turno por alumno.
    // Grupal: un turno por grupo.
    // General: un turno para toda la comision.
    //
    // Por ahora, Main pide la modalidad, pero no puede
    // enviarla a gestionturnero.

    switch (tipo) {

        case 1:
            gestion.crearDefensaParcial(
                comisionEncontrada,
                fecha,
                hora,
                duracionEstimada,
                codigoDeInstancia,
                docente,
                turnero
            );
            break;

        case 2:
            gestion.crearExamenFinal(
                comisionEncontrada,
                fecha,
                hora,
                duracionEstimada,
                codigoDeInstancia,
                docente,
                turnero
            );
            break;

        case 3:
            // PENDIENTE EN PRESENTACIONTPI:
            // Completar los getters que lanzan UnsupportedOperationException.
            gestion.crearPresentacionTPI(
                comisionEncontrada,
                fecha,
                hora,
                duracionEstimada,
                codigoDeInstancia,
                docente,
                turnero
            );
            break;
    }

    System.out.println("Instancia evaluativa registrada.");
    System.out.println(
        "Modalidad seleccionada: " +
        (modalidad == 1 ? "Individual" :
         modalidad == 2 ? "Grupal" : "General")
    );

    // PENDIENTE:
    // La modalidad se muestra, pero no queda guardada, ni se utiliza para generar los turnos.
    // 
    // Turnero.calcularHorarios() tambien esta incompleto.
}




        
        // Tiene que buscar la comision y crear la instancia
        // que corresponda al tipo de examen seleccionado.
        // Las clases DefensaParcial, ExamenFinal y PresentacionTPI
        // ya existen asi que no las hagan potra vez que se me enoja el CEO.
        //
        // Tambien tiene que generar el Turnero de esa instancia.
        // Para una evaluacion individual se crea un turno
        // por alumno. Para una grupal, un turno por grupo.
        // Para una general, u tnurno para toda la comision.
        //
        // Falta adaptar Turno para que pueda representar
        // un alumno, un grupo o una comision completa.
        // Actualmente solamente recibe un Alumno.
        //
        // Falta en Grupo:
        // int contarAlumnosPrioritarios(). LISTO
        //
        // Tiene que recorrer sus integrantes, consultar
        // la prioridad de cada Alumno y devolver cuantos
        // tienen prioridad.
        //
        // Falta en Turnero:
        // void ordenarPorPrioridad().LISTO 
        //
        // Tiene que poner primero los alumnos prioritarios.
        // En las evaluaciones grupales, los grupos con mas
        // integrantes prioritarios tienen que ir primero.
        //
        // Tambien falta en Turnero:
        // void calcularHorarios().FALTA
        //
        // Tiene que calcular la hora estimada de cada turno
        // usando la hora de inicio y los minutos configurados.
        // Tambien tiene que calcular cuando termina la evaluacion.
        // por eso hay que diferenciar las instancias individuales, grupales y generales.
        //
        // Cuando esten esas funciones, se puede descomentar
        // la creacion de la instancia.

        // gestion.crearInstanciaEvaluativa(
        //     comision, tipo, modalidad, fecha, hora, minutos);
    

    //FALTA UN METODO PARA MODIFICAR LA FECHA Y HORA DE UNA INSTANCIA, 
    // POR SI SE EQUIVOCA AL CARGARLA O SI NECESITA HACERLO PORQUE DEJA JULI.
    // aca solo llamo los metodos,

    
static void modificarFechaInstancia(GestionTurnero gestion) {

    System.out.println("\nMODIFICAR FECHA DE INSTANCIA");

    int codigoComision = leerEntero("Codigo de comision: ");
    int codigoInstancia = leerEntero("Codigo de instancia: ");

    String nuevaFecha = leerTexto("Nueva fecha (AAAA-MM-DD): ");

    try {
        LocalDate.parse(nuevaFecha);

        // Este metodo ya existe en GestionTurnero.
        gestion.modificarFechaInstancia(
            codigoComision,
            codigoInstancia,
            nuevaFecha
        );

    } catch (java.time.format.DateTimeParseException e) {
        System.out.println("Formato de fecha no valido.");
    }

    // PENDIENTE:
    // Revisar la busqueda de instancias mediante getCodigo(),
    // porque no coincide con codigoDeInstancia.
}


    
static void modificarHoraInstancia(GestionTurnero gestion) {

    System.out.println("\nMODIFICAR HORA DE INSTANCIA");

    int codigoComision = leerEntero("Codigo de comision: ");
    int codigoInstancia = leerEntero("Codigo de instancia: ");

    String nuevaHora = leerTexto("Nueva hora (HH:MM): ");

    try {
        LocalTime.parse(nuevaHora);

        // Este metodo ya existe en GestionTurnero.
        gestion.modificarHoraInstancia(
            codigoComision,
            codigoInstancia,
            nuevaHora
        );

    } catch (java.time.format.DateTimeParseException e) {
        System.out.println("Formato de hora no valido.");
    }
    

    // PENDIENTE:
    // Revisar la identificacion de instancias por codigo.
    // Al cambiar la hora tambien deberian recalcularse
    // los horarios de los turnos asociados.
}

    // Muestra las instancias evaluativas de una comision.
    // Sirve para consultar las evaluaciones que se programaron
    // y elegir cual se quiere ejecutar.
    static void listarInstancias(GestionTurnero gestion) {

        int codigo = leerEntero("Codigo de comision: ");

        // Falta en GestionTurnero:
        // void listarInstanciasEvaluativas(int codigoComision)
        //
        // Tiene que encontrar la comision y mostrar las
        // instancias que tiene asociadas, con su tipo,
        // fecha, hora y duracion estimada.
        //
        // Para eso falta que las instancias evaluativas
        // queden asociadas a la comision correspondiente.
        //
        // Cuando este hecho, se descomenta.LISTO

         gestion.listarInstanciasEvaluativas(codigo);
    }
    

    
static void ejecutarEvaluacion(GestionTurnero gestion) {

    System.out.println("\nEJECUTAR EVALUACION");

    int codigoComision = leerEntero("Codigo de comision: ");
    int codigoInstancia = leerEntero("Codigo de instancia: ");

    // Obtenemos el turnero asociado a la instancia.
    Turnero turnero = gestion.obtenerTurnero(
        codigoComision, codigoInstancia
    );

    if (turnero == null) {
        System.out.println("No se encontro el turnero.");
        return;
    }

    // aca comprobamos que existan turnos para evaluar.
    if (turnero.getListaTurno().isEmpty()) {
        System.out.println("Esta instancia no tiene turnos cargados.");
        return;
    }

    int opcion;

    do {
        // aca buscamos el primer turno que sigue en evaluacion.
        Turno turnoActual = turnero.getTurnoActual();

        if (turnoActual == null) {
            System.out.println("Evaluacion finalizada.");
            break;
        }

        System.out.println("\nTURNO ACTUAL");
        System.out.println("Numero: " + turnoActual.getNumero());

        // Identificamos a quien corresponde el turno.
        if (turnoActual.getAlumno() != null) {

            System.out.println(
                "Alumno: " + turnoActual.getAlumno().getLegajo()
            );

        } else if (turnoActual.getGrupo() != null) {

            System.out.println(
                "Grupo: " + turnoActual.getGrupo().getNumero()
            );

        } else {

            // PENDIENTE TURNO:
            // Incorporar getComision() para ver las evaluaciones generales.
            System.out.println("Evaluacion de comision completa.");
        }

        System.out.println("\nMENU EVALUACION");
        System.out.println("1. Finalizar turno y continuar");
        System.out.println("2. Marcar como ausente");
        System.out.println("0. Volver");

        opcion = leerEntero("Opcion: ");

        switch (opcion) {

            case 1:
                // PENDIENTE  GESTIONTURNERO:
                // Revisar siguienteTurno() para todas
                // las modalidades de evaluacion.
                gestion.siguienteTurno(
                    codigoComision,
                    codigoInstancia
                );
                break;

            case 2:
                // PENDIENTE GESTIONTURNERO:
                // ponerAusente() solamente contempla alumnos.
                // hay que adaptarse para grupos y comisiones.
                if (turnoActual.getAlumno() == null) {

                    System.out.println(
                        "Marcar ausente no esta disponible " +
                        "para turnos grupales o generales."
                    );

                } else {

                    gestion.ponerAusente(
                        codigoComision,
                        codigoInstancia
                    );
                }
                break;

            case 0:
                System.out.println("Volviendo al menu.");
                break;

            default:
                System.out.println("Opcion incorrecta.");
        }

    } while (opcion != 0);
}


    // Permite mover un turno a otra posicion de la lista.
    // Sirve cuando el docente necesita cambiar el orden
    // de una evaluacion en particular.
    //
    // Ese cambio no modifica el orden de la comision
    // ni la prioridad permanente de los alumnos.
    static void cambiarOrdenTurnos(GestionTurnero gestion) {

        System.out.println("\nCAMBIAR ORDEN DE TURNOS");
        System.out.println("\nCodigo de comision: ");
        int codigoComision = teclado.nextInt();
        System.out.println("\nCodigo de instancia: ");
        int codigoInstancia = teclado.nextInt();
        System.out.println("\nCodigo de turno: ");
        int codigoTurno = teclado.nextInt();
        System.out.println("\nNueva posicion: ");
        int nuevaPosicion = teclado.nextInt();

        gestion.cambiarOrdenTurno(codigoComision, codigoInstancia, codigoTurno, nuevaPosicion);

    }
}

