package frc.robot.commands.Index;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Tower;

public class AutoShotClose extends CommandBase {
    private Shooter shooter;
    private Serializer serializer;
    private Tower tower;
    
    public AutoShotClose(Shooter shooter, Serializer serializer, Tower tower){
        this.shooter = shooter;
        this.serializer = serializer;
        this.tower = tower;
    }

    public void execute(){
        shooter.closeShot();
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
