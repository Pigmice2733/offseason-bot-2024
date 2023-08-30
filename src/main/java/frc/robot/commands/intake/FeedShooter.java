// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants.IntakeConfig;
import frc.robot.subsystems.Intake;

public class FeedShooter extends SequentialCommandGroup {
    public FeedShooter(Intake intake) {
        addCommands(Commands.run(() -> intake.setIntakeWheelsOutput(IntakeConfig.FEED_SHOOTER_SPEEDS)),
                Commands.waitSeconds(1),
                Commands.run(() -> intake.setIntakeWheelsOutput(0)));
        addRequirements(intake);
    }
}
