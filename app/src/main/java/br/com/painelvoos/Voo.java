package br.com.painelvoos;

/**
 * Created by helenamcfreire on 08/04/15.
 */
public class Voo {

    private String horario;
    private String destino;
    private String urlLogoCiaAerea;
    private String numeroVoo;
    private String terminal;
    private String portao;
    private String horarioEstimado;
    private String status;

    public Voo() {

    }

    public Voo(String horario, String destino, String urlLogoCiaAerea, String numeroVoo,
               String terminal, String portao, String horarioEstimado, String status) {
        this.horario = horario;
        this.destino = destino;
        this.urlLogoCiaAerea = urlLogoCiaAerea;
        this.numeroVoo = numeroVoo;
        this.terminal = terminal;
        this.portao = portao;
        this.horarioEstimado = horarioEstimado;
        this.status = status;
    }

    public String getHorario() {
        return horario;
    }

    public void setHorario(String horario) {
        this.horario = horario;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getUrlLogoCiaAerea() {
        return urlLogoCiaAerea;
    }

    public void setUrlLogoCiaAerea(String urlLogoCiaAerea) {
        this.urlLogoCiaAerea = urlLogoCiaAerea;
    }

    public String getNumeroVoo() {
        return numeroVoo;
    }

    public void setNumeroVoo(String numeroVoo) {
        this.numeroVoo = numeroVoo;
    }

    public String getTerminal() {
        return terminal;
    }

    public void setTerminal(String terminal) {
        this.terminal = terminal;
    }

    public String getPortao() {
        return portao;
    }

    public void setPortao(String portao) {
        this.portao = portao;
    }

    public String getHorarioEstimado() {
        return horarioEstimado;
    }

    public void setHorarioEstimado(String horarioEstimado) {
        this.horarioEstimado = horarioEstimado;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
