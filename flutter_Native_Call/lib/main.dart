import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MaterialApp(debugShowCheckedModeBanner: false, home: Myapp()));
}

class Myapp extends StatefulWidget {
  const Myapp({Key? key}) : super(key: key);

  @override
  State<Myapp> createState() => _MyappState();
}

class _MyappState extends State<Myapp> {
  static const batteryChannel = MethodChannel('vijay.com/battery');
  static const batteryChannel1 = MethodChannel('example.com/example');
  static const batteryChannel2 = MethodChannel('camera.com/camera');
  String batteryLevel = "waitting....";
  String batteryLevel1 = "send data to native and receive flutter....";
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Column(
        //crossAxisAlignment: CrossAxisAlignment.center,
        mainAxisAlignment: MainAxisAlignment.center,
        children: [
          Center(
              child: Column(
            children: [
              Text('Methode call frome native code '),
              SizedBox(
                height: 20,
              ),
              Text('${batteryLevel}'),
              SizedBox(
                height: 10,
              ),
              Text('${batteryLevel1}',style: TextStyle(color: Colors.red),),
              SizedBox(
                height: 20,
              ),
              TextButton(
                onPressed: () {
                  getBatteryLevel();
                },
                child: Text('Get Battery Level',style: TextStyle(color: Colors.white ),),
                style: TextButton.styleFrom(
                 backgroundColor: Colors.blue
                ),
              )
            ],
          ))
        ],
      ),
    );
  }

  Future getBatteryLevel() async {
    final arguments1={'name':'vijay'};
    final arguments2="android";
    final String newBatteryLevel = await  batteryChannel.invokeMethod('getBatteryLevel',arguments1);
    final String newBatteryLevel1 = await  batteryChannel1.invokeMethod('getBatteryLevel1',arguments2);
    setState(() {
      batteryLevel = newBatteryLevel;
      batteryLevel1 = newBatteryLevel1;

    });
    await  batteryChannel2.invokeMethod('getBatteryLevel2');
  }
}
