package frc.robot.commands.drivetrain;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj2.command.ProfiledPIDCommand;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class CustomAlignToTarget extends ProfiledPIDCommand{
 
    private Limelight limelight;
    private Drivetrain drivetrain;
    private boolean isAutoEnabled = false;
    

    public CustomAlignToTarget(Drivetrain drivetrain, Limelight limelight, boolean isAutoEnabled){
       super(
            new ProfiledPIDController(0.031, 0, 0.0003,
                        new TrapezoidProfile.Constraints(100, 300)),
            limelight::getXAngleOffset,
            0,
            (output, setpoint) -> drivetrain.arcadeDrive(0, output),
            drivetrain
        );
        addRequirements(drivetrain);

        this.limelight = limelight;
        this.drivetrain = drivetrain;
        this.isAutoEnabled = isAutoEnabled;
        getController().enableContinuousInput(-180, 180);

        getController().setTolerance(.75,4);    
    }

    @Override
    public void initialize() {
        limelight.setLED(true);
    }

    @Override
    public boolean isFinished() {
        if(isAutoEnabled)
            return
                getController().atGoal() || !limelight.targetVisible();
        else
            return false;

    }
    @Override
    public void end(boolean interrupted){
        drivetrain.tankDriveVolts(0, 0);

        if(!isAutoEnabled)
            limelight.setLED(false);
    }
}

