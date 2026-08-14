package org.firstinspires.ftc.teamcode.Swerve;

import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstant.LEFT_BACK_OFFSET_DEG;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstant.LEFT_BACK_STEER_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstant.LEFT_FRONT_OFFSET_DEG;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstant.LEFT_FRONT_STEER_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstant.RIGHT_BACK_OFFSET_DEG;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstant.RIGHT_BACK_STEER_REVERSED;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstant.RIGHT_FRONT_OFFSET_DEG;
import static org.firstinspires.ftc.teamcode.Swerve.SwerveConstant.RIGHT_FRONT_STEER_REVERSED;

import org.firstinspires.ftc.teamcode.hardware.hackingHoundsHardware;

public class SwerveDrivetain {

    public final SwerveDrivePod leftFrontPod, rightFrontPod, leftBackPod, rightBackPod;

    public SwerveDrivetain(hackingHoundsHardware robot) {
        leftFrontPod = new SwerveDrivePod(
                robot.leftFront,
                robot.leftFrontSteer,
                robot.leftFrontEncoder,
                LEFT_FRONT_OFFSET_DEG,
                LEFT_FRONT_STEER_REVERSED
        );
        leftBackPod = new SwerveDrivePod(
                robot.leftBack,
                robot.leftBackSteer,
                robot.leftBackEncoder,
                LEFT_BACK_OFFSET_DEG,
                RIGHT_FRONT_STEER_REVERSED
        );
        rightFrontPod = new SwerveDrivePod(
                robot.rightFront,
                robot.rightFrontSteer,
                robot.rightFrontEncoder,
                RIGHT_FRONT_OFFSET_DEG,
                LEFT_BACK_STEER_REVERSED
        );

        rightBackPod = new SwerveDrivePod(
                robot.rightBack,
                robot.rightBackSteer,
                robot.rightBackEncoder,
                RIGHT_BACK_OFFSET_DEG,
                RIGHT_BACK_STEER_REVERSED
        );
    }
}
