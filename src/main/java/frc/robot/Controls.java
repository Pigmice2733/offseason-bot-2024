package frc.robot;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.Constants.DrivetrainConfig;
import frc.robot.Constants.IntakeConfig;

public class Controls {

    XboxController driver;
    XboxController operator;

    private double threshold = Constants.AXIS_THRESHOLD; // If a value from a joystick is less than this, it will return
                                                        // 0.

    public Controls(XboxController driver, XboxController operator) {
        this.driver = driver;
        this.operator = operator;
    }

    public double getIntakeSpeed(){
        double joystickValue = operator.getLeftY();
        joystickValue = MathUtil.applyDeadband(-joystickValue, threshold); // deals with stick drag


        return joystickValue * IntakeConfig.INTAKE_SPEED;
    }

    public double getDriveSpeed() {
        double joystickValue = driver.getLeftY();
        joystickValue = MathUtil.applyDeadband(-joystickValue, threshold); // deals with stick drag

        return joystickValue * DrivetrainConfig.DRIVE_SPEED;

    }

    public double getTurnSpeed() {
        double joystickValue = driver.getRightX();
        joystickValue = MathUtil.applyDeadband(joystickValue, threshold); // deals with stick drag

        return joystickValue * DrivetrainConfig.TURN_SPEED;
    }
}
