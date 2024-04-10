package br.com.usp.csvimporter.enums;

public enum GeneroEnum {
    FICCAO_CIENTIFICA(1, "Ficção científica"),
    COMEDIA(2, "Comédia"),  
    ROMANCE(3, "Romance"),
    SUSPENSE(4, "Suspense"),
    INFANTIS(5, "Infantis"),
    INFANTIL(5, "Infantil"),
    DOCUMENTARIO(6, "Documentário"),
    DOCUMENTARIOS(6, "Documentários"),
    TERROR(7, "Terror"),
    ACAO(8, "Ação"),
    ANIME(9, "Anime"),
    ESPORTES(10, "Esportes"),
    DRAMA(11, "Drama"),
    FAROESTE(12, "Faroeste"), 
    MUSICA(13, "Música"),
    MUSICAL(13, "Musical"),
    FANTASIA(14, "Fantasia"),
    MILITAR_E_GERRA(15,"Militar e Guerra"),
    AVENTURA(16, "Aventura"),
    HISTORICO(17, "Histórico"),
    LGBTQ(18, "LGBTQ"),  
    FE_E_ESPIRITUALIDADE(19, "Fé e Espiritualidade"),
    ANIMACOES(20, "Animações"),   
    VIDEOCLIPES_E_SHOWS(21, "Videoclipes e Shows"),
    ARTES(22, "Artes");

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
