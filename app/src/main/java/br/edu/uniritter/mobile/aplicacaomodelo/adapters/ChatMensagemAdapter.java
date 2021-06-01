package br.edu.uniritter.mobile.aplicacaomodelo.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import br.edu.uniritter.mobile.aplicacaomodelo.databinding.LayoutChatmensagemRcBinding;
import br.edu.uniritter.mobile.aplicacaomodelo.model.ChatMensagem;

public class ChatMensagemAdapter extends RecyclerView.Adapter<ChatMensagemAdapter.ChatViewHolder> {

    private List<ChatMensagem> mensagens;

    public ChatMensagemAdapter(List<ChatMensagem> lista) {
        Log.i("ChatAdapter"," on Construtor() "+lista.size());
        mensagens = lista;
    }

    @NonNull
    @NotNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        Log.i("ChatAdapter"," on createViewHolder()");
        LayoutChatmensagemRcBinding l = LayoutChatmensagemRcBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ChatViewHolder(l);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ChatViewHolder holder, int position) {
        holder.layout.setObjChat(mensagens.get(position));
    }

    public void setChats(List<ChatMensagem> lista) {
        this.mensagens = lista;
    }
    @Override
    public int getItemCount() {
        return mensagens.size();
    }
    @BindingAdapter({"chatAdapterImageUrl"})
    public static void loadImage(ImageView view, String url) {
        Picasso.get().load(url).into(view);
    }

    public class ChatViewHolder extends RecyclerView.ViewHolder {

        //troquei o viewTodo de View para LayoutBindind
        public LayoutChatmensagemRcBinding layout;

        //troquei o itemView de View para LayoutBindind
        public ChatViewHolder(@NonNull LayoutChatmensagemRcBinding itemView) {
            //alterei aqui colocando o .getRoot()
            super(itemView.getRoot());
            this.layout = itemView;
        }


    }


}
