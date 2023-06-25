package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.IntakeConfig;
import frc.robot.subsystems.Intake;

public class ExtendIntake extends CommandBase {
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Intake intake;
    private final DoubleSupplier speed;
    private final boolean full;

    // !! IMPORTANT !!: This will not be how the intake works, this is temporary
    // code to use for tuning the limits of the intake motor

    /**
     * Moves the intake to an extended position.
     *
     * @param intake The intake subsystem used by this command.
     * @param speed  The speed at which to move the intake.
     * @param full   Determines the end position: if true, the intake extends all
     *               the way; if false, it extends part-way.
     */
    public ExtendIntake(Intake intake, DoubleSupplier speed, boolean full) {
        this.intake = intake;
        this.speed = speed;
        this.full = full;

        addRequirements(intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        intake.setSpeed(speed.getAsDouble());
    }

    // Called when the command finishes.
    @Override
    public void end(boolean interrupted) {
        if (!interrupted) {
            intake.startSpinning();
        }
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (full) {
            return intake.getPosition() >= IntakeConfig.maxExtendDistance;
        } else {
            return intake.getPosition() >= IntakeConfig.midExtendDistance;
        }
    }
}
