package com.scor.rr.service.condition;

import com.scor.rr.utils.ALMFUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Condition Expression
 *
 * @author HADDINI Zakariyae
 *
 */
public abstract class ConditionExpression {

	private ConditionExpression() {
		super();
	}

	public abstract boolean checkCondition(ConditionColumnExtractor extractor);

	/**
	 * Equal Condition
	 */
	public static class EqualCondition extends ConditionExpression {

		private String axisName;
		private String value;

		public EqualCondition() {
			super();
		}

		public EqualCondition(String value) {
			super();
			this.value = StringUtils.replace(value, "\"", "");
		}

		public String getValue() {
			return value;
		}

		public void setValue(String value) {
			this.value = StringUtils.replace(value, "\"", "");
		}

		public String getAxisName() {
			return axisName;
		}

		public void setAxisName(String axisName) {
			this.axisName = axisName;
		}

		@Override
		public boolean checkCondition(ConditionColumnExtractor checker) {
			return StringUtils.equals(value, checker.getColumnValue(axisName));
		}

	}

	/**
	 * In Condition
	 */
	public static class InCondition extends ConditionExpression {

		private String axisName;

		private Set<String> values = new HashSet<>();

		public InCondition() {
			super();
		}

		public InCondition(Collection<String> values) {
			super();

			if (ALMFUtils.isNotNull(values))
				values.forEach(value -> this.values.add(StringUtils.replace(value, "\"", "")));
		}

		public InCondition(String... values) {
			super();

			if (ALMFUtils.isNotNull(values))
				Arrays.asList(values).forEach(value -> this.values.add(StringUtils.replace(value, "\"", "")));
		}

		public Set<String> getValues() {
			return values;
		}

		public void setValues(Set<String> values) {
			if (ALMFUtils.isNotNull(values)) {
				this.values = new HashSet<>();

				values.forEach(value -> this.values.add(StringUtils.replace(value, "\"", "")));
			}
		}

		public String getAxisName() {
			return axisName;
		}

		public void setAxisName(String axisName) {
			this.axisName = axisName;
		}

		@Override
		public boolean checkCondition(ConditionColumnExtractor checker) {
			return this.values.contains(checker.getColumnValue(axisName));
		}

	}

	/**
	 * Or Condition
	 */
	public static class OrCondition extends ConditionExpression {

		private List<ConditionExpression> conditions = new ArrayList<>();

		public OrCondition() {
			super();
		}

		public OrCondition(List<ConditionExpression> conditions) {
			super();

			if (ALMFUtils.isNotNull(conditions))
				this.conditions = conditions;
		}

		public OrCondition(ConditionExpression... conditions) {
			super();

			if (ALMFUtils.isNotNull(conditions))
				this.conditions = Arrays.asList(conditions);
		}

		public List<ConditionExpression> getConditions() {
			return Collections.unmodifiableList(conditions);
		}

		@Override
		public boolean checkCondition(ConditionColumnExtractor checker) {
			boolean check = false;

			for (ConditionExpression condition : this.conditions) {
				check = condition.checkCondition(checker);

				if (check == true)
					break;
			}

			return check;
		}

	}

	/**
	 * And Condition
	 */
	public static class AndCondition extends ConditionExpression {

		private List<ConditionExpression> conditions = new ArrayList<>();

		public AndCondition() {
			super();
		}

		public AndCondition(List<ConditionExpression> conditions) {
			super();

			if (ALMFUtils.isNotNull(conditions))
				this.conditions = conditions;
		}

		public AndCondition(ConditionExpression... conditions) {
			super();

			if (ALMFUtils.isNotNull(conditions))
				this.conditions = Arrays.asList(conditions);
		}

		public List<ConditionExpression> getConditions() {
			return Collections.unmodifiableList(conditions);
		}

		@Override
		public boolean checkCondition(ConditionColumnExtractor checker) {
			boolean check = true;

			for (ConditionExpression condition : this.conditions) {
				check = (check && condition.checkCondition(checker));

				if (check == false)
					break;
			}

			return check;
		}

	}

	/**
	 * Not Condition
	 */
	public static class NotCondition extends ConditionExpression {

		private ConditionExpression condition;

		public NotCondition() {
			super();
		}

		public NotCondition(ConditionExpression condition) {
			super();

			if (ALMFUtils.isNotNull(condition))
				this.condition = condition;
		}

		public ConditionExpression getCondition() {
			return condition;
		}

		@Override
		public boolean checkCondition(ConditionColumnExtractor checker) {
			return !condition.checkCondition(checker);
		}

	}

}
