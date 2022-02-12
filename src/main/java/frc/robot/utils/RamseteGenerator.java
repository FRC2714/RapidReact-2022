package frc.robot.utils;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.trajectory.Trajectory;
import edu.wpi.first.math.trajectory.TrajectoryConfig;
import edu.wpi.first.math.trajectory.TrajectoryGenerator;
import edu.wpi.first.math.trajectory.constraint.CentripetalAccelerationConstraint;
import edu.wpi.first.math.trajectory.constraint.DifferentialDriveVoltageConstraint;
import edu.wpi.first.math.util.Units;
import frc.robot.Constants;
import frc.robot.subsystems.Drivetrain;

import java.util.List;

public class RamseteGenerator {
    private static TrajectoryConfig getConfig(double maxVel, double maxA, boolean isReversed) {

        return new TrajectoryConfig(maxVel, maxA)
                // Add kinematics to ensure max speed is actually obeyed
                .setKinematics(Constants.DriveConstants.kDriveKinematics)
                // Apply the voltage constraint
                .addConstraint(new DifferentialDriveVoltageConstraint(
                        new SimpleMotorFeedforward(
                                Constants.DriveConstants.kStatic,
                                Constants.DriveConstants.kV,
                                Constants.DriveConstants.kA
                        ),
                        Constants.DriveConstants.kDriveKinematics,
                        11
                ))
                .addConstraint(

                        new CentripetalAccelerationConstraint(Units.feetToMeters(4.5))

                ).setReversed(isReversed);

    }

    public static CustomRamseteCommand getRamseteCommand(Drivetrain drivetrain,
                                                         Pose2d startPose,
                                                         List<Translation2d> internalPoints,
                                                         Pose2d endPose,
                                                         double velocity, double acceleration, boolean isReversed) {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                startPose,

                internalPoints,

                endPose,

                getConfig(velocity, acceleration, isReversed)
        );

        return getCustomRamseteCommand(drivetrain, trajectory);
    }


    public static CustomRamseteCommand getRamseteCommand(Drivetrain drivetrain,
                                                         List<Pose2d> waypoints,
                                                         double velocity, double acceleration, boolean isReversed) {
        Trajectory trajectory = TrajectoryGenerator.generateTrajectory(
                waypoints,
                getConfig(velocity, acceleration, isReversed)
        );

        return getCustomRamseteCommand(drivetrain, trajectory);
    }

    private static CustomRamseteCommand getCustomRamseteCommand(Drivetrain drivetrain, Trajectory trajectory) {
        return new CustomRamseteCommand(
                trajectory,
                drivetrain::getPose,
                new RamseteController(Constants.DriveConstants.kRamseteB, Constants.DriveConstants.kRamseteZeta),
                new SimpleMotorFeedforward(
                        Constants.DriveConstants.kStatic,
                        Constants.DriveConstants.kV,
                        Constants.DriveConstants.kA
                ),
                Constants.DriveConstants.kDriveKinematics,
                drivetrain::getWheelSpeeds,
                new PIDController(Constants.DriveConstants.kDriveP, 0, Constants.DriveConstants.kDriveD),
                new PIDController(Constants.DriveConstants.kDriveP, 0, Constants.DriveConstants.kDriveD),
                drivetrain::tankDriveVolts,
                drivetrain
        );
    }

}
