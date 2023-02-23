package com.mobis.trataError;

import android.view.View;

import com.mobis.apresentaMensagem.ApresentaMensagem;
import com.mobis.enumeradores.EnumCampos;

public class TrataErroCamposPreenchidos {

    public static boolean trataInconsistenciaInformacao(View view, EnumCampos.campos campos) {

        String mensagem = "";
        switch (campos) {
            case TODOS_CAMPOS: mensagem = "Nenhum campo foi preenchido"; break;
            case CAMPO_EMAIL: mensagem = "Falta informar o email"; break;
            case CAMPO_SENHA: mensagem = "Falta informar a senha"; break;
            case CAMPO_CONFIRMA_SENHA: mensagem = "Falta informar a confirmação da senha"; break;
            default: return false;
        }

        ApresentaMensagem.ApresentaMensagemRapida(view, mensagem);

        return true;
    }
}
