package com.zsoftware.update;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.pgyersdk.update.PgyUpdateManager;
import com.pgyersdk.update.UpdateManagerListener;
import com.pgyersdk.update.javabean.AppBean;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import com.pgyersdk.update.DownloadFileListener;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CordovaWebView;
import android.net.Uri;
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
                   
                new PgyUpdateManager.Builder()
                        .setForced(true)                //设置是否强制更新,非自定义回调更新接口此方法有用
                        .setUserCanRetry(true)         //失败后是否提示重新下载，非自定义下载 apk 回调此方法有用
                        .setDeleteHistroyApk(true)     // 检查更新前是否删除本地历史 Apk
                        .setUpdateManagerListener(new UpdateManagerListener() {
                            @Override
                            public void onNoUpdateAvailable() {
                                //没有更新是回调此方法
                               Toast.makeText(_activity, "已是最新版", Toast.LENGTH_LONG);
                            }

                            @Override
                            public void onUpdateAvailable(AppBean appBean) {
                                //没有更新是回调此方法
                      

                                //调用以下方法，DownloadFileListener 才有效；如果完全使用自己的下载方法，不需要设置DownloadFileListener
                                PgyUpdateManager.downLoadApk(appBean.getDownloadURL());
                            }

                            @Override
                            public void checkUpdateFailed(Exception e) {
                                //更新检测失败回调
                               Toast.makeText(_activity, "检查失败", Toast.LENGTH_LONG);

                            }
                        })
                        //注意 ：下载方法调用 PgyUpdateManager.downLoadApk(appBean.getDownloadURL()); 此回调才有效
                        .setDownloadFileListener(new DownloadFileListener() {   // 使用蒲公英提供的下载方法，这个接口才有效。
                            @Override
                            public void downloadFailed() {
                                //下载失败
                                Toast.makeText(_activity, "下载失败", Toast.LENGTH_LONG);
                            }

                            @Override
                            public void downloadSuccessful(Uri uri) {
                                PgyUpdateManager.installApk(uri);  // 使用蒲公英提供的安装方法提示用户 安装apk
                            }

                            @Override
                            public void onProgressUpdate(Integer... integers) {
                               
                            }
                        })
                        .register();

                }
            });
        }

        // callbackContext.success();
        return true;
    }
}
