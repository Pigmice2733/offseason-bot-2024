package frc.robot.subsystems.vision;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.VisionConfig;
import frc.robot.subsystems.vision.LimelightHelpers.*;

public class Vision extends SubsystemBase {
  private String camName;

  private LimelightResults results;
  private LimelightTarget_Fiducial[] allTargets;

  private boolean hasTarget;
  private LimelightTarget_Fiducial bestTarget;

  private GenericEntry targetX, targetY, robotX, robotY, translationX, translationY;

  /** Finds and uses AprilTags and other vision targets. */
  public Vision() {
    camName = VisionConfig.CAMERA_NAME;

    // targetX = Constants.VISION_TAB.add("Target X", 0).withPosition(0,
    // 0).getEntry();
    // targetY = Constants.VISION_TAB.add("Target Y", 0).withPosition(1,
    // 0).getEntry();
    // robotX = Constants.VISION_TAB.add("Robot Pose X", 0).withPosition(0,
    // 1).getEntry();
    // robotY = Constants.VISION_TAB.add("Robot Pose Y", 0).withPosition(1,
    // 1).getEntry();
    // translationX = Constants.VISION_TAB.add("Nearest Tag X", 0).withPosition(0,
    // 2).getEntry();
    // translationY = Constants.VISION_TAB.add("Nearest Tag Y", 0).withPosition(1,
    // 2).getEntry();
  }

  @Override
  public void periodic() {
    results = LimelightHelpers.getLatestResults(camName);
    allTargets = results.targets_Fiducials;

    // System.out.println(results.getBotPose2d().toString());

    if (allTargets.length == 0) {
      bestTarget = null;
      hasTarget = false;
      // System.out.println("target: " + -1);
    } else {
      hasTarget = true;
      bestTarget = allTargets[0];
      // System.out.println("target: " + bestTarget.fiducialID);
    }

    // updateEntries();
  }

  private void updateEntries() {
    targetX.setDouble(hasTarget() ? bestTarget.tx : 0);
    targetY.setDouble(hasTarget() ? bestTarget.ty : 0);
    robotX.setDouble(hasTarget() ? getEstimatedRobotPose().getX() : 0);
    robotY.setDouble(hasTarget() ? getEstimatedRobotPose().getY() : 0);
    translationX.setDouble(hasTarget() ? getTranslationToBestTarget().getX() : 0);
    translationY.setDouble(hasTarget() ? getTranslationToBestTarget().getX() : 0);
  }

  /** Returns true when there is a current target. */
  public boolean hasTarget() {
    return hasTarget;
  }

  /** Returns the best target's ID or -1 if no target is found. */
  public int getBestTargetID() {
    return (int) (hasTarget() ? bestTarget.fiducialID : -1);
  }

  /**
   * Returns the robot's estimated 2D position or null if no target is found.
   */
  public Pose2d getEstimatedRobotPose() {
    if (!hasTarget)
      return null;

    return results.getBotPose2d();

    // Rotate 180
    // estimatedPose = new Pose2d(estimatedPose.getX(),
    // estimatedPose.getY(),
    // Rotation2d.fromDegrees(estimatedPose.getRotation().getDegrees() -
    // 180));
  }

  /**
   * Returns the 2D translation between the robot's estimated position and the
   * target.
   */
  public Pose2d getTranslationToBestTarget() {
    return hasTarget() ? bestTarget.getRobotPose_TargetSpace2D() : new Pose2d();
  }
}
