package models;
import java.util.Date;
import java.util.Timer;

public class ExamenFinal extends InstanciaEvaluativa{
    
    // Constructor con atributos heredados de la clase InstanciaEvaluativa
    public ExamenFinal(
        String tipo, Date fecha, Timer hora ,double duracionEstimada) {
        super.tipo = tipo;
        super.fecha = fecha;
        super.hora = hora;
        super.duracionEstimada = duracionEstimada;
    }
    //getters y setters de los atributos heredados de la clase InstanciaEvaluativa
    @Override
    public String getTipo() {
        return this.tipo;
    }

    @Override
    public Date getFecha() {
        return this.fecha;
    }

    @Override
    public Timer getHoraInicio() {
        return this.hora;
    }

    @Override
    public Double getDuracionEstimada() {
        return this.duracionEstimada;
    }

    @Override
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public void setHoraInicio(Timer hora) {
        this.hora = hora;
    }

    @Override
    public void setDuracionEstimada(Double duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }
}