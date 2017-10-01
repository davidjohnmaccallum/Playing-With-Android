package za.co.einsight.templates.simplelistview;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.fonts.MaterialIcons;
import com.joanzapata.iconify.widget.IconTextView;

import za.co.einsight.templates.R;
import za.co.einsight.templates.model.Person;

public class ListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, ApiListResponseListener<Person> {

    private ListView mListView;
    private ViewGroup mFeedbackContainer;
    private SwipeRefreshLayout mSwipeLayout;
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);


        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mListView = (ListView) findViewById(R.id.listView);
        mFeedbackContainer = (ViewGroup) findViewById(R.id.feedbackContainer);
        mSwipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        mSwipeLayout.setOnRefreshListener(this);

        // Load list view

        onRefresh();

        // List view click

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                ListModel model = (ListModel) adapterView.getAdapter().getItem(position);
                Toast.makeText(ListActivity.this, model + " clicked", Toast.LENGTH_SHORT).show();
            }
        });

        throw new RuntimeException("Ahhhhh!");

    }


    @Override
    public void onRefresh() {

        mSwipeLayout.setRefreshing(true);
        new ApiListLoader(this, this, "/person/list?skip=0&limit=100", "GET").execute();

    }

    @Override
    public void setRefreshing(boolean isRefreshing) {
        mSwipeLayout.setRefreshing(isRefreshing);
    }

    @Override
    public void render(ApiListResponse<Person> response) {
        if (response != null && response.getData()!=null && response.getData().size()>0) {

            mListView.setAdapter(new PersonAdapter(this, response.getData()));

            hideFeedback();

        } else {

            mListView.setAdapter(null);

            showFeedback(new IconDrawable(this, MaterialIcons.md_brightness_5).sizeDp(40));

        }
    }

    private void hideFeedback() {
        mFeedbackContainer.removeAllViews();
        mListView.setVisibility(View.VISIBLE);
        mFeedbackContainer.setVisibility(View.GONE);
    }

    private void showFeedback(Drawable drawable) {
        ImageView imageView = new ImageView(this);
        imageView.setImageDrawable(drawable);
        showFeedback(imageView);
    }

    @Override
    public void showFeedback(String text) {
        TextView textView = new IconTextView(this);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView.setText(text);
        showFeedback(textView);
    }

    private void showFeedback(View view) {
        mFeedbackContainer.removeAllViews();
        mFeedbackContainer.addView(view);
        mListView.setVisibility(View.GONE);
        mFeedbackContainer.setVisibility(View.VISIBLE);
    }

}
