package org.firstinspires.ftc.teamcode.Swerve;

import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstant.TurnKd;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstant.TurnKi;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstant.TurnKp;

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

    private final PIDController directionController;


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

        this.directionController = new PIDController(TurnKp, TurnKi, TurnKd);
    }


}
