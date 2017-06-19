package lpsmin.randsode.models;

import java.util.List;

public class Result<T> {

    private int page;
    private List<T> results;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }
}