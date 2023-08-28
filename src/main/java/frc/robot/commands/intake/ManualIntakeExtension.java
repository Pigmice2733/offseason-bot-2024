// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.Constants.IntakeConfig;
import frc.robot.subsystems.Intake;
import static edu.wpi.first.math.trajectory.TrapezoidProfile.State;

import java.util.function.Supplier;

public class ManualIntakeExtension extends CommandBase {
    private final Intake intake;
    private final Supplier<Double> outputSupplier;

    public ManualIntakeExtension(Intake intake, Supplier<Double> outputSupplier) {
        this.intake = intake;
        this.outputSupplier = outputSupplier;

        addRequirements(intake);
    }

    @Override
    public void execute() {
        intake.setExtensionOutputs(outputSupplier.get());
    }
}
