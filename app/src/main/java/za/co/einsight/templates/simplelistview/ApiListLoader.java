package za.co.einsight.templates.simplelistview;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.util.Locale;

import za.co.einsight.templates.BuildConfig;
import za.co.einsight.templates.ErrorResponse;
import za.co.einsight.templates.model.Person;
import za.co.einsight.templates.remote.RestServiceAsyncTask;

public class ApiListLoader extends RestServiceAsyncTask {

    private Context mContext;
    private ApiListResponseListener<Person> mApiResponseListener;

    public ApiListLoader(Context context, ApiListResponseListener<Person> apiResponseListener, String path, String httpMethod) {
        super(context, path, httpMethod);
        mContext = context;
        mApiResponseListener = apiResponseListener;
    }

    private class ApiPersonListResponse extends ApiListResponse<Person> {}

    @Override
    protected void onSuccess(String body) {
        mApiResponseListener.setRefreshing(false);
        Gson gson = new Gson();
        ApiListResponse response = gson.fromJson(body, ApiPersonListResponse.class);
        mApiResponseListener.render(response);
    }

    @Override
    protected void onServerError(int httpCode, String httpMessage, String body) {
        mApiResponseListener.setRefreshing(false);
        if (BuildConfig.DEBUG) {
            Toast.makeText(mContext, String.format(Locale.UK, "%d %s\n%s", httpCode, httpMessage, body), Toast.LENGTH_SHORT).show();
        }
        mApiResponseListener.showFeedback("{md-error 80dp}\n\n" +
                "We're sorry.\n" +
                "Our service is temporarily unavailable.");
    }

    @Override
    protected void onClientError(int httpCode, String httpMessage, ErrorResponse errorResponse) {
        mApiResponseListener.setRefreshing(false);
        if (BuildConfig.DEBUG) {
            Gson gson = new Gson();
            Toast.makeText(mContext, String.format(Locale.UK, "%d %s\n%s", httpCode, httpMessage, gson.toJson(errorResponse)), Toast.LENGTH_SHORT).show();
        }
        String message = errorResponse != null ? errorResponse.getFriendlyMessage() : "";
        mApiResponseListener.showFeedback("{md-error 80dp}\n\n" +
                "We're sorry.\n" +
                "Our service is temporarily unavailable.");
    }

    @Override
    protected void onException(Throwable error) {
        mApiResponseListener.setRefreshing(false);
        if (BuildConfig.DEBUG) {
            if (error != null) {
                Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        mApiResponseListener.showFeedback("{md-cloud-off 80dp}\n\n" +
                "Can't connect to server.");
    }

    @Override
    protected void setHeaders(HttpURLConnection result) {

    }

    @Override
    protected String makeRequest() throws Exception {
        return null;
    }

}