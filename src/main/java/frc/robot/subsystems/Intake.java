package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.MotorControllerGroup;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.IntakeConfig;


public class Intake extends SubsystemBase{

    private final CANSparkMax leftIntake;
    private final CANSparkMax rightIntake;
    private final MotorControllerGroup intakeGroup;
    private double speed;

    public Intake(){

        leftIntake = new CANSparkMax(IntakeConfig.leftIntakePort, MotorType.kBrushless);
        rightIntake = new CANSparkMax(IntakeConfig.rightIntakePort, MotorType.kBrushless);

        intakeGroup = new MotorControllerGroup(leftIntake, rightIntake);

        leftIntake.restoreFactoryDefaults();
        rightIntake.restoreFactoryDefaults();

        speed = 0;

    }

    @Override
    public void periodic(){
    // !! IMPORTANT !!: This will not be how the intake works, this is temporary code to use for tuning the limits of the intake motor 
        setSpeed(speed);
    }

    public void updateSpeed(double speed){
        this.speed = speed;
    }

    public void setSpeed(Double speed){
        intakeGroup.set(speed);
    }

    public double getSpeed(){
        return speed;
    }


    
}
