package com.jenyunlung.baiduad;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.baidu.mobads.AdView;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.BinaryMessenger;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/** BaiduadPlugin */
public class BaiduadPlugin implements FlutterPlugin, MethodCallHandler {
  private static String TAG = "BaiduadPlugin";
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  public static Context applicationContext;
  private BinaryMessenger binaryMessenger;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    applicationContext = flutterPluginBinding.getApplicationContext();
    binaryMessenger = flutterPluginBinding.getBinaryMessenger();
    channel = new MethodChannel(flutterPluginBinding.getFlutterEngine().getDartExecutor(), "baidu_ad");
    channel.setMethodCallHandler(this);
  }

  // This static function is optional and equivalent to onAttachedToEngine. It supports the old
  // pre-Flutter-1.12 Android projects. You are encouraged to continue supporting
  // plugin registration via this function while apps migrate to use the new Android APIs
  // post-flutter-1.12 via https://flutter.dev/go/android-project-migration.
  //
  // It is encouraged to share logic between onAttachedToEngine and registerWith to keep
  // them functionally equivalent. Only one of onAttachedToEngine or registerWith will be called
  // depending on the user's project. onAttachedToEngine or registerWith must both be defined
  // in the same class.
  public static void registerWith(Registrar registrar) {
    final MethodChannel channel = new MethodChannel(registrar.messenger(), "baidu_ad");
    channel.setMethodCallHandler(new BaiduadPlugin());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    Log.i(TAG, "call native method "+call.method);
    switch (call.method) {
      case "config":
        String appID = call.argument("appID");
        // 设置APPSID，文档中没有找到init方法，demo中init方法也被depressed了
        AdView.setAppSid(applicationContext, appID);
        result.success(true);
        break;
      case "loadRewardAD":
        String posID = call.argument("posID");
        RewardADActivity rewardADActivity = new RewardADActivity(binaryMessenger, posID);
        result.success(true);
        break;
      case "getPlatformVersion":
        result.success("Android " + android.os.Build.VERSION.RELEASE);
        break;
      default:
        result.notImplemented();
        break;
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
  }
}
