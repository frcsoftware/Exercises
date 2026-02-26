package frc.robot.internals;

import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.hardware.TalonFXS;
import com.revrobotics.sim.SparkFlexSim;
import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.spark.SparkBase;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkSim;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;

/**
 * If you are trying to edit this file, you have come to the wrong place.
 * Everything you need is within the Robot.java file.
 */
public class Internals {
    public static void initialize(Robot robot, Object root, String path) throws IllegalAccessException {
        for (var field: root.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            var fullPath = path + "/" + field.getName();
            if (SparkBase.class.isAssignableFrom(field.getType())) {
                var motor = (SparkBase) field.get(robot);
                robot.addPeriodic(new SparkVisualizer(fullPath, motor)::update, 0.02);
            } else if (TalonFX.class.isAssignableFrom(field.getType())) {
                var motor = (TalonFX) field.get(robot);
                robot.addPeriodic(new TalonFXVisualizer(fullPath, motor)::update, 0.02);
            } else if (TalonFXS.class.isAssignableFrom(field.getType())) {
                var motor = (TalonFXS) field.get(robot);
                robot.addPeriodic(new TalonFXSVisualizer(fullPath, motor)::update, 0.02);
            } else if (field.getType().getPackageName().contains("frc")) {
                initialize(robot, field.get(root), fullPath);
            }
        }
    }

    private static class TalonFXVisualizer {
        private final String name;
        private final TalonFX motor;
        private final DCMotorSim physicsSim = new DCMotorSim(
            LinearSystemId.createDCMotorSystem(DCMotor.getKrakenX60(1), 0.001, 1),
            DCMotor.getKrakenX60(1)
        );

        public TalonFXVisualizer(String name, TalonFX motor) {
            this.name = name;
            this.motor = motor;
        }

        public void update() {
            physicsSim.update(0.02);
            physicsSim.setInputVoltage(motor.getSimState().getMotorVoltage());
            motor.getSimState().setRotorVelocity(physicsSim.getAngularVelocity());
            motor.getSimState().setRawRotorPosition(physicsSim.getAngularPosition());
            SmartDashboard.putNumber(name + "/Velocity (Rad per Sec)", physicsSim.getAngularVelocityRadPerSec());
        }
    }

    private static class SparkVisualizer {
        private final String name;
        private final SparkSim simState;
        private final DCMotorSim physicsSim = new DCMotorSim(
            LinearSystemId.createDCMotorSystem(DCMotor.getNEO(1), 0.001, 1),
            DCMotor.getNEO(1)
        );

        public SparkVisualizer(String name, SparkBase motor) {
            this.name = name;
            this.simState = motor instanceof SparkMax
                ? new SparkMaxSim((SparkMax) motor, DCMotor.getNEO(1))
                : new SparkFlexSim((SparkFlex) motor, DCMotor.getNeoVortex(1));
        }

        public void update() {
            physicsSim.update(0.02);
            physicsSim.setInputVoltage(simState.getAppliedOutput() * simState.getBusVoltage());
            simState.setPosition(physicsSim.getAngularPositionRotations());
            simState.iterate(physicsSim.getAngularVelocityRPM(), 12, 0.02);
            SmartDashboard.putNumber(name + "/Velocity (Rad per Sec)", physicsSim.getAngularVelocityRadPerSec());
        }
    }

    private static class TalonFXSVisualizer {
        private final String name;
        private final TalonFXS motor;
        private final DCMotorSim physicsSim = new DCMotorSim(
            LinearSystemId.createDCMotorSystem(DCMotor.getKrakenX60(1), 0.001, 1),
            DCMotor.getKrakenX60(1)
        );

        public TalonFXSVisualizer(String name, TalonFXS motor) {
            this.name = name;
            this.motor = motor;
        }

        public void update() {
            physicsSim.update(0.02);
            physicsSim.setInputVoltage(motor.getSimState().getMotorVoltage());
            motor.getSimState().setRotorVelocity(physicsSim.getAngularVelocity());
            motor.getSimState().setRawRotorPosition(physicsSim.getAngularPosition());
            SmartDashboard.putNumber(name + "/Velocity (Rad per Sec)", physicsSim.getAngularVelocityRadPerSec());
        }
    }
}
