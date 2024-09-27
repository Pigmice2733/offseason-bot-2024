package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ShooterConfig;
import frc.robot.subsystems.Shooter;

public class PrepareToShoot extends Command {
  Shooter shooter;

  public PrepareToShoot(Shooter shooter) {
    addRequirements(shooter);
    this.shooter = shooter;
  }

  @Override
  public void initialize() {
    shooter.setMotors(ShooterConfig.LOW_SPEEDS);
  }

  @Override
  public boolean isFinished() {
    return shooter.shooterAtSpeed(ShooterConfig.LOW_SPEEDS);
  }
}
