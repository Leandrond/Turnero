//Leandro
package Models;
import java.time.LocalDate;
import java.time.LocalTime;

public class DefensaParcial extends InstanciaEvaluativa{
    
    // Constructor con atributos heredados de la clase InstanciaEvaluativa
    public DefensaParcial(
        String tipo, LocalDate fecha, LocalTime hora ,double duracionEstimada) {
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
    public LocalDate getFecha() {
        return this.fecha;
    }

    @Override
    public LocalTime getHoraInicio() {
        return this.hora;
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
        this.hora = hora;
    }

    @Override
    public void setDuracionEstimada(Double duracionEstimada) {
        this.duracionEstimada = duracionEstimada;
    }
    @Override 
    public int getCodigo() {
        return this.codigo;
    }
}