import 'package:flutter/material.dart';
import 'package:niubiz_tokenization_flutter/visanet_tokenization.dart';

import 'my_app.dart';

const configDev1 = VisaNetConfig(
  VisaNetEnvironment.dev,
  //company
  logoTitle: "My Company Dev1",
  //credentials
  username: "integraciones.visanet@necomplus.com",
  password: "d5e7nk\$M",
  merchantId: "602545705",
  //card holder
  name: "Nombre1",
  lastName: "Apellido1",
  email: "demo@cuy.pe",
);

void main() => runApp(const MyApp(defaultConfig: configDev1));
