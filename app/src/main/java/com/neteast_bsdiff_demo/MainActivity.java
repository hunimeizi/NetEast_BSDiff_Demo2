package com.neteast_bsdiff_demo;

import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.neteast_bsdiff_demo.utils.UriPareutils;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    //用于在应用程序启动时， 加载本地Lib 库
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Example of a call to a native method
        TextView tv = (TextView) findViewById(R.id.sample_text);
        tv.setText(BuildConfig.VERSION_NAME);
    }

    /**
     * 合成安装包
     *
     * @param oldAPK 旧版本安装包
     * @param patch  差分包
     * @param output 合成之后的新文件
     */
    public native void bsPath(String oldAPK, String patch, String output);

    public void MyOnClick(View view) {
        //服务器下载差分包

        new AsyncTask<Void, Void, File>() {
            @Override
            protected File doInBackground(Void... voids) {
                //合成新文件
                String oldApk = getApplicationInfo().sourceDir;
                //模拟已经下载到了手机内存
                String patch  = new File(Environment.getExternalStorageDirectory(),"patch").getAbsolutePath();
                //新文件路径
                String output = createNewApk().getAbsolutePath();
                bsPath(oldApk, patch, output);
                return new File(output);
            }

            /**
             * 创建合成后的新版本apk文件
             * @return 路径
             */
            private File createNewApk() {
                File newApk = new File(Environment.getExternalStorageDirectory(),"bsdiff.apk");
                if (!newApk.exists()){
                    try {
                        newApk.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return newApk;
            }

            @Override
            protected void onPostExecute(File file) {
                super.onPostExecute(file);
                UriPareutils.startInstall(MainActivity.this, file);
            }
        }.execute();
    }


}
