package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.vision.Vision;

public class DriveToTarget extends SequentialCommandGroup {
  private Pose2d endPose;

  public DriveToTarget(Drivetrain drivetrain, Vision vision) {
    endPose = new Pose2d(
        vision.getTranslationToBestTarget().getX() - 0.5,
        vision.getTranslationToBestTarget().getY(),
        vision.getTranslationToBestTarget().getRotation());
    addCommands(
        drivetrain.turnAngle(endPose.getRotation().getDegrees()),
        drivetrain.driveDistance(endPose.getTranslation().getNorm()));
  }
}
