// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.IntakeConfig;
import frc.robot.subsystems.Intake;
import java.util.function.Supplier;

public class IntakeExtension extends CommandBase {
    private final Intake intake;
    private final Supplier<Double> manualControl;

    private final ProfiledPIDController leftController, rightController;

    public IntakeExtension(Intake intake, Supplier<Double> manualControl) {
        // super(,
        // () -> intake.getExtensionPosition(), () -> new
        // State(intake.getTargetExtensionPosition(), 0),
        // (output, state) -> intake.setExtensionOutputs(output));

        leftController = new ProfiledPIDController(
                IntakeConfig.EXTENSION_P, IntakeConfig.EXTENSION_I, IntakeConfig.EXTENSION_D,
                new Constraints(IntakeConfig.MAX_EXTENSION_VELOSITY, IntakeConfig.MAX_EXTENSION_ACCELERATION));

        rightController = new ProfiledPIDController(
                IntakeConfig.EXTENSION_P, IntakeConfig.EXTENSION_I, IntakeConfig.EXTENSION_D,
                new Constraints(IntakeConfig.MAX_EXTENSION_VELOSITY, IntakeConfig.MAX_EXTENSION_ACCELERATION));

        this.intake = intake;
        this.manualControl = manualControl;
        addRequirements(intake);
    }

    @Override
    public void initialize() {
        intake.resetEncoders();
        leftController.reset(0);
        rightController.reset(0);
    }

    @Override
    public void execute() {
        super.execute();
        // intake.changeSetpoint(manualControl.get());

        double leftPos = intake.getLeftPosition();
        double rightPos = intake.getRightPosition();
        double difference = leftPos - rightPos;

        double diffSq = difference * difference * 0.1;

        double leftOutput = leftController.calculate(leftPos, intake.getTargetExtensionPosition() - diffSq);
        double rightOutput = rightController.calculate(rightPos, intake.getTargetExtensionPosition() + diffSq);

        intake.leftExtensionOutput(leftOutput);
        intake.rightExtensionOutput(rightOutput);

        intake.setpoint = leftController.getSetpoint().position;
    }
}
