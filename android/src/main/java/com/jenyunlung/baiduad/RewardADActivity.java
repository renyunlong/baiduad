package com.jenyunlung.baiduad;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.NonNull;

import com.baidu.mobads.rewardvideo.RewardVideoAd;

import java.util.HashMap;

import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;

public class RewardADActivity extends Activity
        implements RewardVideoAd.RewardVideoAdListener, MethodChannel.MethodCallHandler {
    public static final String TAG = "RewardADActivity";
    private String posID;
    private MethodChannel methodChannel;
    private RewardVideoAd mRewardVideoAd;

    public RewardADActivity(BinaryMessenger messenger, String posID) {
        this.posID = posID;
        this.methodChannel = new MethodChannel(messenger, Constants.rewardID+"_"+posID);
        this.methodChannel.setMethodCallHandler(this);
    }

    //================= MethodCallHandler
    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull MethodChannel.Result result) {
        Log.i(TAG, call.method);
        switch (call.method) {
            case "load":
                this.mRewardVideoAd = new RewardVideoAd(BaiduadPlugin.applicationContext, posID,
                        RewardADActivity.this, true);
                this.mRewardVideoAd.load();
                result.success(true);
                break;
            case "show":
                if (this.mRewardVideoAd == null) {
                    this.mRewardVideoAd = new RewardVideoAd(BaiduadPlugin.applicationContext, posID,
                            RewardADActivity.this, true);
                }
                mRewardVideoAd.show();
                break;
            case "isReady":
                boolean isReady = mRewardVideoAd != null && mRewardVideoAd.isReady();
                result.success(isReady);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    //================= RewardVideoAdListener
    @Override
    public void onAdShow() {
        methodChannel.invokeMethod("onAdShow", null);
    }

    @Override
    public void onAdClick() {
        methodChannel.invokeMethod("onAdClick", null);
    }

    @Override
    public void onAdClose(float v) {
        HashMap<String, Float> params = new HashMap<>();
        params.put("playScale", v);
        methodChannel.invokeMethod("onAdClose", params);
    }

    @Override
    public void onAdFailed(String s) {
        HashMap<String, String> params = new HashMap<>();
        params.put("reason", s);
        methodChannel.invokeMethod("onAdFailed", params);
    }

    @Override
    public void onVideoDownloadSuccess() {
        methodChannel.invokeMethod("onVideoDownloadSuccess", null);
    }

    @Override
    public void onVideoDownloadFailed() {
        methodChannel.invokeMethod("onVideoDownloadFailed", null);
    }

    @Override
    public void playCompletion() {
        methodChannel.invokeMethod("playCompletion", null);
    }
}
