package info.billjordan.funfacts;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class FactListActivity extends AppCompatActivity {

//    private ArrayAdapter<Fact> factArrayAdapter;
    private ListView factListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact_list);

        TextView logoTextView = (TextView) findViewById(R.id.text_view_logo);
        Typeface logoTypeFace = Typeface.createFromAsset(getAssets(), getString(R.string.logo_font));
        logoTextView.setTypeface(logoTypeFace);

        //get the intent
        Intent callingCategoryListActivityIntent = getIntent();
        Category category = (Category) callingCategoryListActivityIntent.getParcelableExtra("category");

        //get facts from server
        FetchFactsTask fetchFactsTask = new FetchFactsTask(this, category.getId());
        fetchFactsTask.execute();

        //set the tempTextView for debugging
        TextView titleTextView = (TextView)this.findViewById(R.id.fact_list_title);
        titleTextView.setText(category.getLabel() + " Fact List");






        //get listView
        factListView = (ListView) this.findViewById(R.id.list_view_facts);


        //add onClick listener to listViewItems
        factListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(
//                        getBaseContext(),
//                        ((Fact) factListView.getAdapter().getItem(position)).getLabel() + "--"
//                                + String.valueOf(((Fact) factListView.getAdapter().getItem(position)).getId()),
//                        Toast.LENGTH_SHORT
//                ).show();
                Intent newFactActivityIntent = new Intent(getBaseContext(), FactActivity.class);
                newFactActivityIntent.putExtra(
                        "fact",
                        ((Fact) factListView.getAdapter().getItem(position))
                );
                newFactActivityIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                try{getBaseContext().startActivity(newFactActivityIntent);}
                catch (Exception e){Log.d("FLA", e.toString());}
                
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fact_list, menu);
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


    /**
     * set the data for the list view adapter
     * @param facts - ArrayList<Fact>
     */
    public void setFactListViewAdapter(ArrayList<Fact> facts){
        ArrayAdapter <Fact> newCategoryArrayAdapter = new ArrayAdapter<Fact>(
                //current context
                getBaseContext(),
                //id of list item layout
                R.layout.list_item_category,
                //id of textview to populate
                R.id.category_label,
                //list of data
                facts
        );

        factListView.setAdapter(newCategoryArrayAdapter);

    }
}
