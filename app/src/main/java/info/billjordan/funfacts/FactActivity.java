package info.billjordan.funfacts;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;


public class FactActivity extends AppCompatActivity {

    private static final String LOG_TAG = "FactActivityLog";
    private Fact fact;
    private TextView questionTextView;
    private TextView answerTextView;
    private Button viewFactAnswerButton;
    private ShareButton shareButton;
    private ShareLinkContent shareContent;
    private String selectedAnswerNumber;
    private RadioButton answer1RadioButton;
    private RadioButton answer2RadioButton;
    private RadioButton answer3RadioButton;
    private RadioButton answer4RadioButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //initialize facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());


        setContentView(R.layout.activity_fact);

        TextView logoTextView = (TextView) findViewById(R.id.text_view_logo);
        Typeface logoTypeFace = Typeface.createFromAsset(getAssets(), getString(R.string.logo_font));
        logoTextView.setTypeface(logoTypeFace);

        Intent callingFactListActivityIntent = getIntent();
        fact = null;
        fact = (Fact) callingFactListActivityIntent.getParcelableExtra("fact");


        TextView debugTextView = (TextView) findViewById(R.id.text_view_debugging);
        debugTextView.setText(
                fact.getLabel() + "---"
                        + String.valueOf(fact.getId())
        );

        questionTextView = (TextView) findViewById(R.id.text_view_fact_question);

        answer1RadioButton = (RadioButton) findViewById(R.id.radio_button_answer_1);
        answer1RadioButton.setText(fact.getAnswerNumber(1));

        answer2RadioButton = (RadioButton) findViewById(R.id.radio_button_answer_2);
        answer2RadioButton.setText(fact.getAnswerNumber(2));

        answer3RadioButton = (RadioButton) findViewById(R.id.radio_button_answer_3);
        answer3RadioButton.setText(fact.getAnswerNumber(3));

        answer4RadioButton = (RadioButton) findViewById(R.id.radio_button_answer_4);
        answer4RadioButton.setText(fact.getAnswerNumber(4));

        questionTextView.setText(fact.getQuestion());

        answerTextView = (TextView) findViewById(R.id.text_view_fact_answer);

        viewFactAnswerButton = (Button) findViewById(R.id.button_view_fact_answer);
//        viewFactAnswerButton.setClickable(false);
        viewFactAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerTextView.setText(fact.getAnswer());
                if (selectedAnswerNumber.equals(fact.getCorrectAnswerNumber())) {
                    answerTextView.setTextColor(getResources().getColor(R.color.correct_answer));
                } else {
                    answerTextView.setTextColor(getResources().getColor(R.color.incorrect_answer));
                }
            }
        });

        //set button unclickable if no answer is selected
        setViewFactAnswerButtonClickableStatus();


        //facebook share
        shareButton = (ShareButton) findViewById(R.id.shareButton);
        shareButton.setBackgroundColor(getResources().getColor(R.color.com_facebook_button_background_color));
        shareContent = new ShareLinkContent.Builder()
                .setContentTitle("Fun Fact")
                .setContentDescription(fact.getQuestion() + "\n\n" + fact.getAnswer())
                .setContentUrl(Uri.parse("http://www.billjordan.info:10080"))
//                        .setImageUrl(Uri.parse("http://developers.facebook.com/android"))
                .build();
        shareButton.setShareContent(shareContent);
        final ShareDialog shareDialog = new ShareDialog(this);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getBaseContext(), "FB SHARE BUTTON", Toast.LENGTH_SHORT).show();
                shareDialog.show(shareContent);
            }
        });

        //tweet
        LinearLayout tweetButton = (LinearLayout) findViewById(R.id.tweet_button_linear_layout);
//        final Intent tweetIntent = new Intent(Intent.ACTION_SEND);
//        tweetIntent.putExtra(Intent.EXTRA_TEXT, fact.getLabel());
//        tweetIntent.setType("application/twitter");
        tweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tweetUrl = "https://twitter.com/intent/tweet?text=PUT TEXT HERE &url="
                        + "https://www.google.com";
                Uri uri = Uri.parse(tweetUrl);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });
        Typeface fontAwesome = Typeface.createFromAsset(getAssets(), "fonts/fontawesome-webfont.ttf");
        TextView tweetButtonIcon = (TextView)findViewById(R.id.twitter_icon_text_view);
                tweetButtonIcon.setTypeface(fontAwesome);
    }

    private void setViewFactAnswerButtonClickableStatus() {
        viewFactAnswerButton.setClickable(
                answer1RadioButton.isChecked()
                        || answer2RadioButton.isChecked()
                        || answer3RadioButton.isChecked()
                        || answer4RadioButton.isChecked()
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onRadioButtonClicked(View view) {
        viewFactAnswerButton.setClickable(true);

        //figure out which button was clicked
        switch (view.getId()) {
            case R.id.radio_button_answer_1:
                selectedAnswerNumber = "1";
                Toast.makeText(getBaseContext(), selectedAnswerNumber, Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio_button_answer_2:
                selectedAnswerNumber = "2";
                Toast.makeText(getBaseContext(), selectedAnswerNumber, Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio_button_answer_3:
                Toast.makeText(getBaseContext(), ((RadioButton) view).getText(), Toast.LENGTH_SHORT).show();
                selectedAnswerNumber = "3";
                Toast.makeText(getBaseContext(), selectedAnswerNumber, Toast.LENGTH_SHORT).show();
                break;
            case R.id.radio_button_answer_4:
                Toast.makeText(getBaseContext(), ((RadioButton) view).getText(), Toast.LENGTH_SHORT).show();
                selectedAnswerNumber = "4";
                Toast.makeText(getBaseContext(), selectedAnswerNumber, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * This method is called after {@link #onStart} when the activity is
     * being re-initialized from a previously saved state, given here in
     * <var>savedInstanceState</var>.  Most implementations will simply use {@link #onCreate}
     * to restore their state, but it is sometimes convenient to do it here
     * after all of the initialization has been done or to allow subclasses to
     * decide whether to use your default implementation.  The default
     * implementation of this method performs a restore of any view state that
     * had previously been frozen by {@link #onSaveInstanceState}.
     * <p/>
     * <p>This method is called between {@link #onStart} and
     * {@link #onPostCreate}.
     *
     * @param savedInstanceState the data most recently supplied in {@link #onSaveInstanceState}.
     * @see #onCreate
     * @see #onPostCreate
     * @see #onResume
     * @see #onSaveInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        answer1RadioButton.setChecked(savedInstanceState.getBoolean("answer1RadioButtonChecked"));
        answer2RadioButton.setChecked(savedInstanceState.getBoolean("answer2RadioButtonChecked"));
        answer3RadioButton.setChecked(savedInstanceState.getBoolean("answer3RadioButtonChecked"));
        answer4RadioButton.setChecked(savedInstanceState.getBoolean("answer4RadioButtonChecked"));
        selectedAnswerNumber = savedInstanceState.getString("selectedAnswerNumber");
        answerTextView.setText(savedInstanceState.getString("answerTextViewText"));
        answerTextView.setTextColor(savedInstanceState.getInt("answerTextViewColor"));
        setViewFactAnswerButtonClickableStatus();
    }

    /**
     * Save all appropriate fragment state.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putBoolean("answer1RadioButtonChecked", answer1RadioButton.isChecked());
        outState.putBoolean("answer2RadioButtonChecked", answer2RadioButton.isChecked());
        outState.putBoolean("answer3RadioButtonChecked", answer3RadioButton.isChecked());
        outState.putBoolean("answer4RadioButtonChecked", answer4RadioButton.isChecked());
        outState.putString("selectedAnswerNumber", selectedAnswerNumber);
        outState.putString("answerTextViewText", answerTextView.getText().toString());
        outState.putInt("answerTextViewColor", answerTextView.getCurrentTextColor());
        super.onSaveInstanceState(outState);
    }
}
