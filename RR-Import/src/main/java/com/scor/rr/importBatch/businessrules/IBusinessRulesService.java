package com.scor.rr.importBatch.businessrules;

/**
 * Rules Services
 * @author Noureddine
 *
 */
public interface IBusinessRulesService {
	
	 /**
	  * Run a named business rule
	  * @param model object model
	  * @param ruleName the rule's name
	  */
	 void runRuleByName(Object model, String ruleName);
	 
	 /**
	  * Invoke a business rule
	  * @param model object model
	  */
	 void invokeRule(Object model);
	 
}
