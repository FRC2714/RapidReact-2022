package frc.robot.commands.auto;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.SynchronousInterrupt.WaitResult;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.commands.Index.Shot;
import frc.robot.commands.Intake.IntakeCommand;
import frc.robot.commands.shooter.TeleOpShooter;
import frc.robot.commands.shooter.TeleOpShooter.ShooterType;
import frc.robot.subsystems.*;
import frc.robot.utils.*;



public class OneBallAuton extends SequentialCommandGroup{

  private Drivetrain drivetrain;


  public OneBallAuton(Drivetrain drivetrain){ 
    this.drivetrain = drivetrain; 
    new InstantCommand(() -> drivetrain.tankDriveVolts(0.5, 0.5)).withTimeout(1);
  }
}