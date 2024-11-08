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
    public static final double TURN_SPEED = 0.75;

    public static final double TRACK_WIDTH = 0.336;
    public static final double GEAR_RATIO = 0.175;
    public static final double WHEEL_DIAMETER_METERS = Units.inchesToMeters(4);

    public static final double SLOW_MULTIPLIER = 0.5;

    // public static final DifferentialConfig DRIVETRAIN_CONFIG = new
    // DifferentialConfig(
    // CANConfig.LEFT_DRIVE_PORT, CANConfig.RIGHT_DRIVE_PORT, -1,
    // -1, LEFT_INVERTED, RIGHT_INVERTED, TRACK_WIDTH, GEAR_RATIO,
    // WHEEL_DIAMETER_METERS, SLOW_MULTIPLIER,
    // 0, 0, 0, 0.089779, 2.4 - 706, 1, 1);
  }

  public static final class IndexerConfig {
    public static final double INDEXER_SPEED = 0.25;
    public static final double FEED_SHOOTER_SPEED = 0.3;
  }

  public static final class FlywheelConfig {
    public static final double RPM_TOLERANCE = 100.0;
    private int canId;
    private double ka = 0.0, ks = 0.0, kv = 0.0;
    private double kp = 0.0, ki = 0.0, kd = 0.0;
    private double gearReduction;

    public FlywheelConfig(int canId, double ka,double ks, double kv, double kp, double ki, double kd, double gearReduction) {
      this.ka = ka;
      this.ks = ks;
      this.kv = kv;
      this.kp = kp;
      this.ki = ki;
      this.kd = kd;
      this.canId = canId;
      this.gearReduction = gearReduction;
    }

    public double getKp() {
      return kp;
    }

    public void setKp(double kp) {
      this.kp = kp;
    }

    public double getKi() {
      return ki;
    }

    public void setKi(double ki) {
      this.ki = ki;
    }

    public double getKd() {
      return kd;
    }

    public void setKd(double kd) {
      this.kd = kd;
    }

    public double getGearReduction() {
      return gearReduction;
    }

    public void setGearReduction(double gearReduction) {
      this.gearReduction = gearReduction;
    }

    public double getKa() {
      return ka;
    }

    public void setKa(double ka) {
      this.ka = ka;
    }

    public double getKs() {
      return ks;
    }

    public void setKs(double ks) {
      this.ks = ks;
    }

    public double getKv() {
      return kv;
    }

    public void setKv(double kv) {
      this.kv = kv;
    }

    public int getCanId() {
      return canId;
    }

    public void setCanId(int canId) {
      this.canId = canId;
    }
  }

  public static final class ShooterConfig {
    public static final ShooterSpeeds STOPPED = new ShooterSpeeds(0, 0);
    public static final ShooterSpeeds HIGH_SPEEDS = new ShooterSpeeds(1000.0, 1000.0);
    public static final ShooterSpeeds LOW_SPEEDS = new ShooterSpeeds(500.0, 500.0);
    public static final ShooterSpeeds MAX_SPEED = new ShooterSpeeds(2000.0, 2000.0);
    public static final double SHOOTING_LIMIT = 75;
    public static final FlywheelConfig UPPER_FLYWHEEL = new FlywheelConfig(CANConfig.UPPER_SHOOT_PORT,0.0, 0.0, 0.000075,0.001,0.0,0.0,16.0/32.0); //yes, I know it's 0.5 but I like putting in the teeth counts to make it easier to modify and the JIT will optimize this out anyway
    public static final FlywheelConfig LOWER_FLYWHEEL = new FlywheelConfig(CANConfig.LOWER_SHOOT_PORT,0.0, 0.0, 0.000075,0.001,0.0,0.0,16.0/32.0);
  }
}
