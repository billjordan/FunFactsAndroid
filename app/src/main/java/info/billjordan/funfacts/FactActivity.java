package info.billjordan.funfacts;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    private Button viewFindAnswerButton;
    private ShareButton shareButton;
    private ShareLinkContent shareContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //initialize facebook sdk
        FacebookSdk.sdkInitialize(getApplicationContext());


        setContentView(R.layout.activity_fact);



        Intent callingFactListActivityIntent = getIntent();
        fact = null;
        fact = (Fact) callingFactListActivityIntent.getParcelableExtra("fact");


        TextView debugTextView = (TextView)findViewById(R.id.text_view_debugging);
        debugTextView.setText(
                fact.getLabel() + "---"
                + String.valueOf(fact.getId())
        );

        questionTextView = (TextView)findViewById(R.id.text_view_fact_question);
        questionTextView.setText(fact.getQuestion());

        answerTextView = (TextView)findViewById(R.id.text_view_fact_answer);

        viewFindAnswerButton = (Button)findViewById(R.id.button_view_fact_answer);
        viewFindAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                answerTextView.setText(fact.getAnswer());
            }
        });


        //facebook share
        shareButton = (ShareButton)findViewById(R.id.shareButton);
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
        Button tweetButton = (Button)findViewById(R.id.tweetButton);
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
}
