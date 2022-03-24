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

public class OneBallAuto extends SequentialCommandGroup {

	public OneBallAuto(Drivetrain drivetrain, Shooter shooter, Intake intake, Serializer serializer, Tower tower) {
		CustomRamseteCommand splineToOut =
			RamseteGenerator.getRamseteCommand(
				drivetrain,
				List.of(
					new Pose2d(Units.feetToMeters(19.4), Units.feetToMeters(16.4), Rotation2d.fromDegrees(161)),
					new Pose2d(Units.feetToMeters(14.1), Units.feetToMeters(18.5), Rotation2d.fromDegrees(150))
				),
				Units.feetToMeters(9), Units.feetToMeters(6), false
			);
		
		addCommands(
			sequence(
				//reset odometry
				new InstantCommand(() -> drivetrain.resetOdometry(splineToOut.getInitialPose())),
				new AutoShotMid(shooter, tower, serializer).withTimeout(5),
				splineToOut
			)

		);
	}

}