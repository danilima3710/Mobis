package com.mobis.trataError;

import android.view.View;

import com.mobis.apresentaMensagem.ApresentaMensagem;

public class TrataErroTelaCriarMovimentacao {
    public static void trataErroCadastrar(View view, boolean result) {
        if (result)
            ApresentaMensagem.ApresentaMensagemRapida(view, "Movimentação cadastrada com sucesso");
        else
            ApresentaMensagem.ApresentaMensagemRapida(view, "Erro ao cadastrar a movimentação");
    }
}
