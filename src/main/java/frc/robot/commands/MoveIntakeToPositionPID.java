package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.PIDCommand;
import frc.robot.Constants.IntakeConfig;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Intake.IntakeState;

public class MoveIntakeToPositionPID extends PIDCommand {

    public MoveIntakeToPositionPID(Intake intake, IntakeState state) {
        super(
            new PIDController(0.1, 0, 0),
                intake::getPosition,
                Intake.getExtendDistance(state),
                (output) -> intake.setSpeed(output),
                intake
        );

        getController().setTolerance(IntakeConfig.POSITION_TOLERANCE);
        addRequirements(intake);
    }

    @Override
    public boolean isFinished() {
        return getController().atSetpoint();
    }
}
