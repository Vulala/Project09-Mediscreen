package com.abernathyclinic.mediscreen.model;

/**
 * Entity mixing a patient with his patient's note. <br>
 */
public class PatientAndPatientNote {

	private Patient patient;
	private PatientNote patientNote;

	public PatientAndPatientNote() {
	}

	public PatientAndPatientNote(Patient patient, PatientNote patientNote) {
		this.patient = patient;
		this.patientNote = patientNote;
	}

	public Patient getPatient() {
		return patient;
	}

	public void setPatient(Patient patient) {
		this.patient = patient;
	}

	public PatientNote getPatientNote() {
		return patientNote;
	}

	public void setPatientNote(PatientNote patientNote) {
		this.patientNote = patientNote;
	}

	@Override
	public String toString() {
		return "The patient's information with his patient's notes: [Patient:" + patient + ", Patient's note:"
				+ patientNote + "]";
	}

}
