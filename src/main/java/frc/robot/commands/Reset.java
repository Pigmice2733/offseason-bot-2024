package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Reset extends ParallelCommandGroup {
    public Reset(Intake intake, Shooter shooter) {
        addCommands(
            intake.stopIndexer(),
            shooter.stopShooter()
        );
        addRequirements(intake, shooter);
    }
}
