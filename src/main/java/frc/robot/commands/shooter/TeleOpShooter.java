package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class TeleOpShooter extends CommandBase {

    private Shooter shooter;
    private double rpm;
    private ShooterType shooterSpeed;

    public TeleOpShooter(Shooter shooter, ShooterType shooterSpeed){
        this.shooter = shooter;
        this.shooterSpeed = shooterSpeed;

    }
    
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        switch(shooterSpeed){
            case CLOSE:
                shooter.closeShot();
                break;
            case MID:
                shooter.midShot();
                break;
            case FAR:
                shooter.longShot();
                break;
            case DISABLE:
                shooter.disable();
                break;
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setShooterPower(0);
        shooter.disable();
        SmartDashboard.putNumber("Current Output 1", 0);
        SmartDashboard.putNumber("Current Output 2", 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(rpm - shooter.getVelocity()) < (.05 *rpm);
    }
    
    public enum ShooterType{
        CLOSE,
        MID,
        FAR,
        DISABLE
    }

}