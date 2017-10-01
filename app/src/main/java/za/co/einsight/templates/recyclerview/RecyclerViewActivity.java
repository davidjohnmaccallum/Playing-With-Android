package za.co.einsight.templates.recyclerview;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.widget.IconTextView;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Locale;

import za.co.einsight.templates.BuildConfig;
import za.co.einsight.templates.ErrorResponse;
import za.co.einsight.templates.R;
import za.co.einsight.templates.model.Person;
import za.co.einsight.templates.remote.RestServiceAsyncTask;

public class RecyclerViewActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeLayout;
    ViewGroup mFeedbackContainer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mSwipeLayout = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        mFeedbackContainer = (ViewGroup) findViewById(R.id.feedbackContainer);

        mSwipeLayout.setOnRefreshListener(this);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //mRecyclerView.setItemAnimator(new Animator);


        onRefresh();

    }

    @Override
    public void onRefresh() {

        mSwipeLayout.setRefreshing(true);
        new Loader().execute();

    }

    private void render(ApiResponse response) {
        if (response != null && response.data!=null && response.data.size()>0) {

            mRecyclerView.setAdapter(new Adapter(response.data));

            hideFeedback();

        } else {

            mRecyclerView.setAdapter(null);

            showFeedback(new IconDrawable(this, MaterialIcons.md_brightness_5).sizeDp(40));

        }
    }

    private void hideFeedback() {
        mFeedbackContainer.removeAllViews();
        mRecyclerView.setVisibility(View.VISIBLE);
        mFeedbackContainer.setVisibility(View.GONE);
    }

    private void showFeedback(Drawable drawable) {
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(drawable);
        showFeedback(imageView);
    }

    private void showFeedback(String text) {
        TextView textView = new IconTextView(this);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setText(text);
        showFeedback(textView);
    }

    private void showFeedback(View view) {
        mFeedbackContainer.removeAllViews();
        mFeedbackContainer.addView(view);
        mRecyclerView.setVisibility(View.GONE);
        mFeedbackContainer.setVisibility(View.VISIBLE);
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView mainText;
        TextView subText;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.imageView);
            mainText = (TextView) itemView.findViewById(R.id.mainText);
            subText = (TextView) itemView.findViewById(R.id.subText);
        }
    }

    private class Adapter extends RecyclerView.Adapter<ViewHolder> {

        private final List<Person> data;

        public Adapter(List<Person> data) {
            this.data = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_card_small, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Person person = data.get(position);
            holder.mainText.setText(person.getMainText());
            holder.subText.setText(person.getSubText());
            holder.imageView.setImageDrawable(person.getImage(RecyclerViewActivity.this));

            if (position == data.size()-1) {
                // Load next page

                // TODO Pagination
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

    private class ApiResponse {
        public int limit;
        public int skip;
        public List<Person> data;
    }

    private class Loader extends RestServiceAsyncTask {

        private Gson gson = new Gson();

        public Loader() {
            super(RecyclerViewActivity.this, "/person/list?skip=0&limit=10", "GET");
        }

        @Override
        protected void onSuccess(String body) {
            mSwipeLayout.setRefreshing(false);
            ApiResponse response = gson.fromJson(body, ApiResponse.class);
            render(response);
        }

        @Override
        protected void onServerError(int httpCode, String httpMessage, String body) {
            mSwipeLayout.setRefreshing(false);
            if (BuildConfig.DEBUG) {
                Toast.makeText(RecyclerViewActivity.this, String.format(Locale.UK, "%d %s\n%s", httpCode, httpMessage, body), Toast.LENGTH_SHORT).show();
            }
            showFeedback("{md-error 80dp}\n\n" +
                    "We're sorry.\n" +
                    "Our service is temporarily unavailable.");
        }

        @Override
        protected void onClientError(int httpCode, String httpMessage, ErrorResponse errorResponse) {
            mSwipeLayout.setRefreshing(false);
            if (BuildConfig.DEBUG) {
                Toast.makeText(RecyclerViewActivity.this, String.format(Locale.UK, "%d %s\n%s", httpCode, httpMessage, gson.toJson(errorResponse)), Toast.LENGTH_SHORT).show();
            }
            showFeedback("{md-error 80dp}\n\n" +
                    "We're sorry.\n" +
                    "Our service is temporarily unavailable.");
        }

        @Override
        protected void onException(Throwable error) {
            mSwipeLayout.setRefreshing(false);
            if (BuildConfig.DEBUG) {
                if (error!=null) {
                    Toast.makeText(RecyclerViewActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            showFeedback("{md-cloud-off 80dp}\n\n" +
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

}
