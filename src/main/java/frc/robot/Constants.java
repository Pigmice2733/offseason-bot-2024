// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.subsystems.Intake.IntakeState;

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
    public static final int JOYSTICK_PORT = 0;

    public final class DrivetrainConfig {

        public static final int LEFT_DRIVE_PORT = 0;
        public static final int RIGHT_DRIVE_PORT = 1;

        public static final int LEFT_FOLLOW_PORT = 2;
        public static final int rightFollowPort = 3;

        public static final double DRIVE_SPEED = 0.5;
        public static final double TURN_SPEED = 0.3;

    }

    public final class IntakeConfig {

        public static final int LEFT_INTAKE_PORT = 0;
        public static final int RIGHT_INTAKE_PORT = 1;
        public static final int INTAKE_WHEELS_PORT = 2;

        public static final double EXTENDING_SPEED = .2;
        public static final double SPINNING_SPEED = 1;

        public static final double INTAKE_SPEED = 1;

        public static final double MAX_EXTEND_DISTANCE = 4.167;
        public static final double MID_EXTEND_DISTANCE = 2.0835;

        public static final double POSITION_TOLERANCE = 0.1;
    }

    public final class ShooterConfig {

        public static final int LEFT_SHOOT_PORT = 0;
        public static final int RIGHT_SHOOT_PORT = 1;

        public static final double MAX_SPEED = .6;
    }

    public final static double AXIS_THRESHOLD = 0.1;
}
