CREATE SCHEMA `catalogo_filmes` DEFAULT CHARACTER SET latin1 ;
CREATE TABLE `catalogo_filmes`.`genero` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100)   
) ENGINE = InnoDB , CHARSET=latin1;

CREATE TABLE `catalogo_filmes`.`empresa` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(80)
) ENGINE = InnoDB , CHARSET=latin1;

CREATE TABLE `catalogo_filmes`.`classificacao_etaria` (
    id INT AUTO_INCREMENT PRIMARY KEY,
	idade INT,
    descricao VARCHAR(50)
) ENGINE = InnoDB , CHARSET=latin1;

CREATE TABLE `catalogo_filmes`.`filme` (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(150) CHARSET latin1,
    sinopse VARCHAR(4000) CHARSET latin1,
    url VARCHAR(400) CHARSET latin1,
    url_imagem VARCHAR(400) CHARSET latin1,
    id_classificacao_etaria INT,
    id_empresa INT,
	
    FOREIGN KEY (id_classificacao_etaria) REFERENCES classificacao_etaria(id),
    FOREIGN KEY (id_empresa) REFERENCES empresa(id)   
) ENGINE = InnoDB CHARSET=latin1;

CREATE TABLE `catalogo_filmes`.`genero_filme` (
    id_filme INT,
    id_genero INT,
    PRIMARY KEY (id_filme, id_genero),
    FOREIGN KEY (id_filme) REFERENCES filme(id),
    FOREIGN KEY (id_genero) REFERENCES genero(id)
) ENGINE = InnoDB CHARSET=latin1;


INSERT INTO catalogo_filmes.classificacao_etaria (descricao) VALUES ('Sem Classificação');
INSERT INTO catalogo_filmes.classificacao_etaria (idade,descricao) VALUES (0,'Livre');
INSERT INTO catalogo_filmes.classificacao_etaria (idade,descricao) VALUES (10,'10 anos');
INSERT INTO catalogo_filmes.classificacao_etaria (idade,descricao) VALUES (12,'12 anos');
INSERT INTO catalogo_filmes.classificacao_etaria (idade,descricao) VALUES (14,'14 anos');
INSERT INTO catalogo_filmes.classificacao_etaria (idade,descricao) VALUES (16,'16 anos');
INSERT INTO catalogo_filmes.classificacao_etaria (idade,descricao) VALUES (18,'18 anos');

INSERT INTO catalogo_filmes.empresa (nome) VALUES ('Netflix');
INSERT INTO catalogo_filmes.empresa (nome) VALUES ('Adrenalina Pura');
INSERT INTO catalogo_filmes.empresa (nome) VALUES ('CurtaOn');
INSERT INTO catalogo_filmes.empresa (nome) VALUES ('MAX');
INSERT INTO catalogo_filmes.empresa (nome) VALUES ('Reserva Imovision');
INSERT INTO catalogo_filmes.empresa (nome) VALUES ('Looke');
INSERT INTO catalogo_filmes.empresa (nome) VALUES ('Love Nature');
INSERT INTO catalogo_filmes.empresa (nome) VALUES ('MGM');
INSERT INTO catalogo_filmes.empresa (nome) VALUES ('Mubi');
INSERT INTO catalogo_filmes.empresa (nome) VALUES ('Paramount+');
INSERT INTO catalogo_filmes.empresa (nome) VALUES ('Prime Vídeo');
INSERT INTO catalogo_filmes.empresa (nome) VALUES ('Stingray');
INSERT INTO catalogo_filmes.empresa (nome) VALUES ('Telecine');
INSERT INTO catalogo_filmes.empresa (nome) VALUES ('Alugue no Prime');

INSERT INTO catalogo_filmes.genero (nome) VALUES ('Ficção científica');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Comédia');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Romance');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Suspense');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Infantis');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Documentário');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Terror');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Ação');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Anime');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Esportes');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Drama');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Faroeste');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Musical');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Fantasia');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Militar e Guerra');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Aventura');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Histórico');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('LGBTQ');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Fé e Espiritualidade');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Animações');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Videoclipes e Shows');
INSERT INTO catalogo_filmes.genero (nome) VALUES ('Artes');


