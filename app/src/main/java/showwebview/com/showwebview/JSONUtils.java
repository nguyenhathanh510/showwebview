package showwebview.com.showwebview;

import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JSONUtils {

    public static String loadJSON(AssetManager asset, String name) {

        String jsonString = null;
        try {
            InputStream is = asset.open(name);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            jsonString = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonString;
    }

    public static Map<String, List<DictionaryValue>> jsonToMap(String json) {

        Map<String, List<DictionaryValue>> dic = new TreeMap<String, List<DictionaryValue>>();

        try {
            JSONArray jsonArray = new JSONArray(json);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String key = object.keys().next();
                JSONObject dicValue = object.getJSONObject(key);
                String type = dicValue.getString("type");
                String def = dicValue.getString("def");

                key = key.toLowerCase();

                if (dic.containsKey(key)) {
                    dic.get(key).add(new DictionaryValue(type, def));
                } else {
                    List<DictionaryValue> listValue = new ArrayList<DictionaryValue>();
                    listValue.add(new DictionaryValue(type, def));
                    dic.put(key, listValue);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return dic;
    }
}
