package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Shooter;

public class TeleOpShooter extends CommandBase {

    private Shooter shooter;
    private double rpm;

    public TeleOpShooter(Shooter shooter, double rpm){
        this.shooter = shooter;
        this.rpm = rpm;
    }

    public TeleOpShooter(Shooter shooter){
        this.shooter = shooter;
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        shooter.setDynamicRpm();
        SmartDashboard.putNumber("Current Output 1", shooter.sM1.getOutputCurrent());
        SmartDashboard.putNumber("Current Output 2", shooter.sM2.getOutputCurrent());
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setShooterPower(0);
        SmartDashboard.putNumber("Current Output 1", 0);
        SmartDashboard.putNumber("Current Output 2", 0);
    }

    @Override
    public boolean isFinished() {
        return Math.abs(rpm - shooter.getVelocity()) < (.05 *rpm);
    }


}