package br.usp.scraper.netflix;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class Titulo {

    private String url;

    private String type;
    private String contentRating;
    private String name;
    private String description;
    private String genre;
    private String image;
    private Integer numberOfSeasons;

    public Titulo() {
    }

    public Titulo(String json) {
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);
        JsonObject object = element.getAsJsonObject();
        this.type = object.get("@type").getAsString();
        Gson gson = new Gson();
        Titulo titulo = gson.fromJson(json, Titulo.class);
        this.url = titulo.url;
        this.contentRating = titulo.contentRating;
        this.name = titulo.name;
        this.description = titulo.description;
        this.genre = titulo.genre;
        this.image = titulo.image;
        this.numberOfSeasons = titulo.numberOfSeasons;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContentRating() {
        return contentRating;
    }

    public void setContentRating(String contentRating) {
        this.contentRating = contentRating;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return
                "url=" + url + '\n' +
                "contentRating=" + contentRating + '\n' +
                "name=" + name + '\n' +
                "type=" + type + '\n' +
                "imagem=" + image + '\n' +
                "description=" + description + '\n' +
                "genre=" + genre + '\n' +
                "numberOfSeasons=" + numberOfSeasons + '\n' ;

    }

    public Integer getNumberOfSeasons() {
        return numberOfSeasons;
    }

    public void setNumberOfSeasons(Integer numberOfSeasons) {
        this.numberOfSeasons = numberOfSeasons;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
