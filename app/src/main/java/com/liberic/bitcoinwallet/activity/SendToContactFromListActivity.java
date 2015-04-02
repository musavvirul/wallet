package com.liberic.bitcoinwallet.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.liberic.bitcoinwallet.R;
import com.liberic.bitcoinwallet.util.Constant;
import com.liberic.bitcoinwallet.util.Globals;
import com.liberic.bitcoinwallet.util.Interface;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.hdodenhof.circleimageview.CircleImageView;

public class SendToContactFromListActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_to_contact);

        Bundle extras = getIntent().getExtras();
        TextView nameOfContactToSend = (TextView) findViewById(R.id.name_contact_to_send);
        nameOfContactToSend.setText(extras.getString(Constant.NAME));

        CircleImageView imageOfContactToSend = (CircleImageView) findViewById(R.id.image_contact_to_send);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(extras.getString(Constant.IMAGE)));
            imageOfContactToSend.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            imageOfContactToSend.setImageResource(R.drawable.bender);
        }

        setTitle("Send to " + nameOfContactToSend.getText());

        TextView nameOfContact = (TextView) findViewById(R.id.name_contact);
        nameOfContact.setText(Globals.user);
        CircleImageView imageOfContact = (CircleImageView) findViewById(R.id.image_contact);
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), Uri.parse(Globals.uriPhoto));
            imageOfContact.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            imageOfContact.setImageResource(R.drawable.homer);
        }


        final EditText bitcoinEditText = (EditText) findViewById(R.id.edit_bitcoins);
        final EditText currencyEditText = (EditText) findViewById(R.id.edit_currency);

        bitcoinEditText.setFilters(new InputFilter[]{
                new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE) {
                    Pattern mPattern = Pattern.compile("[1-9][0-9]{0,7}(\\.[0-9]{0,7}[1-9])?|(0\\.[0-9]{0,7}[1-9])?");

                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        String formattedSource = source.subSequence(start, end).toString();
                        String destPrefix = dest.subSequence(0, dstart).toString();
                        String destSuffix = dest.subSequence(dend, dest.length()).toString();
                        String result = destPrefix + formattedSource + destSuffix;
                        result = result.replace(",", ".");
                        Matcher matcher = mPattern.matcher(result);
                        if (matcher.matches()) {
                            return null;
                        }
                        return "";
                    }
                }
        });
        final Context ctx = this;
        bitcoinEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Double conversion = Interface.convertToCurrencyFromBitcoin(ctx, Double.valueOf(bitcoinEditText.getText().toString()));
                String conversionString = new BigDecimal(conversion).toPlainString();
                if(!conversionString.equals(currencyEditText.getText().toString()))
                    currencyEditText.setText(conversionString);
            }
        });

        currencyEditText.setFilters(new InputFilter[]{
                new DigitsKeyListener(Boolean.FALSE, Boolean.TRUE) {
                    Pattern mPattern = Pattern.compile("[1-9][0-9]*(\\.[0-9]?[1-9])?|(0\\.[0-9]?[1-9])?");

                    @Override
                    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                        String formattedSource = source.subSequence(start, end).toString();
                        String destPrefix = dest.subSequence(0, dstart).toString();
                        String destSuffix = dest.subSequence(dend, dest.length()).toString();
                        String result = destPrefix + formattedSource + destSuffix;
                        result = result.replace(",", ".");
                        Matcher matcher = mPattern.matcher(result);
                        if (matcher.matches()) {
                            return null;
                        }
                        return "";
                    }
                }
        });
        currencyEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Double conversion = Interface.convertToBitcoinFromCurrency(ctx, Double.valueOf(currencyEditText.getText().toString()));
                String conversionString = new BigDecimal(conversion).toPlainString();
                if(!conversionString.equals(bitcoinEditText.getText().toString()))
                    bitcoinEditText.setText(conversionString);
            }
        });

        TextView mCurrencyType = (TextView) findViewById(R.id.icon_currency);
        mCurrencyType.setText(getSharedPreferences(Constant.PREF_CURRENT_USER, MODE_PRIVATE).getString(Constant.CURRENCY_TYPE, null));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_send_to_contact, menu);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_TASK_ON_HOME);
            startActivity(intent);
            return true;
        }

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }
}
