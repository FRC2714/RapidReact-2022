package frc.robot.commands.Index;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Tower;

public class AutoShotMid extends CommandBase{
    private Shooter shooter;
    private Tower tower;
    private Serializer serializer;
    
    public AutoShotMid(Shooter shooter, Tower tower, Serializer serializer){
        this.shooter = shooter;
        this.tower = tower;
        this.serializer = serializer;
    }

    public void execute(){
        shooter.midShot();
        if(shooter.atSetpoint()){
            tower.setBothTowerPower(1);
         //   serializer.serializeBalls();
         System.out.println("Shooting");
        }
    }   

    @Override
    public void end(boolean interrupted){
        shooter.disable();
        tower.disable();
        serializer.disable();
    }
}
