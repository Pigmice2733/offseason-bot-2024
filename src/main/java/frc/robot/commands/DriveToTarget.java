package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.vision.Vision;

public class DriveToTarget extends Command {
  private Supplier<Pose2d> endPose;
  private Drivetrain drivetrain;
  private Vision vision;
  private double distance, angle;

  public DriveToTarget(Drivetrain drivetrain, Vision vision, Supplier<Pose2d> pose) {
    this.drivetrain = drivetrain;
    this.vision = vision;
    this.endPose = pose;
    System.out.println("command created");
  }

  @Override
  public void initialize() {
    System.out.println("command initialized");
    System.out.println(endPose.get().toString());
    System.out.println(Integer.toString(vision.getBestTargetID()));
  }

  @Override
  public void execute() {
    System.out.println("command running");
    distance = endPose.get().getTranslation().getNorm();
    angle = endPose.get().getTranslation().getAngle().getDegrees();
    drivetrain.driveSpeeds(DrivetrainConfig.DRIVE_MULTIPLIER * distance, DrivetrainConfig.TURN_MULTIPLIER * angle);
  }

  @Override
  public void end(boolean interrupted) {
    System.out.println("command finished");
    drivetrain.driveSpeeds(0, 0);
  }

  @Override
  public boolean isFinished() {
    return distance < 0.1 && angle < 5;
  }
}
