package frc.robot.subsystems;

import com.pigmice.frc.lib.shuffleboard_helper.ShuffleboardHelper;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CANConfig;
import frc.robot.Constants.IntakeConfig;

public class Intake extends SubsystemBase {
    private final CANSparkMax leftExtend = new CANSparkMax(CANConfig.LEFT_INTAKE_EXTEND_PORT,
            MotorType.kBrushless);

    private final CANSparkMax rightExtend = new CANSparkMax(CANConfig.RIGHT_INTAKE_EXTEND_PORT,
            MotorType.kBrushless);

    private final MotorControllerGroup extendGroup;
    private final CANSparkMax intakeWheels;

    private double targetExtensionPosition;

    public Intake() {
        intakeWheels = new CANSparkMax(CANConfig.INTAKE_WHEELS_PORT, MotorType.kBrushless);

        leftExtend.restoreFactoryDefaults();
        rightExtend.restoreFactoryDefaults();
        intakeWheels.restoreFactoryDefaults();

        rightExtend.setInverted(true);

        extendGroup = new MotorControllerGroup(leftExtend, rightExtend);

        ShuffleboardHelper.addOutput("Target Intake Position", Constants.INTAKE_TAB, () -> targetExtensionPosition)
                .asDial(0.0, IntakeConfig.MAX_EXTEND_DISTANCE);

        ShuffleboardHelper.addOutput("Current Intake Position", Constants.INTAKE_TAB, () -> getExtensionPosition())
                .asDial(0.0, IntakeConfig.MAX_EXTEND_DISTANCE);
    }

    @Override
    public void periodic() {
    }

    public void setExtensionOutputs(double percent) {
        extendGroup.set(percent);
    }

    public void setTargetExtensionState(IntakeState state) {
        targetExtensionPosition = getExtendDistance(state);
    }

    public double getTargetExtensionPosition() {
        return targetExtensionPosition;
    }

    public void setIntakeWheelsOutput(double percent) {
        intakeWheels.set(percent);
    }

    public double getExtensionPosition() {
        return (leftExtend.getEncoder().getPosition() + rightExtend.getEncoder().getPosition()) / 2;
    }

    public boolean atState(IntakeState state) {
        return Math.abs(getExtensionPosition() - getExtendDistance(state)) < IntakeConfig.POSITION_TOLERANCE;
    }

    public static double getExtendDistance(IntakeState state) {
        switch (state) {
            case Retracted:
                return 0;
            case Middle:
                return IntakeConfig.MID_EXTEND_DISTANCE;
            case Extended:
                return IntakeConfig.MAX_EXTEND_DISTANCE;
            default:
                return 0;
        }
    }

    public enum IntakeState {
        Retracted,
        Middle,
        Extended
    }
}
