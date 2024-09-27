// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

// import com.pigmice.frc.lib.drivetrain.differential.commands.manual.ArcadeDriveDifferential;
// import com.pigmice.frc.lib.drivetrain.subysytems.DifferentialDrivetrain;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.commands.Launch;
import frc.robot.commands.Reset;
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
<<<<<<< HEAD
    // private final DifferentialDrivetrain drivetrain = new DifferentialDrivetrain(
    //        DrivetrainConfig.DRIVETRAIN_CONFIG, true);
=======
    private final DifferentialDrivetrain drivetrain = new DifferentialDrivetrain(
            DrivetrainConfig.DRIVETRAIN_CONFIG, true);
>>>>>>> 2e798f07980545b83d5213ffe81a0965bc68419f
    private final Indexer indexer = new Indexer();
    private final Shooter shooter = new Shooter();

    private final XboxController driver = new XboxController(0);
    private final XboxController operator = new XboxController(1);
    private final Controls controls = new Controls(driver, operator);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
<<<<<<< HEAD
        // drivetrain.setDefaultCommand(
        //        new ArcadeDriveDifferential(drivetrain, controls::getDriveSpeed,
        //                controls::getTurnSpeed));
=======
        drivetrain.setDefaultCommand(
                new ArcadeDriveDifferential(drivetrain, controls::getDriveSpeed,
                        controls::getTurnSpeed));
>>>>>>> 2e798f07980545b83d5213ffe81a0965bc68419f

        // intake.setDefaultCommand(new IntakeExtension(intake,
        // controls::getManualIntakeSpeed));

        configureButtonBindings();
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
