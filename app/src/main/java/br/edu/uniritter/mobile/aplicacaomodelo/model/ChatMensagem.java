package br.edu.uniritter.mobile.aplicacaomodelo.model;

import com.google.firebase.Timestamp;

public class ChatMensagem {

    private String user;
    private String mensagem;
    private Timestamp datahora;

    public ChatMensagem() {
        super();
    }

    public ChatMensagem(String user, String mensagem, Timestamp datahora) {
        this.user = user;
        this.mensagem = mensagem;
        this.datahora = datahora;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Timestamp getDatahora() {
        return datahora;
    }

    public void setDatahora(Timestamp dataHora) {
        this.datahora = dataHora;
    }


    public String toString() {
        return "Nome: "+this.user+"\n"+
                "data: "+this.datahora+"\n"+
                this.mensagem;
    }
}
