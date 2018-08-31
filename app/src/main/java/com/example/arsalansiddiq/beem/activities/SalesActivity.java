package com.example.arsalansiddiq.beem.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.azimolabs.maskformatter.MaskFormatter;
import com.example.arsalansiddiq.beem.R;
import com.example.arsalansiddiq.beem.interfaces.SKUCategoryInterface;
import com.example.arsalansiddiq.beem.models.responsemodels.salesresponsemodels.SalesObjectResponse;
import com.example.arsalansiddiq.beem.models.responsemodels.salesresponsemodels.SalesSKUArrayResponse;
import com.example.arsalansiddiq.beem.utils.Constants;
import com.example.arsalansiddiq.beem.utils.NetworkUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

public class SalesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

//    private List<String> skuCategoryList;
    private SalesSKUArrayResponse skuCategory = null;

    private TextView txtView_recordsSent_count = null;

    private String[] brandList = null;

    private Spinner spinner_gender, spinner_age, spinner_pBrand, spinner_cBrand = null;

    String name, email, gender, age, cBrand, pBrand = null;

    String contact = null;

    private EditText edtText_name, edtText_contact, edtText_email;

    private ArrayAdapter<CharSequence> adapterGender, adapterAge, adapterBrand;
    private Button btn_next;

    private Response<SalesObjectResponse> salesObjectResponse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);

        txtView_recordsSent_count = findViewById(R.id.txtView_recordsSent_count);

        SharedPreferences preferences = this.getSharedPreferences(Constants.SALE_STATUS, MODE_PRIVATE);
        int id = preferences.getInt(Constants.KEY_SALE_STATUS, 0);

        txtView_recordsSent_count.setText(String.valueOf(id));

        spinner_gender = findViewById(R.id.spinner_gender);
        spinner_age = findViewById(R.id.spinner_age);
        spinner_pBrand = findViewById(R.id.spinner_pBrand);
        spinner_cBrand = findViewById(R.id.spinner_cBrand);
        btn_next = findViewById(R.id.btn_next);

        edtText_name = findViewById(R.id.edtText_name);
        edtText_contact = findViewById(R.id.edtText_contact);
        edtText_email = findViewById(R.id.edtText_email);


        spinner_gender.setOnItemSelectedListener(this);
        spinner_age.setOnItemSelectedListener(this);
        spinner_pBrand.setOnItemSelectedListener(this);
        spinner_cBrand.setOnItemSelectedListener(this);

        setupEditTextWithDashes(R.id.edtText_contact, Constants.NUMBERS_DASHED_MASK);

//        genderList = new  {"Male", "Female"};
//        skuCategory = new ArrayList<SalesSKUArrayResponse>();

        getBrandDetails();
    }

    private void getBrandDetails() {

        NetworkUtils networkUtils = new NetworkUtils(SalesActivity.this);

        if (networkUtils.isNetworkConnected()) {
            networkUtils.getBrandsofUser("Brite", new SKUCategoryInterface() {

                @Override
                public void success(Response<SalesObjectResponse> response) {
                    if (response.body().getStatus() == 1) {

                        salesObjectResponse = response;

                        brandList = new String[response.body().getSku().size() + 1];
                        brandList[0] = "Select Brand";

                        for (int i = 0; i < response.body().getSku().size(); i++){

                            skuCategory = response.body().getSku().get(i);
//                            skuCategoryList.add(skuCategory.getSKUCaategory());
                            brandList[i+1] = skuCategory.getSKUCaategory();

                        }

                        setAdatpers(brandList);

                        Log.i("SKU", String.valueOf(response.body().getStatus()));
                        Log.i("SKUCat", String.valueOf(brandList));
                    }
                }

                @Override
                public void failed(String error) {
                    Log.i("SKU", error);
                }
            });
        }
    }

    private void setAdatpers(String[] brandList) {

        adapterGender = ArrayAdapter.createFromResource(SalesActivity.this, R.array.gender_array, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_gender.setAdapter(adapterGender);

        adapterAge = ArrayAdapter.createFromResource(SalesActivity.this, R.array.age_array, android.R.layout.simple_spinner_item);
        adapterAge.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_age.setAdapter(adapterAge);

        adapterBrand = new ArrayAdapter<CharSequence>(SalesActivity.this, android.R.layout.simple_spinner_item, brandList);
        adapterBrand.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_cBrand.setAdapter(adapterBrand);
        spinner_pBrand.setAdapter(adapterBrand);
    }

    public void onNext(View v) {



        name = edtText_name.getText().toString();
        edtText_email.setTextColor(Color.parseColor("#000000"));
        edtText_contact.setTextColor(Color.parseColor("#000000"));

        if (TextUtils.isEmpty(name) || spinner_gender.getSelectedItemPosition() == 0
                || spinner_age.getSelectedItemPosition() == 0 ||spinner_pBrand.getSelectedItemPosition() == 0
                || spinner_cBrand.getSelectedItemPosition() == 0) {

            Toast.makeText(SalesActivity.this, "please complete information", Toast.LENGTH_SHORT).show();

        } else {

            if (TextUtils.isEmpty(edtText_email.getText().toString()) && TextUtils.isEmpty(edtText_contact.getText().toString())) {

                Intent intent = new Intent(SalesActivity.this, OrderActivity.class);
                intent.putExtra("name", name);
                intent.putExtra("contact", "0");
                intent.putExtra("email", "");
                intent.putExtra("gender", gender);
                intent.putExtra("age", age);
                intent.putExtra("pBrand", pBrand);
                startActivity(intent);

            } else {
                if (TextUtils.isEmpty(edtText_email.getText().toString())) {
                    email = "";
                } else {
                    if (isEmailValid(edtText_email.getText().toString())) {
                        email = edtText_email.getText().toString();
                    } else {
                        edtText_email.setTextColor(Color.parseColor("#ff0000"));
                        Toast.makeText(this, " please enter valid crendentials", Toast.LENGTH_SHORT).show();
                    }
                }

                if (TextUtils.isEmpty(edtText_contact.getText().toString())) {
                    contact = "0";
                } else {
                    if (edtText_contact.getText().length() != 13) {
                        edtText_contact.setTextColor(Color.parseColor("#ff0000"));
                    } else {
                        contact = edtText_contact.getText().toString();
                    }
                }

                if (isEmailValid(edtText_email.getText().toString()) && edtText_contact.getText().toString().length() == 13) {

                    contact = contact.replace(" ", "");

                    Intent intent = new Intent(SalesActivity.this, OrderActivity.class);
                    intent.putExtra("name", name);
                    intent.putExtra("contact", contact);
                    intent.putExtra("email", email);
                    intent.putExtra("gender", gender);
                    intent.putExtra("age", age);
                    intent.putExtra("pBrand", pBrand);
                    startActivity(intent);
                }
            }



        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Spinner spinner = (Spinner) adapterView;
        switch (spinner.getId()) {
            case R.id.spinner_gender:
//                if (spinner_gender.getSelectedItemPosition() == 0) {
//                    Toast.makeText(SalesActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
//                } else {
                    gender = spinner_gender.getSelectedItem().toString();
//                }
                break;
            case R.id.spinner_age:
//                if (spinner_age.getSelectedItemPosition() == 0) {
//                    Toast.makeText(SalesActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
//                } else {
                    age = spinner_age.getSelectedItem().toString();
//                }
                break;
            case R.id.spinner_pBrand:
//                if (spinner_pBrand.getSelectedItemPosition() == 0) {
//                    Toast.makeText(SalesActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
//                } else {
                    pBrand = spinner_pBrand.getSelectedItem().toString();
//                }
                break;
            case R.id.spinner_cBrand:
//                if (spinner_cBrand.getSelectedItemPosition() == 0) {
//                    Toast.makeText(SalesActivity.this, "Please Select Gender", Toast.LENGTH_SHORT).show();
//                } else {
                    cBrand = spinner_cBrand.getSelectedItem().toString();
//                }
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:

                Intent intent = new Intent(SalesActivity.this, NavigationDrawerActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                return true;
        }
        return false;
    }


    public boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }

    //Number Validation
    private void setupEditTextWithDashes(int layoutId, String mask) {
        EditText field = (EditText) findViewById(layoutId);
        field.addTextChangedListener(new MaskFormatter(mask, field));
    }

}
