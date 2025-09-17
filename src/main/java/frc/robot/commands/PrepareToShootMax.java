package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ShooterConfig;
import frc.robot.subsystems.Shooter;

public class PrepareToShootMax extends Command {
  Shooter shooter;

  public PrepareToShootMax(Shooter shooter) {
    addRequirements(shooter);
    this.shooter = shooter;
  }

  @Override
  public void initialize() {
    shooter.setMotors(ShooterConfig.MAX_SPEED);
  }

  @Override
  public boolean isFinished() {
    return shooter.shooterAtSpeed(ShooterConfig.MAX_SPEED);
  }
}