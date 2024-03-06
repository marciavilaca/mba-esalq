package br.com.usp.csvimporter.core;

import br.com.usp.csvimporter.entity.Filme;
import br.com.usp.csvimporter.entity.GeneroFilme;
import br.com.usp.csvimporter.utils.CsvUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Component
class ImportaCsv implements  CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        System.out.println("Inicio -" + new Date());
//        String csvFile = "C:/Users/marci/Documents/prime_video/filmes_prime.csv";
        String csvFile = "C:/Users/marci/Documents/netflix/arquivo_netflix.csv";
        String line = "";
        String cvsSplitBy = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile, Charset.forName("ISO-8859-1")))) {
            int i = 0;
            Set<String> idsFilmesPrime = new HashSet<>();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(cvsSplitBy);
                if (CsvUtils.isNetFlix(data[0])) {
                    if ("Movie".equals(data[1].trim())) {
                        salvaDadosFilme(data);
                    }
                } else {
                    String id = CsvUtils.extractIdFromUrl(data[6].trim());
                    if (idsFilmesPrime.contains(id) == false) {
                        idsFilmesPrime.add(id);
                        salvaDadosFilme(data);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Fim -" + new Date());
    }
    @Transactional
    private void salvaDadosFilme (String[] data) {
        Filme filmeBD = entityManager.merge(preencheFilme(data));
        Set<GeneroFilme> generoFilmes = CsvUtils.parseGeneroFilmes(data[4], filmeBD.getId());
        for (GeneroFilme generofilme : generoFilmes) {
            entityManager.merge(generofilme);
        }
    }

    private Filme preencheFilme (String[] data) {
        Filme filme = new Filme();
        filme.setEmpresa(CsvUtils.parseEmpresa(data[0]));
        if (StringUtils.isNotBlank(data[2])) {
            filme.setNome(data[2].trim());
        }
        filme.setClassificacaoEtaria(CsvUtils.parseClassificacaoEtaria(data[3]));

        if (StringUtils.isNotBlank(data[5])) {
            filme.setUrlImagem(data[5].trim());
        }
        if (StringUtils.isNotBlank(data[6])) {
            filme.setUrl(data[6].trim());
        }
        if (data.length >= 8) {
            if (StringUtils.isNotBlank(data[7])) {
                filme.setSinopse(data[7].trim());
            }
        }
        return filme;
    }


}
