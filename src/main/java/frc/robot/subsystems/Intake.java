package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConfig;

public class Intake extends SubsystemBase {
    private final CANSparkMax leftExtend;
    private final CANSparkMax rightExtend;

    private final MotorControllerGroup extendGroup;

    private final CANSparkMax intakeWheels;

    private double targetOutput;

    public Intake() {
        leftExtend = new CANSparkMax(IntakeConfig.LEFT_INTAKE_EXTEND_PORT, MotorType.kBrushless);
        rightExtend = new CANSparkMax(IntakeConfig.RIGHT_INTAKE_EXTEND_PORT, MotorType.kBrushless);
        intakeWheels = new CANSparkMax(IntakeConfig.INTAKE_WHEELS_PORT, MotorType.kBrushless);

        leftExtend.restoreFactoryDefaults();
        rightExtend.restoreFactoryDefaults();
        intakeWheels.restoreFactoryDefaults();

        rightExtend.setInverted(true);
        extendGroup = new MotorControllerGroup(leftExtend, rightExtend);
    }

    @Override
    public void periodic() {
        setExtensionOutputs(targetOutput);
    }

    public void setExtensionOutputs(double percent) {
        extendGroup.set(percent);
    }

    public void setIntakeWheelsOutput(double percent) {
        intakeWheels.set(percent);
    }

    public double getPosition() {
        return (leftExtend.getEncoder().getPosition() + rightExtend.getEncoder().getPosition()) / 2;
    }

    public boolean atState(IntakeState state) {
        return Math.abs(getPosition() - getExtendDistance(state)) < IntakeConfig.POSITION_TOLERANCE;
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
