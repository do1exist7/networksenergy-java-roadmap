interface remoteControllable {
    void turnOn();
    void turnOff();
}

abstract class SmartDevice implements remoteControllable {
    private String deviceName;
    private boolean isOnline;

    public SmartDevice(String deviceName){
        this.setDeviceName(deviceName);
        this.isOnline = false;
    }
    public String getDeviceName(){
        return this.deviceName;
    }
    public void setDeviceName(String deviceName){
        if (deviceName == null || deviceName.isBlank()) {
            throw new IllegalArgumentException("deviceName cannot be null or blank");
        }
        this.deviceName = deviceName;
    }

    public boolean getIsOnline(){
        return this.isOnline;
    }
    public void setIsOnline(boolean isOnline){
        this.isOnline = isOnline;
    }

    public void displayStatus(){
        IO.println("Device name is: %s, and isOnline: %b".formatted(this.deviceName, this.isOnline));
    }
    abstract void performSelfTest();
}

class SmartLight extends SmartDevice{
    private int brightnessLevel;
    public SmartLight(String deviceName){
        super(deviceName);
    }
    @Override
    void performSelfTest(){
        IO.println("Brightness level: %s".formatted(this.brightnessLevel));
        super.displayStatus();
    }
    @Override
    public void turnOn(){
        IO.println("Turning on %s with the brightness %d".formatted(super.getDeviceName(), this.brightnessLevel));
    }
    @Override
    public void turnOff(){
        IO.println("Turning off %s".formatted(super.getDeviceName()));
    }

    public void setBrightnessLevel(int brightnessLevel){
        this.brightnessLevel = brightnessLevel;
    }
}

class SmartThermostat extends SmartDevice{
    private int temperature;
    public SmartThermostat(String deviceName){
        super(deviceName);
    }
    @Override
    void performSelfTest(){
        IO.println("Temperature level: %s".formatted(this.temperature));
        super.displayStatus();
    }
    @Override
    public void turnOn(){
        IO.println("Heating up %s to the temperature %d".formatted(super.getDeviceName(), this.temperature));
    }
    @Override
    public void turnOff(){
        IO.println("Turning off %s".formatted(super.getDeviceName()));
    }

    public void setTemperature(int temperature){
        this.temperature = temperature;
    }
}

void main() {
    remoteControllable[] devices = {new SmartLight("Living Room Light"), new SmartThermostat("Kitchen Thermostat")};
    for(var device : devices){
        device.turnOn();
    }

    try {
        var invalidLight = new SmartLight("");
    } catch (IllegalArgumentException e) {
        IO.println("Catched exception for illegal name!");
    }
}