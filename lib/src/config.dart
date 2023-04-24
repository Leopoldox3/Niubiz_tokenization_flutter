import 'environment.dart';

class VisaNetConfig {
  final VisaNetEnvironment env;

  final String username;
  final String password;
  final String merchantId;

  //holder
  final String name;
  final String lastName;
  final String email;

  //amount
  final double? amount;

  //UI
  final String logoTitle;
  final String? logoAssetName;

  const VisaNetConfig(
    this.env, {
    required this.logoTitle,
    this.logoAssetName,
    required this.username,
    required this.password,
    required this.merchantId,
    required this.name,
    required this.lastName,
    required this.email,
    this.amount,
  });

  Map<String, dynamic> toMap() {
    return {
      'env': env.name,
      'endPoint': env.url,
      //
      'username': username,
      'password': password,
      'merchantId': merchantId,
      //
      'name': name,
      'lastName': lastName,
      'email': email,
      //
      'amount': amount,
      //
      'logoTitle': logoTitle,
      'logoAssetName': logoAssetName
    };
  }
}
