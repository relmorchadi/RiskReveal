package com.scor.rr.service.condition;

/**
 * Condition Root
 * 
 * @author HADDINI Zakariyae
 *
 */
public class ConditionRoot {
	
	private String root;
	private ConditionExpression conditionExpression;

	public ConditionRoot() {
		super();
	}

	public ConditionRoot(String root, ConditionExpression ce) {
		super();
		this.root = root;
		this.conditionExpression = ce;
	}

	public String getRoot() {
		return root;
	}

	public void setRoot(String root) {
		this.root = root;
	}

	public ConditionExpression getConditionExpression() {
		return conditionExpression;
	}

	public void setConditionExpression(ConditionExpression conditionExpression) {
		this.conditionExpression = conditionExpression;
	}

	public boolean checkCondition(ConditionColumnExtractor checker) {
		return this.conditionExpression.checkCondition(checker);
	}
	
}
