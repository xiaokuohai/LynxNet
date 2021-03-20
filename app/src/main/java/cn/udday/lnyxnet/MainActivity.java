package cn.udday.lnyxnet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import cn.udday.lnyx2.Call;
import cn.udday.lnyx2.Lnyx;
import cn.udday.lnyx2.RequestFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        textView = findViewById(R.id.text);
        button.setOnClickListener(this);
    }
    public void toPost(){
        To to = new Lnyx.Builder("https://v2.alapi.cn/api/").build().create(To.class);
        to.toPost("beijing").enqueue(new cn.udday.lnyx2.Callback() {
            @Override
            public void onResponse(cn.udday.lnyx2.Response response) {
                textView.setText(response.body().string());
            }

            @Override
            public void onFail(cn.udday.lnyx2.Request request, IOException e) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                toPost();
                Toast.makeText(this,"点击成功",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}