# Smart Home
A comprehensive IoT solution that enables remote monitoring and control of home devices through a mobile application. This project implements a smart home system with real-time connectivity between embedded hardware and an Android mobile application using Firebase as the cloud backend.

## Project Overview
The Remote Smart Control project is a pervasive computing solution that bridges embedded systems with mobile technology. It allows users to remotely control and monitor various home devices including lighting, temperature, security systems, and more through an intuitive Android application.

### Features 
- **Seamless Connectivity:** WiFi-based communication between hardware and mobile app
- **Remote Control:** Control home devices from anywhere with internet connection
- **Real-time Monitoring:** Live updates of sensor data and device status
- **Cloud Integration:** Firebase Realtime Database for data storage and synchronization
- **Offline Support:** SQLite local caching for offline functionality

## Mobile Application Features
1. Registration & Authentication
    - User registration with profile picture, email, birthdate
    - Firebase Authentication integration
    - "Remember Me" functionality
    - Password recovery system
2. Main Dashboard
    - ListView/RecyclerView of all available actions
    - Search functionality to filter actions
    - Real-time status updates
3. Smart Home Controls

    - **Temperature Monitoring:** Real-time temperature readings from LM35 sensor
    - **Security System:** Password-based door lock with keypad
    - **Lighting Control:** Remote LED on/off control
    - **Intrusion Detection:** Ultrasonic sensor-based entry monitoring
    - **Message Display:** Send custom messages to LCD display


4. Additional Features

    - Activity logging with timestamps
    - User profile management
    - Offline mode with cached data
  
## Hardware Components
- Microcontroller:
   - ESP8266 NodeMCU (recommended) or Arduino Uno + ESP8266 WiFi Module
Sensors:

LM35 Temperature Sensor
HC-SR04 Ultrasonic Sensor


- Actuators:
    - LEDs for lighting control
    - DC Motor for fan control
    - 16x2 LCD Display
    - 4x4 Matrix Keypad


- Basic Components:

    - Breadboard
    - Jump wires
    - Resistors
