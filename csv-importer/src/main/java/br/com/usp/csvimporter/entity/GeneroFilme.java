package br.com.usp.csvimporter.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.annotation.Profile;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "genero_filme")
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GeneroFilme implements Serializable {

    @EmbeddedId
    private GeneroFilmeId id;

    @ManyToOne
    @MapsId("idFilme")
    @JoinColumn(name = "id_filme")
    private Filme filme;

    @ManyToOne
    @MapsId("idGenero")
    @JoinColumn(name = "id_genero")
    private Genero genero;
}
