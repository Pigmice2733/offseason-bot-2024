package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

public class Reset extends ParallelCommandGroup {
    public Reset(Indexer indexer, Shooter shooter) {
        addCommands(
                indexer.stopIndexer(),
                shooter.stopShooter());
        addRequirements(indexer, shooter);
    }
}
