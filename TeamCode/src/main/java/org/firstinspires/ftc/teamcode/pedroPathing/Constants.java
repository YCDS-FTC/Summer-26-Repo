package org.firstinspires.ftc.teamcode.pedroPathing;

import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PredictiveBrakingCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.SwerveConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Swerve.SwerveDrivetrain;

public class Constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(12.3771)
            .headingPIDFCoefficients(new PIDFCoefficients(0.45, 0, 0.025, 0.02))
            .predictiveBrakingCoefficients(new PredictiveBrakingCoefficients(0.2, 0.0721, 0.00175))
            .centripetalScaling(0);




    public static SwerveConstants swerveConstants = new SwerveConstants()
            .maxPower(1)
            .velocity(80)
            .useBrakeModeInTeleOp(true);



    public static PathConstraints pathConstraints = new PathConstraints(0.99, 100, 1, 1);

    public static PinpointConstants localizerConstants = new PinpointConstants()
            .forwardPodY(-3.64114173228)
            .strafePodX(-7.763661417)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.REVERSED)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);

    public static Follower createFollower(HardwareMap hardwareMap) {
        SwerveDrivetrain drivetrain = new SwerveDrivetrain(hardwareMap);
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pathConstraints(pathConstraints)
                .pinpointLocalizer(localizerConstants)
                .swerveDrivetrain(swerveConstants, drivetrain.pods())
                .build();
    }
}