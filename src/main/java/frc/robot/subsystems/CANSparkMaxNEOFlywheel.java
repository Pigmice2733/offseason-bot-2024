package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.robot.Constants;
import frc.robot.Constants.FlywheelConfig;

public class CANSparkMaxNEOFlywheel {
  private final FlywheelConfig config;
  private final CANSparkMax motorController;
  private final PIDController pidController;
  private final SimpleMotorFeedforward feedforward;
  private final GenericEntry shuffleboardRPMDisplay;
  private final GenericEntry shuffleboardSetpointDisplay;
  private double rpmSetPoint = 0.0;
  private String shuffleboardName;

  public CANSparkMaxNEOFlywheel(String shuffleboardName, FlywheelConfig flywheelConfig) {
    config = flywheelConfig;
    this.shuffleboardName = shuffleboardName;
    motorController = new CANSparkMax(config.getCanId(), MotorType.kBrushless);
    motorController.restoreFactoryDefaults();

    feedforward = new SimpleMotorFeedforward(config.getKs(), config.getKv(), config.getKa());
    pidController = new PIDController(config.getKp(), config.getKi(), config.getKd());

    shuffleboardRPMDisplay = Constants.SYSTEMS_TAB.add(shuffleboardName + " RPMs", 0)
        .withWidget(BuiltInWidgets.kTextView).getEntry();
    shuffleboardSetpointDisplay = Constants.SYSTEMS_TAB.add(shuffleboardName + " Setpoint", 0)
        .withWidget(BuiltInWidgets.kTextView).getEntry();
  }

  public double getRpmSetPoint() {
    return rpmSetPoint;
  }

  public void setRpmSetPoint(double rpmSetPoint) {
    this.rpmSetPoint = rpmSetPoint;
    pidController.reset();
    pidController.setSetpoint(rpmSetPoint);
    shuffleboardSetpointDisplay.setDouble(this.rpmSetPoint);
  }

  public double getRPMs() {
    return motorController.getEncoder().getVelocity() * config.getGearReduction();
  }

  public void updateShuffleboard() {
    shuffleboardRPMDisplay.setDouble(getRPMs());
  }

  public void updateMotorOutput() {
    if (rpmSetPoint > 0.0) {
      double rpms = getRPMs();
      System.out.println(shuffleboardName+" rpms: "+rpms+" setpoint: "+rpmSetPoint);
      double output = feedforward.calculate(rpmSetPoint);
      System.out.println("feedforward output: " + output);
      double pidOutput = pidController.calculate(rpms);
      System.out.println(shuffleboardName+" feedforward: "+output+" pid: "+pidOutput);
      output += pidOutput;
      output = MathUtil.clamp(output,0.0,0.7);
      System.out.println(shuffleboardName+" clamped: "+output);
      motorController.set(output);
    } else {
      motorController.set(0.0);
    }
  }

  public boolean isAtSpeed(double targetSpeed) {
    return Math.abs(getRPMs() - targetSpeed) <= FlywheelConfig.RPM_TOLERANCE;
  }
}
