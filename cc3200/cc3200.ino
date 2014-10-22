#include <WiFi.h>

char ssid[] = "test";
int status = WL_IDLE_STATUS;

void setup() {
  Serial.begin(57600);
  Serial.println("Initializing");

  if (is_shield_present() == 0) {
    while(true);
  }

}

void loop() {
  
}

int is_shield_present() {
  if (WiFi.status() == WL_NO_SHIELD) {
    return 0;
  }
  return 1;
}

