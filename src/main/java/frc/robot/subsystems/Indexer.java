package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.CANConfig;
import frc.robot.Constants.IndexerConfig;

public class Indexer extends SubsystemBase {
  private final SparkMax indexer;
  GenericEntry wheelSpeedEntry;

  public Indexer() {
    indexer = new SparkMax(CANConfig.INDEXER_PORT, MotorType.kBrushless);
    SparkMaxConfig indexerConfig = new SparkMaxConfig();
    indexerConfig.inverted(true);
    indexer.configure(indexerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    wheelSpeedEntry = Constants.SYSTEMS_TAB.add("Indexer Speed", 0).getEntry();
  }

  @Override
  public void periodic() {

  }

  public void setIndexerOutput(double percent) {
    if (Math.abs(percent) > 1)
      return;
    indexer.set(percent);
    wheelSpeedEntry.setDouble(percent);
  }

  public double getIndexerSpeed() {
    return indexer.get();
  }

  public Command startIndexer(boolean forward) {
    return Commands
        .runOnce(() -> setIndexerOutput(forward ? IndexerConfig.INDEXER_SPEED : -IndexerConfig.INDEXER_SPEED), this);
  }

  public Command stopIndexer() {
    return Commands.runOnce(() -> setIndexerOutput(0), this);
  }
}
