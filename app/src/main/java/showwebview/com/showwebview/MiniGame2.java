package showwebview.com.showwebview;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MiniGame2 extends AppCompatActivity {

    private RelativeLayout layout;
    private LinearLayout ll;
    private TextView definition;
    private TextView incorrecTextView;
    private TextView correctTextView;
    private Button next;
    private Button submit;
    private Button back;
    private TextView notify;
    private Map<Character, TextView> word;
    private TextView[] c;

    String key;

    int size = MainActivity.listFavorite.size();
    int nWord;
    int nCorrect = 0;
    int index = 0;

    int hidden;
    Map<Integer, Character> listHidden;
    Random random;
    List<Integer> list;

    List<String> listCorrect = new ArrayList<>();
    Map<String, String> listIncorrect = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mini_game_2);

        definition = (TextView) findViewById(R.id.textView3);
        layout = (RelativeLayout) findViewById(R.id.layout);
        ll = (LinearLayout) findViewById(R.id.word);
        next = (Button) findViewById(R.id.next2);
        submit = (Button) findViewById(R.id.submit2);
        back = (Button) findViewById(R.id.back2);
        notify = (TextView) findViewById(R.id.notify2);
        word = new HashMap<>();
        c = new TextView[10];

        c[0] = (TextView) findViewById(R.id.c0);
        c[1] = (TextView) findViewById(R.id.c1);
        c[2] = (TextView) findViewById(R.id.c2);
        c[3] = (TextView) findViewById(R.id.c3);
        c[4] = (TextView) findViewById(R.id.c4);
        c[5] = (TextView) findViewById(R.id.c5);
        c[6] = (TextView) findViewById(R.id.c6);
        c[7] = (TextView) findViewById(R.id.c7);
        c[8] = (TextView) findViewById(R.id.c8);
        c[9] = (TextView) findViewById(R.id.c9);

        random = new Random();
        nWord = size > 10 ? 10 : size;
        list = new ArrayList<>();
        list.add(random.nextInt(size));

        key = MainActivity.listFavorite.get(list.get(0));

        hidden = (int) Math.round(key.length() * 0.4);
        listHidden = new HashMap<>();
        while (listHidden.size() < hidden) {
            int i = random.nextInt(key.length());
            listHidden.put(i, key.charAt(i));
        }

        for (Map.Entry<Integer, Character> charHidden : listHidden.entrySet()) {
            int pos;
            do {
                pos = random.nextInt(10);
            } while (!c[pos].getText().toString().isEmpty());

            c[pos].setText(String.valueOf(charHidden.getValue()).toUpperCase());
        }

        for (int i = 0; i < 10; i++) {
            if (c[i].getText().toString().isEmpty()) c[i].setText(String.valueOf((char) (random.nextInt(26) + 65)));
        }

        for (int i = 0; i < key.length(); i++) {
            TextView textView = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 150);
            params.setMargins(3, 0, 3, 0);
            textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            if (listHidden.containsKey(i)) {
                textView.setBackgroundResource(R.drawable.box_inactive);
                textView.setTag("off");
            }
            else {
                textView.setBackgroundResource(R.drawable.box);
                textView.setText(String.valueOf(key.charAt(i)).toUpperCase());
            }

            textView.setLayoutParams(params);
            textView.setGravity(Gravity.CENTER);
            textView.setTypeface(null, Typeface.BOLD);
            textView.setOnClickListener(keyClick);
            ll.addView(textView);
            word.put(key.charAt(i), textView);
        }

        for (int i = 0; i < 10; i++) {
            c[i].setOnClickListener(charClick);
        }

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                {
                    notify.animate().translationX(0);
                    if (list.size() == size) {
                definition.setText("Result: " + nCorrect + "/" + index);

                        findViewById(R.id.word).setVisibility(View.INVISIBLE);
                        findViewById(R.id.select_character).setTextAlignment(View.INVISIBLE);
                        findViewById(R.id.submit2).setTextAlignment(View.INVISIBLE);
                        definition.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        back.setVisibility(View.VISIBLE);
                        return;
                    }
                    int pos = random.nextInt(size);
                    while (list.contains(pos)) {
                        pos = random.nextInt(size);
                    }
                    list.add(pos);

                    for (int i = 0; i < 10; i++) c[i].setText("");

                    key = MainActivity.listFavorite.get(pos);

                    hidden = (int) Math.round(key.length() * 0.4);
                    listHidden = new HashMap<>();
                    while (listHidden.size() < hidden) {
                        int i = random.nextInt(key.length());
                        listHidden.put(i, key.charAt(i));
                    }

                    for (Map.Entry<Integer, Character> charHidden : listHidden.entrySet()) {
                        do {
                            pos = random.nextInt(10);
                        } while (!c[pos].getText().toString().isEmpty());

                        c[pos].setText(String.valueOf(charHidden.getValue()).toUpperCase());
                    }
                    for (int i = 0; i < 10; i++) {
                        if (c[i].getText().toString().isEmpty()) c[i].setText(String.valueOf((char) (random.nextInt(26) + 65)));
                    }

                    ll.removeAllViews();
                    for (int i = 0; i < key.length(); i++) {
                        TextView textView = new TextView(getApplicationContext());
                        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 150);
                        params.setMargins(3, 0, 3, 0);
                        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        if (listHidden.containsKey(i)) {
                            textView.setBackgroundResource(R.drawable.box_inactive);
                            textView.setTag("off");
                        }
                        else {
                            textView.setBackgroundResource(R.drawable.box);
                            textView.setText(String.valueOf(key.charAt(i)).toUpperCase());
                        }

                        textView.setLayoutParams(params);
                        textView.setGravity(Gravity.CENTER);
                        textView.setOnClickListener(keyClick);
                        ll.addView(textView);
                        word.put(key.charAt(i), textView);
                    }
                }
            }
        });

        final AlphaAnimation animation = new AlphaAnimation( 1.0f , 0.0f ) ;
        animation.setDuration(500);
        animation.setFillAfter(true);
        animation.setStartOffset(1000);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String wordString = "";
                for (int i = 0; i < key.length(); i++) {
                    wordString += ((TextView) ll.getChildAt(i)).getText().toString();
                }

                if (wordString.toLowerCase().equals(key)) {
                    nCorrect++;
                    listCorrect.add(key);
                    //notify.setText("Correct");
                    //notify.setTextColor(0xFF00FF00);
                } else {
                    listIncorrect.put(key, wordString);
                    //notify.setText("Incorrect");
                    //notify.setTextColor(0xFFFF0000);
                }
                notify.startAnimation(animation);

                if (index == nWord - 1 || list.size() == size) {
                    definition.setText("Result: " + nCorrect + "/" + (index + 1));
                    //incorrecTextView.setText("Incorrect answers: " + "\n" + key+"\n");
                    findViewById(R.id.word).setVisibility(View.INVISIBLE);
                    findViewById(R.id.next2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.submit2).setVisibility(View.INVISIBLE);
                    findViewById(R.id.select_character).setVisibility(View.INVISIBLE);
                    back.setVisibility(View.VISIBLE);
                    TextView correct = (TextView) findViewById(R.id.correct);
                    TextView incorrect = (TextView) findViewById(R.id.incorrect);
                    String correctResult = "";
                    for (String s : listCorrect) {
                        correctResult += s + "\n";
                    }

                    String incorrectResult = "";
                    for (Map.Entry<String, String> entry : listIncorrect.entrySet()) {
                        incorrectResult += entry.getValue() + "(" + entry.getKey() + ")\n";
                    }


                    correct.setText("Correct answers : \n" + correctResult);
                    incorrect.setText("Incorrect answers : \n" + incorrectResult.toLowerCase());
                    return;
                }

                int pos = random.nextInt(size);
                while (list.contains(pos)) {
                    pos = random.nextInt(size);
                }
                list.add(pos);

                for (int i = 0; i < 10; i++) c[i].setText("");

                key = MainActivity.listFavorite.get(pos);

                hidden = (int) Math.round(key.length() * 0.4);
                listHidden = new HashMap<>();
                while (listHidden.size() < hidden) {
                    int i = random.nextInt(key.length());
                    listHidden.put(i, key.charAt(i));
                }

                for (Map.Entry<Integer, Character> charHidden : listHidden.entrySet()) {
                    do {
                        pos = random.nextInt(10);
                    } while (!c[pos].getText().toString().isEmpty());

                    c[pos].setText(String.valueOf(charHidden.getValue()).toUpperCase());
                }
                for (int i = 0; i < 10; i++) {
                    if (c[i].getText().toString().isEmpty()) c[i].setText(String.valueOf((char) (random.nextInt(26) + 65)));
                }

                ll.removeAllViews();
                for (int i = 0; i < key.length(); i++) {
                    TextView textView = new TextView(getApplicationContext());
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(150, 150);
                    params.setMargins(3, 0, 3, 0);
                    textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                    if (listHidden.containsKey(i)) {
                        textView.setBackgroundResource(R.drawable.box_inactive);
                        textView.setTag("off");
                    }
                    else {
                        textView.setBackgroundResource(R.drawable.box);
                        textView.setText(String.valueOf(key.charAt(i)).toUpperCase());
                    }

                    textView.setLayoutParams(params);
                    textView.setGravity(Gravity.CENTER);
                    textView.setOnClickListener(keyClick);
                    ll.addView(textView);
                    word.put(key.charAt(i), textView);
                }
                index++;
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MiniGame2.this.finish();
            }
        });
    }

    TextView textView1;
    TextView textView2;
    View.OnClickListener charClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            textView2 = (TextView) v;
            for (int i = 0; i < key.length(); i++) {
                textView1 = (TextView) ll.getChildAt(i);
                Object tag = textView1.getTag();
                if (tag != null && tag.toString().equals("off")) {
                    textView1.setTag("on");
                    textView1.setBackgroundResource(R.drawable.box_insert);
                    textView1.setText(textView2.getText());
                    break;
                }
            }
        }
    };

    View.OnClickListener keyClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            TextView textView = (TextView) v;
            Object tag = textView.getTag();
            if (tag != null && tag.toString().equals("on")) {
                textView.setTag("off");
                textView.setBackgroundResource(R.drawable.box_inactive);
                textView.setText("");
            }
        }
    };
}
