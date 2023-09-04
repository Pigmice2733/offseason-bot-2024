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
    private final CANSparkMax frontMotor, backMotor;

    private final GenericEntry frontSpeedEntry, backSpeedEntry;

    public Shooter() {
        frontMotor = new CANSparkMax(CANConfig.LEFT_SHOOT_PORT, MotorType.kBrushless);
        backMotor = new CANSparkMax(CANConfig.RIGHT_SHOOT_PORT, MotorType.kBrushless);

        frontMotor.restoreFactoryDefaults();
        backMotor.restoreFactoryDefaults();
        backMotor.setInverted(true);

        frontSpeedEntry = Constants.SHOOTER_TAB.add("Front Speed", 0)
                .withWidget(BuiltInWidgets.kNumberBar)
                .withProperties(Map.of("min", 0, "max", 1)).getEntry();

        backSpeedEntry = Constants.SHOOTER_TAB.add("Back Speed", 0)
                .withWidget(BuiltInWidgets.kNumberBar)
                .withProperties(Map.of("min", 0, "max", 1)).getEntry();
    }

    public void spinUpFlywheels(ShooterSpeeds speeds) {
        setMotorOutputs(speeds.frontSpeed, speeds.backSpeed);
    }

    public Command spinUpFlywheelsCommand(ShooterSpeeds speeds) {
        return Commands.runOnce(() -> spinUpFlywheels(speeds), this);
    }

    private void setMotorOutputs(double frontSpeed, double backSpeed) {
        frontMotor.set(frontSpeed);
        backMotor.set(backSpeed);

        frontSpeedEntry.setDouble(frontSpeed);
        backSpeedEntry.setDouble(backSpeed);
    }

    public static class ShooterSpeeds {
        public final double backSpeed;
        public final double frontSpeed;

        public ShooterSpeeds(double backSpeed, double frontSpeed) {
            this.backSpeed = backSpeed;
            this.frontSpeed = frontSpeed;
        }
    }
}
