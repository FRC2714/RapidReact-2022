package frc.robot.commands.Index;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Tower;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;

public class Shot extends CommandBase {

    private Tower tower;
    private IndexType indexType;
    private Serializer serializer;
    private Shooter shooter;

    public Shot(Tower tower, IndexType indexType, Serializer serializer, Shooter shooter){
        this.tower = tower;
        this.indexType = indexType;
        this.serializer = serializer;
        this.shooter = shooter;
    }

    @Override
    public void initialize() {
    }

    @Override
    public void execute() {
        switch(indexType){
            case SHOT:
            tower.setBothTowerPower(0.5);
            serializer.serializeBalls();
            break;
            case EXTAKE:
            tower.setBothTowerPower(-1);
        }
    }

    @Override
    public void end(boolean interrupted) {
        tower.disable();
        serializer.disable();
    }

    public enum IndexType {
        SHOT,
        EXTAKE
    }

}
