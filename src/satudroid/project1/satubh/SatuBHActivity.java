package satudroid.project1.satubh;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
 * Activity utama untuk memaparkan senarai kategori
 */
public class SatuBHActivity extends ListActivity {

    /** Dipanggil apabila class ini dicipta. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, Constant.CATEGORIES);
        setListAdapter(arrayAdapter);
    }

    /** Dipanggil apabila kategori diklik */
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        // Dapatkan kategori yang dipilih
        Object o = this.getListAdapter().getItem(position);
        String category = o.toString();

        // Intent NewsActivity akan dicipta. Beberapa data akan dipass kepada
        // NewsActivity iaitu url dan category.
        Intent intent = new Intent().setClass(SatuBHActivity.this,
                NewsActivity.class);
        intent.putExtra("url", Constant.CATEGORIES_URL[position]);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}