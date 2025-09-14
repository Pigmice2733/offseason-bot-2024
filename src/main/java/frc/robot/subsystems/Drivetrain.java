package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Controls;
import frc.robot.Constants.CANConfig;
import frc.robot.Constants.DrivetrainConfig;

public class Drivetrain extends SubsystemBase {
  private DifferentialDrive drivetrain;
  private final SparkMax leftMotor = new SparkMax(CANConfig.LEFT_DRIVE_PORT, MotorType.kBrushless);
  private final SparkMax rightMotor = new SparkMax(CANConfig.RIGHT_DRIVE_PORT, MotorType.kBrushless);
  private Controls controls;

  private boolean slowMode = false;

  GenericEntry driveSpeedReduction, turnSpeedReduction;

  public Drivetrain(Controls controls) {

    SparkMaxConfig leftConfig = new SparkMaxConfig();
    leftConfig.inverted(false);
    leftConfig.idleMode(IdleMode.kBrake);
    leftMotor.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    SparkMaxConfig rightConfig = new SparkMaxConfig();
    rightConfig.inverted(true);
    rightConfig.idleMode(IdleMode.kBrake);
    rightMotor.configure(rightConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    drivetrain = new DifferentialDrive(leftMotor, rightMotor);

    this.controls = controls;

    driveSpeedReduction = Constants.DRIVETRAIN_TAB.add("Drive Speed", DrivetrainConfig.DRIVE_SPEED).getEntry();
    turnSpeedReduction = Constants.DRIVETRAIN_TAB.add("Turn Speed", DrivetrainConfig.TURN_SPEED).getEntry();
  }

  @Override
  public void periodic() {
    double driveSpeed = controls.getDriveSpeed() * driveSpeedReduction.getDouble(1.0);
    double turnSpeed = controls.getTurnSpeed() * turnSpeedReduction.getDouble(1.0);
    driveSpeed = driveSpeed * (slowMode ? DrivetrainConfig.SLOW_MULTIPLIER : 1.0);
    turnSpeed = turnSpeed * (slowMode ? DrivetrainConfig.SLOW_MULTIPLIER : 1.0);
    drivetrain.arcadeDrive(driveSpeed, turnSpeed);
  }

  public boolean getSlowMode() {
    return slowMode;
  }

  public boolean toggleSlowMode() {
    slowMode = !slowMode;
    return slowMode;
  }
}
