package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;
import frc.robot.utils.*;

import java.util.List;

public class StraightLineTest extends SequentialCommandGroup {

    public StraightLineTest(Drivetrain drivetrain){
        CustomRamseteCommand StraightLine =
            RamseteGenerator.getRamseteCommand(
            drivetrain,
            List.of(
                new Pose2d(Units.feetToMeters(34.882), Units.feetToMeters(14.484), Rotation2d.fromDegrees(0.00)),
                new Pose2d(Units.feetToMeters(24), Units.feetToMeters(14.484), Rotation2d.fromDegrees(0.00))
            ),
            Units.feetToMeters(3), Units.feetToMeters(3), true
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