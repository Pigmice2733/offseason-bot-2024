package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.Rev2mDistanceSensor;
import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.Rev2mDistanceSensor.Port;

import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CANConfig;
import frc.robot.Constants.ShooterConfig;

public class Shooter extends SubsystemBase {
  private final CANSparkMax upperWheel, lowerWheel;
  private final GenericEntry upperWheelRPMs, lowerWheelRPMs;
  private ShooterSpeeds targetSpeeds = ShooterConfig.STOPPED;
  private final Rev2mDistanceSensor sensor;
  private SimpleMotorFeedforward feedforward;

  public Shooter() {
    sensor = new Rev2mDistanceSensor(Port.kOnboard);
    upperWheel = new CANSparkMax(CANConfig.UPPER_SHOOT_PORT, MotorType.kBrushless);
    lowerWheel = new CANSparkMax(CANConfig.LOWER_SHOOT_PORT, MotorType.kBrushless);
    feedforward = new SimpleMotorFeedforward(0.0, 0.1);
    upperWheel.restoreFactoryDefaults();
    lowerWheel.restoreFactoryDefaults();

    upperWheelRPMs = Constants.SYSTEMS_TAB.add("SWU RPMs", 0)
        .withWidget(BuiltInWidgets.kTextView).getEntry();
    lowerWheelRPMs = Constants.SYSTEMS_TAB.add("SWL RPMs", 0)
        .withWidget(BuiltInWidgets.kTextView).getEntry();
  }

  @Override
  public void periodic() {
    upperWheelRPMs.setDouble(upperWheel.getEncoder().getVelocity());
    lowerWheelRPMs.setDouble(lowerWheel.getEncoder().getVelocity());
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
    return sensor.getRange() <= maxRange;
  }

  public void setMotors(ShooterSpeeds speeds) {
    targetSpeeds = speeds;
    setMotorOutputs(targetSpeeds.upperSpeed, targetSpeeds.lowerSpeed);
  }

  private void setMotorOutputs(double upperSpeed, double lowerSpeed) {
    if (Math.abs(upperSpeed) > 1 || Math.abs(lowerSpeed) > 1)
      return;
    upperWheel.set(upperSpeed);
    lowerWheel.set(lowerSpeed);
  }

  public boolean shooterAtSpeed(ShooterSpeeds speeds) {
    return upperWheel.get() >= speeds.upperSpeed && lowerWheel.get() >= speeds.lowerSpeed;
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
