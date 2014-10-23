#include <SPI.h>
#include <WiFi.h>

char ssid[] = "";
char pass[] = "";
int status = WL_IDLE_STATUS;

WiFiClient client;
char server[] = "54.191.19.173";
int port = 5000;

unsigned long last_connection_time = 0;
const unsigned long posting_interval = 10L * 1000L;

void setup() {
  Serial.begin(57600);
  Serial.println("Initializing...");

  if (is_shield_present() == 0) {
    while(true);
  }
    
  check_firmware();
  connect_wpa(ssid, pass);
  
}

void loop() {
  while (client.available()) {
    char c = client.read();
    Serial.write(c);
  }
  
  if (millis() - last_connection_time > posting_interval) {
    http_request();
  }
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
     delay(10000); 
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
    Serial.println("connecting...");

    client.println("POST /ping HTTP/1.1");
    client.println("Host: http://54.191.19.173/");
    client.println("User-Agent: ArduinoWiFi/1.1");
    client.println("Connection: close");
    client.println();

    last_connection_time = millis();
  }
  else {
    Serial.println("connection failed");
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
