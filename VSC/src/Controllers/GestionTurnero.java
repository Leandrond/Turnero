package Controllers;

import Models.Alumno;
import Models.Comision;
import Models.DefensaParcial;
import Models.Docente;
import Models.ExamenFinal;
import Models.Grupo;
import Models.InstanciaEvaluativa;
import Models.Materia;
import Models.PresentacionTPI;
import Models.Turnero;
import Models.Turno;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class GestionTurnero {

    private ArrayList<Docente> listaDocentes;
    private ArrayList<Comision> listaComisiones;
    
    public GestionTurnero() {
        //System.out.println("Hola Gestion del Turnero");
        this.listaDocentes = new ArrayList<>();
        this.listaComisiones = new ArrayList<>();
    }

    //metodo para agregar un docente nuevo recibiendo sus datos
    public void registrarDocente(String nombre, String apellido, String legajo){
        Docente nuevoDocente = new Docente(nombre, apellido, legajo);
        listaDocentes.add(nuevoDocente);
    }

    public void crearComision(int codigo, String nombre, Materia materia){
        Comision nuevaComision = new Comision(codigo, nombre, materia);
        listaComisiones.add(nuevaComision);
        //Se la asignamos al docente registrado (asumiendo que trabajamos con el docente actual)
        if(!this.listaDocentes.isEmpty()){
            Docente docenteActual = this.listaDocentes.get(0); // Tomo el docente activo
            docenteActual.agregarComision(nuevaComision); // Agrego la comisión al docente activo
        }
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
            registrarDocente(nombre, apellido, legajo);
            System.out.println("Docente registrado: " + nombre + " " + apellido + ", Legajo: " + legajo);
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
                System.out.println("No hay docentes registrados en el sistema.");
            }
    }

    public void agregarAlumno(int codigoComision, int legajoAlumno){
        //1- Busco la comision por su codigo en el docente actual
        Comision comisionEncontrada = buscarComisionPorCodigo(codigoComision);
        
        //2- Si encuentra la comision, validamos que el alumno no este ya en la comision y lo agregamos
        if(comisionEncontrada != null){
            // Verifico si el alumno ya está en la comisión
            boolean alumnoExistente = false;
            for (Alumno a : comisionEncontrada.getListaAlumnos()) {
                if (a.getLegajo() == legajoAlumno) {
                    alumnoExistente = true;
                    break;
                }
            }

            if (!alumnoExistente) {
                Alumno nuevoAlumno = new Alumno(legajoAlumno, null); // Estado y prioridad se pueden establecer según sea necesario
                comisionEncontrada.agregarAlumno(nuevoAlumno);// Usamos el método que ya tiene Comision
                System.out.println("Alumno con legajo " + legajoAlumno + " agregado correctamente a la comisión.");
            } else {
                System.out.println("ERROR: Ya existe un alumno con ese legajo en esta comisión.");
            }
        } else {
            System.out.println("No se encontró la comisión con el código proporcionado.");
        }

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
                    System.out.println("Legajo: " + a.getLegajo() + ", Estado: " + a.getEstado());
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
                        System.out.println("Legajo: " + a.getLegajo() + ", Estado: " + a.getEstado());
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
                System.out.println("Legajo: " + alumnoEncontrado.getLegajo() + ", Estado: " + alumnoEncontrado.getEstado());
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
                comisionEncontrada.getListaAlumnos().remove(alumnoEncontrado);
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
    


    public void crearGrupo(int codigoComision, int numeroGrupo, int cupo){
        Comision comisionEncontrada = buscarComisionPorCodigo(codigoComision);

        if(comisionEncontrada != null){
            Grupo nuevoGrupo = new Grupo(numeroGrupo, cupo);
            comisionEncontrada.agregarGrupo(nuevoGrupo);
        } else {
            System.out.println("No se encontró la comisión con el código proporcionado.");
        }
    }


    //responde al metodo de la linea 430
    public void cambiarCupoMaximo(int codigoComision, int numeroGrupo, int nuevoCupo){
        Comision comisionEncontrada = buscarComisionPorCodigo(codigoComision);
        if(comisionEncontrada != null){
            Grupo grupoEncontrado = null;
            for (Grupo g : comisionEncontrada.getListaGrupos()) {
                if (g.getNumero() == numeroGrupo) {
                    grupoEncontrado = g;
                    break;
                }
            }

            if(grupoEncontrado != null){
                grupoEncontrado.setCupoMaximo(nuevoCupo);
                System.out.println("Cupo máximo del grupo " + numeroGrupo + " cambiado a " + nuevoCupo + ".");
            } else {
                System.out.println("No se encontró un grupo con ese número en esta comisión.");
            }
        } else {
            System.out.println("No se encontró la comisión con el código proporcionado.");
        }
    }


    //responde al metodo de la linea 458
    public void agregarAlumnoAGrupo(int codigoComision, int numeroGrupo, int legajoAlumno){
        //Se busca la comision
        Comision comisionEncontrada = buscarComisionPorCodigo(codigoComision);
        if(comisionEncontrada != null){
            //Se busca el grupo
            Grupo grupoEncontrado = null;
            for (Grupo g : comisionEncontrada.getListaGrupos()) {
                if (g.getNumero() == numeroGrupo) {
                    grupoEncontrado = g;
                    break;
                }
            }

            if(grupoEncontrado != null){
                //Se busca el alumno en la comision
                Alumno alumnoEncontrado = buscarAlumnoEnComision(codigoComision, legajoAlumno);
                if(alumnoEncontrado != null){
                    //Se agrega el alumno al grupo
                    grupoEncontrado.agregarAlumno(alumnoEncontrado);
                    System.out.println("Alumno con legajo " + legajoAlumno + " agregado al grupo con éxito.");
                } else {
                    System.out.println("Error: No se encontró un alumno con ese legajo en esta comisión.");
                }
            } else {
                System.out.println("No se encontró un grupo con ese número en esta comisión.");
            }
        } else {
            System.out.println("No se encontró la comisión con el código proporcionado.");
        }
    }


    public void quitarAlumnoDeGrupo(int codigoComision, int numeroGrupo, int legajoAlumno){
       //1. Busco el grupo directamente con el codigo y el numero usando el metodo auxiliar
        Grupo grupoEncontrado = buscarGrupoEnComision(codigoComision, numeroGrupo);

        //2. Si encuentra el grupo, busca el alumno por legajo y lo quita del grupo
        if(grupoEncontrado != null){
            Alumno alumnoEncontrado = buscarAlumnoEnComision(codigoComision, legajoAlumno);
            
            if(alumnoEncontrado != null){
                grupoEncontrado.quitarAlumno(alumnoEncontrado);
                System.out.println("Alumno con legajo " + legajoAlumno + " removido del grupo con éxito.");
                
            } else {
                System.out.println("No se encontró un alumno con ese legajo en esta comisión.");
            }
        } else {
            System.out.println("No se encontró un grupo con ese número en esta comisión.");
        }
    }


    public void cambiarPrioridad(int codigoComision, int legajoAlumno, boolean nuevaPrioridad){
        //1- Busco la comision por su codigo en el docente actual
        Comision comisionEncontrada = buscarComisionPorCodigo(codigoComision);
       
        //2- Si encuentra la comision, busca el alumno por legajo y cambia su prioridad
        if(comisionEncontrada != null){
            Alumno alumnoEncontrado = buscarAlumnoEnComision(codigoComision, legajoAlumno);
            
            if(alumnoEncontrado != null){
                alumnoEncontrado.setPrioridad(nuevaPrioridad);
                System.out.println("Prioridad del alumno con legajo " + legajoAlumno + " cambiada a " + nuevaPrioridad + ".");
            } else {
                System.out.println("No se encontró un alumno con ese legajo en esta comisión.");
            }
        } else {
            System.out.println("No se encontró la comisión con el código proporcionado.");
        }
    }


    public void crearDefensaParcial(
        Comision comisionEncontrada, String fecha, String hora,
        double duracionEstimada, int codigoDeInstancia,
        Docente docente, Turnero turnero) {

    InstanciaEvaluativa instancia = new DefensaParcial(
        "Defensa Parcial",
        LocalDate.parse(fecha),
        LocalTime.parse(hora),
        duracionEstimada,
        comisionEncontrada,
        docente,
        codigoDeInstancia,
        turnero
    );
    // lo agregamos a la lista de instancias evaluativas de la comision, porque la comision es la que tiene la lista de instancias evaluativas
    comisionEncontrada.agregarInstanciaEvaluativa(instancia);
}
      

        // public void crearExamenFinal(Comision comisionEncontrada, LocalDate fecha, LocalTime hora ,double duracionEstimada, Docente docente, int codigoDeInstancia, Turnero turnero) {
            
        // InstanciaEvaluativa inst= new ExamenFinal("Examen Final", fecha, hora, duracionEstimada, comisionEncontrada, docente, codigoDeInstancia, turnero);
        // //1. Crear tipo de examen según el tipo recibido (1,2,3) y asignarle la modalidad, fecha y hora.
        // // esta instancia puede tener mas de 1 docente 
        // // preguntamos si se quiere agregar otro docente y lo agregamos a la lista de docentes de la instancia evaluativa

        // }
    
    public void crearExamenFinal(
        Comision comisionEncontrada, String fecha, String hora,
        double duracionEstimada, int codigoDeInstancia,
        Docente docente, Turnero turnero) {

    InstanciaEvaluativa instancia = new ExamenFinal(
        "Examen Final",
        LocalDate.parse(fecha),
        LocalTime.parse(hora),
        duracionEstimada,
        comisionEncontrada,
        docente,
        codigoDeInstancia,
        turnero
    );
    // lo agregamos a la lista de instancias evaluativas de la comision, porque la comision es la que tiene la lista de instancias evaluativas
    comisionEncontrada.agregarInstanciaEvaluativa(instancia);}
    // public void crearPresentacionTPI(Comision comisionEncontrada, LocalDate fecha, LocalTime hora ,double duracionEstimada, Docente docente, int codigoDeInstancia, Turnero turnero) {
        
    //     InstanciaEvaluativa instancia3= new PresentacionTPI("Presentacion TPI", fecha, hora, duracionEstimada, comisionEncontrada, docente, codigoDeInstancia, turnero);              
       
    //    //1. Crear tipo de examen según el tipo recibido (1,2,3) y asignarle la modalidad, fecha y hora.
    // }

    public void crearPresentacionTPI(
        Comision comisionEncontrada, String fecha, String hora,
        double duracionEstimada, int codigoDeInstancia,
        Docente docente, Turnero turnero) {
    InstanciaEvaluativa instancia = new PresentacionTPI(
        "Presentacion TPI",
        LocalDate.parse(fecha),
        LocalTime.parse(hora),
        duracionEstimada,
        comisionEncontrada,
        docente,
        codigoDeInstancia,
        turnero
    );
    // lo agregamos a la lista de instancias evaluativas de la comision, porque la comision es la que tiene la lista de instancias evaluativas
    comisionEncontrada.agregarInstanciaEvaluativa(instancia);
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

    public void cambiarOrdenTurno(int comision, int instancia, int turno, int posicion) {

    Turnero turneroEncontrado = obtenerTurnero(comision, instancia);

    if (turneroEncontrado != null) {
        for (Turno t : turneroEncontrado.getListaTurno()) {
            if (t.getCodigoInstancia() == turno) {
                turneroEncontrado.cambiarOrden(t, posicion);
                System.out.println("Orden del turno cambiado correctamente.");
                return;
            }
        }

    }
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
                        ins.setFecha(nuevaFecha);
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
                        ins.setHoraInicio(nuevaHora);
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
            registrarDocente(nombre, apellido, legajo);
            System.out.println("Docente registrado: " + nombre + " " + apellido + ", Legajo: " + legajo);
            // lo agregamos a la lista de docentes
            listaDocentes.add(new Docente(nombre, apellido, legajo));
               
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






}


