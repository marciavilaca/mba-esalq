package br.usp.scraper.prime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Filme {

    private String nome;
    private String sinopse;
    private String imagem;
    private String genero;
    private String url;
    private String classificacao;
    private String empresa;


    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append(empresa).append(";");
        sb.append(nome).append(";");
        sb.append(classificacao).append(";");
        sb.append(genero).append(";");
        sb.append(imagem).append(";");
        sb.append(url).append(";");
        sb.append(sinopse);
        return sb.toString();
    }
}
