package com.mobis.trataError;

import android.view.View;

import com.mobis.apresentaMensagem.ApresentaMensagem;
import com.mobis.enumeradores.EnumErros;

public class TrataErroTelaCriarViagem {
    public static boolean trataErroAdicionar(View view, EnumErros erros) {

        String mensagem = "";

        switch (erros) {
            case PASSAGEIRO_JA_INFORMADO : mensagem = "Passageiro já selecionado"; break;
            case LIMITE_MAXIMO_PASSAGEIRO: mensagem = "Quantidade máxima já alcançada"; break;
            case PASSAGEIRO_NAO_INFORMADO: mensagem = "Nenhum passageiro selecionado"; break;
            case OBSERVACAO_NAO_INFORMADO: mensagem = "Observação não informada"; break;
            default: return false;
        }

        ApresentaMensagem.ApresentaMensagemRapida(view, mensagem);
        return true;
    }

    public static void trataErroCadastrar(View view, boolean result) {
        if (result)
            ApresentaMensagem.ApresentaMensagemRapida(view, "Viagem cadastrada com sucesso");
        else
            ApresentaMensagem.ApresentaMensagemRapida(view, "Erro ao cadastrar a viagem");
    }
}
