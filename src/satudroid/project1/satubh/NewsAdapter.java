package satudroid.project1.satubh;

import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Adapter yang akan hold Message data dan setkan data-data kepada View
 */
public class NewsAdapter extends ArrayAdapter<Message> {

    private int mResource;

    public NewsAdapter(Context context, int resource, List<Message> messages) {
        super(context, resource, messages);
        mResource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) LayoutInflater
                    .from(getContext());
            v = vi.inflate(mResource, null);
        }

        Message message = getItem(position);
        if (message != null) {
            // set tajuk pada titleView
            TextView titleView = (TextView) v.findViewById(R.id.title);
            titleView.setText(Html.fromHtml(message.getTitle()));

            // set pubDate pada pubDateView
            TextView pubDateView = (TextView) v.findViewById(R.id.pubDate);
            pubDateView.setText(message.getPubDate());

            // set description pada descriptionView
            TextView descriptionView = (TextView) v
                    .findViewById(R.id.description);
            descriptionView.setText(Html.fromHtml(message.getDesc()));
        }
        return v;
    }
}
