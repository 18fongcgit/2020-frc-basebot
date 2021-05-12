package org.team1619.behavior;

import org.uacr.models.behavior.Behavior;
import org.uacr.shared.abstractions.InputValues;
import org.uacr.shared.abstractions.OutputValues;
import org.uacr.shared.abstractions.RobotConfiguration;
import org.uacr.utilities.Config;
import org.uacr.utilities.logging.LogManager;
import org.uacr.utilities.logging.Logger;

import java.util.Set;

/**
 * Drives the robot in percent mode, based on the joystick values.
 */

public class DriveTrain_Percent implements Behavior {

	private static final Logger sLogger = LogManager.getLogger(DriveTrain_Percent.class);
	private static final Set<String> sSubsystems = Set.of("ss_drivetrain");

	private final InputValues fSharedInputValues;
	private final OutputValues fSharedOutputValues;
	private final String fYAxis;


	private String mStateName;

	public DriveTrain_Percent(InputValues inputValues, OutputValues outputValues, Config config, RobotConfiguration robotConfiguration) {
		fSharedInputValues = inputValues;
		fSharedOutputValues = outputValues;
		fYAxis = robotConfiguration.getString("global_drivetrain", "y");


		mStateName = "DriveTrain";
	}

	@Override
	public void initialize(String stateName, Config config) {
		sLogger.debug("Entering state {}", stateName);

		mStateName = stateName;
	}

	@Override
	public void update() {
		double yAxis = fSharedInputValues.getNumeric(fYAxis);

		// Set the motor speed to the joystick values
		double motorSpeed = yAxis;




		// Set the motors
		fSharedOutputValues.setNumeric("opn_drivetrain_left", "percent", motorSpeed);
		fSharedOutputValues.setNumeric("opn_drivetrain_right", "percent", motorSpeed);

	}


	@Override
	public void dispose() {
		sLogger.trace("Leaving state {}", mStateName);
		fSharedOutputValues.setNumeric("opn_drivetrain_left", "percent", 0.0);
		fSharedOutputValues.setNumeric("opn_drivetrain_right", "percent", 0.0);

	}

	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public Set<String> getSubsystems() {
		return sSubsystems;
	}
}