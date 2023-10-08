// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import com.pigmice.frc.lib.drivetrain.differential.commands.manual.ArcadeDriveDifferential;
import com.pigmice.frc.lib.drivetrain.subysytems.DifferentialDrivetrain;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.XboxController.Button;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.Constants.ShooterConfig;
import frc.robot.commands.intake.FeedShooter;
import frc.robot.commands.intake.IntakeExtension;
import frc.robot.commands.intake.LeaveAuto;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Intake.IntakeState;

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
    private final DifferentialDrivetrain drivetrain = new DifferentialDrivetrain(
            DrivetrainConfig.DRIVETRAIN_CONFIG, true);
    // private final Intake intake = new Intake();
    private final Shooter shooter = new Shooter();

    private final XboxController driver = new XboxController(0);
    private final XboxController operator = new XboxController(1);
    private final Controls controls = new Controls(driver, operator);

    /**
     * The container for the robot. Contains subsystems, OI devices, and commands.
     */
    public RobotContainer() {
        drivetrain.setDefaultCommand(
                new ArcadeDriveDifferential(drivetrain, controls::getDriveSpeed,
                        controls::getTurnSpeed));

        // intake.setDefaultCommand(new IntakeExtension(intake,
        // controls::getManualIntakeSpeed)); TODO

        configureButtonBindings();

        // shooter.spinUpFlywheels(ShooterConfig.HIGH_SPEEDS);
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
        // A - Set shooter wheels to mid speeds
        // new JoystickButton(operator, Button.kA.value)
        // .toggleOnTrue(shooter.spinUpFlywheelsCommand(ShooterConfig.MID_SPEEDS));

        // // Y - Set shooter wheels to high speeds
        // new JoystickButton(operator, Button.kY.value)
        // .onTrue(shooter.spinUpFlywheelsCommand(ShooterConfig.HIGH_SPEEDS));

        // // X - Feed intake
        // new JoystickButton(operator, Button.kX.value)
        // .onTrue(Commands.sequence(
        // new FeedShooter(intake),
        // Commands.waitSeconds(1),
        // shooter.spinUpFlywheelsCommand(ShooterConfig.STOPPED_SPEEDS)));

        // POV UP - Fully retracted intake

        // new POVButton(operator, 180) TODO
        // .onTrue(intake.setTargetExtensionStateCommand(IntakeState.Retracted));

        // // POV RIGHT or LEFT - Half extend intake
        // new POVButton(operator, 90)
        // .onTrue(intake.setTargetExtensionStateCommand(IntakeState.Middle));
        // new POVButton(operator, 270)
        // .onTrue(intake.setTargetExtensionStateCommand(IntakeState.Middle));

        // // POV DOWN - Fully extended intake
        // new POVButton(operator, 0)
        // .onTrue(intake.setTargetExtensionStateCommand(IntakeState.Extended));

        // new JoystickButton(operator, Button.kX.value)
        // .onTrue(shooter.spinUpFlywheelsCommand(ShooterConfig.HIGH_SPEEDS))
        // .onFalse(shooter.stopFlywheelsCommand());
        // new JoystickButton(operator, Button.kA.value)
        // .onTrue(shooter.spinUpFlywheelsCommand(ShooterConfig.INTAKE_SPEEDS))
        // .onFalse(shooter.stopFlywheelsCommand());

        // intake
        new JoystickButton(operator, Button.kY.value).onTrue(shooter.setTopMotorOutputCommand(-0.14))
                .onFalse(shooter.setTopMotorOutputCommand(0));

        // spin up
        // new JoystickButton(operator,
        // Button.kA.value).onTrue(shooter.setBottomMotorOutputCommand(1))
        // .onFalse(shooter.setBottomMotorOutputCommand(0));

        // shoot
        new JoystickButton(operator, Button.kX.value).onTrue(shooter.setTopMotorOutputCommand(1))
                .onFalse(shooter.setTopMotorOutputCommand(0));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand() {
        return new SequentialCommandGroup(// shooter.setBottomMotorOutputCommand(1),
                // Commands.waitSeconds(2),
                shooter.setTopMotorOutputCommand(0.7),
                Commands.waitSeconds(1), shooter.setTopMotorOutputCommand(0),
                new LeaveAuto(drivetrain).withTimeout(10));
        // return shooter.setBottomMotorOutputCommand(1);
        // return new LeaveAuto(drivetrain);
    }
}
