 package frc.robot.commands.Intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Index;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Serializer;

public class IntakeCommand extends CommandBase{

    private Intake intake; 
    private Serializer serializer;
    private IntakeType intakeType;


    
    public IntakeCommand(Intake intake, IntakeType intakeType, Serializer serializer){
        this.intake = intake;
        this.intakeType = intakeType;
        this.serializer = serializer;
    }


    @Override 
    public void execute (){
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

            case DISABLE: 
            intake.disable();
            serializer.disable();
            Index.setIndexState(Index.IndexState.DEFAULT);
            break; 
        }

        //intake.intakeBalls();
        System.out.println("Set motor to 0.5");
        System.out.println("Get output - " + intake.intakeMotor.getAppliedOutput());
    }


    @Override
    public void end(boolean interrupted) {
        super.end(interrupted);

        intake.disable();
        serializer.disable();
    }

    


    public enum IntakeType{
        EXTAKE,
        INTAKE,
        DISABLE,
        SpinIntake
    }

}