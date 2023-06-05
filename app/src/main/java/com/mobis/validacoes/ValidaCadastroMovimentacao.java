package com.mobis.validacoes;

import static com.mobis.enumeradores.EnumCampos.*;
import static com.mobis.enumeradores.EnumErros.*;

import com.mobis.enumeradores.EnumCampos;
import com.mobis.enumeradores.EnumErros;
import com.mobis.enumeradores.EnumMovimentacao;
import com.mobis.mascaras.DataHoraMascara;
import com.mobis.mascaras.DataMascara;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

    public static EnumErros validaDataPreenchida(String data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        if (!DataMascara.isDateTimeValid(data))
            return DATA_INVALIDA;

        return SEM_ERRO;
    }
}
