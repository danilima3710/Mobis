package com.mobis.trataError;

import android.view.View;

import com.mobis.apresentaMensagem.ApresentaMensagem;
import com.mobis.enumeradores.EnumCampos;

public class TrataErroCamposPreenchidos {

    public static boolean trataInconsistenciaInformacao(View view, EnumCampos campos) {

        String mensagem = "";
        switch (campos) {
            case TODOS_CAMPOS        : mensagem = "Nenhum campo foi preenchido"          ; break;
            case CAMPO_EMAIL         : mensagem = "Falta informar o email"               ; break;
            case CAMPO_SENHA         : mensagem = "Falta informar a senha"               ; break;
            case CAMPO_CONFIRMA_SENHA: mensagem = "Falta informar a confirmação da senha"; break;
            case CAMPO_NOME          : mensagem = "Falta informar o nome"                ; break;
            case CAMPO_PASSAGEIRO    : mensagem = "Falta informar o passageiro"          ; break;
            case DATA_HORA           : mensagem = "Falta informar a data e hora"         ; break;
            case ORIGEM              : mensagem = "Falta informar a origem da viagem"    ; break;
            case DESITNO             : mensagem = "Falta informar o destino da viagem"   ; break;
            case DATA                : mensagem = "Falta informar a data"                ; break;
            case VALOR               : mensagem = "Falta informar o valor"               ; break;
            case OBSERVACAO          : mensagem = "Falta informar a observação"          ; break;
            case TIPÒ_MOVIMENTACAO   : mensagem = "Falta informar o tipo da movimentação"; break;
            default: return false;
        }

        ApresentaMensagem.ApresentaMensagemRapida(view, mensagem);

        return true;
    }
}
