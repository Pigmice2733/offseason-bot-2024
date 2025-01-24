package frc.robot.commands;

// import com.revrobotics.Rev2mDistanceSensor;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
// import frc.robot.Constants.ShooterConfig;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class Launch extends SequentialCommandGroup {
  public Launch(Indexer indexer, Shooter shooter) {
    // if (!shooter.isObstacleDetected(ShooterConfig.SHOOTING_LIMIT)) {
      addCommands(
          new PrepareToShoot(shooter),
          indexer.startIndexer(true),
          Commands.waitSeconds(2),
          new Reset(indexer, shooter));
      addRequirements(indexer, shooter);
    // }
  }
}
