package br.edu.uniritter.mobile.aplicacaomodelo.presenters;

import java.util.List;

import br.edu.uniritter.mobile.aplicacaomodelo.model.ChatMensagem;

public interface MainPresenterContrato {

    interface PresenterContrato {
        public void buscarDados();
        public void marcaLido(ChatMensagem chat, boolean lido);
        public void gravaMensagem(String usuario, String msg);

    }

    interface ViewContrato {
        public void carregaRecyclerView(List<ChatMensagem> mensagens);
        public void escondeEditMensagem();
        public void mostraEditMensagem();

    }


}
