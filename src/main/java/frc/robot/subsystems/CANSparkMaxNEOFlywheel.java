package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.robot.Constants;
import frc.robot.Constants.FlywheelConfig;

public class CANSparkMaxNEOFlywheel {
  private final FlywheelConfig config;
  private final CANSparkMax motorController;
  private final SparkPIDController pidController;
  private final RelativeEncoder encoder;
  private final GenericEntry shuffleboardRPMDisplay;
  private final GenericEntry shuffleboardSetpoint;
  private final GenericEntry shuffleboardP;
  private final GenericEntry shuffleboardI;
  private final GenericEntry shuffleboardD;
  private final GenericEntry shuffleboardV;
  private double rpmSetPoint = 0.0;
  private double p,d,i,v;

  public CANSparkMaxNEOFlywheel(String shuffleboardName, FlywheelConfig flywheelConfig) {
    config = flywheelConfig;
    motorController = new CANSparkMax(config.getCanId(), MotorType.kBrushless);
    motorController.restoreFactoryDefaults();
    pidController = motorController.getPIDController();
    encoder = motorController.getEncoder();
    p = flywheelConfig.getKp();
    i = flywheelConfig.getKi();
    d = flywheelConfig.getKd();
    v = flywheelConfig.getKv();

    pidController.setP(p);
    pidController.setI(i);
    pidController.setD(d);
    pidController.setIZone(0);
    pidController.setFF(v);
    pidController.setOutputRange(0, 1);

    shuffleboardRPMDisplay = Constants.SYSTEMS_TAB.add(shuffleboardName + " RPMs", 0)
        .withWidget(BuiltInWidgets.kTextView).getEntry();
    shuffleboardSetpoint = Constants.SYSTEMS_TAB.add(shuffleboardName + " Setpoint", 0).getEntry();
    shuffleboardP = Constants.SYSTEMS_TAB.add(shuffleboardName + " p", p).getEntry();
    shuffleboardI = Constants.SYSTEMS_TAB.add(shuffleboardName + " i", i).getEntry();
    shuffleboardD = Constants.SYSTEMS_TAB.add(shuffleboardName + " d", d).getEntry();
    shuffleboardV = Constants.SYSTEMS_TAB.add(shuffleboardName + " v", v).getEntry();
  }

  public void setRpmSetPoint(double rpmSetPoint) {
    this.rpmSetPoint = rpmSetPoint * (1.0/config.getGearReduction());
    pidController.setReference(rpmSetPoint, CANSparkMax.ControlType.kVelocity);
    shuffleboardSetpoint.setDouble(this.rpmSetPoint);
  }

  public double getRPMs() {
    return encoder.getVelocity();
  }

  public void updateShuffleboard() {
    shuffleboardRPMDisplay.setDouble(getRPMs());
  }

  public void updateMotorOutput() {
    //look for shuffleboard changes
    double newP = shuffleboardP.getDouble(p);
    double newI = shuffleboardI.getDouble(i);
    double newD = shuffleboardD.getDouble(d);
    double newV = shuffleboardV.getDouble(v);
    double newRpmSetPoint = shuffleboardSetpoint.getDouble(rpmSetPoint);
    if (p != newP) {
      p = newP;
      pidController.setP(newP);
    }
    if (i != newI) {
      i = newI;
      pidController.setI(newI);
    }
    if (d != newD) {
      d = newD;
      pidController.setD(newD);
    }
    if (v != newV) {
      v = newV;
      pidController.setFF(newV);
    }
    if (rpmSetPoint != newRpmSetPoint) {
      rpmSetPoint = newRpmSetPoint;
      pidController.setReference(rpmSetPoint,ControlType.kVelocity);
    }
  }

  public boolean isAtSetpoint() {
    return Math.abs(getRPMs() - rpmSetPoint) <= FlywheelConfig.RPM_TOLERANCE;
  }
}
