package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Intake;

public class ExtendIntake extends CommandBase{
    @SuppressWarnings({ "PMD.UnusedPrivateField", "PMD.SingularField" })
    private final Intake intake;
    private final DoubleSupplier speed;

/**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
    public ExtendIntake(Intake intake, DoubleSupplier speed){
        this.intake = intake;
        this.speed = speed;

        addRequirements(intake);
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize(){

    }

    // Called every time the scheduler runs while the command is scheduled.   
    // !! IMPORTANT !!: This will not be how the intake works, this is temporary code to use for tuning the limits of the intake motor 
    @Override
    public void execute(){
        intake.setSpeed(speed.getAsDouble()); 
    }

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted){

    }

    // Returns true when the command should end.
    @Override
    public boolean isFinished(){
        return false;
    }
    
}
