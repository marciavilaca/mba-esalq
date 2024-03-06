package br.com.usp.csvimporter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "filme")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Filme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;
    private String sinopse;
    private String url;
    private String urlImagem;

    @ManyToOne
    @JoinColumn(name = "id_classificacao_etaria")
    private ClassificacaoEtaria classificacaoEtaria;

    @ManyToOne
    @JoinColumn(name = "id_empresa")
    private Empresa empresa;

}
