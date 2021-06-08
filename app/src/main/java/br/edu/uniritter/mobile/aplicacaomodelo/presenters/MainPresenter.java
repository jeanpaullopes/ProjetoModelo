package br.edu.uniritter.mobile.aplicacaomodelo.presenters;

import android.util.Log;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.edu.uniritter.mobile.aplicacaomodelo.R;
import br.edu.uniritter.mobile.aplicacaomodelo.adapters.ChatMensagemAdapter;
import br.edu.uniritter.mobile.aplicacaomodelo.model.Address;
import br.edu.uniritter.mobile.aplicacaomodelo.model.ChatMensagem;
import br.edu.uniritter.mobile.aplicacaomodelo.model.Geo;
import br.edu.uniritter.mobile.aplicacaomodelo.model.User;
import br.edu.uniritter.mobile.aplicacaomodelo.services.ChatMensagemServices;

public class MainPresenter implements MainPresenterContrato.PresenterContrato {

    private MainPresenterContrato.ViewContrato view;

    public MainPresenter(MainPresenterContrato.ViewContrato view) {
        this.view = view;
    }
    public void destroy() {
        this.view = null;
    }
    @Override
    public void buscarDados() {
        ChatMensagemServices.getChatMensagens(new ChatMensagemServices.ChatMensagensCallback() {
            @Override
            public void OnListChatMensagemCallback(List<ChatMensagem> lista) {
                String txt = "";
                for(ChatMensagem msg : lista) {
                    if (msg.getObjUser() != null) {
                        Log.i("Principal", msg.toString());
                    }
                }
                view.carregaRecyclerView(lista);


            }
        });
    }

    @Override
    public void marcaLido(ChatMensagem chat, boolean lido) {
        Log.d("Principal",chat.getId()+" "+chat.isLido());
        chat.setLido( lido );
        ChatMensagemServices.gravaChatMensagem(chat);
    }

    @Override
    public void gravaMensagem(String usuario, String msg) {
        ChatMensagem chat =  new ChatMensagem(usuario, msg,
                new Timestamp(Calendar.getInstance().getTime()));
        Address add = new Address("String street", "String suite", "String city", "String zipcode", new Geo(0.0f,10.0f));
        User user = new User(123, "Jean paul lopes", "jean.paul", "jean.paul@uniritter.edu.br",add);
        chat.setObjUser(user);
        List<Geo> lista =  new ArrayList<>();
        lista.add(new Geo(1.0f, 2.0f));
        lista.add(new Geo(2.0f, 2.0f));
        lista.add(new Geo(3.0f, 2.0f));
        lista.add(new Geo(4.0f, 2.0f));
        chat.lista = lista;
        ChatMensagemServices.gravaChatMensagem(chat);
        view.escondeEditMensagem();
    }
}
