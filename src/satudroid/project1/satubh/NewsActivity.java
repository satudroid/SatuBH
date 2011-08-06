package satudroid.project1.satubh;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class NewsActivity extends Activity {

    // progress dialog yang menunjuk loading mesej
    private ProgressDialog mProgressDialog;

    // Adapter yang akan hold senarai Message dan papar item pada ListView
    private NewsAdapter mNewsAdapter;

    // List yang mengandungi POJO Message
    private List<Message> mMessages;

    /** Dipanggil apabila class ini dicipta. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // setkan layout utama pada Activity ini
        setContentView(R.layout.news);

        // ambik data daripada intent yang dipass daripada SatuBHActivity
        String url = getIntent().getStringExtra("url");
        String category = getIntent().getStringExtra("category");

        // tukar title kepada tajuk kategori
        setTitle(category);

        // init empty List untuk Message
        mMessages = new ArrayList<Message>();

        // init adapter
        mNewsAdapter = new NewsAdapter(NewsActivity.this, R.layout.news_item,
                mMessages);

        // set adapter kepada ListView
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(mNewsAdapter);

        // Panggil AsyncTask bagi memproses data secara background. Oleh itu
        // memberi kesan yang responsive kepada user.
        // User akan dipaparkan dengan UI ini terlebih dahulu tetapi terdapat
        // loading dialog yang menunjukkan data sedang diproses.
        // Jadi, user akan tahu bahawa terdapat backend proses sedang berjalan
        // dan akan menunggu sehingga data siap diproses.
        new LoadNewsTask(this).execute(url);
    }

    /**
     * AsyncTask yang memproses RSS data secara background.
     */
    private static class LoadNewsTask extends
            AsyncTask<String, Void, List<Message>> {

        private WeakReference<NewsActivity> mTarget;

        public LoadNewsTask(NewsActivity activity) {
            mTarget = new WeakReference<NewsActivity>(activity);
        }

        /** Sebelum proses bermula, paparkan loading dialog */
        @Override
        protected void onPreExecute() {
            if (mTarget.get() != null) {
                mTarget.get().mProgressDialog = ProgressDialog.show(
                        mTarget.get(), "Loading", "Please wait...");
            }
        }

        /**
         * Dapatkan RSS data daripada Url secara background dan parse data
         * menggunakan RssHandler
         */
        @Override
        protected List<Message> doInBackground(String... params) {
            List<Message> messages = new ArrayList<Message>();
            if (mTarget.get() != null) {
                try {
                    URL url = new URL(params[0]);
                    HttpURLConnection request = (HttpURLConnection) url
                            .openConnection();

                    request.setConnectTimeout(0);
                    request.setReadTimeout(0);

                    request.setRequestMethod("GET");
                    request.setDoOutput(true);
                    request.connect();
                    BufferedInputStream bis = new BufferedInputStream(
                            request.getInputStream());

                    SAXParserFactory factory = SAXParserFactory.newInstance();
                    SAXParser parser = factory.newSAXParser();
                    RssHandler handler = new RssHandler();
                    parser.parse(bis, handler);
                    messages = handler.getMessages();
                } catch (MalformedURLException e) {
                    Log.e(Constant.LOG, e.getMessage(), e);
                } catch (IOException e) {
                    Log.e(Constant.LOG, e.getMessage(), e);
                } catch (ParserConfigurationException e) {
                    Log.e(Constant.LOG, e.getMessage(), e);
                } catch (SAXException e) {
                    Log.e(Constant.LOG, e.getMessage(), e);
                }
            }
            return messages;
        }

        /**
         * Selepas proses background selesai, close progres dialog dan populate
         * data
         */
        @Override
        protected void onPostExecute(List<Message> result) {
            if (mTarget.get() != null) {
                mTarget.get().mProgressDialog.dismiss();
                mTarget.get().fillData(result);
            }
        }
    }

    /**
     * Populate data Message ke dalam List
     */
    private void fillData(List<Message> result) {
        for (Message message : result) {
            mMessages.add(message);
        }

        // notify adapter yang data telah berubah
        mNewsAdapter.notifyDataSetChanged();
    }
}
