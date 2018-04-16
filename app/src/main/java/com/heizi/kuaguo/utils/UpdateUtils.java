package com.heizi.kuaguo.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;

import com.heizi.mycommon.engypt.Descoder;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;


public class UpdateUtils {
	private static final String UPDATE_SERVERAPK = "laoupdate.apk";
	
	private Context mContext = null;
	private Callback mCallback = null;
	private String mDownloadUrl = "";
	private String mChecksumSHA256 = null;
	
	
	public UpdateUtils(Context context, String mDownloadUrl) {
		mContext = context;
		this.mDownloadUrl = mDownloadUrl;
	}
	
	public interface Callback {
		void onCheck(boolean isNewVersion, String updateContent, String checksumSHA256);
		void onDownload(int percent);
		void onFinish();
		void onError();
	}
	
	public void setCallback(Callback callback) {
		mCallback = callback;
	}
	

	
	public void download() {
		new Thread(new Runnable() {
		
			
			
			public void run() {
				boolean isExist = checkUpdateFileExist(mChecksumSHA256);
				if (isExist) {
                    //下载完成，启动更新
                    mCallback.onFinish();
                    update();
					return;
				}
				
				
				HttpClient client = new DefaultHttpClient();
				client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT,10000);
				client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,10000);
                HttpGet get = new HttpGet(mDownloadUrl);
                HttpResponse response;
                try {
                    response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    long length = entity.getContentLength();
                    InputStream is =  entity.getContent();
                    FileOutputStream fileOutputStream = null;
                    if(is != null){
                        File file = new File(Environment.getExternalStorageDirectory(),UPDATE_SERVERAPK);
                        fileOutputStream = new FileOutputStream(file);
                        byte[] b = new byte[1024];
                        int charb = -1;
                        int count = 0;
                        while((charb = is.read(b))!=-1){
                            fileOutputStream.write(b, 0, charb);
                            count += charb;
                            if (mCallback!=null) {
                            	mCallback.onDownload((int) (count * 100 / length));
							}
                        }
                        fileOutputStream.flush();
                        if(fileOutputStream!=null){
                            fileOutputStream.close();
                        }
                        
                        //下载完成，启动更新
                        mCallback.onFinish();
                        update();
                    } else {
                    	if (mCallback!=null) {
    						mCallback.onError();
    					}
                    }
                    
                }  catch (Exception e) {
                    e.printStackTrace();	
                    if (mCallback!=null) {
						mCallback.onError();
					}
                } 
			}
		}).start();
	}
	
	private void update(){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(new File(Environment.getExternalStorageDirectory(),UPDATE_SERVERAPK))
                , "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }
	
	public boolean checkUpdateFileExist(String sha256) {
		File file = new File(Environment.getExternalStorageDirectory(),UPDATE_SERVERAPK);
		String fileHash = Descoder.getFileSHA256(file);
		if (fileHash==null) {
			return false;
		} else {
			if (sha256!=null && fileHash.equals(sha256)) {
				return true;
			}
		}
		
		return false;
	}
}
