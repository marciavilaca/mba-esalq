package br.usp.scraper.netflix;

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
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class CrawlerNetflix {
    private static final String URL_TITLE_NETFLIX = "https://www.netflix.com/br/title/";
    private static final String URL_NETFLIX = "https://www.netflix.com";
    private static final String URL_CATALOGO_FILMES_NETFLIX = "https://www.netflix.com/br/browse/genre/34399";

    public static void main(String[] args) throws IOException {
        List<Set<String>> interacoes = new ArrayList<>();
        log.info(String.valueOf(new Date()));
        Map<String, Titulo> filmes = new HashMap<>();

        Set<String> falhas = new HashSet<>();
        Set<String> anterior = pegaTitulosPaginaPrincipal();
        interacoes.add(anterior);
        log.info("primeira leva: " + anterior.size());
        log.info(String.valueOf(new Date()));
        geraCSVFilmes(anterior, falhas);
        log.info(String.valueOf(new Date()));

        int size = anterior.size();
        int interacao = 2;
        Set<String> atual = null;
        while (size > 0 && interacao <= 30) {
            atual = new HashSet<>();
            atual = pegaTitulosSemelhantesPaginaFilme(anterior, falhas);
            removeUrlsDuplicadasDaLista(interacoes, atual, falhas);
            log.info(String.valueOf(new Date()));

            geraCSVFilmes(atual, falhas);
            log.info(String.valueOf(new Date()));
            interacao = interacao + 1;
            anterior = atual;
            size = atual.size();
        }
        geraCSVFalhas(falhas);
        log.info("FIM DA EXECUÇÃO - " + LocalDate.now());

    }

    public static Set<String> pegaTitulosPaginaPrincipal() {
        Set<String> listaLinksTitulos = new HashSet<>();
        try {
            Document document = Jsoup.connect(URL_CATALOGO_FILMES_NETFLIX).header("Accept-Language", "pt-BR").get();

            Elements elementsList = document.select("section[class=nm-collections-row]");

            for (Element element : elementsList) {

                Elements idUrls = element.select("img[class=nm-collections-title-img]"); //img com o id do filme pra montar o link
                String url = "";
                for (Element e : idUrls) {
                    url = URL_TITLE_NETFLIX + e.attr("data-title-id");
                    listaLinksTitulos.add(url);// id do filme
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return listaLinksTitulos;
    }


    public static Set<String> pegaTitulosSemelhantesPaginaFilme(Set<String> listaLinksTitulos, Set<String> falhas) {
        Set<String> listaLinksTitulosSemelhantes = new HashSet<>();
        Document document = null;

        for (String url : listaLinksTitulos) {
            document = null;
            try {
                document = Jsoup.connect(url).header("Accept-Language", "pt-BR").get();

                Element element = document.getElementById("section-more-titles");

                if (Objects.nonNull(element)) {
                    Elements list = element.select("a[class=title-link]");

                    for (Element subElement : list) {
                        listaLinksTitulosSemelhantes.add(URL_NETFLIX + subElement.attr("href"));
                    }
                } else {
                    falhas.add(url);
                }

            } catch (Exception e) {
                falhas.add(url);
                log.error(e.getMessage());
            }

        }
        return listaLinksTitulosSemelhantes;
    }

    public static void geraCSVFalhas(Set<String> urlFilmes) throws IOException {
        log.info("Inicio geraCSVFalhas - " + LocalDate.now());
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
        log.info("Fim geraCSVFalhas - " + LocalDate.now());
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

    public static Titulo pegaDadosFilme(String url) throws IOException {
        Document document = null;
        document = Jsoup.connect(url).header("Accept-Language", "pt-BR").get();
        Elements elementsList = document.select("script[type=application/ld+json]");
        String json = elementsList.get(0).html();

        Titulo titulo = new Titulo(json);
        return titulo;
    }

    public static void geraCSVFilmes(Set<String> urlFilmes, Set<String> falhas) throws IOException {
        log.info("Inicio - geraCSVFilmes" + LocalDate.now());
        String csv = "base_full_filmes_netflix.csv";
        CSVWriter csvWriter = new CSVWriter(new FileWriter(csv, Charsets.ISO_8859_1, true), ';',
                CSVWriter.NO_QUOTE_CHARACTER,
                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                CSVWriter.DEFAULT_LINE_END);

        List<String[]> linhas = new ArrayList<>();

        for (String url : urlFilmes) {
            try {
                Titulo filme = pegaDadosFilme(url);
                linhas.add(new String[]{"Netflix", filme.getType(), filme.getName(), filme.getContentRating(), filme.getGenre()
                        , filme.getImage(), filme.getUrl(), filme.getDescription()});
            } catch (Exception e) {
                e.printStackTrace();
                falhas.add(url);
            }
        }
        csvWriter.writeAll(linhas);
        csvWriter.flush();
        csvWriter.close();
        log.info("Fim - geraCSVFilmes" + LocalDate.now());
    }
}
