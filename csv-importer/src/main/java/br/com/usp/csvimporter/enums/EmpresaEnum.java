package br.com.usp.csvimporter.enums;

public enum EmpresaEnum {
    NETFLIX (1, "Netflix"),
    ADRENALINA_PURA (2, "adrenalinapurabr"),
    CURTAON (3, "curtaonbr"),
    MAX (4, "hbomaxbr"),
    MAX_2 (4, ""),
    RESERVA_IMOVISION (5, "imovisionbr"),
    LOOKE (6, "lookebr"),
    LOVE_NATURE (7, "lovenaturebr"),
    MGM (8, "mgmbr"),
    MUBI (9, "mubibr"),
    PARAMOUNT (10, "paramountplusbr"),
    PRIME_VIDEO (11, "prime video"),
    STINGRAY (12, "stingraybr"),
    TELECINE (13, "telecinechannelbr"),
    ALUGUE_NO_PRIME (14, "-");

    private  int codigo;
    private  String nome;

    EmpresaEnum(int codigo, String nome) {
        this.codigo = codigo;
        this.nome = nome;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    public static EmpresaEnum fromNome(String nome) {
        for (EmpresaEnum empresa : EmpresaEnum.values()) {
            if (empresa.nome.equals(nome)) {
                return empresa;
            }
        }
        throw new IllegalArgumentException("Nome de empresa inválido: " + nome);
    }
}
