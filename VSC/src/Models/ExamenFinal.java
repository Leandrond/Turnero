// Leandro
package Models;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class ExamenFinal extends InstanciaEvaluativa{
    //lista docente
    ArrayList<Docente> listaDocentes = new ArrayList<Docente>();

    // Constructor con atributos heredados de la clase InstanciaEvaluativa
    public ExamenFinal(
        String tipo, LocalDate fecha, LocalTime hora ,double duracionEstimada, Comision comision, Docente docente, int codigoDeInstancia, Turnero turnero) {    
        super.tipo = tipo;
        super.fecha = fecha;
        super.horaInicio = hora;
        super.duracionEstimada = duracionEstimada;
        super.comision = comision;
        this.listaDocentes.add(docente);
        super.codigoDeInstancia = codigoDeInstancia;
        super.turnero= turnero;
    }
    //getters y setters de los atributos heredados de la clase InstanciaEvaluativa
    @Override
    public String getTipo() {
        return this.tipo;
    }
    @Override
    public int getCodigo() {
        return codigo;
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
    public int getCodigoDeInstancia() {
        return this.codigoDeInstancia;
    }

    @Override
    public void setCodigoDeInstancia(int codigoDeInstancia) {
        this.codigoDeInstancia = codigoDeInstancia;
    }
    @Override
    public Turnero getTurnero() {
        return this.turnero;
    }

    public ArrayList<Docente> getListaDocente() {
            return this.listaDocentes; // Devuelve la lista completa de docentes
        }
    public void agregarDocente(Docente docente) {
        this.listaDocentes.add(docente);
    }

    @Override
    public Docente getDocente() {
        return this.docente;
    }

}