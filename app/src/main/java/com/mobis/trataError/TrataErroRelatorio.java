package com.mobis.trataError;

import android.view.View;

import com.mobis.apresentaMensagem.ApresentaMensagem;
import com.mobis.enumeradores.EnumErros;

public class TrataErroRelatorio {
    public static boolean trataErroFiltro(View view, EnumErros erros) {
        String mensagem = "";

        switch (erros) {
            case DATA_INICIAL_INVALIDA      : mensagem = "A data inicial está incorreta"; break;
            case DATA_FINAL_INVALIDA        : mensagem = "A data final está incorreta"; break;
            case DATA_INICIAL_DEVE_SER_MENOR: mensagem = "A data inicial deve ser menor que a data final"; break;
            default: return false;
        }

        ApresentaMensagem.ApresentaMensagemRapida(view, mensagem);
        return true;
    }
}
