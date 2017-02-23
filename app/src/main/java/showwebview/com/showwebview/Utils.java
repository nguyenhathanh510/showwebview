package showwebview.com.showwebview;

/**
 * Created by Nguyen Ha Thanh on 10/11/2016.
 */
import android.content.Context;
import android.content.res.AssetManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Utils {
    public static String readDataByAsset(AssetManager asset, String fileName) {

        String str = null;
        try {
            InputStream is = asset.open(fileName);
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            str = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }

    public static String readDataByContext(Context context, String fileName) {

        File path = context.getFilesDir();
        File file = new File(path, fileName);

        int length = (int) file.length();
        byte[] buffer = new byte[length];

        FileInputStream in;
        String str = null;

        try {
            in = new FileInputStream(file);
            in.read(buffer);
            in.close();
            str = new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return str;
    }

    public static void writeDataToFile(Context context, String fileName, String data) {

        File path = context.getFilesDir();
        File file = new File(path, fileName);

        FileOutputStream out = null;

        try {
            file.createNewFile();
            out = new FileOutputStream(file);
            out.write(data.getBytes());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
