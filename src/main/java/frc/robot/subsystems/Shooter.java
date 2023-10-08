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

public class Shooter extends SubsystemBase {
    private final CANSparkMax bottomMotor, topMotor;

    private final GenericEntry bottomSpeedEntry, topSpeedEntry;

    public Shooter() {
        bottomMotor = new CANSparkMax(CANConfig.TOP_SHOOT_PORT, MotorType.kBrushless);
        topMotor = new CANSparkMax(CANConfig.BOTTOM_SHOOT_PORT, MotorType.kBrushless);

        bottomMotor.restoreFactoryDefaults();
        topMotor.restoreFactoryDefaults();
        topMotor.setInverted(true);

        bottomSpeedEntry = Constants.SHOOTER_TAB.add("Bottom Speed", 0)
                .withWidget(BuiltInWidgets.kNumberBar)
                .withProperties(Map.of("min", -1, "max", 1)).getEntry();

        topSpeedEntry = Constants.SHOOTER_TAB.add("Top Speed", 0)
                .withWidget(BuiltInWidgets.kNumberBar)
                .withProperties(Map.of("min", -1, "max", 1)).getEntry();
    }

    public void spinUpFlywheels(ShooterSpeeds speeds) {
        setMotorOutputs(speeds.topSpeed, speeds.bottomSpeed);
    }

    public Command spinUpFlywheelsCommand(ShooterSpeeds speeds) {
        return Commands.runOnce(() -> spinUpFlywheels(speeds), this);
    }

    public void stopFlywheels() {
        setMotorOutputs(0, 0);
    }

    public Command stopFlywheelsCommand() {
        return Commands.runOnce(() -> stopFlywheels());
    }

    private void setMotorOutputs(double bottomSpeed, double topSpeed) {
        topMotor.set(bottomSpeed);
        bottomMotor.set(topSpeed);

        bottomSpeedEntry.setDouble(bottomSpeed);
        topSpeedEntry.setDouble(topSpeed);
    }

    public void periodic() {
        // bottomMotor.set(0.5);
        // topMotor.set(0.5);
    }

    public static class ShooterSpeeds {
        public final double bottomSpeed;
        public final double topSpeed;

        public ShooterSpeeds(double bottomSpeed, double topSpeed) {
            this.bottomSpeed = topSpeed;
            this.topSpeed = bottomSpeed;
        }
    }
}
