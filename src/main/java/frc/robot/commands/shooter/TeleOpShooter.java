package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Tower;

public class TeleOpShooter extends CommandBase {

    private Shooter shooter;
    private ShooterType shooterSpeed;
    private Tower tower;
    private Serializer serializer;

    public TeleOpShooter(Shooter shooter, ShooterType shooterSpeed, Tower tower, Serializer serializer){
        this.shooter = shooter;
        this.shooterSpeed = shooterSpeed;
        this.tower = tower; 
        this.serializer = serializer;

    }
    
    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        switch(shooterSpeed){
            case CLOSE:
                shooter.closeShot();
                if(shooter.atSetpoint()){
                    tower.setBothTowerPower(1);
                    serializer.serializeBalls();
                }
                else{
                    tower.disable();
                    serializer.disable();
                }
                System.out.println(shooter.getVelocity());
                break;
            case MID:
                shooter.midShot();
                if(shooter.atSetpoint()){
                    tower.setBothTowerPower(1);
                    serializer.serializeBalls();
                }else{
                    tower.disable();
                    serializer.disable();
                }
                System.out.println(shooter.getVelocity());
                break;
            case FAR:
                shooter.longShot();
                if(shooter.atSetpoint()){
                    tower.setBothTowerPower(1);
                    serializer.serializeBalls();
                }else{
                    tower.disable();
                    serializer.disable();
                }
                System.out.println(shooter.getVelocity());
                break;
            case DISABLE:
                shooter.disable();
                tower.disable();
                serializer.disable();
                break;
        }
        
    }

    @Override
    public void end(boolean interrupted) {
        shooter.setShooterPower(0);
        shooter.disable();
        tower.disable();
        serializer.disable();
        SmartDashboard.putNumber("Current Output 1", 0);
        SmartDashboard.putNumber("Current Output 2", 0);
        System.out.println("Shooter Disabled");
    }
    
    public enum ShooterType{
        CLOSE,
        MID,
        FAR,
        DISABLE
    }

}