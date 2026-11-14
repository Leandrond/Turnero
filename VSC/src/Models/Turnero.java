package Models;
import java.util.ArrayList;

import Models.Instancias.InstanciaEvaluativa;

public class Turnero
{
    // instance variables - replace the example below with your own
    private  ArrayList<Turno> turnos = new ArrayList<>();
    
    
    public Turnero()
    {
        // initialise instance variables
        turnos = new ArrayList<>();
        
    }

    public ArrayList<Turno> getTurnero()
    {
        return this.turnos;
    }
    public void agregarTurno(Turno turno)
    {
        turnos.add(turno);
        
    }
    
    public ArrayList<Turno> getListaTurno()
    {
        return turnos;
    }
    
    public boolean cambiarOrden(Turno turno, int nuevaPosicion) {
        int posicionActual = turnos.indexOf(turno);
        if (posicionActual < 0 || nuevaPosicion < 0 || nuevaPosicion >= turnos.size()) {
            return false;
        }

        int desde = Math.min(posicionActual, nuevaPosicion);
        int hasta = Math.max(posicionActual, nuevaPosicion);
        for (int i = desde; i <= hasta; i++) {
            if (turnos.get(i).getEstado() != Estado.Pendiente) {
                return false;
            }
        }

        turnos.remove(posicionActual);
        turnos.add(nuevaPosicion, turno);
        return true;
    }

    public void ordenarTurnosPorPrioridad() {
        ordenarPorPrioridad();
    }

    public boolean estaIniciado() {
        for (Turno turno : turnos) {
            if (turno.getEstado() != Estado.Pendiente) {
                return true;
            }
        }
        return false;
    }

    public void calcularHorarios() {
        if (turnos.isEmpty()) {
            return;
        }

        InstanciaEvaluativa instancia = turnos.get(0).getInstancia();
        if (instancia == null || instancia.getDuracionEstimada() <= 0) {
            return;
        }

        java.time.LocalDateTime horario = java.time.LocalDateTime.of(
                instancia.getFecha(), instancia.getHoraInicio());
        long segundos = Math.round(instancia.getDuracionEstimada() * 60);

        for (Turno turno : turnos) {
            if (turno.getEstado() == Estado.Pendiente) {
                turno.setFecha(horario.toLocalDate());
                turno.setHora(horario.toLocalTime());
            } else {
                horario = java.time.LocalDateTime.of(turno.getFecha(), turno.getHora());
            }
            horario = horario.plusSeconds(segundos);
        }
    }

    public void ordenarPorPrioridad() {
        for (int i = 1; i < turnos.size(); i++) {
            Turno turno = turnos.get(i);
            if (turno.getEstado() != Estado.Pendiente) {
                continue;
            }

            int posicion = i;
            while (posicion > 0
                    && turnos.get(posicion - 1).getEstado() == Estado.Pendiente
                    && turnos.get(posicion - 1).getNivelPrioridad() < turno.getNivelPrioridad()) {
                turnos.set(posicion, turnos.get(posicion - 1));
                posicion--;
            }
            turnos.set(posicion, turno);
        }
    }

    public void actualizarPendientes(Alumno alumno, Grupo grupo) {
        boolean afectado = false;
        for (int i = turnos.size() - 1; i >= 0; i--) {
            Turno turno = turnos.get(i);
            if (turno.getEstado() != Estado.Pendiente) {
                continue;
            }
            if (alumno != null && turno.getAlumno() == alumno) {
                turnos.remove(i);
                afectado = true;
            } else if (grupo != null && turno.getGrupo() == grupo) {
                if (grupo.getlistaAlumnos().isEmpty()) {
                    turnos.remove(i);
                }
                afectado = true;
            }
        }
        if (afectado) {
            ordenarPorPrioridad();
            calcularHorarios();
        }
    }

    public Turno iniciarOContinuar() {
        Turno actual = getTurnoActual();
        if (actual != null) {
            return actual;
        }

        for (Turno turno : turnos) {
            if (turno.getEstado() == Estado.Pendiente) {
                turno.fijarIntegrantes();
                turno.setEstado(Estado.Evaluando);
                return turno;
            }
        }
        return null;
    }

    public Turno getTurnoActual() {
        for (Turno turno : turnos) {
            if (turno.getEstado() == Estado.Evaluando) {
                return turno;
            }
        }
        return null; // No hay turno en curso
    }

    public Turno obtenerSiguienteTurno(Turno turnoActual) {
        for (int i = 0; i < turnos.size(); i++) {
            if (turnos.get(i).equals(turnoActual) && i + 1 < turnos.size()) {
                return turnos.get(i + 1);
            }
        }
        return null; // No hay siguiente turno
    }

    



}
    



    //HACER

    
    