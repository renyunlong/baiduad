import 'package:baiduad/reward_ad.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:baiduad/baiduad.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion = "ssss";
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await Baiduad.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  RewardAD _rewardAD;

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: GestureDetector(
            onTap: () {
              print("click click");
              Baiduad.initSDK(appID: "b2ea9c02");
              Baiduad.loadRewardAD(posID: "7193673");
              RewardAD rewardAD = RewardAD(posID: "7193673", adEventCallback: _adEventCallback);
              rewardAD.loadAD();
            },
            child: Text('Running on: $_platformVersion\n'),
          ),
        ),
      ),
    );
  }

  void _adEventCallback(RewardADEvent event, Map params) {
    switch (event) {
      case RewardADEvent.onVideoDownloadSuccess:
        _rewardAD.showAD();
        break;
      case RewardADEvent.onAdClose:
      case RewardADEvent.playCompletion:
        Navigator.of(context).pop();
        showDialog(
            context: context,
            builder: (context) {
              return Center(
                child: ClipRRect(
                  clipBehavior: Clip.antiAliasWithSaveLayer,
                  borderRadius: BorderRadius.circular(32.0),
                  child: Card(
                    child: Container(
                      width: 320.0,
                      height: 280.0,
                      color: Colors.red,
                      alignment: Alignment.center,
                      child: Text(
                        '恭喜你获得10元',
                        textScaleFactor: 2.1,
                      ),
                    ),
                  ),
                ),
              );
            });
        break;
      default:
    }
  }
}
