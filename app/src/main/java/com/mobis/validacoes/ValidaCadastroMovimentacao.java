package com.mobis.validacoes;

import static com.mobis.enumeradores.EnumCampos.*;

import com.mobis.enumeradores.EnumCampos;
import com.mobis.enumeradores.EnumMovimentacao;

public class ValidaCadastroMovimentacao {
    public static EnumCampos validaCamposPreenchidos(String data, float valor, String observacao, EnumMovimentacao.TipoMovimentacao enumMovimentacao) {
        if (data.trim().isEmpty() && observacao.trim().isEmpty() && valor <= 0f && enumMovimentacao == EnumMovimentacao.TipoMovimentacao.NAO_RESPONDER)
            return TODOS_CAMPOS;

        if (data.trim().isEmpty())
            return DATA;

        if (observacao.trim().isEmpty())
            return OBSERVACAO;

        if (valor <= 0f)
            return VALOR;

        if (enumMovimentacao == EnumMovimentacao.TipoMovimentacao.NAO_RESPONDER)
            return TIPÒ_MOVIMENTACAO;

        return CAMPO_VALIDO;
    }
}
