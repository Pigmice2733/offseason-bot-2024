package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkBase.IdleMode;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.WaitCommand;
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

    GenericEntry setDriveSpeed, setTurnSpeed;

    public Drivetrain(Controls controls) {
        leftMotor.restoreFactoryDefaults();
        rightMotor.restoreFactoryDefaults();
        leftMotor.setInverted(false);
        rightMotor.setInverted(true);
        leftMotor.setIdleMode(IdleMode.kBrake);
        rightMotor.setIdleMode(IdleMode.kBrake);

        drivetrain = new DifferentialDrive(leftMotor, rightMotor);

        this.controls = controls;

        setDriveSpeed = Constants.SYSTEMS_TAB.add("Drive Speed", DrivetrainConfig.DRIVE_SPEED).getEntry();
        setTurnSpeed = Constants.SYSTEMS_TAB.add("Turn Speed", DrivetrainConfig.TURN_SPEED).getEntry();
    }

    public void driveJoysticks() {
        double driveSpeed = controls.getDriveSpeed() * DrivetrainConfig.DRIVE_SPEED; // * setDriveSpeed.getDouble(1.0);
        double turnSpeed = controls.getTurnSpeed() * DrivetrainConfig.TURN_SPEED; // * setTurnSpeed.getDouble(1.0);
        driveSpeed = driveSpeed * (slowMode ? DrivetrainConfig.SLOW_MULTIPLIER : 1.0);
        turnSpeed = turnSpeed * (slowMode ? DrivetrainConfig.SLOW_MULTIPLIER : 1.0);
        drivetrain.arcadeDrive(driveSpeed, turnSpeed);
    }

    public Command turnAngle(double degrees) {
        return new SequentialCommandGroup(
                new InstantCommand(() -> drivetrain.arcadeDrive(0, DrivetrainConfig.TURN_SPEED)),
                new WaitCommand(DrivetrainConfig.DEG_PER_SEC * degrees),
                new InstantCommand(() -> drivetrain.arcadeDrive(0, 0)));
    }

    public Command driveDistance(double meters) {
        return new SequentialCommandGroup(
                new InstantCommand(() -> drivetrain.arcadeDrive(DrivetrainConfig.DRIVE_SPEED, 0)),
                new WaitCommand(DrivetrainConfig.M_PER_SEC * meters),
                new InstantCommand(() -> drivetrain.arcadeDrive(0, 0)));
    }

    public Command driveTime(double seconds) {
        return new SequentialCommandGroup(
                new InstantCommand(() -> drivetrain.arcadeDrive(DrivetrainConfig.DRIVE_SPEED, 0)),
                new WaitCommand(seconds),
                new InstantCommand(() -> drivetrain.arcadeDrive(0, 0)));
    }

    public boolean getSlowMode() {
        return slowMode;
    }

    public boolean toggleSlowMode() {
        slowMode = !slowMode;
        return slowMode;
    }
}
