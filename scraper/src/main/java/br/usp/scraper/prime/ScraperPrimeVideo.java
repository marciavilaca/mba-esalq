package br.usp.scraper.prime;

import com.google.common.base.Charsets;
import com.opencsv.CSVWriter;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class ScraperPrimeVideo {
    private static final String URL_CATALOGO_FILMES_AMAZON = "https://www.primevideo.com/storefront/ref=atv_hm_offers_c_9zZ8D2_hm_mv?contentType=movie&contentId=home";
    private static final String URL_AMAZON = "https://www.primevideo.com";

    public static void main(String[] args) {
        log.info("Início da execução:" + LocalDate.now());
        List<Set<String>> interacoes = new ArrayList<>();
        Set<String> falhas = new HashSet<>();

        Map<String, Filme> filmes = new HashMap<>();

        Set<String> anterior = new HashSet<>();
        try {
            pegaFilmesNaPagina(URL_CATALOGO_FILMES_AMAZON, anterior, falhas, filmes);

            interacoes.add(anterior);
            int size = anterior.size();
            int interacao = 2;
            Set<String> atual = null;
            int total = anterior.size();
            while (size > 0 && interacao <= 30) {
                log.info("Inicio iteração: " + interacao + "-" + LocalDate.now());
                atual = new HashSet<>();
                for (String link : anterior) {
                    pegaFilmesNaPagina(link, atual, falhas, filmes);
                }
                removeUrlsDuplicadasDaLista(interacoes, atual, falhas);
                interacao = interacao + 1;
                anterior = atual;
                size = atual.size();
                total = total + size;
                log.info("Fim iteração: " + interacao + "-" + LocalDate.now());
            }

            geraCSVFalhas(falhas);
            geraCSVFilmes(filmes);
            log.info("FIM da execução:" + LocalDate.now());
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public static void geraCSVFilmes(Map<String, Filme> filmes) throws IOException {
        log.info("Inicio - geraCSVFilmes" + LocalDate.now());
        String csv = "filmes_prime.csv";
        CSVWriter csvWriter = new CSVWriter(new FileWriter(csv, Charsets.ISO_8859_1, true), ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        List<String[]> linhas = new ArrayList<>();

        for (Filme filme : filmes.values()) {
            linhas.add(new String[]{filme.getEmpresa(),"Movie", filme.getNome(), filme.getClassificacao(), filme.getGenero()
                    , filme.getImagem(), filme.getUrl(), filme.getSinopse()});
        }
        csvWriter.writeAll(linhas);

        csvWriter.flush();
        csvWriter.close();
        log.info("Fim - geraCSVFilmes" + LocalDate.now());
    }

    public static void removeUrlsDuplicadasDaLista(List<Set<String>> interacoes, Set<String> atual, Set<String> falhas) {
        log.info("Antes da limpeza total:" + atual.size());
        for (Set<String> interacao : interacoes) {
            for (String url : interacao) {
                atual.remove(url);
            }
        }
        for (String url : falhas) {
            atual.remove(url);
        }
        interacoes.add(atual);
        log.info("Depois da limpeza total:" + atual.size());
    }

    public static void pegaFilmesNaPagina(String url, Set<String> links, Set<String> falhas, Map<String, Filme> filmes) {
        try {
            Document document = Jsoup.connect(url).header("Accept-Language", "pt-BR").get();
            Elements elementsList = document.select("article[data-card-entity-type=Movie]");
            String urlFilme = "";
            for (Element element : elementsList) {
                urlFilme = URL_AMAZON + element.getElementsByTag("a").get(0).attr("href");
                if (filmes.containsKey(urlFilme) == false) {
                    links.add(urlFilme);
                    Filme filme = preencheDadosFilme(falhas, element.getElementsByTag("img").first().attr("src"),
                            urlFilme
                            , element.attr("data-card-title"));
                    filmes.put(urlFilme, filme);
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            falhas.add(url);
        }
    }

    private static Filme preencheDadosFilme(Set<String> falhas, String urlImagem, String urlFilme, String nome) {
        Filme filme = new Filme();
        try {
            filme.setUrl(urlFilme);
            filme.setImagem(urlImagem);
            filme.setNome(nome);
            Document document = Jsoup.connect(urlFilme).header("Accept-Language", "pt-BR").get();
            Element sinopse = document.select("span[class=_1H6ABQ]").first();
            if (Objects.nonNull(sinopse)) {
                filme.setSinopse(sinopse.text());
            }
            Element classificacaoEtaria = document.select("span[data-testid=rating-badge]").first();
            if (Objects.nonNull(classificacaoEtaria)) {
                filme.setClassificacao(classificacaoEtaria.attr("aria-label"));
            }
            Element empresa = document.select("a[data-testid=channelLogo-link]").first();
            if (Objects.nonNull(empresa)) {
                String contentId = getContentIdFromHref(empresa.attr("href"));
                filme.setEmpresa(contentId);
            } else {
                Element primeImage = document.select("img[data-testid=channelLogo-image]").first();
                if (Objects.nonNull(primeImage)) {
                    String img = primeImage.attr("src");
                    if (img.contains("primeLogo")) {
                        filme.setEmpresa("prime video");
                    } else {
                        filme.setEmpresa("-");
                    }
                } else {
                    filme.setEmpresa("-");
                }
            }

            Elements generos = document.getElementsByClass("dv-node-dp-genres").select("a[class=_1NNx6V]");
            if (Objects.nonNull(generos)) {
                List<String> generosList = new ArrayList<>();
                for (Element genero : generos) {
                    generosList.add(genero.text());
                }
                filme.setGenero(String.join(",", generosList));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            falhas.add(urlFilme);
        }
        return filme;
    }

    private static String getContentIdFromHref(String href) {
        String[] parts = href.split("&");
        for (String part : parts) {
            if (part.startsWith("contentId=")) {
                return part.substring(10); // 10 é o comprimento de "contentId="
            }
        }
        return null;
    }


    public static void geraCSVFalhas(Set<String> urlFilmes) throws IOException {
        log.info("csv  falhas");
        String csv = "falhas.csv";
        CSVWriter csvWriter = new CSVWriter(new FileWriter(csv, true), ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        List<String[]> linhas = new ArrayList<>();

        for (String urlFilme : urlFilmes) {
            linhas.add(new String[]{"Falha", urlFilme});
        }
        csvWriter.writeAll(linhas);
        csvWriter.flush();
        csvWriter.close();
        log.info("fim csv falhas ");
    }
}
