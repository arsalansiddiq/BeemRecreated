package com.example.arsalansiddiq.beem.activities;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arsalansiddiq.beem.MainActivity;
import com.example.arsalansiddiq.beem.R;
import com.example.arsalansiddiq.beem.adapters.CustomListAdapter;
import com.example.arsalansiddiq.beem.databases.BeemDatabase;
import com.example.arsalansiddiq.beem.databases.BeemPreferences;
import com.example.arsalansiddiq.beem.databases.room.BeemAppDatabase;
import com.example.arsalansiddiq.beem.databases.room.tables.SalesStatus;
import com.example.arsalansiddiq.beem.interfaces.LoginInterface;
import com.example.arsalansiddiq.beem.interfaces.SKUCategoryInterface;
import com.example.arsalansiddiq.beem.interfaces.SampleInterface;
import com.example.arsalansiddiq.beem.models.HolderListModel;
import com.example.arsalansiddiq.beem.models.responsemodels.LoginResponse;
import com.example.arsalansiddiq.beem.models.responsemodels.salesresponsemodels.SalesObjectResponse;
import com.example.arsalansiddiq.beem.models.responsemodels.salesresponsemodels.SalesSKUArrayResponse;
import com.example.arsalansiddiq.beem.utils.AppUtils;
import com.example.arsalansiddiq.beem.utils.Constants;
import com.example.arsalansiddiq.beem.utils.CustomAlertDialog;
import com.example.arsalansiddiq.beem.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class OrderActivity extends AppCompatActivity implements LocationListener {

    private final String LOG_TAG = OrderActivity.class.getName();

    private Spinner spinner_saleStatus = null;
    private NetworkUtils networkUtils = null;
    String cusName, contact; String email; String gender; String age; String cBrand = null;
    String pBrand = null;
    Integer saleStatus = 0;
    private LocationManager locationManager = null;
    private float latitude, longitude;
    private BeemDatabase beemDatabase = null;

    private ListView listView_order = null;
    private Intent intent = null;
    private FrameLayout frameLayout_noProducts = null;
    private View view = null;
    private EditText edtText_loose = null;
    private EditText edtText_carton = null;
    private TextView txtView_name = null;
    private LinearLayout linearLayout_bottom;

    private List<HolderListModel> holderListModelList;

    private List<SalesSKUArrayResponse> salesSKUArrayResponseArrayList = null;

    CustomAlertDialog customAlertDialog;

    int doubleQuantity = 0;
    private AppUtils appUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        beemDatabase = new BeemDatabase(this);
        beemDatabase.getReadableDatabase();

        customAlertDialog = new CustomAlertDialog(this);
        networkUtils = new NetworkUtils(this);
        appUtils = new AppUtils(this);

        spinner_saleStatus = findViewById(R.id.spinner_saleStatus);

        listView_order = findViewById(R.id.listView_order);
        linearLayout_bottom = findViewById(R.id.linearLayout_bottom);

        listView_order.setDescendantFocusability(ViewGroup.FOCUS_AFTER_DESCENDANTS);
        listView_order.setItemsCanFocus(true);

        frameLayout_noProducts = findViewById(R.id.frameLayout_noProducts);

        holderListModelList = new ArrayList<>();

        if (getIntent().getExtras() != null) intent = getIntent();
        cusName = intent.getStringExtra("name");
        contact = intent.getStringExtra("contact");
        email = intent.getStringExtra("email");
        gender = intent.getStringExtra("gender");
        age = intent.getStringExtra("age");
        pBrand = intent.getStringExtra("pBrand");
        cBrand = intent.getStringExtra("cBrand");

        ArrayAdapter adapterGender = ArrayAdapter.createFromResource(this, R.array.saleStatus_array, android.R.layout.simple_spinner_item);
        adapterGender.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_saleStatus.setAdapter(adapterGender);
        spinner_saleStatus.setSelection(2);

        spinner_saleStatus.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position == 2) {
//                    getSalesId();
                    linearLayout_bottom.setVisibility(View.VISIBLE);
                } else if (position == 1){
                    linearLayout_bottom.setVisibility(View.GONE);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        frameLayout_noProducts.setVisibility(View.GONE);

        if (spinner_saleStatus.getSelectedItemPosition() == 2) {
            linearLayout_bottom.setVisibility(View.VISIBLE);
            getSalesId();
            getBrandItems();
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onSubmit(View view) {

        if (spinner_saleStatus.getSelectedItemPosition() == 2) {
            getLocation();
            getSelectedItemAndPrice();
        } else {
            Toast.makeText(this, "Please Select Sale Status", Toast.LENGTH_SHORT).show();
        }

    }


    void sendOrder(final int sales_id) {

        Log.i("dates", appUtils.getDate());

        for (int i = 0; i < holderListModelList.size(); i++) {

            final HolderListModel holderListModel = holderListModelList.get(i);

            networkUtils.sendOrderDetail(holderListModel.getStoreId(), sales_id, appUtils.getDate(), holderListModel.getBrand()
                    , holderListModel.getSkuCategory() , holderListModel.getSKU(), holderListModel.getSaleType(), holderListModel.getNoItem(),
                    holderListModel.getPrice(), holderListModel.getsAmount(), new SampleInterface() {

                        @Override
                        public void success(LoginResponse loginResponse) {
                            Log.i(LOG_TAG, "sendOrder Status" + loginResponse.getStatus() + "  " + holderListModel.getStoreId());
                            final BeemPreferences beemPreferences = new BeemPreferences(OrderActivity.this);
                            beemPreferences.initialize_and_createPreferences_forStatus(loginResponse.getStatus());

                            SalesStatus salesStatus = new SalesStatus();

                            salesStatus.setTotal_sales(1);

                        }

                        @Override
                        public void failed(String error) {

                        }
                    });

        }

        intent = new Intent(OrderActivity.this, SalesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = (float) location.getLatitude();
        longitude = (float) location.getLongitude();

        Log.i("LocationOrder", latitude + "  " + longitude);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},99);
            return;
        } else {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, OrderActivity.this);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch(keyCode)
        {
            case KeyEvent.KEYCODE_BACK:

                intent = new Intent(OrderActivity.this, SalesActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

                return true;
        }
        return false;
    }

    private void getBrandItems() {


        NetworkUtils networkUtils = new NetworkUtils(OrderActivity.this);

        if (networkUtils.isNetworkConnected()) {
            networkUtils.getBrandsofUser(cBrand, new SKUCategoryInterface() {

                @Override
                public void success(Response<SalesObjectResponse> response) {
                    Log.i(LOG_TAG, "getBrandItems Status" + response.body().getStatus());
                    if (response.body().getStatus() == 1) {

                        salesSKUArrayResponseArrayList = response.body().getSku();
                        CustomListAdapter adapter = new CustomListAdapter(OrderActivity.this, 0, salesSKUArrayResponseArrayList);
                        listView_order.setAdapter(adapter);

                    } else {
                        Toast.makeText(OrderActivity.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failed(String error) {
                    Log.i("SKU", error);
                    Toast.makeText(OrderActivity.this, error, Toast.LENGTH_SHORT).show();
                    listView_order.setVisibility(View.GONE);

                    frameLayout_noProducts.setVisibility(View.VISIBLE);
                }
            });
        } else {
            Toast.makeText(this, "please check your internet connection", Toast.LENGTH_SHORT).show();
            frameLayout_noProducts.setVisibility(View.VISIBLE);
        }

    }


    void getSelectedItemAndPrice() {

        SalesSKUArrayResponse skuArrayResponse;


        beemDatabase.removeSelectedItemTableRaws();

        int listLength = listView_order.getChildCount();

        int checkSelectionExist = 0;
        doubleQuantity = 0;

        int looseItem, cartonItem;

        if  (listLength > 0) {

            for (int i = 0; i < listLength; i++) {

                looseItem = 0; cartonItem = 0;

                view = listView_order.getChildAt(i);

                txtView_name = (TextView) view.findViewById(R.id.txtView_name);

                edtText_loose = (EditText) view.findViewById(R.id.edtText_loose);
                edtText_carton = (EditText) view.findViewById(R.id.edtText_carton);

                String looseText = edtText_loose.getText().toString().trim();
                String cartonText = edtText_carton.getText().toString().trim();

                if (looseText.equals("")  &&
                        cartonText.equals("")) {
                } else if (looseText.length() > 0 &&
                        cartonText.length() > 0){
                    doubleQuantity += 1;
                    checkSelectionExist++;
                } else if (looseText.length() > 0 &&
                        cartonText.equals("")){
                    looseItem = Integer.parseInt(looseText);
                } else if (looseText.equals("") &&
                        cartonText.length() > 0){
                    cartonItem = Integer.parseInt(cartonText);
                }

//                if (looseItem != 0 || cartonItem != 0 && doubleQuantity == 0) {
//                    checkSelectionExist++;
//                }

                if (looseItem > 0 || cartonItem > 0) {

                    checkSelectionExist++;

                    int saleTypes, totalItem;
                    if (cartonItem != 0) {
                        saleTypes = 1;
                        totalItem = cartonItem;
                    } else {
                        saleTypes = 0;
                        totalItem = looseItem;
                    }

                    skuArrayResponse = salesSKUArrayResponseArrayList.get(i);
                    LoginResponse loginResponse = beemDatabase.getUserDetail();

                    HolderListModel holder = new HolderListModel(Integer.valueOf(loginResponse.getStoreId()), saleStatus, appUtils.getDate(), skuArrayResponse.getBrand(),
                            0, skuArrayResponse.getCateId(), saleTypes, totalItem, skuArrayResponse.getPrice(),
                            skuArrayResponse.getPrice() * totalItem);

                    holderListModelList.add(holder);

                }

            }

            if (checkSelectionExist > 0) {

                if (doubleQuantity > 0) {
                    holderListModelList.clear();
                    customAlertDialog.hideDialog();
                    customAlertDialog.showDialog(false);
                } else {
                    sendOrder(saleStatus);
                }
            } else {
                customAlertDialog.hideDialog();
                customAlertDialog.showDialog(true);
            }
        } else {
            listView_order.setVisibility(View.GONE);
            frameLayout_noProducts.setVisibility(View.VISIBLE);

        }

    }

    void getSalesId() {

        getLocation();

        SharedPreferences preferences = this.getSharedPreferences(Constants.BA_ATTENDANCE_ID, MODE_PRIVATE);
        int id = preferences.getInt(Constants.KEY_BA_ATTENDANCE_ID, 0);

        LoginResponse loginResponse = beemDatabase.getUserDetail();

        if (networkUtils.isNetworkConnected()) {

            String currentString = age;
            String[] separated = currentString.split("-");

            int calculatedAge = Integer.parseInt(separated[0]);
            calculatedAge += Integer.parseInt(separated[1]);

            calculatedAge = calculatedAge / 2;

//            int parsedContact = Integer.parseInt(contact);

            networkUtils.sendSaleDetail(cusName, contact, email, gender, calculatedAge, cBrand, pBrand, saleStatus,
                    id, loginResponse.getName(), "Manager", "Karachi", (int) latitude, new LoginInterface() {
                        @Override
                        public void success(Response<LoginResponse> loginResponse) {
                            Log.i(LOG_TAG, "getSalesId Status" + loginResponse.body().getStatus());
                            if (loginResponse.body().getStatus() == 1) {
                                saleStatus = loginResponse.body().getSales_id();
                            }
                        }

                        @Override
                        public void failed(String error) {
                            Toast.makeText(OrderActivity.this, error, Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            Toast.makeText(this, "Please Check Your Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

}