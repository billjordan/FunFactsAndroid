package info.billjordan.funfacts;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;


public class FactListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fact_list);

        //get the intent
        Intent intent = getIntent();

        //set the tempTextView for debugging
        TextView titleTextView = (TextView)this.findViewById(R.id.fact_list_title);
        titleTextView.setText(intent.getStringExtra("category") + " Fact List");


         //fake local data
        String[] tempCategoriesArray = {
                "Fact 1",
                "Fact 2",
                "Fact 3"
        };

        ArrayList<String> facts = new ArrayList<String>(Arrays.asList(tempCategoriesArray));


        //get listView
        ListView factListView = (ListView) this.findViewById(R.id.list_view_facts);

        //make adapter
        ArrayAdapter<String> factArrayAdapter = new ArrayAdapter<String>(
                //current context
                getBaseContext(),
                //id of list item layout
                R.layout.list_item_fact,
                //id of textview to populate
                R.id.fact_label,
                //list of data
                facts
        );

        //set adapter
        factListView.setAdapter(factArrayAdapter);

        //add onClick listener to listViewItems
        factListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(
                        getBaseContext(),
                        ((TextView) view.findViewById(R.id.fact_label)).getText(),
                        Toast.LENGTH_SHORT
                ).show();
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
}
