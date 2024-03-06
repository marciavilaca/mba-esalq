package br.com.usp.csvimporter.utils;

import br.com.usp.csvimporter.entity.ClassificacaoEtaria;
import br.com.usp.csvimporter.entity.Empresa;
import br.com.usp.csvimporter.entity.Filme;
import br.com.usp.csvimporter.entity.Genero;
import br.com.usp.csvimporter.entity.GeneroFilme;
import br.com.usp.csvimporter.entity.GeneroFilmeId;
import br.com.usp.csvimporter.enums.ClassificacaoEtariaEnum;
import br.com.usp.csvimporter.enums.EmpresaEnum;
import br.com.usp.csvimporter.enums.GeneroEnum;
import org.apache.commons.lang3.StringUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvUtils {

    public static Empresa parseEmpresa(String value) {
        Empresa empresa = null;
        if (StringUtils.isNotBlank(value)) {
            EmpresaEnum generoEmum = EmpresaEnum.fromNome(value.trim());
            empresa = new Empresa();
            empresa.setId(generoEmum.getCodigo());
        }
        return empresa;
    }

    public static boolean isNetFlix(String value) {
        if (StringUtils.isNotBlank(value)) {
            EmpresaEnum generoEmum = EmpresaEnum.fromNome(value.trim());
            if (generoEmum.getCodigo() == EmpresaEnum.NETFLIX.getCodigo()) {
                return true;
            }
        }
        return false;
    }

    public static Set<GeneroFilme> parseGeneroFilmes(String value, Integer idFilme) {

        Set<GeneroFilme> generos = new HashSet<>();
        if (StringUtils.isNotBlank(value)) {
            String[] values = value.split(",");
            for (String generoStr : values) {
                GeneroEnum generoEmum = GeneroEnum.fromNome(generoStr.trim());

                GeneroFilmeId generoFilmeId = new GeneroFilmeId();
                generoFilmeId.setIdGenero(generoEmum.getCodigo());
                generoFilmeId.setIdFilme(idFilme);
                Genero genero = new Genero();
                genero.setId(generoEmum.getCodigo());
                Filme filme = new Filme();
                filme.setId(idFilme);
                GeneroFilme generoFilme = new GeneroFilme(generoFilmeId, filme, genero);
                generos.add(generoFilme);
            }

        }
        return generos;
    }

    public static ClassificacaoEtaria parseClassificacaoEtaria(String value) {
        ClassificacaoEtaria classificacaoEtaria = new ClassificacaoEtaria();
        Integer id = ClassificacaoEtariaEnum.SEM_CLASSIFICACAO.getCodigo();
        if (StringUtils.isNotBlank(value)) {

            if (value.contains("10")) {
                id = ClassificacaoEtariaEnum.DEZ_ANOS.getCodigo();
            } else if (value.contains("12")) {
                id = ClassificacaoEtariaEnum.DOZE_ANOS.getCodigo();
            } else if (value.contains("14")) {
                id = ClassificacaoEtariaEnum.QUATORZE_ANOS.getCodigo();
            } else if (value.contains("16")) {
                id = ClassificacaoEtariaEnum.DEZESSEIS_ANOS.getCodigo();
            } else if (value.contains("18")) {
                id = ClassificacaoEtariaEnum.DEZOITO_ANOS.getCodigo();
            } else if (value.contains("VERIFIQUE")) {
                id = ClassificacaoEtariaEnum.SEM_CLASSIFICACAO.getCodigo();
            } else {
                id = ClassificacaoEtariaEnum.LIVRE.getCodigo();
            }
        }
        classificacaoEtaria.setId(id);
        return classificacaoEtaria;
    }

    public static String extractIdFromUrl(String url) {
        String id = null;
        String regex = "\\/detail\\/([A-Za-z0-9]+)\\/";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);
        if (matcher.find()) {
            id = matcher.group(1);
        }
        return id;
    }
}
