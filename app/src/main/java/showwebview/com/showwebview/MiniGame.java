package showwebview.com.showwebview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiniGame extends AppCompatActivity {

    private EditText word;
    private TextView definition;
    private Button next;
    private Button submit;
    private Button back;
    private TextView notify;

    String key;
    DictionaryValue dicValue;

    int size = MainActivity.listFavorite.size();
    int nWord;
    int nCorrect = 0;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mini_game);

        word = (EditText) findViewById(R.id.editText);
        definition = (TextView) findViewById(R.id.textView2);
        next = (Button) findViewById(R.id.next);
        submit = (Button) findViewById(R.id.submit);
        back = (Button) findViewById(R.id.back);
        notify = (TextView) findViewById(R.id.notify);

        final Random random = new Random();
        nWord = size > 10 ? 10 : size;
        final List<Integer> list = new ArrayList<>();
        list.add(random.nextInt(size));

        key = MainActivity.listFavorite.get(list.get(0));
        dicValue = MainActivity.dictionary.get(key).get(0);
        definition.setText("(" + dicValue.getType() + ")\n" + dicValue.getDefinition());

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == size) {
                    definition.setText("Result:\n" +
                            "\tCorrect: " + nCorrect + "/" + index);
                    MiniGame.this.findViewById(R.id.linearLayout).setVisibility(View.INVISIBLE);
                    back.setVisibility(View.VISIBLE);
                    return;
                }
                int pos = random.nextInt(size);
                while (list.contains(pos)) {
                    pos = random.nextInt(size);
                }
                list.add(pos);
                key = MainActivity.listFavorite.get(pos);
                dicValue = MainActivity.dictionary.get(key).get(0);
                word.setText("");
                definition.setText("(" + dicValue.getType() + ")\n" + dicValue.getDefinition());
            }
        });

        final AlphaAnimation animation = new AlphaAnimation( 1.0f , 0.0f ) ;
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setStartOffset(1000);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (index == nWord - 1 || list.size() == size) {
                    definition.setText("\n" +
                            "\tResult: " + nCorrect + "/" + (index + 1));
                    MiniGame.this.findViewById(R.id.linearLayout).setVisibility(View.INVISIBLE);
                    back.setVisibility(View.VISIBLE);
                    return;
                }
                if (word.getText().toString().toLowerCase().equals(key)) {
                    nCorrect++;
//                    notify.setText("Correct");
//                    notify.setTextColor(0xFF00FF00);
                } else {
//                    notify.setText("Incorrect");
//                    notify.setTextColor(0xFFFF0000);
                }
                notify.startAnimation(animation);

                int pos = random.nextInt(size);
                while (list.contains(pos)) {
                    pos = random.nextInt(size);
                }
                list.add(pos);
                key = MainActivity.listFavorite.get(pos);
                dicValue = MainActivity.dictionary.get(key).get(0);
                word.setText("");
                definition.setText("(" + dicValue.getType() + ")\n" + dicValue.getDefinition());
                index++;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiniGame.this.finish();
            }
        });
    }
}
