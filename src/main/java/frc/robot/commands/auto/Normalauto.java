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
                new Pose2d(Units.feetToMeters(29.24), Units.feetToMeters(20.04), Rotation2d.fromDegrees(0.00)),
                new Pose2d(Units.feetToMeters(44.99), Units.feetToMeters(22.85), Rotation2d.fromDegrees(0.00))
            ),
            Units.feetToMeters(10), Units.feetToMeters(10), true
        );
                addCommands(
                    sequence(
                       // hopefully the code for shooter here new AutomaticShoot(shooter, conveyor, intake, 2620, false, 3), //was 2300
                    ),
                    deadline(
                        new InstantCommand(() -> drivetrain.resetOdometry(testSpline.getInitialPose())),
                        testSpline.andThen(() -> drivetrain.tankDriveVolts(0,0))
                    )
                );
    }
}