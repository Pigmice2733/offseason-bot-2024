// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.MotorTester;
import frc.robot.Constants.DrivetrainConfig;

public class Drivetrain extends SubsystemBase {

    // private final NetworkTableEntry xPosEntry, yPosEntry,
    // leftOutputEntry, rightOutputEntry;

    private final CANSparkMax leftDrive;
    private final CANSparkMax rightDrive;

    // private final AHRS gyro;

    // private final DifferentialDriveOdometry odometry;

    private double leftSpeed, rightSpeed;

    /** Creates a new Drivetrain. */
    public Drivetrain() {
        leftDrive = new CANSparkMax(DrivetrainConfig.LEFT_DRIVE_PORT, MotorType.kBrushless);
        rightDrive = new CANSparkMax(DrivetrainConfig.RIGHT_DRIVE_PORT, MotorType.kBrushless);
        leftDrive.restoreFactoryDefaults();
        rightDrive.restoreFactoryDefaults();
        rightDrive.setInverted(true);

        MotorTester.registerCANMotor("Left Drive", leftDrive);
        MotorTester.registerCANMotor("Right Drive", rightDrive);

        // gyro = new AHRS();

        // odometry = new DifferentialDriveOdometry(new Rotation2d(), new Pose2d());

        rightDrive.restoreFactoryDefaults();
        leftDrive.restoreFactoryDefaults();
    }

    @Override
    public void periodic() {
        applyTargetOutputs();
    }

    private void applyTargetOutputs() {
        leftDrive.set(leftSpeed);
        rightDrive.set(rightSpeed);
    }

    public void arcadeDrive(double forwardSpeed, double turnSpeed) {
        double left = forwardSpeed + turnSpeed;
        double right = forwardSpeed - turnSpeed;

        setTargetOutputs(left, right);
    }

    public void setTargetOutputs(double leftPercent, double rightPercent) {
        leftSpeed = leftPercent;
        rightSpeed = rightPercent;
    }
}
