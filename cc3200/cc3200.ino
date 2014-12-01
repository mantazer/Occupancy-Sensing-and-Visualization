// Add request timeout

#include <SPI.h>
#include <WiFi.h>

char ssid[] = "wahoo";
char pass[] = "";
int status = WL_IDLE_STATUS;

WiFiClient client;
char server[] = "54.148.64.121";
int port = 5000;

unsigned long last_connection_time = 0;
const unsigned long posting_interval = 10L * 1000L;

int pir_pin = 28; // CC3200 PIN 53
int pir_val = 0;

int motion_flag = 0;

void setup() {
  Serial.begin(57600);
  Serial.println("Initializing...");

  if (is_shield_present() == 0) {
    while(true);
  }
    
  check_firmware();
//  connect_wpa(ssid, pass);
  connect_unprotected(ssid);
  
  pinMode(pir_pin, INPUT);
  attachInterrupt(pir_pin, motion_triggered, FALLING);
}

void loop() {
  while (client.available()) {
    char c = client.read();
    Serial.write(c);
  }
  
  if (motion_flag) {
    Serial.println("Motion detected");
    http_request();
    motion_flag = 0;
  }
  
//  if (millis() - last_connection_time > posting_interval) {
//    int pir_val = digitalRead(pir_pin);
//    Serial.println(pir_val);
//    http_request();
//  }

}

int is_shield_present() {
  if (WiFi.status() == WL_NO_SHIELD) {
    Serial.println("WiFi shield not found.");
    return 0;
  }
  Serial.println("WiFi shield found.");
  return 1;
}

void connect_wpa(char ssid[], char pass[]) {
   while (status != WL_CONNECTED) {
     Serial.print("Attempting to connect to: ");
     Serial.println(ssid);
     status = WiFi.begin(ssid, pass);
   }
  Serial.print("Connected to ");
  Serial.println(ssid);
}

void connect_unprotected(char ssid[]) {
   while (status != WL_CONNECTED) {
     Serial.print("Attempting to connect to: ");
     Serial.println(ssid);
     status = WiFi.begin(ssid);
     delay(10000); 
   }
  Serial.print("Connected to ");
  Serial.println(ssid);
}

void check_firmware() {
  String version = WiFi.firmwareVersion();
  Serial.print("Version: ");
  Serial.print(version);
  Serial.println(". Recommended 1.1.0");
}

void http_request() {
  client.stop();
  
  if (client.connect(server, port)) {
    Serial.println("Sending request...");

    client.println("POST /ping HTTP/1.1");
    client.println("Host: http://placeholder/");
    client.println("User-Agent: ArduinoWiFi/1.1");
    client.println("node-id: 304");
    client.println("node-floor: 3rd Floor");
    client.println("Connection: close");
    client.println();
    
    delay(1000);
    last_connection_time = millis();
  }
  else {
    Serial.println("Connection failed");
  }
  
}

void printWifiData() {
  IPAddress ip = WiFi.localIP();
    Serial.print("IP Address: ");
  Serial.println(ip);
  Serial.println(ip);
  
  // device MAC address:
  byte mac[6];  
  WiFi.macAddress(mac);
  Serial.print("MAC address: ");
  Serial.print(mac[5],HEX);
  Serial.print(":");
  Serial.print(mac[4],HEX);
  Serial.print(":");
  Serial.print(mac[3],HEX);
  Serial.print(":");
  Serial.print(mac[2],HEX);
  Serial.print(":");
  Serial.print(mac[1],HEX);
  Serial.print(":");
  Serial.println(mac[0],HEX);
 
}

void printCurrentNet() {
  Serial.print("SSID: ");
  Serial.println(WiFi.SSID());

  // Router MAC address
  byte bssid[6];
  WiFi.BSSID(bssid);    
  Serial.print("BSSID: ");
  Serial.print(bssid[5],HEX);
  Serial.print(":");
  Serial.print(bssid[4],HEX);
  Serial.print(":");
  Serial.print(bssid[3],HEX);
  Serial.print(":");
  Serial.print(bssid[2],HEX);
  Serial.print(":");
  Serial.print(bssid[1],HEX);
  Serial.print(":");
  Serial.println(bssid[0],HEX);

  long rssi = WiFi.RSSI();
  Serial.print("signal strength (RSSI):");
  Serial.println(rssi);

  byte encryption = WiFi.encryptionType();
  Serial.print("Encryption Type:");
  Serial.println(encryption,HEX);
  Serial.println();
}

void motion_triggered() {
  motion_flag = 1;
}

