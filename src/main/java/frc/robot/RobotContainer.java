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
import frc.robot.commands.Launch;
import frc.robot.commands.Reset;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Indexer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.RangeSensor;


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
    private final Indexer indexer;
    private final Shooter shooter;
    private final Drivetrain drivetrain;
    private final RangeSensor sensor;

    private final XboxController driver;
    private final XboxController operator;
    private final Controls controls;

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        configureButtonBindings();

        driver = new XboxController(0);
        operator = new XboxController(1);

        controls = new Controls(driver, operator);
        drivetrain = new Drivetrain(controls);

        indexer = new Indexer();
        shooter = new Shooter();
        sensor = new RangeSensor();
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

        // A - launch ball
        new JoystickButton(operator, Button.kA.value).onTrue(new Launch(indexer, shooter));

        // Y - stop subsystems
        new JoystickButton(operator, Button.kY.value).onTrue(new Reset(indexer, shooter));
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
