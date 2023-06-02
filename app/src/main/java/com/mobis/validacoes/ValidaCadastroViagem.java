package com.mobis.validacoes;

import static com.mobis.enumeradores.EnumCampos.*;
import static com.mobis.enumeradores.EnumErros.*;

import com.mobis.enumeradores.EnumCampos;
import com.mobis.enumeradores.EnumErros;
import com.mobis.models.Passageiro;

import java.util.List;

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

    public static EnumCampos validaCamposPreenchidos(String dataHora, String origem, String destino) {
        if (dataHora.trim().isEmpty() && origem.trim().isEmpty() && destino.trim().isEmpty())
            return TODOS_CAMPOS;

        if (dataHora.trim().isEmpty())
            return DATA_HORA;

        if (origem.trim().isEmpty())
            return ORIGEM;

        if (destino.trim().isEmpty())
            return DESITNO;

        return CAMPO_VALIDO;
    }
}
