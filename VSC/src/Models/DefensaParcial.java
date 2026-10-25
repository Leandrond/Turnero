//Leandro
package Models;
import java.time.LocalDate;
import java.time.LocalTime;

public class DefensaParcial extends InstanciaEvaluativa{

    // Constructor con atributos heredados de la clase InstanciaEvaluativa
    public DefensaParcial(
        String tipo, LocalDate fecha,LocalTime hora ,double duracionEstimada, Comision comision, Docente docente, int codigoDeInstancia, Turnero turnero) {
        super.tipo = tipo;
        super.fecha = fecha;
        super.horaInicio = hora;
        super.duracionEstimada = duracionEstimada;
        super.comision = comision;
        super.docente = docente;
        super.codigoDeInstancia = codigoDeInstancia;
        super.turnero = turnero;
    }
    //getters y setters de los atributos heredados de la clase InstanciaEvaluativa
    @Override
    public String getTipo() {
        return this.tipo;
    }

    @Override
    public LocalDate getFecha() {   
        return this.fecha;
    }

    @Override
    public LocalTime getHoraInicio() {
        return this.horaInicio;
    }

    @Override
    public Double getDuracionEstimada() {
        return this.duracionEstimada;
    }

    @Override
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public void setHoraInicio(LocalTime hora) {
        this.horaInicio = hora;
    }

    @Override
    public void setDuracionEstimada(Double duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }
    @Override
    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    @Override
    public void setComision(Comision comision) {
        this.comision = comision;
    }
    @Override
    public void setCodigoDeInstancia(int codigoDeInstancia) {
        this.codigoDeInstancia = codigoDeInstancia;
    }
    @Override
    public int getCodigoDeInstancia() {
        return this.codigoDeInstancia;
    }

    @Override
    public Docente getDocente() {
        return this.docente;
    }

    @Override
    public Comision getComision() {
        return this.comision;
    }
    @Override
    public Turnero getTurnero() {
        return this.turnero;
    }
    @Override
    public void setTurnero(Turnero turnero) {
        this.turnero = turnero;
    }
}