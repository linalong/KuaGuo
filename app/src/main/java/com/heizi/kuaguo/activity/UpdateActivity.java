package com.heizi.kuaguo.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.heizi.mycommon.utils.WebViewUtil;
import com.heizi.kuaguo.R;


/**
 * Created by leo on 17/8/21.
 */

public class UpdateActivity extends BaseActivity {
    WebView mWebView;
    String url = "";

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_web;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("下载新版本");

        ImageView btn_back = (ImageView) findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        url = getIntent().getStringExtra("url");
        mWebView = (WebView) findViewById(R.id.webview);
        WebViewUtil webViewUtil = new WebViewUtil(this);
        webViewUtil.setView(mWebView);
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
        }
    }
}
