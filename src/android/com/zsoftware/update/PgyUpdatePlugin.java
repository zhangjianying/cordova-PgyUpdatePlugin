package com.zsoftware.update;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import org.json.JSONArray;
import org.json.JSONException;

public class PgyUpdatePlugin extends CordovaPlugin {
    private Context mContext;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        this.mContext = this.cordova.getActivity().getApplicationContext();
    }

    @Override
    public boolean execute(String action, JSONArray data, final CallbackContext callbackContext) throws JSONException {
        final Activity _activity = this.cordova.getActivity();


        if (action.equals("updateApp")) {
            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    new PgyUpdateManager.Builder()
                            .setForced(false)                //设置是否强制更新
                            .setUserCanRetry(true)         //失败后是否提示重新下载
                            .setDeleteHistroyApk(true)     // 检查更新前是否删除本地历史 Apk
                            .register();
                }
            });
        }

        if (action.equals("updateAppAndListener")) {
            cordova.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    PgyUpdateManager.register(new UpdateManagerListener() {   // 弃用方法，不推介
                        @Override
                        public void onNoUpdateAvailable() {
                            //检测没有更新的回调
                            Toast.makeText(_activity, "已是最新版", Toast.LENGTH_LONG);
                        }

                        @Override
                        public void onUpdateAvailable(AppBean appBean) {
                            //调用以下方法，DownloadFileListener 才有效；如果完全使用自己的下载方法，不需要设置DownloadFileListener
                            PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
                            callbackContext.success();
                        }

                        @Override
                        public void checkUpdateFailed(Exception e) {   //再回调失败的时候，增加了新的接口

                        }
                    });

                }
            });
        }

        // callbackContext.success();
        return true;
    }
}
