package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
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
    private double driveSpeed;

    GenericEntry setDriveSpeed, setTurnSpeed;

    public Drivetrain(Controls controls) {
        leftMotor.restoreFactoryDefaults();
        rightMotor.restoreFactoryDefaults();
        leftMotor.setInverted(false);
        rightMotor.setInverted(true);

        drivetrain = new DifferentialDrive(leftMotor, rightMotor);

        this.controls = controls;

        setDriveSpeed = Constants.SYSTEMS_TAB.add("Drive Speed", DrivetrainConfig.DRIVE_SPEED).getEntry();
        setTurnSpeed = Constants.SYSTEMS_TAB.add("Turn Speed", DrivetrainConfig.TURN_SPEED).getEntry();
    }

    @Override
    public void periodic() {
        driveSpeed = setDriveSpeed.getDouble(controls.getDriveSpeed()) * (slowMode ? DrivetrainConfig.SLOW_MULTIPLIER : 1);
        drivetrain.arcadeDrive(driveSpeed, setTurnSpeed.getDouble(controls.getTurnSpeed()));
    }

    public boolean getSlowMode() {
        return slowMode;
    }

    public boolean toggleSlowMode() {
        slowMode = !slowMode;
        return slowMode;
    }
}
