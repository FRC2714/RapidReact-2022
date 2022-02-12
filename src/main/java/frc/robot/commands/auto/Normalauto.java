package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;
import frc.robot.utils.*;

import java.util.List;

public class Normalauto extends SequentialCommandGroup {

    public Normalauto(Drivetrain drivetrain){
        new Rotation2d();
        new Rotation2d();
        CustomRamseteCommand testSpline =
            RamseteGenerator.getRamseteCommand(
            drivetrain,
            List.of(
                new Pose2d(Units.feetToMeters(9.93), Units.feetToMeters(6.45), Rotation2d.fromDegrees(0.00)),
                new Pose2d(Units.feetToMeters(11.50), Units.feetToMeters(23.00), Rotation2d.fromDegrees(0.00))
            ),
            Units.feetToMeters(3), Units.feetToMeters(3), true
        );
                addCommands(
                    sequence(
                        new InstantCommand(() -> drivetrain.resetOdometry(testSpline.getInitialPose())),
                        testSpline.andThen(() -> drivetrain.tankDriveVolts(0,0))
                    )
                );
    }
}