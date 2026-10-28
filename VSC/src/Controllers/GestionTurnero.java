package Controllers;

import Models.Alumno;
import Models.Comision;
import Models.Docente;
import Models.Materia;
import Models.Grupo;
import models.DefensaParcial;
import models.ExamenFinal;
import models.InstanciaEvaluativa;
import models.Turno;
import models.Turnero;

import java.lang.reflect.Array;
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
                Alumno nuevoAlumno = new Alumno(legajoAlumno);
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

    private Comision buscarComisionPorCodigo(int codigoComision) {
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

    private Alumno buscarAlumnoEnComision(int codigoComision, int legajoAlumno) {
        Comision comision = buscarComisionPorCodigo(codigoComision);
        if (comision != null) {
            return comision.buscarAlumnoPorLegajo(legajoAlumno);
        }
        return null; // No se encontró el alumno
    }

    private Grupo buscarGrupoEnComision(int codigoComision, int numeroGrupo) {
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

    public void crearInstanciaEvaluativa(int codigoComision, int tipo, int modalidad, String fecha, String hora, int minutos){
        Comision comisionEncontrada = buscarComisionPorCodigo(codigoComision);
        
       //1. Crear tipo de examen según el tipo recibido (1,2,3) y asignarle la modalidad, fecha y hora.
        if(tipo == 1){
            nuevaInstancia = new DefensaParcial();
        }else if(tipo == 2){
            nuevaInstancia = new ExamenFinal();
        }else if(tipo == 3){
            nuevaInstancia = new PresentacionTPI();
        }

        if (nuevaInstancia != null) {
            nuevaInstancia.setModalidad(modalidad);
            nuevaInstancia.setFecha(fecha);
            nuevaInstancia.setHora(hora);
            comisionEncontrada.agregarInstanciaEvaluativa(nuevaInstancia);

            //2. Genero el Turnero de esa instancia segun la modalidad
            Turnero turnero = new Turnero();

            if (modalidad == 1) { //Individual, turno por alumno
                for(Alumno a : comisionEncontrada.getListaAlumnos()){
                    turnero.agregarTurno(new Turno(a));

                }
            } else if (modalidad == 2) { //Grupal, turno por grupo
                for(Grupo g : comisionEncontrada.getListaGrupos()){
                    turnero.agregarTurno(new Turno(g));
                }
            }else if (modalidad == 3){// General (un turno para toda la comision)
                turnero.agregarTurno(new Turno(comisionEncontrada));
            }

            //3. ordeno por prioridad y calcular horarios
            turnero.ordenarTurnosPorPrioridad();
            turnero.calcularHorarios(hora, minutos);

            //4. Asocio el turnero a la instancia evaluativa y guardo en comision
            nuevaInstancia.setTurnero(turnero);
            comisionEncontrada.agregarInstanciaEvaluativa(nuevaInstancia);

            System.out.println("Instancia evaluativa creada y turnero generado correctamente.");

        } else {
            System.out.println("Tipo de instancia evaluativa no válido.");
        }
        
    }

    public void modificarFechaInstancia(int codigoComision, int codigoInstancia, String nuevaFechaStr, String nuevaHoraStr){
        //1. Busco la comision con el metodo auxiliar
        Comision comisionEncontrada = buscarComisionPorCodigo(codigoComision);

        if(comisionEncontrada != null){
            //2. Convierto los String a LocalDate y LocalTime
            LocalDate nuevaFecha = LocalDate.parse(nuevaFechaStr);
            LocalTime nuevaHora = LocalTime.parse(nuevaHoraStr);

            boolean instanciaEncontrada = false;
        

            //3. Recorro las instancias de la comision para buscar la que coincide con el codigo
            if(comisionEncontrada.getListaInstanciasEvaluativas() != null){
                for(InstanciaEvaluativa ins : comisionEncontrada.getListaInstanciasEvaluativas()){
                    if(ins.getCodigo() == codigoInstancia){
                        //3. Si la encuentro, cambio la fecha y hora
                        ins.setFecha(nuevaFecha);
                        ins.setHora(nuevaHora);
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







}






