// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.Constants.IntakeConfig;
import frc.robot.subsystems.Intake;
import static edu.wpi.first.math.trajectory.TrapezoidProfile.State;

import java.util.function.Supplier;

public class IntakeExtension extends ProfiledPIDCommand {
    private final Intake intake;
    private final Supplier<Double> manualControl;

    public IntakeExtension(Intake intake, Supplier<Double> manualControl) {
        super(new ProfiledPIDController(
                IntakeConfig.EXTENSION_P, IntakeConfig.EXTENSION_I, IntakeConfig.EXTENSION_D,
                new Constraints(IntakeConfig.MAX_EXTENSION_VELOSITY, IntakeConfig.MAX_EXTENSION_ACCELERATION)),
                () -> intake.getExtensionPosition(), () -> new State(intake.getTargetExtensionPosition(), 0),
                (output, state) -> intake.setExtensionOutputs(output));

        this.intake = intake;
        this.manualControl = manualControl;
        addRequirements(intake);
    }

    @Override
    public void execute() {
        super.execute();

        intake.changeSetpoint(manualControl.get());
        getController().setGoal(intake.getExtensionPosition());
    }
}
