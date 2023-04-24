class VisaNetEnvironment {
  final String name;
  final String url;

  const VisaNetEnvironment(this.name, this.url);

  static const dev =
      VisaNetEnvironment('dev', 'https://apitestenv.vnforapps.com');
  static const prod =
      VisaNetEnvironment('prod', 'https://apiprod.vnforapps.com');
}
