package frc.robot.subsystems;

import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.Rev2mDistanceSensor.Port;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ShooterConfig;

public class Shooter extends SubsystemBase {
  private final CANSparkMaxNEOFlywheel upperFlywheel, lowerFlywheel;
  private ShooterSpeeds targetSpeeds = ShooterConfig.STOPPED;
  private final Rev2mDistanceSensor sensor;
  private GenericEntry shuffleboardRange;

  public Shooter() {
    sensor = new Rev2mDistanceSensor(Port.kOnboard);
    sensor.setAutomaticMode(true);  // required for onboard as it creates a background thread to read
    
    upperFlywheel = new CANSparkMaxNEOFlywheel("SWU", ShooterConfig.UPPER_FLYWHEEL, true);
    lowerFlywheel = new CANSparkMaxNEOFlywheel("SWL", ShooterConfig.LOWER_FLYWHEEL, false);
    shuffleboardRange = Constants.SYSTEMS_TAB.add("Range", 0).withWidget(BuiltInWidgets.kTextView).getEntry();
  }

  @Override
  public void periodic() {
    upperFlywheel.updateShuffleboard();
    lowerFlywheel.updateShuffleboard();
    if (sensor.isRangeValid()) {
      shuffleboardRange.setDouble(sensor.getRange());
    }
    upperFlywheel.updateMotorOutput();
    lowerFlywheel.updateMotorOutput();
  }

  public Command stopShooter() {
    return Commands.runOnce(() -> setMotors(ShooterConfig.STOPPED), this);
  }

  public Command startShooter(ShooterSpeeds speeds) {
    return Commands.runOnce(() -> setMotors(speeds), this);
  }

  public ShooterSpeeds getTargetSpeeds() {
    return targetSpeeds;
  }

  public boolean isObstacleDetected(double maxRange) {
    return sensor.isRangeValid() && sensor.getRange() <= maxRange;
  }

  public void setMotors(ShooterSpeeds speeds) {
    targetSpeeds = speeds;
    setMotorOutputs(targetSpeeds.upperSpeed, targetSpeeds.lowerSpeed);
  }

  private void setMotorOutputs(double upperSpeed, double lowerSpeed) {
    upperFlywheel.setWheelSpeed(upperSpeed);
    lowerFlywheel.setWheelSpeed(lowerSpeed);
  }

  public boolean shooterAtSpeed(ShooterSpeeds speeds) {
    return upperFlywheel.isAtSetpoint() && lowerFlywheel.isAtSetpoint();
  }

  public static class ShooterSpeeds {
    public final double upperSpeed;
    public final double lowerSpeed;

    public ShooterSpeeds(double upperSpeed, double lowerSpeed) {
      this.upperSpeed = upperSpeed;
      this.lowerSpeed = lowerSpeed;
    }

    public boolean equals(Object object) {
      if (!(object instanceof ShooterSpeeds)) {
        return false;
      }
      ShooterSpeeds otherSpeeds = (ShooterSpeeds) object;
      return otherSpeeds.lowerSpeed == lowerSpeed && otherSpeeds.upperSpeed == upperSpeed;
    }
  }

  public boolean isStopped() {
    return targetSpeeds.equals(ShooterConfig.STOPPED);
  }
}
