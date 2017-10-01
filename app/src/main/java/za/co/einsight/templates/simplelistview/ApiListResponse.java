package za.co.einsight.templates.simplelistview;

import java.util.List;

public class ApiListResponse<K> {

    private int limit;
    private int skip;
    private List<K> data;

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public List<K> getData() {
        return data;
    }

    public void setData(List<K> data) {
        this.data = data;
    }

}