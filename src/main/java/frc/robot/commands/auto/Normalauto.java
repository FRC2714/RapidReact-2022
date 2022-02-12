package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.*;
import frc.robot.utils.CustomRamseteCommand;
import frc.robot.utils.RamseteGenerator;

import java.util.List;

public class Normalauto extends SequentialCommandGroup {

    public Normalauto(Drivetrain drivetrain){
        new Rotation2d();
        new Rotation2d();
        CustomRamseteCommand testSpline =
                RamseteGenerator.getRamseteCommand(
                        drivetrain,
                  
                      List.of(
                                new Pose2d(Units.feetToMeters(11.89), Units.feetToMeters(24.65), Rotation2d.fromDegrees(0)),
                                new Pose2d(Units.feetToMeters(15.46), Units.feetToMeters(24.68), Rotation2d.fromDegrees(0))
                        ),
                        Units.feetToMeters(13), Units.feetToMeters(8), false
                );
                addCommands(
                    sequence(
                        new InstantCommand(() -> drivetrain.resetOdometry(testSpline.getInitialPose())),
                        testSpline.andThen(() -> drivetrain.tankDriveVolts(0,0))
                    )
                );
    }
}