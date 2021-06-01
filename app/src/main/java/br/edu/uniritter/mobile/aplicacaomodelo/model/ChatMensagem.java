package br.edu.uniritter.mobile.aplicacaomodelo.model;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class ChatMensagem {

    @DocumentId
    private String id;

    private String user;
    private String mensagem;
    private Timestamp datahora;
    private boolean lido;
    @Exclude
    public String url = "https://100maisnemmenos.files.wordpress.com/2009/07/rh_2009.jpg";

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getDataFormatada() {
        DateFormat df = new SimpleDateFormat("dd/MM/YYYY HH:mm");
        return df.format(getDatahora().toDate());
    }

    public boolean isLido() {
        return lido;
    }

    public void setLido(boolean lido) {
        this.lido = lido;
    }

    public String toString() {
        return "id: "+this.id+"\n+" +
                "Nome: "+this.user+"\n"+
                "data: "+this.datahora+"\n"+
                this.mensagem+"\n"+"\n";
    }
}
