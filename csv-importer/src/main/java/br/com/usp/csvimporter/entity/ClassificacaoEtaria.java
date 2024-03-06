package br.com.usp.csvimporter.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "classificacao_etaria")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClassificacaoEtaria {
    @Id
    private Integer id;

    private Integer idade;

    private String descricao;
}
