package com.mobis.validacoes;

import static com.mobis.enumeradores.EnumErros.*;

import com.mobis.enumeradores.EnumErros;
import com.mobis.mascaras.DataMascara;

import java.text.ParseException;
import java.util.Date;

public class ValidaRelatorio {

    public static EnumErros validaFiltros(String sDataInicial, String sDataFinal) throws ParseException {
        if (!sDataInicial.trim().isEmpty() && !DataMascara.isDateTimeValid(sDataInicial))
            return DATA_INICIAL_INVALIDA;

        if (!sDataFinal.trim().isEmpty() && !DataMascara.isDateTimeValid(sDataFinal))
            return DATA_FINAL_INVALIDA;

        if (sDataInicial.trim().isEmpty() || sDataFinal.trim().isEmpty())
            return SEM_ERRO;

        Date dataInicial = DataMascara.convertStringToDate(sDataInicial);
        Date dataFinal   = DataMascara.convertStringToDate(sDataFinal);

        if (dataFinal.before(dataInicial) || dataInicial.after(dataFinal))
            return DATA_INICIAL_DEVE_SER_MENOR;

        return SEM_ERRO;
    }
}
