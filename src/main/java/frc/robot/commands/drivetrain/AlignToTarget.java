package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj.controller.ProfiledPIDCommand;

public class AlignToTarget extends ProfiledPIDCommand{
    private Limelight limelight;
    private Drivetrain drivetrain;
    private boolean isAutoEnabled = false;

    public AlignToTarget(Drivetrain drivetrain, Limelight limelight){
        this.limelight = limelight;
        this.drivetrain = drivetrain;
    }

    public AlignToTarget(Drivetrain drivetrain, Limelight limelight, boolean isAutoEnabled){
        this.limelight = limelight;
        this.drivetrain = drivetrain;
        t
    }
}