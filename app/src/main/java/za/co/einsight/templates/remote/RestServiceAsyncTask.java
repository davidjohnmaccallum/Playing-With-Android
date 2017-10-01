package za.co.einsight.templates.remote;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import za.co.einsight.templates.BuildConfig;
import za.co.einsight.templates.ErrorResponse;
import za.co.einsight.templates.R;

public abstract class RestServiceAsyncTask extends AsyncTask<Void, Void, Void> {

    private static final String LOG_TAG = "RestServiceAsyncTask";

    private final Context mContext;
    private final String mPath;
    private final String mHttpMethod;
    private final String mApiBase;
    private Throwable mException;
    private int mResponseCode;
    private String mResponseMessage;
    private String mBody;
    private ErrorResponse mErrorResponse;

    public RestServiceAsyncTask(Context context, String path, String httpMethod) {
        mContext = context;
        mPath = path;
        mHttpMethod = httpMethod;
        mApiBase = mContext.getString(R.string.apiBaseUrl);
    }

    protected void onSuccess(String body) {
        if (BuildConfig.DEBUG) {
            Log.i(LOG_TAG, "onSuccess");
            Log.i(LOG_TAG, body);
        }
    }

    protected void onServerError(int httpCode, String httpMessage, String body) {
        if (BuildConfig.DEBUG) {
            Log.e(LOG_TAG, "onServerError");
            Log.e(LOG_TAG, httpCode + " " + httpMessage);
            Log.e(LOG_TAG, body);
        }
    }

    protected void onClientError(int httpCode, String httpMessage, ErrorResponse errorResponse) {
        if (BuildConfig.DEBUG) {
            Log.e(LOG_TAG, "onClientError");
            Log.e(LOG_TAG, httpCode + " " + httpMessage);
            if (errorResponse!=null) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Log.i(LOG_TAG, gson.toJson(errorResponse));
            }
        }
    }

    protected void onException(Throwable error) {
        if (BuildConfig.DEBUG) {
            Log.e(LOG_TAG, "onException", error);
        }
    }

    protected abstract void setHeaders(HttpURLConnection result);
    protected abstract String makeRequest() throws Exception;

    @Override
    protected Void doInBackground(Void... none) {
        try {

            URL url = new URL(mApiBase + mPath);
            HttpURLConnection conn = makeConnection(url);
            conn.setRequestProperty("Content-Type", "application/json");
            setHeaders(conn);
            conn.connect();

            String request = makeRequest();

            logInfo(mHttpMethod + " " + url);
            logInfo(mHttpMethod + " " + url);

            if (request!=null) {
                logInfo(request);
                sendRequest(conn, request);
            }

            mResponseCode = conn.getResponseCode();
            mResponseMessage = conn.getResponseMessage();

            logInfo(mResponseCode + " " + mResponseMessage);

            if (mResponseCode >= 400) {

                logError(mBody, null);
                mBody = streamToString(conn.getErrorStream());
                mErrorResponse = parseErrorResponse(mBody);
                return null;

            } else {

                mBody = streamToString(conn.getInputStream());
                logInfo(mBody);

            }

        } catch (Exception e) {
            mException = e;
            logError("Error", e);
        }

        return null;
    }

    private void logInfo(String message) {
        if (BuildConfig.DEBUG) {
            Log.i(LOG_TAG, message);
        }
    }

    private void logError(String message, Throwable tr) {
        if (BuildConfig.DEBUG) {
            Log.e(LOG_TAG, message, tr);
        }
    }
    
    @Override
    protected void onPostExecute(Void aVoid) {

        if (mException!=null) {

            onException(mException);

        } else if (mResponseCode >= 400 && mResponseCode < 500) {

            onClientError(mResponseCode, mResponseMessage, mErrorResponse);

        } else if (mResponseCode >= 500) {

            onServerError(mResponseCode, mResponseMessage, mBody);

        } else {

            onSuccess(mBody);

        }

    }

    private String streamToString(InputStream in) throws IOException {
        InputStream bin = new BufferedInputStream(in);
        String result = IOUtils.toString(bin, "UTF-8");
        bin.close();
        return result;
    }

    private ErrorResponse parseErrorResponse(String body) {
        if (body==null || body.trim().length()==0) return null;
        Gson gson = new Gson();
        return gson.fromJson(body, ErrorResponse.class);
    }

    private HttpURLConnection makeConnection(URL url) throws IOException, URISyntaxException {
        HttpURLConnection result = (HttpURLConnection) url.openConnection();
        result.setConnectTimeout(mContext.getResources().getInteger(R.integer.httpConnectTimeout));
        result.setReadTimeout(mContext.getResources().getInteger(R.integer.httpReadTimeout));
        result.setRequestMethod(mHttpMethod);
        return result;
    }

    private void sendRequest(HttpURLConnection conn, String request) throws IOException {
        conn.setDoOutput(true);
        DataOutputStream out = new DataOutputStream(conn.getOutputStream());
        out.writeBytes(request);
        out.flush();
        out.close();
    }

}
