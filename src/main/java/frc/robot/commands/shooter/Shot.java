package frc.robot.commands.shooter;

import java.util.Set;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Tower;

public class Shot implements Command {
    private Shooter shooter;
    private Serializer serializer;
    private Tower tower;
    private ShotMode shotMode;

    public Shot(Shooter shooter, Serializer serializer, Tower tower, ShotMode shotMode){
        this.shooter = shooter;
        this.serializer = serializer;
        this.tower = tower; 
        this.shotMode = shotMode;
    }

    public enum ShotMode{
        CLOSE,
        MID,
        FAR
    }



    public void execute(){
        switch(shotMode){
            case CLOSE:
            shooter.closeShot();
            if(shooter.atSetpoint()){
                tower.setBothTowerPower(1);
                serializer.serializeBalls();
            }
            break;
            case MID:
            shooter.midShot();
            if(shooter.atSetpoint()){
                tower.setBothTowerPower(1);
                serializer.serializeBalls();
            }
            break;
            case FAR:
            shooter.midShot();
            if(shooter.atSetpoint()){
                tower.setBothTowerPower(1);
                serializer.serializeBalls();
            }
            break;
        }
    }

    public void end(){
        shooter.disable();
        tower.disable();
        serializer.disable();
    }

    @Override
    public Set<Subsystem> getRequirements() {
        // TODO Auto-generated method stub
        return null;
    }
}
