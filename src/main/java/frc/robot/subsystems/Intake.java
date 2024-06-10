package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CANConfig;
import frc.robot.Constants.IntakeConfig;

public class Intake extends SubsystemBase {
    private final CANSparkMax indexer;
    GenericEntry wheelSpeedEntry;

    public Intake() {
        indexer = new CANSparkMax(CANConfig.INDEXER_PORT, MotorType.kBrushless);
        indexer.restoreFactoryDefaults();

        wheelSpeedEntry = Constants.SYSTEMS_TAB.add("Indexer Speed", 0).getEntry();
    }

    @Override
    public void periodic() {

    }

    public void setIndexerOutput(double percent) {
        if(Math.abs(percent) > 1) return;
        indexer.set(percent);
        wheelSpeedEntry.setDouble(percent);
    }

    public double getIndexerSpeed() {
        return indexer.get();
    }

    public Command startIndexer() {
        return Commands.runOnce(() -> setIndexerOutput(IntakeConfig.INTAKE_SPEED), this);
    }

    public Command stopIndexer() {
        return Commands.runOnce(() -> setIndexerOutput(0), this);
    }
}
