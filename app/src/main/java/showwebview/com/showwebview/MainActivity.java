package showwebview.com.showwebview;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

    private EditText edtURL;
    private Button btnGo;
    private WebView wvContent;
    private TextView textView;
    private WebView ggTranslate;
    private ListView favorite;
    private ImageView star;
    private TabHost tabHost;
    private ArrayAdapter<String> adapter;
    private String selectMethod;
    private final String URL = "https://translate.google.com/?hl=vi";
    static List<String> listFavorite = new ArrayList<>();
    static Map<String, List<DictionaryValue>> dictionary = new TreeMap<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    //ggTranslate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        for (char c = 'a'; c <= 'z'; c++) {
            dictionary.putAll(JSONUtils.jsonToMap(JSONUtils.loadJSON(getAssets(), c + ".json")));
        }

        init();

        wvContent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    wvContent.evaluateJavascript("(function(){return window.getSelection().toString()})()",
                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String value) {
                                    value = value.replace("\"", "");
                                    if (value.isEmpty()) {
                                        tabHost.setVisibility(View.INVISIBLE);
                                    } else {

                                        List<DictionaryValue> dictionaryValue = dictionary.get(value.toLowerCase());
                                        if (dictionaryValue != null) {
                                            selectMethod = value.toLowerCase();
                                            DictionaryValue dicValue = dictionaryValue.get(0);
                                            textView.setText(value + " (" + dicValue.getType() + ")\n" + dicValue.getDefinition());
                                            setFavorite(listFavorite.contains(value.toLowerCase()), false);
                                            star.setVisibility(View.VISIBLE);
                                        } else {
                                            textView.setText("Not found!");
                                            star.setVisibility(View.INVISIBLE);
                                        }
                                        tabHost.setVisibility(View.VISIBLE);
                                    }

                                }
                            });
                }


                return false;
            }
        });

        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                String currentUrl = ggTranslate.getUrl();
                String newUrl = URL + selectMethod;
                if (tabId.equals("Tab2")) {
                    if (currentUrl == null) {
                        ggTranslate.loadUrl(newUrl);
                    } else {
                        ggTranslate.loadUrl(newUrl);
                    }
                }
            }
        });
        star.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = star.getTag().toString();
                setFavorite(tag.equals("off"), true);
            }
        });

        //kich vao word trong favorite chuyen sang dictionary
        favorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectMethod = listFavorite.get(position);
                List<DictionaryValue> dictionaryValue = dictionary.get(selectMethod.toLowerCase());
                if (dictionaryValue != null) {
                    DictionaryValue dicValue = dictionaryValue.get(0);
                    textView.setText(selectMethod + "(" + dicValue.getType() + ")\n" + dicValue.getDefinition());
                    //textView.setText( dictionaryValue + "(" + dicValue.getType() + ")\n" + dicValue.getDefinition());


                    tabHost.setVisibility(View.VISIBLE);
                } else {
                    textView.setText("Not found!");
                }
                tabHost.setCurrentTab(0);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void init() {
        edtURL = (EditText) findViewById(R.id.aaaa);
        btnGo = (Button) findViewById(R.id.btnGo);
        wvContent = (WebView) findViewById(R.id.wvContent);
        wvContent.getSettings().setLoadsImagesAutomatically(true);
        wvContent.getSettings().setJavaScriptEnabled(true);
        wvContent.setWebViewClient(new WebViewClient());
        // wvContent.loadUrl("http://www.google.com");
        textView = (TextView) findViewById(R.id.textView);
        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWebview();
            }
        });
        star = (ImageView) findViewById(R.id.imageView);
        ggTranslate = (WebView) findViewById(R.id.webView);
        favorite = (ListView) findViewById(R.id.listView);

        tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        tabHost.setVisibility(View.INVISIBLE);
        TabHost.TabSpec spec1 = tabHost.newTabSpec("tabsTab1");
        spec1.setContent(R.id.textView);
        spec1.setIndicator("Dictionary");
        tabHost.addTab(spec1);

        TabHost.TabSpec spec2 = tabHost.newTabSpec("Tab2");
        spec2.setContent(R.id.webView);
        spec2.setIndicator("GG Translate");
        tabHost.addTab(spec2);

        TabHost.TabSpec spec3 = tabHost.newTabSpec("Tab3");
        spec3.setContent(R.id.listView);
        spec3.setIndicator("Favorite");
        tabHost.addTab(spec3);

        tabHost.setCurrentTab(0);

        String strFavorite = Utils.readDataByContext(getApplicationContext(), "favorite");
        if (strFavorite != null) {
            Collections.addAll(listFavorite, strFavorite.split("\n"));
        }

        adapter = new ArrayAdapter<>(favorite.getContext(), android.R.layout.simple_list_item_1, listFavorite);
        favorite.setAdapter(adapter);


        ggTranslate.getSettings().setLoadsImagesAutomatically(true);
        ggTranslate.getSettings().setJavaScriptEnabled(true);
        ggTranslate.setWebViewClient(new WebViewClient());
    }

    private void showWebview() {
        String url = edtURL.getText().toString().trim();
        // url = "http://www.google.com";
        // url = "http://www.google.com";
        Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
        if (!url.isEmpty()) {
            wvContent.loadUrl("http://" + url);
        }
    }

    private void setFavorite(boolean isFavorite, boolean isImageClicked) {
        if (isFavorite) {
            star.setImageResource(android.R.drawable.btn_star_big_on);
            star.setTag("on");
            if (isImageClicked) listFavorite.add(selectMethod.toLowerCase());
        } else {
            star.setImageResource(android.R.drawable.btn_star_big_off);
            star.setTag("off");
            if (isImageClicked) listFavorite.remove(selectMethod.toLowerCase());
        }
        Utils.writeDataToFile(getApplicationContext(), "favorite", TextUtils.join("\n", listFavorite));
        adapter.notifyDataSetChanged();
    }

    ///////////////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        new MenuInflater(this).inflate(R.menu.option, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miniGame:
                Intent intent = new Intent(getApplicationContext(), MiniGame.class);
                startActivity(intent);
                break;
            case R.id.miniGame2:
                intent = new Intent(getApplicationContext(), MiniGame2.class);
                startActivity(intent);
                break;
            case R.id.favorite:
                tabHost.setVisibility(View.VISIBLE);
                tabHost.setCurrentTab(2);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        setContentView(R.layout.activity_main);
        super.onBackPressed();
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
