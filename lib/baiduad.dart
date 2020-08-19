
import 'dart:async';

import 'package:baiduad/const.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class Baiduad {
  static const MethodChannel _channel =
      const MethodChannel(pluginID);

  static Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  static Future<bool> initSDK({@required String appID}) async =>
      await _channel.invokeMethod('config', {'appID': appID});

  static Future<bool> loadRewardAD({@required String posID}) async {
    return await _channel.invokeMethod('loadRewardAD', {'posID': posID});
  }
}
