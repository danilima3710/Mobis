package com.mobis.trataError;

import static com.mobis.enumeradores.EnumErros.FINALIZANDO_VIAGEM_ANTES;

import android.view.View;

import com.mobis.apresentaMensagem.ApresentaMensagem;
import com.mobis.enumeradores.EnumErros;

public class TrataErroTelaCriarViagem {
    public static boolean trataErroAdicionar(View view, EnumErros erros) {

        String mensagem = "";

        switch (erros) {
            case PASSAGEIRO_JA_INFORMADO  : mensagem = "Passageiro já selecionado"                            ; break;
            case LIMITE_MAXIMO_PASSAGEIRO : mensagem = "Quantidade máxima já alcançada"                       ; break;
            case PASSAGEIRO_NAO_INFORMADO : mensagem = "Nenhum passageiro selecionado"                        ; break;
            case OBSERVACAO_NAO_INFORMADO : mensagem = "Observação não informada"                             ; break;
            case DATA_HORA_INVALIDA       : mensagem = "Data e hora estão num formato inválidos"              ; break;
            case DATA_HORA_MENOR_QUE_ATUAL: mensagem = "A data e hora devem ser maior que a data e hora atual"; break;
            default: return false;
        }

        ApresentaMensagem.ApresentaMensagemRapida(view, mensagem);
        return true;
    }

    public static boolean trataErroDataHora(View view, EnumErros erros) {

        String mensagem = "";

        switch (erros) {
            case DATA_HORA_INVALIDA       : mensagem = "Data e hora estão num formato inválidos"              ;break;
            case DATA_HORA_MENOR_QUE_ATUAL: mensagem = "A data e hora devem ser maior que a data e hora atual";break;
            default: return false;
        }

        ApresentaMensagem.ApresentaMensagemRapida(view, mensagem);
        return true;
    }

    public static boolean trataErroCadastrar(View view, boolean result) {
        if (result) {
            ApresentaMensagem.ApresentaMensagemRapida(view, "Viagem cadastrada com sucesso");
            return true;
        }
        else
            ApresentaMensagem.ApresentaMensagemRapida(view, "Erro ao cadastrar a viagem");

        return false;
    }

    public static void trataErroEditar(View view, boolean result) {
        if (result)
            ApresentaMensagem.ApresentaMensagemRapida(view, "Viagem editada com sucesso");
        else
            ApresentaMensagem.ApresentaMensagemRapida(view, "Erro ao editar a viagem");
    }

    public static void trataErroRemover(View view, boolean result) {
        if (result)
            ApresentaMensagem.ApresentaMensagemRapida(view, "Viagem removida com sucesso");
        else
            ApresentaMensagem.ApresentaMensagemRapida(view, "Erro ao remover a viagem");
    }

    public static void trataErroFinalizar(View view, boolean result) {
        if (result)
            ApresentaMensagem.ApresentaMensagemRapida(view, "Viagem finalizada com sucesso");
        else
            ApresentaMensagem.ApresentaMensagemRapida(view, "Erro ao finalizar a viagem");
    }

    public static boolean trataErroFinalizar(View view, EnumErros erros) {
        if (erros == FINALIZANDO_VIAGEM_ANTES) {
            ApresentaMensagem.ApresentaMensagemRapida(view, "A viagem não pode ser finalizada pois a data e hora atual é menor que a data e hora da viagem");
            return true;
        }

        return false;
    }
}
