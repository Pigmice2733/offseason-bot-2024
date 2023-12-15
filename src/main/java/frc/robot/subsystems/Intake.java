package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.pigmice.frc.lib.shuffleboard_helper.ShuffleboardHelper;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile.Constraints;
import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CANConfig;
import frc.robot.Constants.IntakeConfig;

public class Intake extends SubsystemBase {
    private final CANSparkMax extendMotor = new CANSparkMax(CANConfig.LEFT_INTAKE_EXTEND_PORT,
            MotorType.kBrushless);

    private final CANSparkMax intakeWheels;

    private double targetExtensionPosition;

    private final ProfiledPIDController pidController;
    DoubleSupplier inputSpeed;

    public Intake(DoubleSupplier inputSpeed) {
        this.inputSpeed = inputSpeed;
        intakeWheels = new CANSparkMax(CANConfig.INTAKE_WHEELS_PORT, MotorType.kBrushless);

        pidController = new ProfiledPIDController(IntakeConfig.EXTENSION_P, IntakeConfig.EXTENSION_I,
                IntakeConfig.EXTENSION_D,
                new Constraints(IntakeConfig.MAX_EXTENSION_VELOCITY,
                        IntakeConfig.MAX_EXTENSION_ACCELERATION));

        ShuffleboardHelper.addProfiledController("Controller", Constants.INTAKE_TAB, pidController,
                IntakeConfig.MAX_EXTENSION_VELOCITY, IntakeConfig.MAX_EXTENSION_ACCELERATION);

        extendMotor.restoreFactoryDefaults();
        extendMotor.getEncoder().setPosition(0);
        intakeWheels.restoreFactoryDefaults();

        extendMotor.setInverted(false);

        ShuffleboardHelper.addOutput("Target Pos", Constants.INTAKE_TAB, () -> targetExtensionPosition)
                .asDial(0.0, IntakeConfig.MAX_EXTEND_DISTANCE);

        ShuffleboardHelper.addOutput("Pos", Constants.INTAKE_TAB, () -> getPosition())
                .asDial(0.0, IntakeConfig.MAX_EXTEND_DISTANCE);

        ShuffleboardHelper.addOutput("Setpoint", Constants.INTAKE_TAB, () -> pidController.getSetpoint().position)
                .asDial(0.0, IntakeConfig.MAX_EXTEND_DISTANCE);

        ShuffleboardHelper.addOutput("Output", Constants.INTAKE_TAB, () -> extendMotor.get());

        ShuffleboardHelper.addInput("Input", Constants.INTAKE_TAB,
                (input) -> setTargetExtensionPos((double) input), 0.0);

    }

    @Override
    public void periodic() {
        setIntakeWheelsOutput(0.3);
        extensionOutput(inputSpeed.getAsDouble() * IntakeConfig.EXTENDING_SPEED);
        // double calculatedOutput = pidController.calculate(getPosition(),
        // targetExtensionPosition);
        // extensionOutput(calculatedOutput);
    }

    public void extensionOutput(double percent) {
        extendMotor.set(-percent);
    }

    public void setTargetExtensionState(IntakeState state) {
        targetExtensionPosition = getExtendDistance(state);
    }

    public void setTargetExtensionPos(double pos) {
        targetExtensionPosition = pos;
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

    public double getPosition() {
        return extendMotor.getEncoder().getPosition();
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
        extendMotor.getEncoder().setPosition(0);
    }
}
