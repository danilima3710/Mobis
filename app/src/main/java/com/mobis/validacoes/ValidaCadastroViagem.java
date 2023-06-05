package com.mobis.validacoes;

import static com.mobis.enumeradores.EnumCampos.*;
import static com.mobis.enumeradores.EnumErros.*;

import com.mobis.enumeradores.EnumCampos;
import com.mobis.enumeradores.EnumErros;
import com.mobis.mascaras.DataHoraMascara;
import com.mobis.models.Passageiro;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ValidaCadastroViagem {
    public static EnumErros validaAdicionarPassageiro(List<Passageiro> passageiroList, Passageiro passageiroAtual) {
        if (passageiroList.size() >= 4)
            return LIMITE_MAXIMO_PASSAGEIRO;

        if (passageiroAtual == null)
            return PASSAGEIRO_NAO_INFORMADO;

        if (verificarPassageiroSelecionado(passageiroList, passageiroAtual))
            return PASSAGEIRO_JA_INFORMADO;

        return SEM_ERRO;
    }

    public static EnumErros validaAdicionarObservacao(String observacao) {
        if (observacao.trim().isEmpty())
            return OBSERVACAO_NAO_INFORMADO;

        return SEM_ERRO;
    }

    public static boolean verificarPassageiroSelecionado(List<Passageiro> passageiroList, Passageiro passageiro) {
        for (Passageiro passageiroSelecionado : passageiroList) {
            return passageiro.getId().equals(passageiroSelecionado.getId());
        }

        return false;
    }

    public static EnumCampos validaCamposPreenchidos(String dataHora, String origem, String destino, float valorPassageiro) {
        if (dataHora.trim().isEmpty() && origem.trim().isEmpty() && destino.trim().isEmpty() && valorPassageiro == 0)
            return TODOS_CAMPOS;

        if (dataHora.trim().isEmpty())
            return DATA_HORA;

        if (origem.trim().isEmpty())
            return ORIGEM;

        if (destino.trim().isEmpty())
            return DESITNO;

        if (valorPassageiro == 0)
            return VALOR;

        return CAMPO_VALIDO;
    }

    public static EnumErros validaDataHoraPreenchida(String data) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        try {
            if (!DataHoraMascara.isDateTimeValid(data))
                return DATA_HORA_INVALIDA;

            Date dataHoraAtual = new Date();
            Date dataHora = dateFormat.parse(data);

            if (dataHora.before(dataHoraAtual))
                return DATA_HORA_MENOR_QUE_ATUAL;

            return SEM_ERRO;
        }
         catch (ParseException e) {
            return ERRO;
        }
    }

    public static EnumErros validaFinalizarViagem(String data) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());

        Date dataHoraAtual = new Date();
        Date dataHora = dateFormat.parse(data);

        if (dataHora.before(dataHoraAtual))
            return FINALIZANDO_VIAGEM_ANTES;

        return SEM_ERRO;
    }
}
