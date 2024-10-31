// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

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
  public static final ShuffleboardTab SYSTEMS_TAB = Shuffleboard.getTab("Subsystems");

  public static final class CANConfig {
    public static final int LEFT_DRIVE_PORT = 11;
    public static final int RIGHT_DRIVE_PORT = 12;

    public static final int INDEXER_PORT = 2;

    public static final int UPPER_SHOOT_PORT = 20;
    public static final int LOWER_SHOOT_PORT = 21;
  }

  public static final class DrivetrainConfig {
    public static final double DRIVE_SPEED = 0.5;
    public static final double TURN_SPEED = 1.0;

    public static final double TRACK_WIDTH = 0.336;
    public static final double GEAR_RATIO = 0.175;
    public static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(4);

    public static final double SLOW_MULTIPLIER = 0.5;

    // public static final DifferentialConfig DRIVETRAIN_CONFIG = new DifferentialConfig(
    //     CANConfig.LEFT_DRIVE_PORT, CANConfig.RIGHT_DRIVE_PORT, -1,
    //     -1, LEFT_INVERTED, RIGHT_INVERTED, TRACK_WIDTH, GEAR_RATIO, WHEEL_DIAMETER_METERS, SLOW_MULTIPLIER,
    //     0, 0, 0, 0.089779, 2.4 - 706, 1, 1);
  }

  public static final class IndexerConfig {
    public static final double INDEXER_SPEED = 0.25;
    public static final double FEED_SHOOTER_SPEED = 0.3;
  }

  public static final class ShooterConfig {
    public static final ShooterSpeeds STOPPED = new ShooterSpeeds(0, 0);
    public static final ShooterSpeeds HIGH_SPEEDS = new ShooterSpeeds(0.99, 0.99);
    public static final ShooterSpeeds LOW_SPEEDS = new ShooterSpeeds(0.4, 0.4);
    public static final double SHOOTING_LIMIT = 75;

  }
}
