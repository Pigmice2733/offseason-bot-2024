package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.vision.Vision;

public class DriveToTarget extends SequentialCommandGroup {
  private Supplier<Pose2d> endPose;

  public DriveToTarget(Drivetrain drivetrain, Vision vision) {
    endPose = () -> vision.hasTarget()
        ? new Pose2d(
            vision.getTranslationToBestTarget().getX() - 0.5,
            vision.getTranslationToBestTarget().getY(),
            vision.getTranslationToBestTarget().getRotation())
        : new Pose2d();
    addCommands(
        drivetrain.turnAngle(endPose.get().getRotation().getDegrees()),
        drivetrain.driveDistance(endPose.get().getTranslation().getNorm()));
  }
}
