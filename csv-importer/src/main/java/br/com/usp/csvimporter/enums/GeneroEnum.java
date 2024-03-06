package br.com.usp.csvimporter.enums;

public enum GeneroEnum {
    FICCAO_CIENTIFICA(1, "Ficção científica"),
    COMEDIA(2, "Comédia"),
    VARIEDADES(35, "Variedades"),
    ROMANCE(4, "Romance"),
    SUSPENSE(5, "Suspense"),
    INFANTIS(6, "Infantis"),
    INFANTIL(6, "Infantil"),
    DOCUMENTARIO(7, "Documentário"),
    DOCUMENTARIOS(7, "Documentários"),
    TERROR(8, "Terror"),
    ACAO(9, "Ação"),
    ANIME(10, "Anime"),
    ESPORTES(11, "Esportes"),
    DRAMA(12, "Drama"),
    FAROESTE(13, "Faroeste"),
    REALITY_SHOWS(14, "Reality shows"),
    TEMA_ESPECIAL(15, "Tema especial"),
    TALK_SHOW(35, "Talk Show"),
    MUSICA(17, "Música"),
    MUSICAL(17, "Musical"),
    FANTASIA(19, "Fantasia"),
    MILITAR_E_GERRA(20,"Militar e Guerra"),
    AVENTURA(21, "Aventura"),
    HISTORICO(22, "Histórico"),
    LGBTQ(23, "LGBTQ"),
    INTERNACIONAIS(24, "Internacionais"),
    FE_E_ESPIRITUALIDADE(25, "Fé e Espiritualidade"),
    ANIMACOES(26, "Animações"),
    EPG_VIDEOCLIPES_E_SHOWS(27, "EPG Videoclipes e Shows"),
    VIDEOCLIPES_E_SHOWS(27, "Videoclipes e Shows"),
    ARTES(28, "Artes"),
    ENTRETENIMENTO_E_CULTURA(29, "Entretenimento e Cultura"),
    PUBLICO_YOUNG_ADULT(30, "Público Young Adult (Jovens Adultos)"),
    EPG(31, "EPG"),
    INTERESSES_ESPECIAIS(32, "Interesses Especiais"),
    ARTHOUSE(33, "Arthouse"),
    SEM_ROTEIRO(34,"Sem roteiro"),
    TALK_SHOW_VARIEDADES(35,"Talk Shows e Variedades"),
    EROTICO(36,"Erótico");

    private  int codigo;
    private  String nome;

    GeneroEnum(int codigo, String nome) {
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

    public static GeneroEnum fromNome(String nome) {
        for (GeneroEnum genero : GeneroEnum.values()) {
            if (genero.nome.equals(nome)) {
                return genero;
            }
        }
        throw new IllegalArgumentException("Nome de gênero inválido: " + nome);
    }
}
