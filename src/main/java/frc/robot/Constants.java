// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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

    public static final int joystickPort = 0;

    public final class DrivetrainConfig {

        public static final int leftDrivePort = 0;
        public static final int rightDrivePort = 1;

        public static final int leftFollowPort = 2;
        public static final int rightFollowPort = 3;

        public static final double driveSpeed = 0.5;
        public static final double turnSpeed = 0.3;

    }

    public final class IntakeConfig {

        public static final int leftIntakePort = 0;
        public static final int rightIntakePort = 1;
        public static final int intakeWheelsPort = 2;

        public static final double extendingSpeed = .2;
        public static final double spinningSpeed = 1;

        public static final double maxExtendDistance = 4.167;
        public static final double midExtendDistance = 2.0835;
    }

    public final class ShooterConfig {

        public static final int leftShootPort = 0;
        public static final int rightShootPort = 1;

        public static final double maxSpeed = .6;
    }

    public final static double axisThreshold = 0.1;
}
