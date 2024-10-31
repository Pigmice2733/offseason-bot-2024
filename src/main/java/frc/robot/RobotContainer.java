// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.ShooterConfig;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in
 * the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of
 * the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
    private final Indexer indexer = new Indexer();
    private final Shooter shooter = new Shooter();
    private final XboxController driver = new XboxController(0);
    private final XboxController operator = new XboxController(1);
    private final Controls controls = new Controls(driver, operator);
    private final Drivetrain drivetrain = new Drivetrain(controls);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        configureButtonBindings();
    }

    private void addOperatorControls(XboxController controller) {
        new JoystickButton(controller,Button.kRightBumper.value).onTrue(indexer.startIndexer(true));
        new JoystickButton(controller,Button.kRightBumper.value).onFalse(indexer.stopIndexer());
        new JoystickButton(controller,Button.kLeftBumper.value).onTrue(indexer.startIndexer(false));
        new JoystickButton(controller,Button.kLeftBumper.value).onFalse(indexer.stopIndexer());
        
        new JoystickButton(controller, Button.kY.value).onTrue(shooter.startShooter(ShooterConfig.HIGH_SPEEDS));
        new JoystickButton(controller, Button.kA.value).onTrue(shooter.startShooter(ShooterConfig.LOW_SPEEDS));
        new JoystickButton(controller, Button.kX.value).onTrue(shooter.stopShooter());
    }

    /**
     * Use this method to define your button->command mappings. Buttons can be
     * created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing
     * it to a {@link
     * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
     */
    private void configureButtonBindings() {
        // Driver X - slow mode
        new JoystickButton(driver, Button.kX.value).onTrue(new InstantCommand(drivetrain::toggleSlowMode, drivetrain));
        addOperatorControls(driver);
        addOperatorControls(operator);
    }

    public void periodic() {
    }
    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return null;
    }
}
