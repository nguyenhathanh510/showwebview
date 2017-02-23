package showwebview.com.showwebview;

import android.service.dreams.DreamService;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by Nguyen Ha Thanh on 11/8/2016.
 */

public class SleepScreen extends DreamService {
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        setInteractive(true);
        setFullscreen(true);

        RelativeLayout relativeLayout = new RelativeLayout(this);
        TextView textView = new TextView(this);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL);

        Random random = new Random();

        params.topMargin = 500;


        textView.setLayoutParams(params);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        String key = MainActivity.listFavorite.get(random.nextInt(MainActivity.listFavorite.size()-1));
        DictionaryValue dictionaryValue = MainActivity.dictionary.get(key).get(0);
        textView.setText(key + " (" + dictionaryValue.getType() + ")\n" + dictionaryValue.getDefinition());
        relativeLayout.addView(textView);
        setContentView(relativeLayout);
    }
}
