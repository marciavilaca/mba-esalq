package br.com.usp.csvimporter.enums;

public enum ClassificacaoEtariaEnum {
    SEM_CLASSIFICACAO(1),
    LIVRE(2),
    DEZ_ANOS(3),
    DOZE_ANOS(4),
    QUATORZE_ANOS(5),
    DEZESSEIS_ANOS(6),
    DEZOITO_ANOS(7);

    private final int codigo;

    ClassificacaoEtariaEnum(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return codigo;
    }
}
