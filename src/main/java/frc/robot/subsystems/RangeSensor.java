package frc.robot.subsystems;

import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.Port;

public class RangeSensor {
    private Rev2mDistanceSensor sensor;
    
    public RangeSensor() {
        sensor = new Rev2mDistanceSensor(Port.kOnboard);
    }

    public double getDistance() {
        return sensor.getRange();
    }
}
