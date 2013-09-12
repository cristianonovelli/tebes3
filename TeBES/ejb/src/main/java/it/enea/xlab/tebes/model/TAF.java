package it.enea.xlab.tebes.model;

import it.enea.xlab.tebes.entity.Action;
import it.enea.xlab.tebes.entity.Input;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

public class TAF {

	private String name;
	private Target target;
	private TestRule predicate;
	private Vector<Action> tests;
	private boolean skipTurnedON;
	private String prescription;
	private Hashtable<String, ReportFragment> reportFragments;
	private String note;
	private List<Input> inputs;
	
	public TAF() {
		
	}


	public TAF(String name, Target target, TestRule predicate, Vector<Action> prerequisites,
			boolean skipTurnedON, String prescription,
			Hashtable<String, ReportFragment> reportFragments, List<Input> inputs, String note) {

		this.name = name;
		this.target = target;
		this.predicate = predicate;
		this.tests = prerequisites;
		this.skipTurnedON = skipTurnedON;
		this.prescription = prescription;
		this.reportFragments = reportFragments;
		this.note = note;
		this.setInputs(inputs);
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public boolean isSkipTurnedON() {
		return skipTurnedON;
	}

	public void setSkipTurnedON(boolean skipTurnedON) {
		this.skipTurnedON = skipTurnedON;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public TestRule getPredicate() {
		return predicate;
	}

	public void setPredicate(TestRule predicate) {
		this.predicate = predicate;
	}

	public Vector<Action> getTests() {
		return tests;
	}

	public void setTests(Vector<Action> tests) {
		this.tests = tests;
	}

	public String getPrescription() {
		return prescription;
	}

	public void setPrescription(String prescription) {
		this.prescription = prescription;
	}

	public Hashtable<String, ReportFragment> getReportFragments() {
		return reportFragments;
	}

	public void setReportFragments(Hashtable<String, ReportFragment> reportFragments) {
		this.reportFragments = reportFragments;
	}


	public List<Input> getInputs() {
		return inputs;
	}


	public void setInputs(List<Input> inputs) {
		this.inputs = inputs;
	}

}
