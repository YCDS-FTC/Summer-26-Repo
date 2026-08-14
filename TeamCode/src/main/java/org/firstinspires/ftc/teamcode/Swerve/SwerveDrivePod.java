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

        double currentAngle = getAngle();

        double steerControllerOutput = steerController.calculate(currentAngle, targetAngle);

        //We could remove this if we properly configured them in hardware
        if (steerReversed) {
            steerControllerOutput = -steerControllerOutput;
        }

        steerServo.setPower(steerControllerOutput);
        driveMotor.setPower(targetDrivePower);
    }
}
