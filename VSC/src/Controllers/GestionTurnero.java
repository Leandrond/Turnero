package Controllers;
import Dao.AlumnoDao;
import Dao.ComisionDao;
import Dao.DocenteDao;
import Dao.GrupoDao;
import Dao.InstanciaEvaluativaDao;
import Dao.TurnoDao;
import Models.Alumno;
import Models.Comision;
import Models.Docente;
import Models.Estado;
import Models.Grupo;
import Models.Instancias.DefensaParcial;
import Models.Instancias.ExamenFinal;
import Models.Instancias.InstanciaEvaluativa;
import Models.Instancias.PresentacionTPI;
import Models.Materia;
import Models.Modalidad;
import Models.Turnero;
import Models.Turno;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;

public class GestionTurnero {

    private ArrayList<Docente> listaDocentes;
    private ArrayList<Comision> listaComisiones;
    
    public GestionTurnero() {
        //System.out.println("Hola Gestion del Turnero");
        this.listaDocentes = new ArrayList<>();
        this.listaComisiones = new ArrayList<>();
    }

    //metodo para agregar un docente nuevo recibiendo sus datos
    public void registrarDocente(String nombre, String apellido, String legajo)
            throws SQLException {
        Docente nuevoDocente = new Docente(nombre, apellido, legajo);

        new DocenteDao().registrar(nuevoDocente);
        listaDocentes.add(nuevoDocente);
    }
    
    public Docente cargarDocentePorLegajo(String legajo) throws SQLException {
    Docente docente = new DocenteDao().buscarPorLegajo(legajo);

    if (docente != null) {
        listaDocentes.add(docente);
    }

    return docente;
    }

    public void cargarComisionesDelDocente() throws SQLException {
        Docente docente = getDocenteActual();

        if (docente == null) {
            throw new IllegalStateException("Primero debe cargarse un docente.");
        }

        ArrayList<Comision> comisiones =
            new ComisionDao().listarPorDocente(docente);

        listaComisiones.addAll(comisiones);
        docente.getListaComisiones().addAll(comisiones);
    }
    
    
    
    
    public void cargarAlumnosDeComisiones() throws SQLException {
        AlumnoDao alumnoDao = new AlumnoDao();

        for (Comision comision : listaComisiones) {
            comision.getListaAlumnos().addAll(
                alumnoDao.listarPorComision(comision.getCodigoComision())
            );
        }
    }
    
    
    public void cargarGruposDeComisiones() throws SQLException {
        GrupoDao grupoDao = new GrupoDao();

        for (Comision comision : listaComisiones) {
            ArrayList<Grupo> grupos =
                grupoDao.listarPorComision(comision.getCodigoComision());

            for (Grupo grupo : grupos) {
                ArrayList<Integer> legajos = grupoDao.listarLegajosIntegrantes(
                    comision.getCodigoComision(),
                    grupo.getNumero()
                );

                for (int legajo : legajos) {
                    Alumno alumno =
                        comision.buscarAlumnoPorLegajo(legajo);

                    if (alumno != null) {
                        grupo.agregarAlumno(alumno);
                    }
                }

                comision.agregarGrupo(grupo);
            }
        }
    }
    
    
    public void crearComision(int codigo, String nombre, Materia materia)
            throws SQLException {

        for (Comision comision : listaComisiones) {
            if (comision.getCodigoComision() == codigo) {
                System.out.println("Ya existe una comisión con ese código.");
                return;
            }
        }

        Docente docenteActual = getDocenteActual();
        if (docenteActual == null) {
            throw new IllegalStateException("No hay un docente cargado.");
        }

        Comision nuevaComision = new Comision(codigo, nombre, materia);

        new ComisionDao().registrarConMateria(
            nuevaComision,
            docenteActual.getLegajo()
        );

        listaComisiones.add(nuevaComision);
        docenteActual.agregarComision(nuevaComision);

        System.out.println("Comisión registrada correctamente.");
    }

    public Docente getDocenteActual() {
        if (!this.listaDocentes.isEmpty()) {
            return this.listaDocentes.get(0);
        }
        return null;
    }
    
    public boolean tieneDocenteRegistrado(){
        return !listaDocentes.isEmpty();
    }
    
    
    public void registroDocente(String nombre, String apellido, String legajo){
        if(!tieneDocenteRegistrado()){
            // Simulamos la entrada de datos del docente
            try {
                registrarDocente(nombre, apellido, legajo);
                System.out.println("Docente registrado: " + nombre + " " + apellido + ", Legajo: " + legajo);
            } catch (SQLException e) {
                System.err.println("Error al registrar el docente: " + e.getMessage());
            }
        }else{
            System.out.println("Ya hay un docente registrado en el sistema.");
        }
    }

    public void listarComisiones(){
        if(!this.listaComisiones.isEmpty()){
            Docente docenteActual = this.listaDocentes.get(0); // Tomo el docente activo
            ArrayList<Comision> comisionesDocente = docenteActual.getListaComisiones();
            if(!comisionesDocente.isEmpty()){
                System.out.println("Comisiones del docente " + docenteActual.getNombreCompleto() + ":");
                for (Comision comision : comisionesDocente) {
                    System.out.println("Código: " + comision.getCodigoComision() + ", Nombre: " + comision.getNombre() + ", Materia: " + comision.getMateria().getNombre());
                }
            } else {
                System.out.println("El docente no tiene comisiones registradas.");

            } 
            
        }else{
                System.out.println("No hay Comisiones registrados en el sistema.");
            }
    }



    public void agregarAlumno(
            int codigoComision, int legajoAlumno, String nombre, String apellido)
            throws SQLException {

        Comision comision = buscarComisionPorCodigo(codigoComision);

        if (comision == null) {
            System.out.println("No se encontró la comisión.");
            return;
        }

        for (Alumno alumno : comision.getListaAlumnos()) {
            if (alumno.getLegajo() == legajoAlumno) {
                System.out.println("Ya existe un alumno con ese legajo en esta comisión.");
                return;
            }
        }

        Alumno nuevoAlumno = new Alumno(nombre, apellido, legajoAlumno);

        new AlumnoDao().registrarEnComision(nuevoAlumno, codigoComision);
        comision.agregarAlumno(nuevoAlumno);

        System.out.println("Alumno registrado correctamente.");
    }
    
    
    public void listarAlumnos(int codigoComision){
        //1- Busco la comision por su codigo en el docente actual
        Comision comisionEncontrada = buscarComisionPorCodigo(codigoComision);

        // Si encuentra la comision, recorre y lista los alumnos
        if(comisionEncontrada != null){
            ArrayList<Alumno> listaAlumnos = comisionEncontrada.getListaAlumnos();
            if(!listaAlumnos.isEmpty()){
                System.out.println("Alumnos de la comisión " + comisionEncontrada.getNombre() + ":");
                for (Alumno a : listaAlumnos) {
                    System.out.println("Nombre: " + a.getNombreCompleto() + ", Legajo: " + a.getLegajo());
                }
            } else {
                System.out.println("No hay alumnos registrados en esta comisión.");
            }
        } else {
            System.out.println("No se encontró la comisión con el código proporcionado.");
        }
    }


    public void listarGrupos(int codigoComision){
        //1- Busco la comision por su codigo en el docente actual
        Comision comisionEncontrada = buscarComisionPorCodigo(codigoComision);

        // Si encuentra la comision, recorre y lista los grupos
        if(comisionEncontrada != null){
            ArrayList<Grupo> listaGrupos = comisionEncontrada.getListaGrupos();
            if(listaGrupos != null &&!listaGrupos.isEmpty()){
                System.out.println("Grupos de la comisión " + comisionEncontrada.getNombre() + ":");
                for (Grupo g : listaGrupos) {
                    System.out.println("Número de Grupo: " + g.getNumero() + ", Cupo Máximo: " + g.getCupoMaximo());
                    System.out.println("Integrantes:");
                    for (Alumno a : g.getlistaAlumnos()) {
                        System.out.println("Nombre: " + a.getNombreCompleto() + ", Legajo: " + a.getLegajo());
                    }
                }
            } else {
                System.out.println("No hay grupos registrados en esta comisión.");
            }
        } else {
            System.out.println("No se encontró la comisión con el código proporcionado.");
        }
    }

    
    public void buscarAlumno(int codigoComision, int legajoAlumno){
        //1- Busco la comision por su codigo en el docente actual
        Comision comisionEncontrada = buscarComisionPorCodigo(codigoComision);
       
        //2- Si encuentra la comision, busca el alumno por legajo
        if(comisionEncontrada != null){
            Alumno alumnoEncontrado = buscarAlumnoEnComision(codigoComision, legajoAlumno);
            if(alumnoEncontrado != null){
                System.out.println("Alumno encontrado en la comisión " + comisionEncontrada.getNombre() + ":");
                System.out.println("Nombre: " + alumnoEncontrado.getNombreCompleto() + ", Legajo: " + alumnoEncontrado.getLegajo());
            } else {
                System.out.println("No se encontró un alumno con ese legajo en esta comisión.");
            }
        } else {
            System.out.println("No se encontró la comisión con el código proporcionado.");
        }
    }

    public void darDeBajaAlumno(int codigoComision, int legajoAlumno){
        //1- Busco la comision por su codigo en el docente actual
        Comision comisionEncontrada = buscarComisionPorCodigo(codigoComision);
       
        //2- Si encuentra la comision, busca el alumno por legajo y lo da de baja
        if(comisionEncontrada != null){
            Alumno alumnoEncontrado = buscarAlumnoEnComision(codigoComision, legajoAlumno);
            
            if(alumnoEncontrado != null){
                comisionEncontrada.darDeBajaAlumno(legajoAlumno);
                for (Grupo grupo : comisionEncontrada.getListaGrupos()) {
                    if (grupo.quitarAlumno(alumnoEncontrado)) {
                        actualizarGrupo(comisionEncontrada, grupo);
                    }
                }
                for (InstanciaEvaluativa instancia : comisionEncontrada.getListaInstanciasEvaluativas()) {
                    instancia.getTurnero().actualizarPendientes(alumnoEncontrado, null);
                }
                System.out.println("Alumno con legajo " + legajoAlumno + " dado de baja de la comisión " + comisionEncontrada.getNombre() + ".");
            } else {
                System.out.println("No se encontró un alumno con ese legajo en esta comisión.");
            }
        } else {
            System.out.println("No se encontró la comisión con el código proporcionado.");
        }

    }

    public Comision buscarComisionPorCodigo(int codigoComision) {
        if (!this.listaDocentes.isEmpty()) {
            Docente docenteActual = this.listaDocentes.get(0); // Tomo el docente activo
            for (Comision c : docenteActual.getListaComisiones()) {
                if (c.getCodigoComision() == codigoComision) {
                    return c;
                }
            }
        }
        return null; // No se encontró la comisión
    }

    public Alumno buscarAlumnoEnComision(int codigoComision, int legajoAlumno) {
        Comision comision = buscarComisionPorCodigo(codigoComision);
        if (comision != null) {
            return comision.buscarAlumnoPorLegajo(legajoAlumno);
        }
        return null; // No se encontró el alumno
    }

    public Grupo buscarGrupoEnComision(int codigoComision, int numeroGrupo) {
        Comision comision = buscarComisionPorCodigo(codigoComision);
        if (comision != null && comision.getListaGrupos() != null) {
            for (Grupo g : comision.getListaGrupos()) {
                if (g.getNumero() == numeroGrupo) {
                    return g;
                }
            }
        }
        return null; // No se encontró el grupo
    }
    


    public void crearGrupo(int codigoComision, int numeroGrupo, int cupo)
            throws SQLException {

        Comision comision = buscarComisionPorCodigo(codigoComision);

        if (comision == null) {
            System.out.println("No se encontró la comisión.");
            return;
        }

        if (cupo <= 0) {
            System.out.println("El cupo tiene que ser mayor que cero.");
            return;
        }

        for (Grupo grupo : comision.getListaGrupos()) {
            if (grupo.getNumero() == numeroGrupo) {
                System.out.println("Ya existe un grupo con ese número en esta comisión.");
                return;
            }
        }

        Grupo nuevoGrupo = new Grupo(numeroGrupo, cupo);
        new GrupoDao().crear(nuevoGrupo, codigoComision);
        comision.agregarGrupo(nuevoGrupo);

        System.out.println("Grupo creado correctamente.");
    }

    //responde al metodo de la linea 430
    public void cambiarCupoMaximo(
            int codigoComision, int numeroGrupo, int nuevoCupo)
            throws SQLException {

        Grupo grupo = buscarGrupoEnComision(codigoComision, numeroGrupo);

        if (grupo == null) {
            System.out.println("No se encontró el grupo en esta comisión.");
            return;
        }

        if (nuevoCupo <= 0 || nuevoCupo < grupo.getlistaAlumnos().size()) {
            System.out.println(
                "El cupo debe ser positivo y no menor que la cantidad de integrantes."
            );
            return;
        }

        new GrupoDao().actualizarCupo(codigoComision, numeroGrupo, nuevoCupo);
        grupo.setCupoMaximo(nuevoCupo);

        System.out.println("Cupo actualizado correctamente.");
    }

    //responde al metodo de la linea 458
    public void agregarAlumnoAGrupo(
            int codigoComision, int numeroGrupo, int legajoAlumno)
            throws SQLException {

        Grupo grupo = buscarGrupoEnComision(codigoComision, numeroGrupo);
        if (grupo == null) {
            System.out.println("No se encontró el grupo en esta comisión.");
            return;
        }

        Alumno alumno = buscarAlumnoEnComision(codigoComision, legajoAlumno);
        if (alumno == null) {
            System.out.println("No se encontró el alumno en esta comisión.");
            return;
        }

        if (grupo.getlistaAlumnos().contains(alumno)) {
            System.out.println("El alumno ya integra este grupo.");
            return;
        }

        if (!grupo.tieneDisponibilidad()) {
            System.out.println("El grupo ya alcanzó su cupo máximo.");
            return;
        }

        new GrupoDao().agregarAlumno(codigoComision, numeroGrupo, legajoAlumno);
        grupo.agregarAlumno(alumno);
        actualizarGrupo(buscarComisionPorCodigo(codigoComision), grupo);

        System.out.println("Alumno agregado al grupo.");
    }

    public void quitarAlumnoDeGrupo(
            int codigoComision, int numeroGrupo, int legajoAlumno)
            throws SQLException {

        Grupo grupo = buscarGrupoEnComision(codigoComision, numeroGrupo);
        if (grupo == null) {
            System.out.println("No se encontró el grupo en esta comisión.");
            return;
        }

        Alumno alumno = buscarAlumnoEnComision(codigoComision, legajoAlumno);
        if (alumno == null || !grupo.getlistaAlumnos().contains(alumno)) {
            System.out.println("El alumno no integra este grupo.");
            return;
        }

        new GrupoDao().quitarAlumno(codigoComision, numeroGrupo, legajoAlumno);
        grupo.quitarAlumno(alumno);
        actualizarGrupo(buscarComisionPorCodigo(codigoComision), grupo);

        System.out.println("Alumno quitado del grupo.");
    }

    private void actualizarGrupo(Comision comision, Grupo grupo) {
        for (InstanciaEvaluativa instancia : comision.getListaInstanciasEvaluativas()) {
            instancia.getTurnero().actualizarPendientes(null, grupo);
        }
    }

        public void cambiarPrioridad(
                int codigoComision, int legajoAlumno, boolean nuevaPrioridad)
                throws SQLException {

            Comision comision = buscarComisionPorCodigo(codigoComision);

            if (comision == null) {
                System.out.println("No se encontró la comisión.");
                return;
            }

            Alumno alumno = buscarAlumnoEnComision(codigoComision, legajoAlumno);

            if (alumno == null) {
                System.out.println("No se encontró el alumno en esta comisión.");
                return;
            }

            if (alumno.getPrioridad() == nuevaPrioridad) {
                System.out.println("El alumno ya tiene ese nivel de prioridad.");
                return;
            }

            new AlumnoDao().cambiarPrioridad(legajoAlumno, nuevaPrioridad);
            alumno.setPrioridad(nuevaPrioridad);
            actualizarTurnosPendientes(comision, alumno);

            System.out.println(
                "Prioridad del alumno con legajo " + legajoAlumno +
                " cambiada a " + nuevaPrioridad + "."
            );
        }


    private void actualizarTurnosPendientes(Comision comision, Alumno alumno) {
        for (InstanciaEvaluativa instancia : comision.getListaInstanciasEvaluativas()) {
            if (instancia.getModalidad() == Modalidad.GENERAL) {
                continue;
            }

            Turnero turnero = instancia.getTurnero();
            for (Turno turno : turnero.getListaTurno()) {
                if (turno.getEstado() == Estado.PENDIENTE &&
                        (turno.getAlumno() == alumno || (turno.getGrupo() != null
                                && turno.getGrupo().getlistaAlumnos().contains(alumno)))) {
                    turnero.ordenarPorPrioridad();
                    turnero.calcularHorarios();
                    break;
                }
            }
        }
    }

    public boolean crearDefensaParcial(
        Comision comisionEncontrada, String fecha, String hora,
        double duracionEstimada, int codigoDeInstancia,
        Docente docente, Turnero turnero, Modalidad modalidad) throws SQLException{

    InstanciaEvaluativa instancia = new DefensaParcial(
        "Defensa Parcial",
        LocalDate.parse(fecha),
        LocalTime.parse(hora),
        duracionEstimada,
        comisionEncontrada,
        docente,
        codigoDeInstancia,
        turnero,
        modalidad
    );
    // lo agregamos a la lista de instancias evaluativas de la comision, porque la comision es la que tiene la lista de instancias evaluativas
    return registrarInstancia(comisionEncontrada, instancia, docente);
}
      

        // public boolean crearExamenFinal(Comision comisionEncontrada, LocalDate fecha, LocalTime hora ,double duracionEstimada, Docente docente, int codigoDeInstancia, Turnero turnero) {
            
        // InstanciaEvaluativa inst= new ExamenFinal("Examen Final", fecha, hora, duracionEstimada, comisionEncontrada, docente, codigoDeInstancia, turnero);
        // //1. Crear tipo de examen según el tipo recibido (1,2,3) y asignarle la modalidad, fecha y hora.
        // // esta instancia puede tener mas de 1 docente 
        // // preguntamos si se quiere agregar otro docente y lo agregamos a la lista de docentes de la instancia evaluativa

        // }
    
    public boolean crearExamenFinal(
        Comision comisionEncontrada, String fecha, String hora,
        double duracionEstimada, int codigoDeInstancia,
        Docente docente, Turnero turnero, Modalidad modalidad) throws  SQLException {

    InstanciaEvaluativa instancia = new ExamenFinal(
        "Examen Final",
        LocalDate.parse(fecha),
        LocalTime.parse(hora),
        duracionEstimada,
        comisionEncontrada,
        docente,
        codigoDeInstancia,
        turnero,
        modalidad
    );
    // lo agregamos a la lista de instancias evaluativas de la comision, porque la comision es la que tiene la lista de instancias evaluativas
    return registrarInstancia(comisionEncontrada, instancia,docente);}
    // public boolean crearPresentacionTPI(Comision comisionEncontrada, LocalDate fecha, LocalTime hora ,double duracionEstimada, Docente docente, int codigoDeInstancia, Turnero turnero) {
        
    //     InstanciaEvaluativa instancia3= new PresentacionTPI("Presentacion TPI", fecha, hora, duracionEstimada, comisionEncontrada, docente, codigoDeInstancia, turnero);              
       
    //    //1. Crear tipo de examen según el tipo recibido (1,2,3) y asignarle la modalidad, fecha y hora.
    // }

    public boolean crearPresentacionTPI(
        Comision comisionEncontrada, String fecha, String hora,
        double duracionEstimada, int codigoDeInstancia,
        Docente docente, Turnero turnero, Modalidad modalidad) throws  SQLException{
    InstanciaEvaluativa instancia = new PresentacionTPI(
        "Presentacion TPI",
        LocalDate.parse(fecha),
        LocalTime.parse(hora),
        duracionEstimada,
        comisionEncontrada,
        docente,
        codigoDeInstancia,
        turnero,
        modalidad
    );
    // lo agregamos a la lista de instancias evaluativas de la comision, porque la comision es la que tiene la lista de instancias evaluativas
    return registrarInstancia(comisionEncontrada, instancia, docente);
    }


    private boolean registrarInstancia(
        Comision comision, InstanciaEvaluativa instancia, Docente docente)
        throws SQLException {

    for (InstanciaEvaluativa registrada : comision.getListaInstanciasEvaluativas()) {
        if (registrada.getCodigo() == instancia.getCodigo()) {
            System.out.println("Ya hay una instancia con ese código en la comisión.");
            return false;
        }
    }

    Turnero turnero = instancia.getTurnero();
    LocalDate fecha = instancia.getFecha();
    LocalTime hora = instancia.getHoraInicio();

    switch (instancia.getModalidad()) {
        case INDIVIDUAL:
            for (Alumno alumno : comision.getListaAlumnos()) {
                turnero.agregarTurno(new Turno(instancia, alumno, fecha, hora));
            }
            break;
        case GRUPAL:
            for (Grupo grupo : comision.getListaGrupos()) {
                if (!grupo.getlistaAlumnos().isEmpty()) {
                    turnero.agregarTurno(new Turno(instancia, grupo, fecha, hora));
                }
            }
            break;
        case GENERAL:
            turnero.agregarTurno(new Turno(instancia, comision, fecha, hora));
            break;
    }

    if (turnero.getListaTurno().isEmpty()) {
        System.out.println("No hay alumnos o grupos con integrantes para generar turnos.");
        return false;
    }

    turnero.ordenarPorPrioridad();
    turnero.calcularHorarios();

    new InstanciaEvaluativaDao().registrar(
        instancia,
        comision.getCodigoComision(),
        Collections.singletonList(docente),
        turnero.getListaTurno()
    );

    comision.agregarInstanciaEvaluativa(instancia);
    return true;
}

    public Turnero obtenerTurnero(int codigoComision, int codigoInstancia) {

        Comision c = buscarComisionPorCodigo(codigoComision);
        if (c != null) {
            for (InstanciaEvaluativa instancia : c.getListaInstanciasEvaluativas()) {
                if (instancia.getCodigo() == codigoInstancia) {
                    return instancia.getTurnero();
                }
            }
        }
        return null;
    }



    //metodo para modificar la fecha y hora de una instancia evaluativa dependiendo su tipo (defensa parcial, presentacion TPI o examen final) y su codigo de instancia
    public void modificarFechaInstancia(int codigoComision, int codigoInstancia, String nuevaFechaStr){
        //1. Busco la comision con el metodo auxiliar
        Comision comisionEncontrada = buscarComisionPorCodigo(codigoComision);

        if(comisionEncontrada != null){
            //2. Convierto los String a LocalDate y LocalTime
            LocalDate nuevaFecha = LocalDate.parse(nuevaFechaStr);

            boolean instanciaEncontrada = false;

            //3. Recorro las instancias de la comision para buscar la que coincide con el codigo
            if(comisionEncontrada.getListaInstanciasEvaluativas() != null){
                for(InstanciaEvaluativa ins : comisionEncontrada.getListaInstanciasEvaluativas()){
                    if(ins.getCodigo() == codigoInstancia){
                        //3. Si la encuentro, cambio la fecha y hora
                        if (ins.getTurnero().estaIniciado()) {
                            System.out.println("La evaluacion ya empezo. No se puede cambiar el inicio.");
                            return;
                        }
                        ins.setFecha(nuevaFecha);
                        ins.getTurnero().calcularHorarios();
                        guardarFechaHora(codigoComision, ins); // Guarda la nueva fecha en la base
                        System.out.println("Fecha y hora de la instancia evaluativa modificadas correctamente.");
                        instanciaEncontrada = true;
                        break;
                    }

                }

            }
            if(instanciaEncontrada){
                System.out.println("La fecha y la hora de la instancia fueron modificadas con éxito.");

            }else {
                System.out.println("No se encontró la instancia evaluativa con el código proporcionado.");
            }
        }else {
            System.out.println("No se encontró la comisión con el código proporcionado.");
        }
    }        
    
    public void modificarHoraInstancia(int codigoComision, int codigoInstancia, String nuevaHoraStr){
        //1. Busco la comision con el metodo auxiliar
        Comision comisionEncontrada = buscarComisionPorCodigo(codigoComision);

        if(comisionEncontrada != null){
            //2. Convierto el String a LocalTime
            LocalTime nuevaHora = LocalTime.parse(nuevaHoraStr);

            boolean instanciaEncontrada = false;
        

            //3. Recorro las instancias de la comision para buscar la que coincide con el codigo
            if(comisionEncontrada.getListaInstanciasEvaluativas() != null){
                for(InstanciaEvaluativa ins : comisionEncontrada.getListaInstanciasEvaluativas()){
                    if(ins.getCodigo() == codigoInstancia){
                        //3. Si la encuentro, cambio la hora
                        if (ins.getTurnero().estaIniciado()) {
                            System.out.println("La evaluacion ya empezo. No se puede cambiar el inicio.");
                            return;
                        }
                        ins.setHoraInicio(nuevaHora);
                        ins.getTurnero().calcularHorarios();
                        guardarFechaHora(codigoComision, ins); // Guarda la nueva hora en la base
                        System.out.println("Hora de la instancia evaluativa modificada correctamente.");
                        instanciaEncontrada = true;
                        break;
                    }
                }
            }
            if(instanciaEncontrada){
                System.out.println("La hora de la instancia fue modificada con éxito.");

            }else {
                System.out.println("No se encontró la instancia evaluativa con el código proporcionado.");
            }
        }else {
            System.out.println("No se encontró la comisión con el código proporcionado.");
        }
    }

    public void listarInstanciasEvaluativas(int codigoComision){
        //1- Busco la comision por su codigo en el docente actual
        Comision comisionEncontrada = buscarComisionPorCodigo(codigoComision);

        // Si encuentra la comision, recorre y lista las instancias evaluativas
        if(comisionEncontrada != null){
            ArrayList<InstanciaEvaluativa> listaInstancias = comisionEncontrada.getListaInstanciasEvaluativas();
            if(listaInstancias != null && !listaInstancias.isEmpty()){
                System.out.println("Instancias evaluativas de la comisión " + comisionEncontrada.getNombre() + ":");
                for (InstanciaEvaluativa ins : listaInstancias) {
                    System.out.println("Código: " + ins.getCodigo() +
                        ", Tipo: " + ins.getTipo() +
                        ", Fecha: " + ins.getFecha() +
                        ", Hora: " + ins.getHoraInicio() +
                        ", Duración Estimada: " + ins.getDuracionEstimada());
                }
            } else {
                System.out.println("No hay instancias evaluativas registradas en esta comisión.");
            }
        } else {
            System.out.println("No se encontró la comisión con el código proporcionado.");
        }
    }

    public void validacionDocente(String docente) {
        // Verificar si el docente existe en la lista de docentes
        boolean docenteValido = false;
        for (Docente d : listaDocentes) {
            if (d.getNombreCompleto().equalsIgnoreCase(docente)) {
                docenteValido = true;
                break;
            }
        }

        if (!docenteValido) {
            System.out.println("El docente ingresado no está registrado. Por favor, registre al docente antes de continuar.");
            // Aquí podrías agregar lógica para registrar al docente si es necesario
            registrarDocenteSiNoExiste(docente.split(" ")[0], docente.split(" ")[1], ""); // Asumiendo que el legajo se puede dejar vacío o se puede solicitar al usuario
        }
    }

    // metodo para registrar docente si no esta validado
    public void registrarDocenteSiNoExiste(String nombre, String apellido, String legajo) {
        boolean docenteExistente = false;
        for (Docente d : listaDocentes) {
            if (d.getNombreCompleto().equalsIgnoreCase(nombre + " " + apellido)) {
                docenteExistente = true;
                break;
            }
        }

        if (!docenteExistente) {
            try {
                registrarDocente(nombre, apellido, legajo);
                System.out.println("Docente registrado: " + nombre + " " + apellido + ", Legajo: " + legajo);
            } catch (SQLException e) {
                System.err.println("Error al registrar el docente: " + e.getMessage());
            }
               
        } else {
            System.out.println("El docente ya está registrado en el sistema.");
        }
    }
    
    
    // buscamos el docente y traemos el objoeto docente 
    public Docente busquedaDocente(String docente) {
        for (Docente d : listaDocentes) {
            if (d.getNombreCompleto().equalsIgnoreCase(docente)) {
                return d; // Retorna el objeto Docente si se encuentra
            }
        }
        System.out.println("El docente ingresado no está registrado.");
        return null; // Retorna null si no se encuentra
    }

    // Guarda en la base la fecha y la hora de la instancia y los horarios de sus turnos
    private void guardarFechaHora(
            int codigoComision, InstanciaEvaluativa instancia) {
        try {
            new InstanciaEvaluativaDao().actualizarFechaHora(
                codigoComision, instancia.getCodigo(),
                instancia.getFecha(), instancia.getHoraInicio()
            );
            guardarTurnos(
                codigoComision, instancia.getCodigo(), instancia.getTurnero()
            );
        } catch (SQLException e) {
            System.err.println("No se pudo guardar la fecha y la hora: " + e.getMessage());
        }
    }
    
    // Guarda en la base todos los turnos de una instancia tal como estan en memoria
    private void guardarTurnos(
            int codigoComision, int codigoInstancia, Turnero turnero) {
        try {
            new TurnoDao().reemplazarTurnos(
                codigoComision, codigoInstancia, turnero.getListaTurno()
            );
        } catch (SQLException e) {
            System.err.println("No se pudieron guardar los turnos: " + e.getMessage());
        }
    }

    public void cambiarOrdenTurno(int codigoComision, int codigoInstancia, int codigoTurno, int nuevaPosicion) {

        // Busco el Turnero correspondiente a la comisión y la instancia
        Turnero turnero = obtenerTurnero(codigoComision, codigoInstancia);
       
        if (turnero != null) {
            Turno turnoAMover = null;
            
            for (Turno t : turnero.getListaTurno()) { // Recorro la lista de turnos para encontrar el turno con el código proporcionado
                if (t.getNumero() == codigoTurno) { // Si el código del turno coincide, lo guardo en turnoAMover
                    turnoAMover = t;
                    break;
                }
            }
            // Verifico si se encontró el turno y cambio su orden en el Turnero
            if (turnoAMover != null) {
               
                ArrayList<Turno> listaTurnos = turnero.getListaTurno();
                
                if(nuevaPosicion >= 0 && nuevaPosicion < listaTurnos.size()){
                    //Llamo a cambiarOrden del Turnero para cambiar la posicion del turno
                    if (!turnero.cambiarOrden(turnoAMover, nuevaPosicion)) {
                        System.out.println("Solo se pueden mover turnos entre posiciones pendientes.");
                        return;
                    }
                    //Recalculo los horarios estimados porque cambio la posicion del turno
                    turnero.calcularHorarios();
                    System.out.println("Orden del turno cambiado correctamente.");
                    guardarTurnos(codigoComision, codigoInstancia, turnero); // Guarda en la base el nuevo orden
                }else{
                    
                    System.out.println("La nueva posición proporcionada es inválida. Debe estar entre 1 y " + listaTurnos.size() + ".");
                }
            }else{
                System.out.println("No se encontró un turno con el código proporcionado en el Turnero.");
            }
        }else{
            System.out.println("No se encontró un Turnero para la comisión y la instancia proporcionadas.");
                    
        }
    }


    // en el menu de instancias evaluativas, validamos que haya comisiones cargadas antes de permitir crear instancias evaluativas
    public boolean validarComisiones() {
        if (this.listaComisiones.isEmpty()) {
            System.out.println("No hay comisiones cargadas. Por favor, registre al menos una comisión .");
            return false;
     }
        return true;
    }
    // en el menu de registro de docentes, validamos que haya docentes cargados
    public boolean validarnDocente() {
        if (this.listaDocentes.isEmpty()) {
            System.out.println("No hay docentes cargados. Por favor, registre al menos un docente.");
            return false;
        }
        return true;
    }

    public boolean validarAlumnos(int codigo){
        Comision comisionEncontrada = buscarComisionPorCodigo(codigo);
        if (comisionEncontrada .getListaAlumnos() != null)
            {return true;}
        return false;

    }


    
    public ArrayList<Turno> filtrarYOrdenarPorPrioridad(int codigoComision, int codigoInstancia) {

        Turnero turnero = obtenerTurnero(codigoComision, codigoInstancia);

        if (turnero != null) {

            ArrayList<Turno> listaTurnos = turnero.getListaTurno();
            ArrayList<Turno> turnosPendientes = new ArrayList<>();

            for (Turno turno : listaTurnos) {
                if (turno.getEstado() == Estado.PENDIENTE) {
                    turnosPendientes.add(turno);
                }
            }

            // Prioritarios primero
            for (int i = 0; i < turnosPendientes.size() - 1; i++) {
                for (int j = i + 1; j < turnosPendientes.size(); j++) {

                        if (turnosPendientes.get(j).getNivelPrioridad()
                            > turnosPendientes.get(i).getNivelPrioridad()) {

                        Turno aux = turnosPendientes.get(i);
                        turnosPendientes.set(i, turnosPendientes.get(j));
                        turnosPendientes.set(j, aux);
                    }
                }
            }

            return turnosPendientes;
        }

        return new ArrayList<>();
    }


    public void siguienteTurno(
            int codigoComision,
            int codigoInstancia,
            Integer nota) {

        Turnero turnero = obtenerTurnero(
            codigoComision, codigoInstancia
        );

        if (turnero == null) {
            System.out.println("No se encontro el turnero.");
            return;
        }

        Turno turnoActual = turnero.getTurnoActual();

        if (turnoActual == null) {
            System.out.println("No hay un turno en evaluacion.");
            return;
        }

        Modalidad modalidad =
            turnoActual.getInstancia().getModalidad();

        // Validar segun la modalidad
        if (modalidad == Modalidad.GENERAL) {

            if (nota != null) {
                System.out.println(
                    "La evaluacion general no recibe nota."
                );
                return;
            }

        } else {

            if (nota == null || nota < 1 || nota > 10) {
                System.out.println(
                    "La nota debe estar entre 1 y 10."
                );
                return;
            }

            turnoActual.setNota(nota);
        }

        // Finalizar solamente despues de validar
        turnoActual.setEstado(Estado.FINALIZADO);

        System.out.println("Turno finalizado.");

        turnero.iniciarOContinuar();
        guardarTurnos(codigoComision, codigoInstancia, turnero); // Guarda en la base la nota y el estado
    }

        public void ponerAusente(
            int codigoComision,
            int codigoInstancia) {

        Turnero turnero = obtenerTurnero(
            codigoComision, codigoInstancia
        );

        if (turnero == null) {
            System.out.println("No se encontro el turnero.");
            return;
        }

        Turno turnoActual = turnero.getTurnoActual();

        if (turnoActual == null) {
            System.out.println("No hay un turno en evaluacion.");
            return;
        }

        Modalidad modalidad =
            turnoActual.getInstancia().getModalidad();


        if (modalidad != Modalidad.GENERAL) {
            turnoActual.setNota(0);
        }

        turnoActual.setEstado(Estado.AUSENTE);

        System.out.println("Turno marcado como ausente.");

        turnero.iniciarOContinuar();
        guardarTurnos(codigoComision, codigoInstancia, turnero); // Guarda en la base el estado ausente
    }

    // public Boolean validarGrupos(){
        
    // }


   
    public ArrayList<Turno> obtenerCalificacionesAlumno(
        int codigoComision,
        int legajo) {

    ArrayList<Turno> resultados = new ArrayList<>();

        Comision comision =
            buscarComisionPorCodigo(codigoComision);

        if (comision == null) {
            return resultados;
        }

        for (InstanciaEvaluativa instancia :
                comision.getListaInstanciasEvaluativas()) {

            if (instancia.getModalidad() == Modalidad.GENERAL) {
                continue;
            }

            for (Turno turno :
                    instancia.getTurnero().getListaTurno()) {

                if (turno.getNota() != null && turno.incluyeAlumno(legajo) && (turno.getEstado() == Estado.FINALIZADO || turno.getEstado() == Estado.AUSENTE)) {

                    resultados.add(turno);
                }
            }
        }

        return resultados;
    }
}