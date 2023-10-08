// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.intake;

import com.pigmice.frc.lib.drivetrain.subysytems.DifferentialDrivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;

public class LeaveAuto extends CommandBase {
  DifferentialDrivetrain drivetrain;

  /** Creates a new LeaveAuto. */
  public LeaveAuto(DifferentialDrivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    drivetrain.arcadeDrive(-0.25, 0);
    System.out.println(drivetrain.getAverageDistance());
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    drivetrain.arcadeDrive(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return Math.abs(drivetrain.getAverageDistance()) > 4.5;
  }
}
