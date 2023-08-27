package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConfig;

public class Shooter extends SubsystemBase {
    private final CANSparkMax leftMotor, rightMotor;

    private final MotorControllerGroup motorGroup;

    public Shooter() {
        leftMotor = new CANSparkMax(ShooterConfig.LEFT_SHOOT_PORT, MotorType.kBrushless);
        rightMotor = new CANSparkMax(ShooterConfig.RIGHT_SHOOT_PORT, MotorType.kBrushless);

        leftMotor.restoreFactoryDefaults();
        rightMotor.restoreFactoryDefaults();
        rightMotor.setInverted(true);

        motorGroup = new MotorControllerGroup(leftMotor, rightMotor);
    }

    public void setOutputs(double speed) {
        motorGroup.set(speed);
    }
}
