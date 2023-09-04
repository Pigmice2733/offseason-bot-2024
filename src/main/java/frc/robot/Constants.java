// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pigmice.frc.lib.drivetrain.differential.DifferentialConfig;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.subsystems.Shooter.ShooterSpeeds;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide
 * numerical or boolean
 * constants. This class should not be used for any other purpose. All constants
 * should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>
 * It is advised to statically import this class (or one of its inner classes)
 * wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final double AXIS_THRESHOLD = 0.1;

    public static final ShuffleboardTab DRIVETRAIN_TAB = Shuffleboard.getTab("Drivetrain");
    public static final ShuffleboardTab INTAKE_TAB = Shuffleboard.getTab("Intake");
    public static final ShuffleboardTab SHOOTER_TAB = Shuffleboard.getTab("Shooter");

    public static final class CANConfig {
        public static final int LEFT_DRIVE_PORT = 11;
        public static final int RIGHT_DRIVE_PORT = 12;

        public static final int LEFT_INTAKE_EXTEND_PORT = 0;
        public static final int RIGHT_INTAKE_EXTEND_PORT = 1;
        public static final int INTAKE_WHEELS_PORT = 2;

        public static final int LEFT_SHOOT_PORT = 3;
        public static final int RIGHT_SHOOT_PORT = 4;
    }

    public static final class DrivetrainConfig {
        public static final boolean LEFT_INVERTED = false;
        public static final boolean RIGHT_INVERTED = false;

        public static final double DRIVE_SPEED = 0.5;
        public static final double TURN_SPEED = 0.3;

        public static final double TRACK_WIDTH = 0.336;
        public static final double GEAR_RATIO = 0.175;
        public static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(4);

        public static final double SLOW_MULTIPLIER = 0.5;

        public static final DifferentialConfig DRIVETRAIN_CONFIG = new DifferentialConfig(
                CANConfig.LEFT_DRIVE_PORT, CANConfig.RIGHT_DRIVE_PORT, -1,
                -1, LEFT_INVERTED, RIGHT_INVERTED, TRACK_WIDTH, GEAR_RATIO, WHEEL_DIAMETER_METERS, SLOW_MULTIPLIER,
                0, 0, 0, 0.089779, 2.4706, 1, 1);
    }

    public static final class IntakeConfig {
        public static final double EXTENDING_SPEED = .2;
        public static final double SPINNING_SPEED = 0.5;

        public static final double MANUAL_EXTENSION_SPEED = 0.2;

        public static final double MAX_EXTEND_DISTANCE = 4.167;
        public static final double MID_EXTEND_DISTANCE = 2.0835;

        public static final double POSITION_TOLERANCE = 0.1;

        public static final double EXTENSION_P = 0.001;
        public static final double EXTENSION_I = 0;
        public static final double EXTENSION_D = 0;

        public static final double MAX_EXTENSION_VELOSITY = 1; // rad/sec
        public static final double MAX_EXTENSION_ACCELERATION = 1; // rad/sec/sec

        public static final double FEED_SHOOTER_SPEEDS = 0.3;
    }

    public static final class ShooterConfig {
        public static final ShooterSpeeds STOPPED_SPEEDS = new ShooterSpeeds(0, 0);
        public static final ShooterSpeeds MID_SPEEDS = new ShooterSpeeds(0.5, 0.5);
        public static final ShooterSpeeds HIGH_SPEEDS = new ShooterSpeeds(0.5, 0.5);
    }
}
