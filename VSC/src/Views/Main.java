
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

// Main

public class Main {

    static Scanner teclado = new Scanner(System.in);

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
        // registrarDocente(gestion);

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

        // Esta parte queda comentada porque faltan dos metodos
        // en GestionTurnero:
        //
        // boolean tieneDocenteRegistrado()
        // Tiene que devolver true si ya hay un docente cargado
        // y false si todavia no se registro ninguno. LISTO 
        //
        // void registrarDocente(String nombre, String apellido, String legajo)
        // Tiene que crear el objeto Docente y guardar la referencia
        // para poder usarlo mientras el programa este abierto. LISTO
        //
        // Docente ya tiene el constructor con esos tres datos.
        // Cuando esten hechos los metodos de GestionTurnero,
        // se descomenta este bloque.
        // ustedes se preguntaran, porque el if a continuacion no tiene un else.
        // bueno es porque solo entra al if si no esta registrado el decente.
        System.out.println("\nREGISTRO DEL DOCENTE");
                
        String nombre = teclado.nextLine();
        String apellido = teclado.nextLine();
        String legajo = teclado.nextLine();     
        
        
        gestion.registrarDocente(nombre, apellido, legajo);
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
            System.out.println("0. Volver");

            opcion = teclado.nextInt();

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

                case 0:
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
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

        // metodo para buscar la comision por codigo y devolverla, si no existe avisar.
        Comision comisionEncontrada = gestion.buscarComisionPorCodigo(comision);

        // con comision Encontrada podemos crear la instancia evaluativa, pero primero necesitamos que tipo de instancia es, fecha, hora y duracion estimada.
        System.out.println("1. Defensa parcial");
        System.out.println("2. Examen final");
        System.out.println("3. Presentacion TPI");
        System.out.println("Tipo de instancia: ");
        int tipo = teclado.nextInt();
        
        switch (tipo) {
            case 1:
                System.out.println("Fecha (AAAA-MM-DD): ");
                String fecha = teclado.next();
                System.out.println("Hora (HH:MM): ");
                String hora = teclado.next();
                System.out.println("Duracion estimada (en minutos): ");
                double duracionEstimada = teclado.nextDouble();
                System.out.println("Codigo de instancia: ");
                int codigoDeInstancia = teclado.nextInt();
                gestion.crearDefensaParcial (comisionEncontrada, fecha,  hora,  duracionEstimada, codigoDeInstancia, docente, turnero);
                break;
            case 2:
                System.out.println("Fecha (AAAA-MM-DD): ");
                fecha = teclado.next();
                System.out.println("Hora (HH:MM): ");
                hora = teclado.next();
                System.out.println("Duracion estimada (en minutos): ");
                duracionEstimada = teclado.nextDouble();
                System.out.println("Codigo de instancia: ");
                codigoDeInstancia = teclado.nextInt();
                System.out.println("Docente: ");
                String docente = teclado.next();
                System.out.println("Turnero");
                Turnero turnos = new Turnero();

                gestion.crearExamenFinal(comisionEncontrada,"Examen Final", fecha, hora, duracionEstimada, turnos, docente, codigoDeInstancia, turnero);
                break;
            case 3:
                System.out.println("Fecha (AAAA-MM-DD): ");
                fecha = teclado.next();
                System.out.println("Hora (HH:MM): ");
                hora = teclado.next();
                System.out.println("Duracion estimada (en minutos): ");
                duracionEstimada = teclado.nextDouble();
                System.out.println("Codigo de instancia: ");
                codigoDeInstancia = teclado.nextInt();
                System.out.println("Turnero");
                Turnero turnos = new Turnero();
                System.out.println("Codigo de instancia: ");
                codigoDeInstancia = teclado.nextInt();
                gestion.crearPresentacionTPI(comisionEncontrada, "Presentacion TPI", fecha, hora, duracionEstimada, turnos, turnero, codigoDeInstancia); 
                break;
            default:
                System.out.println("Tipo de instancia no válido.");
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
    }

    //FALTA UN METODO PARA MODIFICAR LA FECHA Y HORA DE UNA INSTANCIA, 
    // POR SI SE EQUIVOCA AL CARGARLA O SI NECESITA HACERLO PORQUE DEJA JULI.
    // aca solo llamo los metodos,

    static void modificarFechaInstancia(GestionTurnero gestion) {

        int comision = leerEntero("Codigo de comision: ");
        int instancia = leerEntero("Codigo de instancia: ");
        String fecha = leerTexto("Nueva fecha AAAA-MM-DD: ");
        String hora = leerTexto("Nueva hora HH:MM: ");

        // Falta en GestionTurnero:
        // void modificarFechaInstancia(int codigoComision, int codigoInstancia, String nuevaFecha, String nuevaHora)
        //
        // Tiene que buscar la comision y la instancia correspondiente
        // y cambiar la fecha y hora de esa instancia.
        //REVISAR
        // Cuando este hecho, se descomenta. LISTO(CREO)

        gestion.modificarFechaInstancia(comision, instancia, fecha, hora);
        
    }

    static void modificarHoraInstancia(GestionTurnero gestion) {

        int comision = leerEntero("Codigo de comision: ");
        int instancia = leerEntero("Codigo de instancia: ");
        String hora = leerTexto("Nueva hora HH:MM: ");

        // Falta en GestionTurnero:
        // void modificarHoraInstancia(int codigoComision, int codigoInstancia, String nuevaHora)
        //
        // Tiene que buscar la comision y la instancia correspondiente
        // y cambiar la hora de esa instancia.
        //
        // Cuando este hecho, se descomenta.
        //REVISAR
         gestion.modificarHoraInstancia(comision, instancia, hora);
        
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

    // Ejecuta una instancia evaluativa que ya esta creada.
    // Muestra el primer turno que siga en estado Evaluando.
    // Cuando el docente termina, marca Finalizado o Ausente.
    // Despues busca el siguiente turno que siga en Evaluando.
    //
    // No hace falta un estado Pendiente porque todos los
    // turnos empiezan en Evaluando y se van descartando
    // de la busqueda a medida que se terminan.
    //
    // Cuando siguienteTurno() devuelve null significa que
    // no queda nadie por evaluar y termina la instancia.
    static void ejecutarEvaluacion(GestionTurnero gestion) {

        System.out.println("\nEJECUTAR EVALUACION");
        System.out.println("\nCodigo de comision: ");
        int codigoComision = teclado.nextInt();
        System.out.println("\nCodigo de instancia: ");
        int codigoInstancia = teclado.nextInt();

        
        
        Turnero turnero = gestion.obtenerTurnero(codigoComision, codigoInstancia);

        if(turnero.getTurnoActual().getInstancia().getTipo() == "Defensa Parcial"){

            int tipoTurnero = 1;

        } else if(turnero.getTurnoActual().getInstancia().getTipo() == "Examen Final"){
            int tipoTurnero = 1;
            
        } else if(turnero.getTurnoActual().getInstancia().getTipo() == "Presentacion TPI"){

            int tipoTurnero = 2;
        }

        switch (tipoTurnero) {
            case 1:
                    System.out.println("\nMENU EVALUACION");
                    System.out.println("1. Siguiente turno");
                    System.out.println("2. Marcar como ausente");

                    int opcion = teclado.nextInt();

                    switch (opcion) {
                        case 1:
                        
                            gestion.siguienteTurno(codigoComision, codigoInstancia);

                            break;
                        
                        case 2:

                            gestion.ponerAusente(codigoComision, codigoInstancia);
                            break;

                        default:
                            break;
                    }
                break;

            case 2:
                
                    
                break;
        
            default:
                break;
        }

        System.out.println("\nMENU EVALUACION");
        System.out.println("1. Siguiente turno");
        System.out.println("2. Marcar como ausente");

        int opcion = teclado.nextInt();

        switch (opcion) {
            case 1:
               
                gestion.siguienteTurno(codigoComision, codigoInstancia);

                break;
            
            case 2:

                gestion.ponerAusente(codigoComision, codigoInstancia);
                break;

            default:
                break;
        }

        // Para evaluaciones grupales o generales tambien
        // falta poder mostrar el grupo o la comision,
        // porque actualmente Turno solo tiene getAlumno().
        //
        // Cuando esten esos metodos y las clases se puedan
        // importar correctamente, se descomenta el bloque.

        // Turnero turnero = gestion.obtenerTurnero(comision, instancia);
        // Turno turno = turnero.siguienteTurno();
        //
        // while (turno != null) {
        //
        //     System.out.println("Alumno: "
        //         + turno.getAlumno().getLegajo());
        //
        //     System.out.println("1. Finalizado");
        //     System.out.println("2. Ausente");
        //
        //     int opcion = leerEntero("Estado: ");
        //
        //     if (opcion == 1) {
        //         double nota = Double.parseDouble(
        //             leerTexto("Nota: "));
        //
        //         turno.setNota(nota);
        //         turno.setEstado(Estado.Finalizado);
        //
        //     } else if (opcion == 2) {
        //         turno.setEstado(Estado.Ausente);
        //
        //     } else {
        //         System.out.println("Opcion incorrecta.");
        //         continue;
        //     }
        //
        //     turno = turnero.siguienteTurno();
        // }
        //
        // System.out.println("Instancia evaluativa finalizada");
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

