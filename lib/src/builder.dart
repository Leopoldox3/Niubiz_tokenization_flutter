import 'result.dart';

class VisaNetResultBuilder {
  final Map<dynamic, dynamic> data;

  const VisaNetResultBuilder(this.data);

  VisaNetResult build() => VisaNetResult(
        resultCode: data['resultCode'],
        message: data['message'],
        data: data['data'],
      );
}
