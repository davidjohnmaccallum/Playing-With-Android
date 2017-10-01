package za.co.einsight.templates.simplelistview;

public interface ApiListResponseListener<K> {

    void setRefreshing(boolean isRefreshing);
    void render(ApiListResponse<K> response);
    void showFeedback(String feedback);

}
