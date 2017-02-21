package us.conqr.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class EditItemActivity extends AppCompatActivity {
    EditText etItem;
    int pos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        String itemText = getIntent().getStringExtra("itemText");
        pos = getIntent().getIntExtra("pos", -1);
        etItem = (EditText) findViewById(R.id.etItem);
        etItem.setText(itemText);
    }

    public void onSubmit(View v) {
        Intent data = new Intent();
        data.putExtra("itemText", etItem.getText().toString());
        data.putExtra("pos", pos);
        setResult(RESULT_OK, data);
        this.finish();
    }
}
