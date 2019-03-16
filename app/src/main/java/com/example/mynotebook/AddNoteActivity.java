package com.example.mynotebook;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class AddNoteActivity extends AppCompatActivity {

    EditText text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        text = (EditText) findViewById(R.id.editText);
        final SharedPreferences sharedpreferences = getSharedPreferences("List", Context.MODE_PRIVATE);
        final Intent intent = getIntent();
        text.setText(intent.getStringExtra("re-edit"));
        text.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId== EditorInfo.IME_ACTION_DONE){
                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                        if(intent.getIntExtra("position", -1) == -1) {
                            MainActivity.notes.add(text.getText().toString());
                            try {
                                sharedpreferences.edit().putString("List",ObjectSerializer.serialize(MainActivity.notes)).commit();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        else {
                            MainActivity.notes.set(intent.getIntExtra("position", -1), text.getText().toString());
                            try {
                                sharedpreferences.edit().putString("List",ObjectSerializer.serialize(MainActivity.notes)).commit();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        MainActivity.adapter.notifyDataSetChanged();
                        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                        return  true;
                }
                return  false;
            }
        });
    }
}
