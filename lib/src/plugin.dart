import 'dart:async';

import 'package:flutter/services.dart';

import 'builder.dart';
import 'config.dart';
import 'result.dart';

class VisaNetTokenization {
  const VisaNetTokenization._();

  static const String _channelName = 'visanet_tokenization';
  static const MethodChannel _channel = MethodChannel(_channelName);

  static Future<VisaNetResult> start(VisaNetConfig config) async {
    final result = await _channel.invokeMapMethod('', config.toMap());

    final builder = VisaNetResultBuilder(result!);
    return builder.build();
  }

  static Future<String> get test async {
    final String result = await _channel.invokeMethod('test');
    return result;
  }
}
