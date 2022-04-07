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

public class TwoBallAuto extends SequentialCommandGroup {

	public TwoBallAuto(Drivetrain drivetrain, Shooter shooter, Intake intake, Serializer serializer, Tower tower, Limelight limelight) {
		CustomRamseteCommand splineToBallOne =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(22.4), Units.feetToMeters(14), Rotation2d.fromDegrees(135)),
					new Pose2d(Units.feetToMeters(19.4), Units.feetToMeters(16.9), Rotation2d.fromDegrees(135))
				),
				Units.feetToMeters(9), Units.feetToMeters(6), false
			);
		
			CustomRamseteCommand splineToBallTwo =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(19.4), Units.feetToMeters(16.9), Rotation2d.fromDegrees(135)),
					new Pose2d(Units.feetToMeters(15.7), Units.feetToMeters(20.8), Rotation2d.fromDegrees(135))
				),
				Units.feetToMeters(6), Units.feetToMeters(3), false
			);
		addCommands(
			sequence(
				//reset odometry
				new InstantCommand(() -> drivetrain.resetOdometry(splineToBallOne.getInitialPose())),
				splineToBallOne,
				//Intake first ball
				deadline(
					splineToBallTwo.andThen(() -> drivetrain.tankDriveVolts(0,0)),
					new AutoIntake(intake, tower, serializer)
				),
				//Align and Shoot
				new CustomAlignToTarget(drivetrain, limelight, true).withTimeout(2),
				new AutoShotMid(shooter, tower, serializer).withTimeout(3)
				
			)

		);
	}

}