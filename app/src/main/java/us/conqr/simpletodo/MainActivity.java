package us.conqr.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<ToDoItem> items;
    ArrayAdapter<ToDoItem> itemsAdapter;
    ListView lvItems;
    private final int REQUEST_CODE = 20;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseHelper = new DatabaseHelper(this);

        lvItems = (ListView) findViewById(R.id.lvItems);
        items = databaseHelper.getAllToDoItems();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);

        setupListViewListner();
    }

    /*
    private void readItems() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try {
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            items = new ArrayList<String>();
        }
    }*/

    /*
    private void writeItems() {
        File fileDir = getFilesDir();
        File todoFile = new File(fileDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, items);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */

    private void setupListViewListner() {
        lvItems.setOnItemLongClickListener(
            new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
                    ToDoItem toDoItem = items.get(pos);
                    databaseHelper.DeleteItem(toDoItem);
                    itemsAdapter.notifyDataSetChanged();
                    items.remove(pos);
                    //writeItems();
                    return true;
                }
            }
        );

        //
        lvItems.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View item, int pos, long id) {
                    String itemText = items.get(pos).toString();
                    Intent i = new Intent(MainActivity.this, EditItemActivity.class);
                    i.putExtra("itemText", itemText);
                    i.putExtra("pos", pos);
                    startActivityForResult(i, REQUEST_CODE);
                }
            }
        );
    }

    public void onAddItem(View v) {
        EditText etNewItem = (EditText) findViewById(R.id.etNewItem);

        String itemText = etNewItem.getText().toString();

        ToDoItem toDoItem = new ToDoItem(itemText);

        databaseHelper.AddToDoItem(toDoItem);
        etNewItem.setText("");

        items.clear();
        items.addAll(databaseHelper.getAllToDoItems());

        itemsAdapter.notifyDataSetChanged();
        //writeItems();
    }

    // The method is to get back results from activity called from this app
    // This works when the sub-activity is called using startActivityForResult
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            String itemText = data.getExtras().getString("itemText");
            int pos = data.getExtras().getInt("pos");
            ToDoItem toDoItem = items.get(pos);
            toDoItem.setItem(itemText);
            items.set(pos, toDoItem);
            databaseHelper.UpdateItem(toDoItem);
            itemsAdapter.notifyDataSetChanged();
            //writeItems();
        }
    }
}
