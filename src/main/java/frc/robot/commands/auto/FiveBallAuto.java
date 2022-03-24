package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Index.AutoShotMid;
import frc.robot.commands.Intake.AutoIntake;
import frc.robot.commands.drivetrain.CustomAlignToTarget;
import frc.robot.subsystems.*;
import frc.robot.utils.*;

import java.util.List;

public class FiveBallAuto extends SequentialCommandGroup {

	public FiveBallAuto(Drivetrain drivetrain, Shooter shooter, Intake intake, Serializer serializer, Tower tower, Limelight limelight) {
		CustomRamseteCommand splineToBallOne =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(28.0), Units.feetToMeters(6.0), Rotation2d.fromDegrees(-90)),
					new Pose2d(Units.feetToMeters(25.4), Units.feetToMeters(1.7), Rotation2d.fromDegrees(-175))
				),
				Units.feetToMeters(9), Units.feetToMeters(6), false
			);
		CustomRamseteCommand splineToBallTwo =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(25.4), Units.feetToMeters(1.7), Rotation2d.fromDegrees(-175)),
					new Pose2d(Units.feetToMeters(16.7), Units.feetToMeters(6.2), Rotation2d.fromDegrees(-145))
				),
				Units.feetToMeters(9), Units.feetToMeters(6), false
			);
		CustomRamseteCommand splineToHuman =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(16.7), Units.feetToMeters(6.2), Rotation2d.fromDegrees(-145)),
					new Pose2d(Units.feetToMeters(4.3), Units.feetToMeters(4.6), Rotation2d.fromDegrees(-135))
				),
				Units.feetToMeters(9), Units.feetToMeters(6), false
			);
      CustomRamseteCommand splineToShot =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(4.3), Units.feetToMeters(4.6), Rotation2d.fromDegrees(-135)),
					new Pose2d(Units.feetToMeters(16.7), Units.feetToMeters(6.2), Rotation2d.fromDegrees(-145))
				),
				Units.feetToMeters(9), Units.feetToMeters(6), true
			);
		addCommands(
			sequence(
				//reset odometry
				new InstantCommand(() -> drivetrain.resetOdometry(splineToBallOne.getInitialPose())),

				//shoot preloaded ball
				new AutoShotMid(shooter, tower, serializer).withTimeout(1),

				//Run intake and move to first ball
				deadline(
					splineToBallOne,
					new AutoIntake(intake, tower, serializer)
				),
				//Run intake and move to second ball
				deadline(
					splineToBallTwo,
					new AutoIntake(intake, tower, serializer)
				),
				//Align and shoot two balls
				new CustomAlignToTarget(drivetrain, limelight, true).withTimeout(1),
				new AutoShotMid(shooter, tower, serializer).withTimeout(3),

				//Move to human player
				deadline(
					splineToHuman
				),
				//Wait for human player
				new AutoIntake(intake, tower, serializer).withTimeout(2),

				//Return to shooting position
				deadline(
					splineToShot
				),
				//Align and shoot two balls
				new CustomAlignToTarget(drivetrain, limelight, true).withTimeout(1),
				new AutoShotMid(shooter, tower, serializer).withTimeout(3)

			)

		);
	}

}