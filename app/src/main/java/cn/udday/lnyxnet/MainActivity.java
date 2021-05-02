package cn.udday.lnyxnet;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;

import cn.udday.lnyx2.Callback;
import cn.udday.lnyx2.Lnyx;
import cn.udday.lnyx2.Request;
import cn.udday.lnyx2.Response;
import cn.udday.lnyxg.LnyxJson;
import cn.udday.lnyxp.LnyxP;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button button;
    private Button button2;
    private TextView textView;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        textView = findViewById(R.id.text);
        imageView = findViewById(R.id.image);
        button.setOnClickListener(this);
        button2.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= 23 && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }
    }
    public void toPost(){
        To to = new Lnyx.Builder("https://v2.alapi.cn/api/").build().create(To.class);
        to.toGet().enqueue(new Callback() {
            @Override
            public void onResponse(Response response) throws IOException {
                String string = response.body().string();
                QingHua qingHua = LnyxJson.fromJson(string, QingHua.class);
                textView.setText(qingHua.getData().getContent());
            }

            @Override
            public void onFail(Request request, IOException e) {

            }
        });
//        to.toPost("beijing").enqueue(new cn.udday.lnyx2.Callback() {
//            @Override
//            public void onResponse(cn.udday.lnyx2.Response response) {
//                textView.setText(response.body().string());
//            }
//
//            @Override
//            public void onFail(cn.udday.lnyx2.Request request, IOException e) {
//
//            }
//        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button:
                toPost();
                LnyxP.with(this).load("https://v2.alapi.cn/api/acg?token=a337xZZFXIn7SqD1").into(imageView);
                Toast.makeText(this,"点击成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.button2:
                LnyxP.with(this).removeCache();
                break;
        }
    }
}