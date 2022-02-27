 package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Serializer;

public class IntakeCommand extends CommandBase{

    private Intake intake; 
    private IntakeType intakeType;
    private Serializer serializer;

    
    public IntakeCommand(Intake intake, IntakeType intakeType, Serializer serializer){
        this.intake = intake;
        this.intakeType = intakeType;
        this.serializer = serializer;

   
    }


    @Override 

    public void initialize (){
        switch(intakeType){
            case EXTAKE:
            intake.extakeBalls(); 
            serializer.unCerealBalls();
            Index.setIndexState(Index.IndexState.EXTAKING); 
            break;
            
            case INTAKE:
            intake.intakeBalls();  
            serializer.serializeBalls();          
            Index.setIndexState(Index.IndexState.INTAKING);
            break;
        }
    }

    


    public enum IntakeType{
        EXTAKE,
        INTAKE
    }

}