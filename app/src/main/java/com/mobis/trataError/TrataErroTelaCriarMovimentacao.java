package com.mobis.trataError;

import android.view.View;

import com.mobis.apresentaMensagem.ApresentaMensagem;
import com.mobis.enumeradores.EnumErros;

public class TrataErroTelaCriarMovimentacao {
    public static void trataErroCadastrar(View view, boolean result) {
        if (result)
            ApresentaMensagem.ApresentaMensagemRapida(view, "Movimentação cadastrada com sucesso");
        else
            ApresentaMensagem.ApresentaMensagemRapida(view, "Erro ao cadastrar a movimentação");
    }

    public static boolean trataErroData(View view, EnumErros erros) {

        String mensagem = "";

        switch (erros) {
            case DATA_INVALIDA       : mensagem = "Data está num formato inválidos"       ;break;
            case DATA_MENOR_QUE_ATUAL: mensagem = "A data deve ser maior que a data atual";break;
            default: return false;
        }

        ApresentaMensagem.ApresentaMensagemRapida(view, mensagem);
        return true;
    }

    public static void trataErroEditar(View view, boolean result) {
        if (result)
            ApresentaMensagem.ApresentaMensagemRapida(view, "Movimentação editada com sucesso");
        else
            ApresentaMensagem.ApresentaMensagemRapida(view, "Erro ao editar a movimentação");
    }

    public static void trataErroRemover(View view, boolean result) {
        if (result)
            ApresentaMensagem.ApresentaMensagemRapida(view, "Movimentação removida com sucesso");
        else
            ApresentaMensagem.ApresentaMensagemRapida(view, "Erro ao remover a movimentação");
    }
}
