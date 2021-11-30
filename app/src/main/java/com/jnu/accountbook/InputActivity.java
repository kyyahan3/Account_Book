package com.jnu.accountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Intent intent=getIntent();
        int position=intent.getIntExtra("position",0);

        EditText editTextName=findViewById(R.id.edit_text_name);
        EditText editTextMoney=findViewById(R.id.edit_text_money);
        String name=intent.getStringExtra("name");
        Double money=intent.getDoubleExtra("money",0);
        if(null!=name){
            editTextName.setText(name);
            editTextMoney.setText(String.valueOf(money));
        }

        Button buttonOk=this.findViewById(R.id.button_ok);
        buttonOk.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent=new Intent();
            intent.putExtra("position",position);
            intent.putExtra("name",editTextName.getText().toString());
            intent.putExtra("money",Double.parseDouble(editTextMoney.getText().toString()));
            setResult(MainActivity.RESULT_CODE_ADD_DATA,intent);
            InputActivity.this.finish();
        }
        });

    }

}