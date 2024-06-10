package frc.robot.subsystems;

import java.util.Map;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CANConfig;
import frc.robot.Constants.ShooterConfig;

public class Shooter extends SubsystemBase {
    private final CANSparkMax upperWheel, lowerWheel;
    private final GenericEntry upperWheelEntry, lowerWheelEntry;

    public Shooter() {
        upperWheel = new CANSparkMax(CANConfig.UPPER_SHOOT_PORT, MotorType.kBrushless);
        lowerWheel = new CANSparkMax(CANConfig.LOWER_SHOOT_PORT, MotorType.kBrushless);

        upperWheel.restoreFactoryDefaults();
        lowerWheel.restoreFactoryDefaults();
        lowerWheel.setInverted(true);

        upperWheelEntry = Constants.SYSTEMS_TAB.add("Upper Shooter Wheel Speed", 0)
                .withWidget(BuiltInWidgets.kNumberBar)
                .withProperties(Map.of("min", 0, "max", 1)).getEntry();

        lowerWheelEntry = Constants.SYSTEMS_TAB.add("Lower Shooter Wheel Speed", 0)
                .withWidget(BuiltInWidgets.kNumberBar)
                .withProperties(Map.of("min", 0, "max", 1)).getEntry();
    }

    @Override
    public void periodic() {
        
    }

    public Command prepareToShoot() {
        return Commands.runOnce(() -> setMotors(ShooterConfig.MID_SPEEDS), this);
    }

    public Command stopShooter() {
        return Commands.runOnce(() -> setMotors(ShooterConfig.STOPPED), this);
    }

    private void setMotors(ShooterSpeeds speeds) {
        setMotorOutputs(speeds.upperSpeed, speeds.lowerSpeed);
    }

    private void setMotorOutputs(double upperSpeed, double lowerSpeed) {
        if(Math.abs(upperSpeed) > 1 || Math.abs(lowerSpeed) > 1) return;
        
        upperWheel.set(upperSpeed);
        lowerWheel.set(lowerSpeed);
        upperWheelEntry.setDouble(upperSpeed);
        lowerWheelEntry.setDouble(lowerSpeed);
    }

    public static class ShooterSpeeds {
        public final double upperSpeed;
        public final double lowerSpeed;

        public ShooterSpeeds(double backSpeed, double frontSpeed) {
            this.upperSpeed = backSpeed;
            this.lowerSpeed = frontSpeed;
        }
    }
}
