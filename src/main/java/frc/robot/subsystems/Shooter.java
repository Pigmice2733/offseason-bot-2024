package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CANConfig;

public class Shooter extends SubsystemBase {
    private final CANSparkMax leftMotor, rightMotor;
    private final MotorControllerGroup motorGroup;

    private final GenericEntry shooterSpeedEntry;

    public Shooter() {
        leftMotor = new CANSparkMax(CANConfig.LEFT_SHOOT_PORT, MotorType.kBrushless);
        rightMotor = new CANSparkMax(CANConfig.RIGHT_SHOOT_PORT, MotorType.kBrushless);

        leftMotor.restoreFactoryDefaults();
        rightMotor.restoreFactoryDefaults();
        rightMotor.setInverted(true);

        motorGroup = new MotorControllerGroup(leftMotor, rightMotor);

        shooterSpeedEntry = Constants.SHOOTER_TAB.add("Shooter Speed", 0)
                .withWidget(BuiltInWidgets.kNumberBar)
                .withProperties(Map.of("min", 0, "max", 1)).getEntry();
    }

    public void setFlywheelOutput(double speed) {
        motorGroup.set(speed);
        shooterSpeedEntry.setDouble(speed);
    }
}
