
package Views;

import java.util.Scanner;
import Controllers.GestionTurnero;
import Models.Docente;
import Models.Estado;
import Models.Turno;
import Models.Materia;



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

    // Lee un numero entero por teclado.
    // Se usa en los menus y cuando hay que ingresar un codigo,
    // legajo o cantidad. Si se escriben letras, vuelve a pedir
    // el numero en lugar de cortar el programa.
    // Este es el metodo que estaba arriba y que seguro lo vieron y dijeron "que hace esto?"
    // ahora ya saben, lee un numero entero y lo devuelve al metodo que lo pidio.
    // Lean, no sean cabeza de tupper.
    static int leerEntero(String mensaje) {

        while (true) {
            System.out.print(mensaje);

            try {
                return Integer.parseInt(teclado.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Ingresa un numero valido.");
            }
        }
    }

    // Lee un texto y lo devuelve al metodo que lo pidio.
    // Sirve para cargar nombres, fechas y otros datos
    // que se ingresan por teclado.
    // Y seguro se preguntaran... para que? La respuesta esta en sus corazones.
    // y aca abajo, es para no hacer mil metodos que pidan texto,
    // en mi cabeza compila pero habria que probar que ande.
    static String leerTexto(String mensaje) {

        System.out.print(mensaje);
        return teclado.nextLine();
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

         if (gestion.tieneDocenteRegistrado()) {
             return;
            }
        
        System.out.println("\nREGISTRO DEL DOCENTE");
        
        String nombre = leerTexto("Nombre: ");
        String apellido = leerTexto("Apellido: ");
        String legajo = leerTexto("Legajo: ");
        
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

    // Pide los datos para crear una comision nueva.
    // La comision tiene que quedar asociada al docente
    // y a la materia que se esta dictando, porque un docente tiene mas de una comision
    // y mas de una materia.
    static void crearComision(GestionTurnero gestion) {

        int codigo = leerEntero("Codigo de comision: ");
        String nombre = leerTexto("Nombre: ");
        String materia = leerTexto("Materia: ");

        // Falta en GestionTurnero:
        // void crearComision(int codigo, String nombre, String materia)
        //
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

        /*VER--> Obtengo el docente actual del gestor y creo la materia
         */
         Docente docenteActual = gestion.getDocenteActual();
         Materia nuevaMateria = new Materia(materia, docenteActual);
         gestion.crearComision(codigo, nombre, nuevaMateria);
    }

    // Muestra las comisiones que tiene cargadas el docente.
    // Sirve para consultar los codigos y saber con cual
    // comision quiere trabajar.
    static void listarComisiones(GestionTurnero gestion) {

        // Falta en GestionTurnero:
        // void listarComisiones()
        //
        // Tiene que recorrer las comisiones del docente
        // y mostrar el codigo, nombre y materia de cada una.
        // Para hacerlo necesita que Docente tenga una lista
        // de comisiones y un metodo para consultarla.
        //
        // Cuando este implementado, se descomenta. LISTO

        gestion.listarComisiones();
    }

    // Muestra las opciones para administrar los alumnos.
    // Primero pide la comision para que todas las operaciones
    // se hagan sobre la lista de alumnos que corresponde.
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

    // Pide el legajo del alumno que se quiere agregar.
    // Ese alumno tiene que quedar dentro de la comision
    // seleccionada, no en una lista general.
    static void agregarAlumno(GestionTurnero gestion, int codigo) {

        int legajo = leerEntero("Legajo: ");

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

        // Falta en GestionTurnero:
        // void listarAlumnos(int codigoComision)
        //
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

        int legajo = leerEntero("Legajo: ");

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

        int legajo = leerEntero("Legajo: ");

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

        int codigo = leerEntero("Codigo de comision: ");
        int opcion;

        do {
            System.out.println("\nGRUPOS");
            System.out.println("1. Crear grupo");
            System.out.println("2. Agregar alumno a grupo");
            System.out.println("3. Quitar alumno de grupo");
            System.out.println("4. Ver grupos");
            System.out.println("0. Volver");

            opcion = leerEntero("Opcion: ");

            switch (opcion) {
                case 1:
                    crearGrupo(gestion, codigo);
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

                case 0:
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
            }

        } while (opcion != 0);
    }

    // Crea un grupo dentro de la comision seleccionada.
    // Pide el numero de grupo y el cupo maximo de integrantes.
    static void crearGrupo(GestionTurnero gestion, int codigo) {

        int numero = leerEntero("Numero de grupo: ");
        int cupo = leerEntero("Cupo maximo: ");

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

        int numero = leerEntero("Numero de grupo: ");
        int cupo = leerEntero("Nuevo cupo maximo: ");

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

        int numero = leerEntero("Numero de grupo: ");
        int legajo = leerEntero("Legajo: ");

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

        int numero = leerEntero("Numero de grupo: ");
        int legajo = leerEntero("Legajo: ");

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

        int codigo = leerEntero("Codigo de comision: ");
        int legajo = leerEntero("Legajo del alumno: ");

        System.out.println("1. Dar prioridad");
        System.out.println("2. Quitar prioridad");

        int opcion = leerEntero("Opcion: ");

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
        // Cuando esten esos metodos, se descomenta el bloque.

        // if (opcion == 1) {
        //     gestion.cambiarPrioridad(codigo, legajo, true);
        // } else if (opcion == 2) {
        //     gestion.cambiarPrioridad(codigo, legajo, false);
        // } else {
        //     System.out.println("Opcion incorrecta.");
        // }
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
    static void crearInstancia(GestionTurnero gestion) {

        int comision = leerEntero("Codigo de comision: ");

        System.out.println("1. Defensa parcial");
        System.out.println("2. Examen final");
        System.out.println("3. Presentacion TPI");

        int tipo = leerEntero("Tipo: ");

        System.out.println("1. Individual");
        System.out.println("2. Grupal");
        System.out.println("3. General");

        int modalidad = leerEntero("Modalidad: ");

        String fecha = leerTexto("Fecha AAAA-MM-DD: ");
        String hora = leerTexto("Hora HH:MM: ");
        int minutos = leerEntero("Minutos por turno: ");

        // Falta en GestionTurnero:
        // void crearInstanciaEvaluativa(int comision, int tipo,
        //     int modalidad, String fecha, String hora, int minutos)
        //
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
        // int contarAlumnosPrioritarios()
        //
        // Tiene que recorrer sus integrantes, consultar
        // la prioridad de cada Alumno y devolver cuantos
        // tienen prioridad.
        //
        // Falta en Turnero:
        // void ordenarPorPrioridad()
        //
        // Tiene que poner primero los alumnos prioritarios.
        // En las evaluaciones grupales, los grupos con mas
        // integrantes prioritarios tienen que ir primero.
        //
        // Tambien falta en Turnero:
        // void calcularHorarios()
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
        //
        // Cuando este hecho, se descomenta.

        // gestion.modificarFechaInstancia(comision, instancia, fecha, hora);
        
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

        // gestion.modificarHoraInstancia(comision, instancia, hora);
        
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
        // Cuando este hecho, se descomenta.

        // gestion.listarInstanciasEvaluativas(codigo);
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

        int comision = leerEntero("Codigo de comision: ");
        int instancia = leerEntero("Codigo de instancia: ");

        // Este bloque queda comentado porque faltan los
        // metodos para encontrar la instancia, guardar la nota
        // y cambiar manualmente el estado de cada turno.
        //
        // Falta en GestionTurnero:
        // Turnero obtenerTurnero(int codigoComision, int codigoInstancia)
        //
        // Tiene que buscar la instancia de esa comision
        // y devolver el Turnero que le corresponde.
        //
        // Falta en Turno:
        // void setEstado(Estado estado)
        //
        // Tiene que cambiar el estado a Finalizado o Ausente
        // segun lo que indique el docente.
        //
        // Tambien falta definir donde se guarda la nota.
        // Si se guarda en Turno, hace falta:
        // void setNota(double nota)
        //
        // Turnero.siguienteTurno() ya existe.
        // Devuelve el primer turno en Evaluando o null
        // cuando ya no encuentra ninguno.
        //
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

        int comision = leerEntero("Codigo de comision: ");
        int instancia = leerEntero("Codigo de instancia: ");
        int turno = leerEntero("Codigo de turno: ");
        int posicion = leerEntero("Nueva posicion: ");

        // Falta en GestionTurnero:
        // void cambiarOrdenTurno(int comision, int instancia,
        //     int turno, int posicion)
        //
        // Tiene que encontrar el Turnero de esa instancia
        // y el turno que se quiere mover.
        //
        // Despues tiene que llamar a cambiarOrden(Turno, int),
        // que ya existe en Turnero.
        //
        // Tambien tiene que comprobar que la posicion
        // ingresada sea valida para no generar un error.
        //
        // Despues de mover el turno tiene que recalcular
        // los horarios estimados, porque cambia el orden.
        //
        // Cuando este hecho, se descomenta.

        // gestion.cambiarOrdenTurno(
        //     comision, instancia, turno, posicion);
    }
}

