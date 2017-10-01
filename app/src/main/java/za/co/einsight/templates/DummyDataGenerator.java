package za.co.einsight.templates;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import za.co.einsight.templates.simplelistview.ListModel;

public class DummyDataGenerator {

    public List<ListModel> getListViewData(int count, int delay) {

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<ListModel> result = new ArrayList<>();
        for (int i=0; i<count; i++) {
            result.add(new ListViewModelImpl());
        }
        return result;
    }

    private class ListViewModelImpl implements ListModel {

        @Override
        public String getMainText() {
            return "Main text";
        }

        @Override
        public String getSubText() {
            return "Sub text";
        }

        @Override
        public Drawable getImage(Context context) {
            return context.getResources().getDrawable(R.mipmap.ic_launcher);
        }
    }

}

