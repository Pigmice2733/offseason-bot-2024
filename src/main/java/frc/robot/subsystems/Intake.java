package frc.robot.subsystems;

import com.pigmice.frc.lib.shuffleboard_helper.ShuffleboardHelper;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CANConfig;
import frc.robot.Constants.IntakeConfig;

public class Intake extends SubsystemBase {
    private final CANSparkMax leftExtend = new CANSparkMax(CANConfig.LEFT_INTAKE_EXTEND_PORT,
            MotorType.kBrushless);

    private final CANSparkMax rightExtend = new CANSparkMax(CANConfig.RIGHT_INTAKE_EXTEND_PORT,
            MotorType.kBrushless);

    private final CANSparkMax intakeWheels;

    private double targetExtensionPosition;
    public double setpoint;

    GenericEntry leftSpeedEntry, rightSpeedEntry;

    public Intake() {
        intakeWheels = new CANSparkMax(CANConfig.INTAKE_WHEELS_PORT, MotorType.kBrushless);

        leftExtend.restoreFactoryDefaults();
        rightExtend.restoreFactoryDefaults();
        intakeWheels.restoreFactoryDefaults();

        rightExtend.setInverted(true);
        leftExtend.setInverted(false);

        ShuffleboardHelper.addOutput("Target Pos", Constants.INTAKE_TAB, () -> targetExtensionPosition)
                .asDial(0.0, IntakeConfig.MAX_EXTEND_DISTANCE);

        ShuffleboardHelper.addOutput("Left Pos", Constants.INTAKE_TAB, () -> getLeftPosition())
                .asDial(0.0, IntakeConfig.MAX_EXTEND_DISTANCE);

        ShuffleboardHelper.addOutput("Right Pos", Constants.INTAKE_TAB, () -> getRightPosition())
                .asDial(0.0, IntakeConfig.MAX_EXTEND_DISTANCE);

        ShuffleboardHelper.addOutput("Setpoint", Constants.INTAKE_TAB, () -> setpoint)
                .asDial(0.0, IntakeConfig.MAX_EXTEND_DISTANCE);

        leftSpeedEntry = Constants.INTAKE_TAB.add("left out", 0).getEntry();
        rightSpeedEntry = Constants.INTAKE_TAB.add("right out", 0).getEntry();

    }

    @Override
    public void periodic() {

    }

    public void rightExtensionOutput(double percent) {
        leftExtend.set(percent);
        leftSpeedEntry.setDouble(percent);
    }

    public void leftExtensionOutput(double percent) {
        percent *= 1.5;
        rightExtend.set(percent);
        rightSpeedEntry.setDouble(percent);
    }

    public void setTargetExtensionState(IntakeState state) {
        targetExtensionPosition = getExtendDistance(state);
    }

    public Command setTargetExtensionStateCommand(IntakeState state) {
        return Commands.runOnce(() -> setTargetExtensionState(state), this);
    }

    public void changeSetpoint(double delta) {
        targetExtensionPosition += delta;
    }

    public double getTargetExtensionPosition() {
        return targetExtensionPosition;
    }

    public void setIntakeWheelsOutput(double percent) {
        intakeWheels.set(percent);
    }

    public double getLeftPosition() {
        return leftExtend.getEncoder().getPosition();
    }

    public double getRightPosition() {
        return -rightExtend.getEncoder().getPosition();
    }

    // public boolean atState(IntakeState state) {
    // return Math.abs(getLeftPosition() - getExtendDistance(state)) <
    // IntakeConfig.POSITION_TOLERANCE;
    // }

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

    public void resetEncoders() {
        leftExtend.getEncoder().setPosition(0);
        rightExtend.getEncoder().setPosition(0);
    }
}
