import 'dart:convert';
import 'package:flutter/material.dart';
import 'package:niubiz_tokenization_flutter/visanet_tokenization.dart';

class MyApp extends StatefulWidget {
  final VisaNetConfig defaultConfig;

  const MyApp({super.key, required this.defaultConfig});

  @override
  MyAppState createState() => MyAppState();
}

class MyAppState extends State<MyApp> {
  late VisaNetResult result;

  @override
  void initState() {
    super.initState();

    result = const VisaNetResult(data: '', resultCode: '', message: '');
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
        home: Scaffold(
            appBar: AppBar(
              title: const Text('VisaNet Tokenization example'),
            ),
            body: Center(
              child: SingleChildScrollView(
                padding: const EdgeInsets.all(16),
                child: Center(
                  child: Column(
                    mainAxisSize: MainAxisSize.min,
                    children: <Widget>[
                      ElevatedButton(
                          onPressed: onStart,
                          child: const Text('Tokenization')),
                      const SizedBox(height: 24),
                      const Text('Result',
                          style: TextStyle(
                              fontSize: 16, fontWeight: FontWeight.bold)),
                      const SizedBox(height: 12),
                      Text('Code: ${result.resultCode}'),
                      const Text('Message:'),
                      Container(
                        constraints: const BoxConstraints(maxWidth: 320),
                        child: Text(result.message ?? ""),
                      ),
                      const SizedBox(height: 8),
                      const Text('Data: '),
                      Container(
                        constraints: const BoxConstraints(maxWidth: 320),
                        child: Text(_formatResult(result.data)),
                      )
                    ],
                  ),
                ),
              ),
            )));
  }

  String _formatResult(String data) {
    try {
      final object = json.decode(data);
      return const JsonEncoder.withIndent('  ').convert(object);
    } catch (_) {}

    return data ?? '';
  }

  VisaNetConfig createVisaNetConfig() {
    if (widget.defaultConfig != null) {
      return widget.defaultConfig;
    }

    // SET YOUR VALUES HERE!  //
    const config = VisaNetConfig(
      //Dev
      VisaNetEnvironment.dev,

      //Prod
      //VisaNetEnvironment.prod,

      //company
      logoTitle: '<CompanyName>',
      logoAssetName: '<asset_image_path>',

      //credentials
      username: '',
      password: '',
      merchantId: '',

      //card holder
      name: '',
      lastName: '',
      email: '',
      amount: 0,
    );

    return config;
  }

  void onStart() async {
    final config = createVisaNetConfig();

    final _result = await VisaNetTokenization.start(config);

    if (result.isSuccessful) {
      //ok
    } else if (result.isFailed) {
      debugPrint('error: ${result.message}');
    }

    setState(() {
      result = _result;
    });
  }
}
