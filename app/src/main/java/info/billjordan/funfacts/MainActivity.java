package info.billjordan.funfacts;

import android.app.LauncherActivity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //fake local data
        Category[] tempCategoriesArray = new Category[]{
                new Category("Category 1", 1),
                new Category("Category 2", 2),
                new Category("Category 3", 3),
        };

        final ArrayList<Category> categories = new ArrayList<Category>(Arrays.asList(tempCategoriesArray));


        //get listView
        ListView categoryListView = (ListView) this.findViewById(R.id.list_view_categories);

        //make adapter
        ArrayAdapter<Category> categoryArrayAdapter = new ArrayAdapter<Category>(
                //current context
                getBaseContext(),
                //id of list item layout
                R.layout.list_item_category,
                //id of textview to populate
                R.id.category_label,
                //list of data
                categories
        );

        //set adapter
        categoryListView.setAdapter(categoryArrayAdapter);

        //add onClick listener to listViewItems
        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(
                        getBaseContext(),
                        ((TextView)view.findViewById(R.id.category_label)).getText(),
                        Toast.LENGTH_SHORT
                ).show();
                Intent intent = new Intent(getBaseContext(), FactListActivity.class);
                intent.putExtra(
                        "category",
                        categories.get(position)
                );
                //nescessary to start new activity from outside activity
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getBaseContext().startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
