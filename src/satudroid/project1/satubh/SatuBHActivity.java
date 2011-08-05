package satudroid.project1.satubh;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class SatuBHActivity extends ListActivity {
    /** Kategori */
    public static final String[] categories = new String[] { "Mutakhir",
            "Muka Depan", "Nasional", "Wilayah", "Sukan", "Dunia", "Hip",
            "Rencana", "Agama", "Ekonomi", "Ratu", "Pendidikan" };

    /** Url untuk setiap kategori */
    public static final String[] categoryUrls = new String[] {
            "http://www.bharian.com.my/bharian/RSS/rss_html?section=Mutakhir",
            "http://www.bharian.com.my/bharian/RSS/rss_html?section=MukaDepan",
            "http://www.bharian.com.my/bharian/RSS/rss_html?section=Nasional",
            "http://www.bharian.com.my/bharian/RSS/rss_html?section=Wilayah",
            "http://www.bharian.com.my/bharian/RSS/rss_html?section=Sukan",
            "http://www.bharian.com.my/bharian/RSS/rss_html?section=Dunia",
            "http://www.bharian.com.my/bharian/RSS/rss_html?section=Hip",
            "http://www.bharian.com.my/bharian/RSS/rss_html?section=Rencana",
            "http://www.bharian.com.my/bharian/RSS/rss_html?section=Agama",
            "http://www.bharian.com.my/bharian/RSS/rss_html?section=Ekonomi",
            "http://www.bharian.com.my/bharian/RSS/rss_html?section=Ratu",
            "http://www.bharian.com.my/bharian/RSS/rss_html?section=Pendidikan" };

    /** Dipanggil apabila class ini dicipta. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, categories);
        setListAdapter(arrayAdapter);
    }

    /** Dipanggil apabila kategori diklik */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // dapatkan kategori yang dipilih
        Object o = this.getListAdapter().getItem(position);
        String category = o.toString();

        // Papar Toast menunjukkan kategori dipilih beserta RSS Url
        Toast.makeText(this, "Kategori " + category + " dipilih [" + categoryUrls[position] + "]",
                Toast.LENGTH_LONG).show();
    }

}