package org.firstinspires.ftc.teamcode.Swerve;

import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.ANALOG_MAX_VOLTAGE;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.ANALOG_MIN_VOLTAGE;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.LEFT_BACK_DRIVE_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.LEFT_BACK_ENCODER_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.LEFT_BACK_OFFSET_DEG;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.LEFT_BACK_STEER_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.LEFT_FRONT_DRIVE_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.LEFT_FRONT_ENCODER_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.LEFT_FRONT_OFFSET_DEG;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.LEFT_FRONT_STEER_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.RIGHT_BACK_DRIVE_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.RIGHT_BACK_ENCODER_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.RIGHT_BACK_OFFSET_DEG;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.RIGHT_BACK_STEER_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.RIGHT_FRONT_DRIVE_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.RIGHT_FRONT_ENCODER_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.RIGHT_FRONT_OFFSET_DEG;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.RIGHT_FRONT_STEER_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.TRACK_LENGTH;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.TRACK_WIDTH;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.TurnKd;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.TurnKf;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.TurnKi;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstants.TurnKp;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.ftc.drivetrains.CoaxialPod;
import com.pedropathing.ftc.drivetrains.SwervePod;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Builds the four CoaxialPods (Pedro Pathing's native swerve pod) from hardware config names,
 * for use with FollowerBuilder.swerveDrivetrain(...). Pod offsets use Pedro's own Odometry
 * Coordinate System (x = forward(+), y = left(+)), NOT the x-forward/y-right convention the old
 * hand-rolled SwerveDriveCoordinator used.
 */
public class SwerveDrivetrain {

    public final CoaxialPod leftFrontPod, rightFrontPod, leftBackPod, rightBackPod;

    public SwerveDrivetrain(HardwareMap hardwareMap) {
        PIDFCoefficients turnPIDF = new PIDFCoefficients(TurnKp, TurnKi, TurnKd, TurnKf);

        double halfLength = TRACK_LENGTH / 2.0;
        double halfWidth = TRACK_WIDTH / 2.0;

        leftFrontPod = new CoaxialPod(
                hardwareMap, "leftFront", "leftFrontSteer", "leftFrontEncoder",
                turnPIDF,
                LEFT_FRONT_DRIVE_REVERSED ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD,
                LEFT_FRONT_STEER_REVERSED ? CRServo.Direction.REVERSE : CRServo.Direction.FORWARD,
                Math.toRadians(LEFT_FRONT_OFFSET_DEG),
                new Pose(halfLength, halfWidth),
                ANALOG_MIN_VOLTAGE, ANALOG_MAX_VOLTAGE,
                LEFT_FRONT_ENCODER_REVERSED
        );

        leftBackPod = new CoaxialPod(
                hardwareMap, "leftBack", "leftBackSteer", "leftBackEncoder",
                turnPIDF,
                LEFT_BACK_DRIVE_REVERSED ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD,
                LEFT_BACK_STEER_REVERSED ? CRServo.Direction.REVERSE : CRServo.Direction.FORWARD,
                Math.toRadians(LEFT_BACK_OFFSET_DEG),
                new Pose(-halfLength, halfWidth),
                ANALOG_MIN_VOLTAGE, ANALOG_MAX_VOLTAGE,
                LEFT_BACK_ENCODER_REVERSED
        );

        rightFrontPod = new CoaxialPod(
                hardwareMap, "rightFront", "rightFrontSteer", "rightFrontEncoder",
                turnPIDF,
                RIGHT_FRONT_DRIVE_REVERSED ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD,
                RIGHT_FRONT_STEER_REVERSED ? CRServo.Direction.REVERSE : CRServo.Direction.FORWARD,
                Math.toRadians(RIGHT_FRONT_OFFSET_DEG),
                new Pose(halfLength, -halfWidth),
                ANALOG_MIN_VOLTAGE, ANALOG_MAX_VOLTAGE,
                RIGHT_FRONT_ENCODER_REVERSED
        );

        rightBackPod = new CoaxialPod(
                hardwareMap, "rightBack", "rightBackSteer", "rightBackEncoder",
                turnPIDF,
                RIGHT_BACK_DRIVE_REVERSED ? DcMotorSimple.Direction.REVERSE : DcMotorSimple.Direction.FORWARD,
                RIGHT_BACK_STEER_REVERSED ? CRServo.Direction.REVERSE : CRServo.Direction.FORWARD,
                Math.toRadians(RIGHT_BACK_OFFSET_DEG),
                new Pose(-halfLength, -halfWidth),
                ANALOG_MIN_VOLTAGE, ANALOG_MAX_VOLTAGE,
                RIGHT_BACK_ENCODER_REVERSED
        );
    }

    public SwervePod[] pods() {
        return new SwervePod[]{leftFrontPod, rightFrontPod, leftBackPod, rightBackPod};
    }
}
