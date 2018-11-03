package com.example.home.androiddevelopment;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtFirstNumber, edtSecondNumber;
    Button btnMultiply;
    TextView txtMultiplyResult;
    MultiplyInterface myInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtFirstNumber = findViewById(R.id.edtFirstNumber);
        edtSecondNumber = findViewById(R.id.edtSecondNumber);
        btnMultiply = findViewById(R.id.btnMultiply);
        txtMultiplyResult = findViewById(R.id.txtMultiplyResult);

        btnMultiply.setOnClickListener(MainActivity.this);

        Intent multiplyService = new Intent(MainActivity.this, MultiplicationService.class);
        bindService(multiplyService, myServiceConnection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection myServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

            myInterface = MultiplyInterface.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    public void onClick(View v) {
        int firstNumber = Integer.parseInt(edtFirstNumber.getText().toString());
        int secondNumber = Integer.parseInt(edtSecondNumber.getText().toString());
        try {
            int result = myInterface.multiplyTwoValues(firstNumber, secondNumber);
            txtMultiplyResult.setText(result + "");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}



