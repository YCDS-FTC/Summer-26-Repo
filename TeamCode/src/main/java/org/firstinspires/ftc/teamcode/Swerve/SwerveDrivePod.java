package org.firstinspires.ftc.teamcode.Swerve;

import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.TurnKd;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.TurnKi;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.TurnKp;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.seattlesolvers.solverslib.controller.PIDController;

public class SwerveDrivePod {
    private final DcMotorEx    driveMotor;
    private final CRServo      steerServo;
    private final AnalogInput  encoder;
    private final double       offsetDeg;
    private final boolean      steerReversed;

    private final PIDController steerController;


    public SwerveDrivePod(DcMotorEx driveMotor,
                            CRServo steerServo,
                            AnalogInput encoder,
                            double offsetDeg,
                            boolean steerReversed) {
        this.driveMotor    = driveMotor;
        this.steerServo    = steerServo;
        this.encoder       = encoder;
        this.offsetDeg     = offsetDeg;
        this.steerReversed = steerReversed;

        this.steerController = new PIDController(TurnKp, TurnKi, TurnKd);
    }


    public double getAngle(){
        return EncoderUtil.getEncoderAngle(encoder) - offsetDeg;
    }

    public void setPod(double targetAngle, double targetDrivePower){

        double drivePower = targetDrivePower;
        double currentAngle = getAngle();
        double delta = EncoderUtil.closestAngle(currentAngle, targetAngle);

//if the delta is more than 90 turn the clsoer angle while reversing the wheel? and maybe 90 isnt the right value here idk
        if (Math.abs(delta) > 90.0) {
            delta -= Math.signum(delta) * 180.0;
            drivePower = -drivePower;
        }

        double angle = currentAngle + delta;

        double steerControllerOutput = steerController.calculate(currentAngle, angle);

        //We could remove this if we properly configured them in hardware
        if (steerReversed) {
            steerControllerOutput = -steerControllerOutput;
        }

        steerServo.setPower(steerControllerOutput);
        driveMotor.setPower(drivePower);
    }
}
