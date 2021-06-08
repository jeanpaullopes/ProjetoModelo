package br.edu.uniritter.mobile.aplicacaomodelo.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.edu.uniritter.mobile.aplicacaomodelo.model.ChatMensagem;

public class FonteDados {
    private static Map<String,ChatMensagem> alunos  = new HashMap<>();
    private static Map<String,Object> turmas = new HashMap<>();

    public static void putAluno(ChatMensagem aluno) {
        alunos.put(aluno.getId(), aluno);
        List a =  new ArrayList(alunos.values());
    }
    public static ChatMensagem getAluno(String id) {
        return  alunos.get(id);
        //FonteDados.putAluno();
    }
}
