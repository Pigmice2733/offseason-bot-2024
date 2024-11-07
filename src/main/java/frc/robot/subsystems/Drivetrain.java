package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Controls;
import frc.robot.Constants.CANConfig;
import frc.robot.Constants.DrivetrainConfig;

public class Drivetrain extends SubsystemBase {
    private DifferentialDrive drivetrain;
    private final CANSparkMax leftMotor = new CANSparkMax(CANConfig.LEFT_DRIVE_PORT, MotorType.kBrushless);
    private final CANSparkMax rightMotor = new CANSparkMax(CANConfig.RIGHT_DRIVE_PORT, MotorType.kBrushless);
    private Controls controls;

    private boolean slowMode = false;

    GenericEntry driveSpeedReduction, turnSpeedReduction;

    public Drivetrain(Controls controls) {
        leftMotor.restoreFactoryDefaults();
        rightMotor.restoreFactoryDefaults();
        leftMotor.setInverted(false);
        rightMotor.setInverted(true);
        leftMotor.setIdleMode(IdleMode.kBrake);
        rightMotor.setIdleMode(IdleMode.kBrake);


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
