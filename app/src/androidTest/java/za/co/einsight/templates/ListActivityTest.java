package za.co.einsight.templates;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.net.HttpURLConnection;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import za.co.einsight.templates.remote.RestServiceAsyncTask;
import za.co.einsight.templates.simplelistview.ListActivity;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class ListActivityTest {

    private static final String LOG_TAG = "TestRunner";
    @Rule
    public ActivityTestRule<ListActivity> rule = new ActivityTestRule<ListActivity>(ListActivity.class);

    @Test
    public void testApi200() {

        final CountDownLatch signal = new CountDownLatch(1);

        final boolean[] onSuccessRan = new boolean[1];
        final boolean[] onServerErrorRan = new boolean[1];
        final boolean[] onClientErrorRan = new boolean[1];
        final boolean[] onExceptionRan = new boolean[1];

        new RestServiceAsyncTask(rule.getActivity(), "/person/list", "GET") {

            @Override
            protected void onSuccess(String body) {
                Log.i(LOG_TAG, "onSuccess");
                Log.i(LOG_TAG, body);
                onSuccessRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void onServerError(int httpCode, String httpMessage, String body) {
                Log.i(LOG_TAG, "onServerError");
                Log.i(LOG_TAG, String.format(Locale.UK, "%d %s", httpCode, httpMessage));
                Log.i(LOG_TAG, body);
                onServerErrorRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void onClientError(int httpCode, String httpMessage, ErrorResponse errorResponse) {
                Log.i(LOG_TAG, "onClientError");
                Log.i(LOG_TAG, String.format(Locale.UK, "%d %s", httpCode, httpMessage));
                if (errorResponse!=null) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    Log.i(LOG_TAG, gson.toJson(errorResponse));
                }
                onClientErrorRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void onException(Throwable error) {
                Log.i(LOG_TAG, "onException");
                Log.i(LOG_TAG, "", error);
                onExceptionRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void setHeaders(HttpURLConnection result) {
            }

            @Override
            protected String makeRequest() throws Exception {
                return null;
            }

        }.execute();

        try {
            signal.await();
            assertTrue("onSuccess should have run", onSuccessRan[0]);
            assertFalse("onServerErrorRan should not have run", onServerErrorRan[0]);
            assertFalse("onClientErrorRan should not have run", onClientErrorRan[0]);
            assertFalse("onExceptionRan should not have run", onExceptionRan[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testApi400() {

        final CountDownLatch signal = new CountDownLatch(1);

        final boolean[] onSuccessRan = new boolean[1];
        final boolean[] onServerErrorRan = new boolean[1];
        final boolean[] onClientErrorRan = new boolean[1];
        final boolean[] onExceptionRan = new boolean[1];

        new RestServiceAsyncTask(rule.getActivity(), "/person/listxx", "GET") {

            @Override
            protected void onSuccess(String body) {
                Log.i(LOG_TAG, "onSuccess");
                Log.i(LOG_TAG, body);
                onSuccessRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void onServerError(int httpCode, String httpMessage, String body) {
                Log.i(LOG_TAG, "onServerError");
                Log.i(LOG_TAG, String.format(Locale.UK, "%d %s", httpCode, httpMessage));
                Log.i(LOG_TAG, body);
                onServerErrorRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void onClientError(int httpCode, String httpMessage, ErrorResponse errorResponse) {
                Log.i(LOG_TAG, "onClientError");
                Log.i(LOG_TAG, String.format(Locale.UK, "%d %s", httpCode, httpMessage));
                if (errorResponse!=null) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    Log.i(LOG_TAG, gson.toJson(errorResponse));
                }
                onClientErrorRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void onException(Throwable error) {
                Log.i(LOG_TAG, "onException");
                Log.i(LOG_TAG, "", error);
                onExceptionRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void setHeaders(HttpURLConnection result) {
            }

            @Override
            protected String makeRequest() throws Exception {
                return null;
            }

        }.execute();

        try {
            signal.await();
            assertFalse("onSuccess should not have run", onSuccessRan[0]);
            assertFalse("onServerErrorRan should not have run", onServerErrorRan[0]);
            assertTrue("onClientErrorRan should have run", onClientErrorRan[0]);
            assertFalse("onExceptionRan should not have run", onExceptionRan[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testApi500() {

        final CountDownLatch signal = new CountDownLatch(1);

        final boolean[] onSuccessRan = new boolean[1];
        final boolean[] onServerErrorRan = new boolean[1];
        final boolean[] onClientErrorRan = new boolean[1];
        final boolean[] onExceptionRan = new boolean[1];

        new RestServiceAsyncTask(rule.getActivity(), "/crash", "GET") {

            @Override
            protected void onSuccess(String body) {
                Log.i(LOG_TAG, "onSuccess");
                Log.i(LOG_TAG, body);
                onSuccessRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void onServerError(int httpCode, String httpMessage, String body) {
                Log.i(LOG_TAG, "onServerError");
                Log.i(LOG_TAG, String.format(Locale.UK, "%d %s", httpCode, httpMessage));
                Log.i(LOG_TAG, body);
                onServerErrorRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void onClientError(int httpCode, String httpMessage, ErrorResponse errorResponse) {
                Log.i(LOG_TAG, "onClientError");
                Log.i(LOG_TAG, String.format(Locale.UK, "%d %s", httpCode, httpMessage));
                if (errorResponse!=null) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    Log.i(LOG_TAG, gson.toJson(errorResponse));
                }
                onClientErrorRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void onException(Throwable error) {
                Log.i(LOG_TAG, "onException");
                Log.i(LOG_TAG, "", error);
                onExceptionRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void setHeaders(HttpURLConnection result) {
            }

            @Override
            protected String makeRequest() throws Exception {
                return null;
            }

        }.execute();

        try {
            signal.await();
            assertFalse("onSuccess should not have run", onSuccessRan[0]);
            assertTrue("onServerErrorRan should have run", onServerErrorRan[0]);
            assertFalse("onClientErrorRan should not have run", onClientErrorRan[0]);
            assertFalse("onExceptionRan should not have run", onExceptionRan[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testApiException() {

        final CountDownLatch signal = new CountDownLatch(1);

        final boolean[] onSuccessRan = new boolean[1];
        final boolean[] onServerErrorRan = new boolean[1];
        final boolean[] onClientErrorRan = new boolean[1];
        final boolean[] onExceptionRan = new boolean[1];

        new RestServiceAsyncTask(rule.getActivity(), "/person/list", "SNOT") {

            @Override
            protected void onSuccess(String body) {
                Log.i(LOG_TAG, "onSuccess");
                Log.i(LOG_TAG, body);
                onSuccessRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void onServerError(int httpCode, String httpMessage, String body) {
                Log.i(LOG_TAG, "onServerError");
                Log.i(LOG_TAG, String.format(Locale.UK, "%d %s", httpCode, httpMessage));
                Log.i(LOG_TAG, body);
                onServerErrorRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void onClientError(int httpCode, String httpMessage, ErrorResponse errorResponse) {
                Log.i(LOG_TAG, "onClientError");
                Log.i(LOG_TAG, String.format(Locale.UK, "%d %s", httpCode, httpMessage));
                if (errorResponse!=null) {
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    Log.i(LOG_TAG, gson.toJson(errorResponse));
                }
                onClientErrorRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void onException(Throwable error) {
                Log.i(LOG_TAG, "onException");
                Log.i(LOG_TAG, "", error);
                onExceptionRan[0] = true;
                signal.countDown();
            }

            @Override
            protected void setHeaders(HttpURLConnection result) {
            }

            @Override
            protected String makeRequest() throws Exception {
                return null;
            }

        }.execute();

        try {
            signal.await();
            assertFalse("onSuccess should not have run", onSuccessRan[0]);
            assertFalse("onServerErrorRan should not have run", onServerErrorRan[0]);
            assertFalse("onClientErrorRan should not have run", onClientErrorRan[0]);
            assertTrue("onExceptionRan should have run", onExceptionRan[0]);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
