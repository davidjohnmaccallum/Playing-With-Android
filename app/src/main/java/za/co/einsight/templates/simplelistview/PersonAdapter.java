package za.co.einsight.templates.simplelistview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import za.co.einsight.templates.R;
import za.co.einsight.templates.model.Person;

public class PersonAdapter extends ArrayAdapter<Person> {

    public PersonAdapter(Context context, List<Person> objects) {
        super(context, R.layout.row_simple2, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.row_simple2, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.mainText = (TextView) convertView.findViewById(R.id.mainText);
            viewHolder.subText = (TextView) convertView.findViewById(R.id.subText);
            convertView.setTag(viewHolder);
        }

        ListModel model = getItem(position);

        ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.mainText.setText(model.getMainText());
        viewHolder.subText.setText(model.getSubText());
        viewHolder.imageView.setImageDrawable(model.getImage(getContext()));

        return convertView;
    }

    private static class ViewHolder {
        ImageView imageView;
        TextView mainText;
        TextView subText;
    }
}