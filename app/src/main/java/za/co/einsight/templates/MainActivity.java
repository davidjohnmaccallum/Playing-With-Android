package za.co.einsight.templates;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import za.co.einsight.templates.recyclerview.RecyclerViewActivity;
import za.co.einsight.templates.simplelistview.ListActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup container = (ViewGroup) findViewById(R.id.container);

        {
            Button button = new Button(this);
            button.setText("List View");
            container.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, ListActivity.class));
                }
            });
        }

        {
            Button button = new Button(this);
            button.setText("Recycler View");
            container.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, RecyclerViewActivity.class));
                }
            });
        }

        {
            Button button = new Button(this);
            button.setText("View Pager");
            container.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, ViewPagerActivity.class));
                }
            });
        }

        {
            Button button = new Button(this);
            button.setText("Ken Burns");
            container.addView(button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, KenBurnsActivity.class));
                }
            });
        }

    }
}
