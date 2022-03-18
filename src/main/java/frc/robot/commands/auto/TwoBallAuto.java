package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.Index.AutoShotMid;
import frc.robot.commands.Intake.AutoIntake;
import frc.robot.subsystems.*;
import frc.robot.utils.*;

import java.util.List;

public class TwoBallAuto extends SequentialCommandGroup {

	public TwoBallAuto(Drivetrain drivetrain, Shooter shooter, Intake intake, Serializer serializer, Tower tower) {
		CustomRamseteCommand splineToBallOne =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(30.7), Units.feetToMeters(11.2), Rotation2d.fromDegrees(-22)),
					new Pose2d(Units.feetToMeters(38.6), Units.feetToMeters(8.1), Rotation2d.fromDegrees(-32))
				),
				Units.feetToMeters(9), Units.feetToMeters(6), false
			);
		addCommands(
			sequence(
				//reset odometry
				new InstantCommand(() -> drivetrain.resetOdometry(splineToBallOne.getInitialPose())),

				deadline(
					//Run intake and vove to first ball
					splineToBallOne,
					new AutoIntake(intake, tower, serializer)

				),
				deadline(
					//Run shooter for 0.5s
					new AutoShotMid(shooter, tower, serializer).withTimeout(2.5)
				)
			)

		);
	}

}