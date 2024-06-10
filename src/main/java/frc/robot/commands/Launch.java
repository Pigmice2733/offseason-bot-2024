package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Launch extends ParallelCommandGroup {
    public Launch(Intake intake, Shooter shooter) {
        addCommands(
            intake.startIndexer(),
            shooter.prepareToShoot()
        );
        addRequirements(intake, shooter);
    }
}
