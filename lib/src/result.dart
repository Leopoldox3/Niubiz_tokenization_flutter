class VisaNetResult {
  final String resultCode;
  final String message;
  final String data;

  const VisaNetResult(
      {required this.resultCode, required this.message, required this.data});

  bool get isSuccessful => (resultCode == 'SUCCESS');

  bool get isFailed => (resultCode == 'ERROR');

  bool get isUserCanceled => (resultCode == 'USER_CANCELED');
}
