package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;
import frc.robot.utils.*;

import java.util.List;

public class SplineTest extends SequentialCommandGroup {

    public SplineTest(Drivetrain drivetrain){
        CustomRamseteCommand StraightLine =
            RamseteGenerator.getRamseteCommand(
            drivetrain,
            List.of(
                new Pose2d(Units.feetToMeters(31), Units.feetToMeters(12), Rotation2d.fromDegrees(0.00)),
                new Pose2d(Units.feetToMeters(37), Units.feetToMeters(5), Rotation2d.fromDegrees(0.00))
            ),
            Units.feetToMeters(3), Units.feetToMeters(3), false
        );
            addCommands(
                sequence(
                 new InstantCommand(() -> drivetrain.resetOdometry(StraightLine.getInitialPose())),
                 StraightLine.andThen(() -> drivetrain.tankDriveVolts(0, 0)
                 )
        )
    );
    }
}