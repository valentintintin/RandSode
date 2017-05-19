package lpsmin.randsode.models;

import java.util.List;

import lpsmin.randsode.models.database.Serie;

public class Result {

    private int page;
    private List<Serie> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<Serie> getResults() {
        return results;
    }

    public void setResults(List<Serie> results) {
        this.results = results;
    }
}