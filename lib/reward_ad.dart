import 'package:baiduad/baiduad.dart';
import 'package:baiduad/const.dart';
import 'package:flutter/services.dart';

class RewardAD {
  final String posID;
  final RewardADCallback adEventCallback;
  MethodChannel methodChannel;

  RewardAD({this.posID, this.adEventCallback}) {
    methodChannel = MethodChannel('$rewardID\_$posID');
    methodChannel.setMethodCallHandler(_handleCall);
    Baiduad.loadRewardAD(posID: posID);
  }

  Future<void> _handleCall(MethodCall call) async {
    if (adEventCallback != null) {
      RewardADEvent event;
      switch (call.method) {
        case 'onAdFailed':
          event = RewardADEvent.onAdFailed;
          break;
        case 'onVideoDownloadSuccess':
          event = RewardADEvent.onVideoDownloadSuccess;
          break;
        case 'onAdShow':
          event = RewardADEvent.onAdShow;
          break;
        case 'onAdClick':
          event = RewardADEvent.onAdClick;
          break;
        case 'onVideoDownloadFailed':
          event = RewardADEvent.onVideoDownloadFailed;
          break;
        case 'onAdClose':
          event = RewardADEvent.onAdClose;
          break;
        case 'playCompletion':
          event = RewardADEvent.playCompletion;
          break;
      }
      adEventCallback(event, call.arguments);
    }
  }

  Future<void> loadAD() async {
    await methodChannel.invokeMethod('load');
  }

  Future<void> showAD() async {
    await methodChannel.invokeMethod('show');
  }

  Future<bool> isReady() async {
    await methodChannel.invokeMethod('isReady');
  }
}

typedef RewardADCallback = Function(RewardADEvent event, Map args);

enum RewardADEvent {
  onAdFailed,
  onVideoDownloadSuccess,
  onAdShow,
  onAdClick,
  onVideoDownloadFailed,
  onAdClose,
  playCompletion,
}