package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class AutoIntake extends CommandBase {

    private TeleOpShooter shooter;
    private Intake intake;
    private SingleShot conveyor;
    private IntakeType intakeType;

    public AutoIntake(TeleOpShooter shooter, Intake intake, SingleShot conveyor, IntakeType intakeType){
        this.shooter = shooter;
        this.intake = intake;
        this.conveyor = conveyor;
        this.intakeType = intakeType;
    }

    @Override
    public void initialize() {
        conveyor.enable();
        switch (intakeType){
            case INTAKE:
                conveyor.setConveyorState(SingleShot.ConveyorState.INTAKING);
                intake.intakePowerCells();
                break;
            case EXTAKE:
                intake.extakePowerCells();
                conveyor.setConveyorState(SingleShot.ConveyorState.EXTAKING);
                break;
            case SHOOT:
                conveyor.setConveyorState(SingleShot.ConveyorState.SHOOTING);
                break;
            case FORCED_CONVEYOR_INTAKE:
                conveyor.setConveyorState(SingleShot.ConveyorState.FORCED_CONVEYOR_INTAKE);
                break;
            case FORCED_CONVEYOR_EXTAKE:
                conveyor.setConveyorState(SingleShot.ConveyorState.FORCED_CONVEYOR_EXTAKE);
                break;
            case UNJAM_STUCK_BALL:
                intake.setSerializerPower(-Constants.IntakeConstants.kSerializerPower);
                conveyor.setConveyorState(SingleShot.ConveyorState.EXTAKING);
            case UNRESTRICTED_SHOOT:
                conveyor.setConveyorState(SingleShot.ConveyorState.UNRESTRICTED_SHOOTING);
                break;
        }

        System.out.println("Auto Intake Triggered. Type -- " + intakeType);
    }

    @Override
    public void execute() {
        // logic handled in conveyor periodic
        if(intakeType.equals(IntakeType.SHOOT))
            conveyor.setConveyorState(SingleShot.ConveyorState.SHOOTING);
    }

    @Override
    public void end(boolean interrupted) {
        intake.disbale();
        conveyor.disable();
        conveyor.setConveyorState(SingleShot.ConveyorState.DEFAULT);
        System.out.println("Auto Intake Ended. Type -- " + intakeType);
    }

    public enum IntakeType{
        INTAKE,
        EXTAKE,
        SHOOT,
        UNRESTRICTED_SHOOT,
        FORCED_CONVEYOR_INTAKE,
        FORCED_CONVEYOR_EXTAKE,
        UNJAM_STUCK_BALL
    }

}

