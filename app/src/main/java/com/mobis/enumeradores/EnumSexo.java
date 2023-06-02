package com.mobis.enumeradores;

public enum EnumSexo {
    MASCULINO,
    FEMININO,
    INDEFINIDO,
    OUTRO,
    NAO_RESPONDER;

    public static EnumSexo stringToEnum(String opcao) {
        switch (opcao) {
            case "Masculino": return MASCULINO;
            case "Feminino": return FEMININO;
            case "Indefinido": return INDEFINIDO;
            case "Outro": return OUTRO;
            default: return NAO_RESPONDER;
        }
    }
}
