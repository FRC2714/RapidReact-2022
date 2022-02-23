package frc.robot.commands.Index;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Serializer;
import frc.robot.subsystems.Shooter;
import frc.robot.subsystems.Index.IndexState;

public class SingleShot extends CommandBase {

    private Shooter shooter;
    private Index index;
    private Intake intake;
    private Serializer serializer;
    private IndexType indexType;

    public SingleShot(Shooter shooter, Intake intake, Index index, Serializer serializer, IndexType indexType){
        this.shooter = shooter;
        this.index = index;
        this.intake = intake;
        this.serializer = serializer; 
        this.indexType = indexType;
    }

    @Override
    public void initialize() {
        switch(indexType){
            case LOADING:
                serializer.serializeBalls();
                index.setIndexState(Index.IndexState.LOADING);
                index.isStored();
                if(index.ballStored) break;
            case MULTISHOT:
                //set shooter to target rpm
                //AlignToTarget
                if(index.ballStored||shooter.atSetpoint()){
                    index.setIndexState(IndexState.SHOOTING);
                    if(!index.ballStored){
                        index.setIndexState(IndexState.DEFAULT);
                        break;
                    }
                }
                else if(shooter.atSetpoint()){
                    //set index type to Index
                }
                else if(index.ballStored){
                    //wait?
                }
                else{
                    //What should I do here? 
                }
            case SINGLESHOT:
                //set shooter to targetRPM
                //AlignToTarget
                if(shooter.atSetpoint()){
                    //shoot
                    break;
                }
            case INDEX:
                serializer.serializeBalls();
                index.setIndexState(Index.IndexState.LOADING);
                index.isStored();
                if(index.ballStored){
                    //sets index type to multi-shot
                    break; 
                }    
        }
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        intake.disbale();
        shooter.disable();
        index.disable();
        index.setIndexState(Index.IndexState.DEFAULT);
    }

    public enum IndexType{
        LOADING,
        MULTISHOT,
        SINGLESHOT,
        INDEX
    }
}
