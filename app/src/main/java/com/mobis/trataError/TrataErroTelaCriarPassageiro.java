package com.mobis.trataError;

import android.view.View;

import com.mobis.apresentaMensagem.ApresentaMensagem;

public class TrataErroTelaCriarPassageiro {
    public static void trataErroCadastrar(View view, boolean result) {
        if (result)
            ApresentaMensagem.ApresentaMensagemRapida(view, "Passageiro cadastrado com sucesso");
        else
            ApresentaMensagem.ApresentaMensagemRapida(view, "Erro ao cadastrar o passageiro");
    }

    public static void trataErroEditar(View view, boolean result) {
        if (result)
            ApresentaMensagem.ApresentaMensagemRapida(view, "Passageiro editada com sucesso");
        else
            ApresentaMensagem.ApresentaMensagemRapida(view, "Erro ao editar o passageiro");
    }

    public static void trataErroRemover(View view, boolean result) {
        if (result)
            ApresentaMensagem.ApresentaMensagemRapida(view, "Passageiro removido com sucesso");
        else
            ApresentaMensagem.ApresentaMensagemRapida(view, "Erro ao remover o passageiro");
    }
}
