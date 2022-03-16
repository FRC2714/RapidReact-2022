package frc.robot.commands.Index;

import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Tower;

public class AutoShotMid {
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
            tower.setBothTowerPower(0.25);
            serializer.serializeBalls();
        }
    }   

    public void end(){
        shooter.disable();
        tower.disable();
        serializer.disable();
    }
}
