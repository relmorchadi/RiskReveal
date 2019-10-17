package com.scor.rr.service.condition;

import java.util.ArrayList;
import java.util.List;

/**
 * Exposure Summary Condition Parser
 * 
 * @author HADDINI Zakariyae
 *
 */
public class ExposureSummaryConditionParser implements ExposureSummaryConditionParserConstants {

	final public ConditionRoot parseStatement() throws ParseException {
		ConditionRoot cr;

		cr = statement();

		return cr;
	}

	final private ConditionRoot statement() throws ParseException {
		Token t;

		ConditionExpression ce;

		t = jj_consume_token(TYPE);

		jj_consume_token(DEFINITION);

		ce = conditionTree();

		return new ConditionRoot(t.image, ce);
	}

	final private ConditionExpression conditionTree() throws ParseException {
		ConditionExpression ce;

		switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
		case NOT: {
			ce = not();
			break;
		}
		case AND: {
			ce = and();
			break;
		}
		case OR: {
			ce = or();
			break;
		}
		case COLUMNS: {
			ce = test();
			break;
		}
		default:
			jj_la1[0] = jj_gen;
			jj_consume_token(-1);
			throw new ParseException();
		}

		return ce;
	}

	final private ConditionExpression not() throws ParseException {
		ConditionExpression ce;

		jj_consume_token(NOT);

		ce = conditionTree();

		return new ConditionExpression.NotCondition(ce);
	}

	final private ConditionExpression and() throws ParseException {
		ConditionExpression ce;

		List<ConditionExpression> cel = new ArrayList<>();

		jj_consume_token(AND);

		jj_consume_token(LPARENCHAR);

		ce = conditionTree();

		cel.add(ce);

		label_1: while (true) {
			switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
			case COMMA_SEPARATOR: {
				;
				break;
			}
			default:
				jj_la1[1] = jj_gen;
				break label_1;
			}

			jj_consume_token(COMMA_SEPARATOR);

			ce = conditionTree();

			cel.add(ce);
		}

		jj_consume_token(RPARENCHAR);

		return new ConditionExpression.AndCondition(cel);
	}

	final private ConditionExpression or() throws ParseException {
		ConditionExpression ce;

		List<ConditionExpression> cel = new ArrayList<>();

		jj_consume_token(OR);

		jj_consume_token(LPARENCHAR);

		ce = conditionTree();

		cel.add(ce);

		label_2: while (true) {
			switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
			case COMMA_SEPARATOR: {
				;
				break;
			}
			default:
				jj_la1[2] = jj_gen;
				break label_2;
			}

			jj_consume_token(COMMA_SEPARATOR);

			ce = conditionTree();

			cel.add(ce);
		}

		jj_consume_token(RPARENCHAR);

		return new ConditionExpression.OrCondition(cel);
	}

	final private String quotedText() throws ParseException {
		Token t;

		t = jj_consume_token(QUOTEDSTRING);

		return t.image;
	}

	final private String column() throws ParseException {
		Token t;

		t = jj_consume_token(COLUMNS);

		return t.image;
	}

	final private ConditionExpression test() throws ParseException {
		String c;

		ConditionExpression ce;

		c = column();

		switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
		case IN: {
			ce = test_in(c);
			{
				if ("" != null)
					return ce;
			}
		}
		case EQUAL: {
			ce = test_eq(c);
			return ce;
		}
		default:
			jj_la1[3] = jj_gen;
			jj_consume_token(-1);
			throw new ParseException();
		}
	}

	final private ConditionExpression test_in(String column) throws ParseException {
		ConditionExpression.InCondition ie = new ConditionExpression.InCondition();

		ie.setAxisName(column);

		String v;

		jj_consume_token(IN);

		jj_consume_token(LPARENCHAR);

		v = quotedText();

		ie.getValues().add(v);

		label_3: while (true) {
			switch ((jj_ntk == -1) ? jj_ntk_f() : jj_ntk) {
			case COMMA_SEPARATOR: {
				;
				break;
			}
			default:
				jj_la1[4] = jj_gen;
				break label_3;
			}

			jj_consume_token(COMMA_SEPARATOR);

			v = quotedText();

			ie.getValues().add(v);
		}

		jj_consume_token(RPARENCHAR);

		return ie;
	}

	final private ConditionExpression test_eq(String column) throws ParseException {
		ConditionExpression.EqualCondition ec = new ConditionExpression.EqualCondition();

		ec.setAxisName(column);

		String v;

		jj_consume_token(EQUAL);

		v = quotedText();

		ec.setValue(v);

		return ec;
	}

	/** Generated Token Manager. */
	public ExposureSummaryConditionParserTokenManager token_source;
	SimpleCharStream jj_input_stream;
	/** Current token. */
	public Token token;
	/** Next token. */
	public Token jj_nt;
	private int jj_ntk;
	private int jj_gen;
	final private int[] jj_la1 = new int[5];
	static private int[] jj_la1_0;
	static {
		jj_la1_init_0();
	}

	private static void jj_la1_init_0() {
		jj_la1_0 = new int[] { 0x81c0, 0x1000, 0x1000, 0x600, 0x1000, };
	}

	/** Constructor with InputStream. */
	public ExposureSummaryConditionParser(java.io.InputStream stream) {
		this(stream, null);
	}

	/** Constructor with InputStream and supplied encoding */
	public ExposureSummaryConditionParser(java.io.InputStream stream, String encoding) {
		try {
			jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		token_source = new ExposureSummaryConditionParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 5; i++)
			jj_la1[i] = -1;
	}

	/** Reinitialise. */
	public void ReInit(java.io.InputStream stream) {
		ReInit(stream, null);
	}

	/** Reinitialise. */
	public void ReInit(java.io.InputStream stream, String encoding) {
		try {
			jj_input_stream.ReInit(stream, encoding, 1, 1);
		} catch (java.io.UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 5; i++)
			jj_la1[i] = -1;
	}

	/** Constructor. */
	public ExposureSummaryConditionParser(java.io.Reader stream) {
		jj_input_stream = new SimpleCharStream(stream, 1, 1);
		token_source = new ExposureSummaryConditionParserTokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 5; i++)
			jj_la1[i] = -1;
	}

	/** Reinitialise. */
	public void ReInit(java.io.Reader stream) {
		if (jj_input_stream == null) {
			jj_input_stream = new SimpleCharStream(stream, 1, 1);
		} else {
			jj_input_stream.ReInit(stream, 1, 1);
		}
		if (token_source == null) {
			token_source = new ExposureSummaryConditionParserTokenManager(jj_input_stream);
		}

		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 5; i++)
			jj_la1[i] = -1;
	}

	/** Constructor with generated Token Manager. */
	public ExposureSummaryConditionParser(ExposureSummaryConditionParserTokenManager tm) {
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 5; i++)
			jj_la1[i] = -1;
	}

	/** Reinitialise. */
	public void ReInit(ExposureSummaryConditionParserTokenManager tm) {
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
		jj_gen = 0;
		for (int i = 0; i < 5; i++)
			jj_la1[i] = -1;
	}

	private Token jj_consume_token(int kind) throws ParseException {
		Token oldToken;
		if ((oldToken = token).next != null)
			token = token.next;
		else
			token = token.next = token_source.getNextToken();
		jj_ntk = -1;
		if (token.kind == kind) {
			jj_gen++;
			return token;
		}
		token = oldToken;
		jj_kind = kind;
		throw generateParseException();
	}

	/** Get the next Token. */
	final public Token getNextToken() {
		if (token.next != null)
			token = token.next;
		else
			token = token.next = token_source.getNextToken();
		jj_ntk = -1;
		jj_gen++;
		return token;
	}

	/** Get the specific Token. */
	final public Token getToken(int index) {
		Token t = token;
		for (int i = 0; i < index; i++) {
			if (t.next != null)
				t = t.next;
			else
				t = t.next = token_source.getNextToken();
		}
		return t;
	}

	private int jj_ntk_f() {
		if ((jj_nt = token.next) == null)
			return (jj_ntk = (token.next = token_source.getNextToken()).kind);
		else
			return (jj_ntk = jj_nt.kind);
	}

	private List<int[]> jj_expentries = new ArrayList<int[]>();
	private int[] jj_expentry;
	private int jj_kind = -1;

	/** Generate ParseException. */
	public ParseException generateParseException() {
		jj_expentries.clear();
		boolean[] la1tokens = new boolean[17];
		if (jj_kind >= 0) {
			la1tokens[jj_kind] = true;
			jj_kind = -1;
		}
		for (int i = 0; i < 5; i++) {
			if (jj_la1[i] == jj_gen) {
				for (int j = 0; j < 32; j++) {
					if ((jj_la1_0[i] & (1 << j)) != 0) {
						la1tokens[j] = true;
					}
				}
			}
		}
		for (int i = 0; i < 17; i++) {
			if (la1tokens[i]) {
				jj_expentry = new int[1];
				jj_expentry[0] = i;
				jj_expentries.add(jj_expentry);
			}
		}
		int[][] exptokseq = new int[jj_expentries.size()][];
		for (int i = 0; i < jj_expentries.size(); i++) {
			exptokseq[i] = jj_expentries.get(i);
		}
		return new ParseException(token, exptokseq, tokenImage);
	}

	/** Enable tracing. */
	final public void enable_tracing() {
	}

	/** Disable tracing. */
	final public void disable_tracing() {
	}

}
