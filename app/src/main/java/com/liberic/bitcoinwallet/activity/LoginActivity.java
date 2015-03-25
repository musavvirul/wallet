package com.liberic.bitcoinwallet.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.liberic.bitcoinwallet.R;
import com.liberic.bitcoinwallet.util.Constant;
import com.liberic.bitcoinwallet.util.Security;

public class LoginActivity extends Activity {
    private EditText username;
    private EditText password;
    private Button login;
    private CheckBox saveCredentials;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(!checkIfLogged()) {
            setContentView(R.layout.activity_login);

            username = (EditText) findViewById(R.id.username);
            password = (EditText) findViewById(R.id.password);

            saveCredentials = (CheckBox) findViewById(R.id.save_credentials);

            login = (Button) findViewById(R.id.login);
        } else {
            //Pantalla de carga contra el servidor
            //setScreenLoginCorrect();
        }
    }

    public void login(View view) throws Exception {
        //TODO Comprobar contra el servidor
        if (username.getText().toString().equals("admin") && password.getText().toString().equals("admin")) {
            if(saveCredentials.isChecked()) {
                SharedPreferences pref = getPreferences(MODE_PRIVATE);
                SharedPreferences.Editor editor = pref.edit();
                editor.putString(Constant.USER, username.getText().toString());
                editor.putString(Constant.PASS, Security.encrypt(password.getText().toString()));
                editor.apply();

                Toast.makeText(getApplicationContext(), "Logged with save",Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(getApplicationContext(), "Logged witout save",Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "Wrong Credentials", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    private boolean checkIfLogged() {
        SharedPreferences pref = getPreferences(MODE_PRIVATE);
        //TODO Comprobar contra el servidor
        if(pref.getString(Constant.USER, null) != null && pref.getString(Constant.PASS, null) != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        return false;
    }


}
