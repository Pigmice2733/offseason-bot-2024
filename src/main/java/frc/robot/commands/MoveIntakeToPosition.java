package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.SimpleWidget;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.IntakeConfig;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Intake.IntakeState;

public class MoveIntakeToPosition extends CommandBase {
    private final Intake intake;
    private final double targetPosition;

    /**
     * Moves the intake to an extended position.
     *
     * @param intake The intake subsystem used by this command.
     * @param speed  The speed at which to move the intake.
     */
    public MoveIntakeToPosition(Intake intake, DoubleSupplier speed, IntakeState intakeState) {
        this.intake = intake;
        targetPosition = Intake.getExtendDistance(intakeState);

        addRequirements(intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        
    }

    @Override
    public void execute() {
        if (intake.getPosition() < targetPosition)
            intake.startSpinning();
        else intake.spinBackwards();
    }

    // Called when the command finishes.
    @Override
    public void end(boolean interrupted) {
        intake.stopSpinning();
    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        return Math.abs(intake.getPosition()-targetPosition) < IntakeConfig.POSITION_TOLERANCE;
    }
}
