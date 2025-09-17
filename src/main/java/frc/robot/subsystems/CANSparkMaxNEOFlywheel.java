package frc.robot.subsystems;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.ClosedLoopSlot;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import frc.robot.Constants;
import frc.robot.Constants.FlywheelConfig;
import frc.robot.Constants.ShooterConfig;

public class CANSparkMaxNEOFlywheel {
  private final FlywheelConfig config;
  private final SparkMax motorController;
  private final SparkClosedLoopController pidController;
  private final RelativeEncoder encoder;
  private final GenericEntry shuffleboardRPMDisplay;
  private final GenericEntry shuffleboardSetpoint;
  private final GenericEntry shuffleboardWheelSpeed;
  private final GenericEntry shuffleboardP;
  private final GenericEntry shuffleboardI;
  private final GenericEntry shuffleboardD;
  private final GenericEntry shuffleboardV;
  private final GenericEntry shuffleboardMaxAccel;
  private double rpmSetPoint = 0.0;
  private double wheelSpeed = 0.0;
  private double p, d, i, v;
  private double maxAccel = 1500.0;

  public CANSparkMaxNEOFlywheel(String shuffleboardName, FlywheelConfig flywheelConfig, boolean inverted) {
    config = flywheelConfig;
    motorController = new SparkMax(config.getCanId(), MotorType.kBrushless);
    SparkMaxConfig motorConfig = new SparkMaxConfig();
    motorConfig.inverted(inverted);
    motorConfig.idleMode(IdleMode.kCoast);
    motorConfig.closedLoop.smartMotion.maxAcceleration(maxAccel, ClosedLoopSlot.kSlot0);
    motorConfig.closedLoop.smartMotion.maxVelocity(10000, ClosedLoopSlot.kSlot0);
    motorConfig.closedLoop.smartMotion.minOutputVelocity(400, ClosedLoopSlot.kSlot0);
    motorConfig.closedLoop.smartMotion.allowedClosedLoopError(10, ClosedLoopSlot.kSlot0);
    motorConfig.closedLoop.p(p).i(i).d(d);
    motorConfig.closedLoop.iZone(0);
    motorConfig.closedLoop.velocityFF(v);
    motorConfig.closedLoop.outputRange(0, 1);

    motorController.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    pidController = motorController.getClosedLoopController();
    encoder = motorController.getEncoder();
    p = flywheelConfig.getKp();
    i = flywheelConfig.getKi();
    d = flywheelConfig.getKd();
    v = flywheelConfig.getKv();

    maxAccel = ShooterConfig.MAX_ACCEL;

    shuffleboardRPMDisplay = Constants.SYSTEMS_TAB.add(shuffleboardName + " RPMs", 0)
        .withWidget(BuiltInWidgets.kTextView).getEntry();
    shuffleboardSetpoint = Constants.SYSTEMS_TAB.add(shuffleboardName + " Setpoint", 0).getEntry();
    shuffleboardWheelSpeed = Constants.SYSTEMS_TAB.add(shuffleboardName + " WheelSpeed", 0).getEntry();
    shuffleboardMaxAccel = Constants.SYSTEMS_TAB.add(shuffleboardName + " MaxAccel", maxAccel).getEntry();
    shuffleboardP = Constants.SYSTEMS_TAB.add(shuffleboardName + " p", p).getEntry();
    shuffleboardI = Constants.SYSTEMS_TAB.add(shuffleboardName + " i", i).getEntry();
    shuffleboardD = Constants.SYSTEMS_TAB.add(shuffleboardName + " d", d).getEntry();
    shuffleboardV = Constants.SYSTEMS_TAB.add(shuffleboardName + " v", v).getEntry();
  }

  public void setWheelSpeed(double wheelspeed) {
    this.wheelSpeed = wheelspeed;
    shuffleboardWheelSpeed.setDouble(wheelspeed);
    setRpmSetPoint(wheelspeed * (1.0 / config.getGearReduction()));
  }

  private void setRpmSetPoint(double rpmSetPoint) {
    double p = rpmSetPoint < getRPMs() ? config.getKp() / 2 : config.getKp();
    SparkMaxConfig motorConfig = new SparkMaxConfig();
    motorConfig.closedLoop.p(p);
    motorController.configure(motorConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

    this.rpmSetPoint = rpmSetPoint;
    if (rpmSetPoint <= 100) {
      motorController.disable();
    } else {
      pidController.setReference(rpmSetPoint, SparkMax.ControlType.kSmartVelocity);
    }
    shuffleboardSetpoint.setDouble(this.rpmSetPoint);
  }

  public double getRPMs() {
    return encoder.getVelocity();
  }

  public void updateShuffleboard() {
    shuffleboardRPMDisplay.setDouble(getRPMs());
  }

  public void updateMotorOutput() {
    // look for shuffleboard changes
    double newP = shuffleboardP.getDouble(p);
    double newI = shuffleboardI.getDouble(i);
    double newD = shuffleboardD.getDouble(d);
    double newV = shuffleboardV.getDouble(v);
    double newWheelSpeed = shuffleboardWheelSpeed.getDouble(wheelSpeed);
    double newMaxAccel = shuffleboardMaxAccel.getDouble(maxAccel);
    boolean configUpdateRequired = false;
    SparkMaxConfig motorConfig = new SparkMaxConfig();
    if (p != newP) {
      p = newP;
      configUpdateRequired = true;
      motorConfig.closedLoop.p(p);
    }
    if (i != newI) {
      i = newI;
      configUpdateRequired = true;
      motorConfig.closedLoop.i(i);
    }
    if (d != newD) {
      d = newD;
      configUpdateRequired = true;
      motorConfig.closedLoop.d(d);
    }
    if (v != newV) {
      v = newV;
      configUpdateRequired = true;
      motorConfig.closedLoop.velocityFF(v);
    }
    if (maxAccel != newMaxAccel) {
      maxAccel = newMaxAccel;
      configUpdateRequired = true;
      motorConfig.closedLoop.smartMotion.maxAcceleration(maxAccel, ClosedLoopSlot.kSlot0);
    }
    if (newWheelSpeed != wheelSpeed) {
      setWheelSpeed(newWheelSpeed);
    }
    if (configUpdateRequired) {
      motorController.configure(motorConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    }
  }

  public boolean isAtSetpoint() {
    return Math.abs(getRPMs() - rpmSetPoint) <= FlywheelConfig.RPM_TOLERANCE;
  }
}
