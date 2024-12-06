package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.vision.LimelightHelpers;

public class DriveToPosition extends Command {
  private Drivetrain drivetrain;
  private double distance;

  public DriveToPosition(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
  }

  @Override
  public void execute() {
    distance = 30 - LimelightHelpers.getTY("");
    drivetrain.driveSpeeds(DrivetrainConfig.DRIVE_MULTIPLIER * distance, 0);
  }

  @Override
  public boolean isFinished() {
    return distance <= 0;
  }

  @Override
  public void end(boolean interrupted) {
    drivetrain.driveSpeeds(0, 0);
  }
}
