package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class Launch extends SequentialCommandGroup {
  public Launch(Indexer indexer, Shooter shooter) {
    addCommands(
        new PrepareToShoot(shooter),
        indexer.startIndexer(),
        Commands.waitSeconds(2),
        new Reset(indexer, shooter));
    addRequirements(indexer, shooter);
  }
}
