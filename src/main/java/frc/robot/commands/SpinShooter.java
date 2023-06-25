package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class SpinShooter extends CommandBase {
    private final Shooter shooter;
    private final DoubleSupplier speed;

    public SpinShooter(Shooter shooter, DoubleSupplier speed) {
        this.shooter = shooter;
        this.speed = speed;

        addRequirements(shooter);
    }

    @Override
    public void initialize() {
        shooter.setSpeed(speed.getAsDouble());
    }

    @Override
    public boolean isFinished() {
        return false;
    }

}
