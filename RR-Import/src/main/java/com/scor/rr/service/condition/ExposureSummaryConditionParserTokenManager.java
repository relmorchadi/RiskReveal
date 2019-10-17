package com.scor.rr.service.condition;

/** Token Manager. */
@SuppressWarnings("unused")
public class ExposureSummaryConditionParserTokenManager implements ExposureSummaryConditionParserConstants {

	/** Debug output. */
	public java.io.PrintStream debugStream = System.out;

	/** Set debug output. */
	public void setDebugStream(java.io.PrintStream ds) {
		debugStream = ds;
	}

	private final int jjStopStringLiteralDfa_0(int pos, long active0) {
		switch (pos) {
		case 0:
			if ((active0 & 0x2L) != 0L)
				return 92;
			if ((active0 & 0x10L) != 0L)
				return 93;
			if ((active0 & 0x40L) != 0L)
				return 33;
			return -1;
		case 1:
			if ((active0 & 0x40L) != 0L)
				return 32;
			return -1;
		default:
			return -1;
		}
	}

	private final int jjStartNfa_0(int pos, long active0) {
		return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0), pos + 1);
	}

	private int jjStopAtPos(int pos, int kind) {
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		return pos + 1;
	}

	private int jjMoveStringLiteralDfa0_0() {
		switch (curChar) {
		case 10:
			return jjStartNfaWithStates_0(0, 1, 92);
		case 34:
			return jjStartNfaWithStates_0(0, 4, 93);
		case 40:
			return jjStopAtPos(0, 13);
		case 41:
			return jjStopAtPos(0, 14);
		case 58:
			return jjStopAtPos(0, 5);
		case 61:
			return jjStopAtPos(0, 10);
		case 65:
		case 97:
			return jjMoveStringLiteralDfa1_0(0x40L);
		case 73:
		case 105:
			return jjMoveStringLiteralDfa1_0(0x200L);
		case 78:
		case 110:
			return jjMoveStringLiteralDfa1_0(0x100L);
		case 79:
		case 111:
			return jjMoveStringLiteralDfa1_0(0x80L);
		default:
			return jjMoveNfa_0(0, 0);
		}
	}

	private int jjMoveStringLiteralDfa1_0(long active0) {
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			jjStopStringLiteralDfa_0(0, active0);
			return 1;
		}
		switch (curChar) {
		case 78:
		case 110:
			if ((active0 & 0x200L) != 0L)
				return jjStopAtPos(1, 9);
			return jjMoveStringLiteralDfa2_0(active0, 0x40L);
		case 79:
		case 111:
			return jjMoveStringLiteralDfa2_0(active0, 0x100L);
		case 82:
		case 114:
			if ((active0 & 0x80L) != 0L)
				return jjStopAtPos(1, 7);
			break;
		default:
			break;
		}
		return jjStartNfa_0(0, active0);
	}

	private int jjMoveStringLiteralDfa2_0(long old0, long active0) {
		if (((active0 &= old0)) == 0L)
			return jjStartNfa_0(0, old0);
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			jjStopStringLiteralDfa_0(1, active0);
			return 2;
		}
		switch (curChar) {
		case 68:
		case 100:
			if ((active0 & 0x40L) != 0L)
				return jjStopAtPos(2, 6);
			break;
		case 84:
		case 116:
			if ((active0 & 0x100L) != 0L)
				return jjStopAtPos(2, 8);
			break;
		default:
			break;
		}
		return jjStartNfa_0(1, active0);
	}

	private int jjStartNfaWithStates_0(int pos, int kind, int state) {
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		try {
			curChar = input_stream.readChar();
		} catch (java.io.IOException e) {
			return pos + 1;
		}
		return jjMoveNfa_0(state, pos + 1);
	}

	static final long[] jjbitVec0 = { 0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL };

	private int jjMoveNfa_0(int startState, int curPos) {
		int startsAt = 0;
		jjnewStateCnt = 92;
		int i = 1;
		jjstateSet[0] = startState;
		int kind = 0x7fffffff;
		for (;;) {
			if (++jjround == 0x7fffffff)
				ReInitRounds();
			if (curChar < 64) {
				long l = 1L << curChar;
				do {
					switch (jjstateSet[--i]) {
					case 92:
						if ((0x100003200L & l) != 0L) {
							jjCheckNAddStates(0, 2);
						} else if (curChar == 44) {
							if (kind > 12)
								kind = 12;
							{
								jjCheckNAddTwoStates(4, 5);
							}
						} else if (curChar == 10) {
							jjCheckNAddStates(0, 2);
						}
						if ((0x100003200L & l) != 0L) {
							if (kind > 3)
								kind = 3;
							{
								jjCheckNAddTwoStates(74, 75);
							}
						} else if (curChar == 10) {
							if (kind > 3)
								kind = 3;
							{
								jjCheckNAddTwoStates(74, 75);
							}
						}
						break;
					case 0:
						if ((0x100003200L & l) != 0L) {
							if (kind > 2)
								kind = 2;
							{
								jjCheckNAddStates(3, 8);
							}
						} else if (curChar == 10) {
							if (kind > 3)
								kind = 3;
							{
								jjCheckNAddStates(9, 13);
							}
						} else if (curChar == 44) {
							if (kind > 12)
								kind = 12;
							{
								jjCheckNAddTwoStates(4, 5);
							}
						} else if (curChar == 34) {
							jjCheckNAddTwoStates(1, 2);
						}
						break;
					case 93:
						if ((0xfffffffbffffffffL & l) != 0L) {
							jjCheckNAddTwoStates(1, 2);
						} else if (curChar == 34) {
							if (kind > 11)
								kind = 11;
						}
						break;
					case 1:
						if ((0xfffffffbffffffffL & l) != 0L) {
							jjCheckNAddTwoStates(1, 2);
						}
						break;
					case 2:
						if (curChar == 34 && kind > 11)
							kind = 11;
						break;
					case 3:
						if (curChar != 44)
							break;
						if (kind > 12)
							kind = 12; {
						jjCheckNAddTwoStates(4, 5);
					}
						break;
					case 4:
						if ((0x100003200L & l) == 0L)
							break;
						if (kind > 12)
							kind = 12; {
						jjCheckNAddTwoStates(4, 5);
					}
						break;
					case 5:
						if (curChar != 10)
							break;
						if (kind > 12)
							kind = 12; {
						jjCheckNAddTwoStates(4, 5);
					}
						break;
					case 34:
						if (curChar == 49 && kind > 15)
							kind = 15;
						break;
					case 72:
						if ((0x100003200L & l) == 0L)
							break;
						if (kind > 2)
							kind = 2; {
						jjCheckNAddStates(3, 8);
					}
						break;
					case 73:
						if ((0x100003200L & l) == 0L)
							break;
						if (kind > 2)
							kind = 2; {
						jjCheckNAdd(73);
					}
						break;
					case 74:
						if ((0x100003200L & l) == 0L)
							break;
						if (kind > 3)
							kind = 3; {
						jjCheckNAddTwoStates(74, 75);
					}
						break;
					case 75:
						if (curChar != 10)
							break;
						if (kind > 3)
							kind = 3; {
						jjCheckNAddTwoStates(74, 75);
					}
						break;
					case 76:
						if ((0x100003200L & l) != 0L) {
							jjCheckNAddStates(0, 2);
						}
						break;
					case 77:
						if (curChar == 10) {
							jjCheckNAddStates(0, 2);
						}
						break;
					case 78:
						if (curChar != 10)
							break;
						if (kind > 3)
							kind = 3; {
						jjCheckNAddStates(9, 13);
					}
						break;
					default:
						break;
					}
				} while (i != startsAt);
			} else if (curChar < 128) {
				long l = 1L << (curChar & 077);
				do {
					switch (jjstateSet[--i]) {
					case 33:
						if ((0x1000000010L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 37;
						else if ((0x400000004000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 32;
						break;
					case 0:
						if ((0x1000000010000L & l) != 0L) {
							jjAddStates(14, 15);
						} else if ((0x4000000040000L & l) != 0L) {
							jjAddStates(16, 17);
						} else if ((0x200000002L & l) != 0L) {
							jjAddStates(18, 19);
						} else if ((0x8000000080000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 18;
						else if ((0x800000008L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 11;
						break;
					case 93:
					case 1: {
						jjCheckNAddTwoStates(1, 2);
					}
						break;
					case 6:
						if ((0x200000002000000L & l) != 0L && kind > 15)
							kind = 15;
						break;
					case 7:
						if ((0x4000000040000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 6;
						break;
					case 8:
						if ((0x10000000100000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 7;
						break;
					case 9:
						if ((0x400000004000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 8;
						break;
					case 10:
						if ((0x20000000200000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 9;
						break;
					case 11:
						if ((0x800000008000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 10;
						break;
					case 12:
						if ((0x800000008L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 11;
						break;
					case 13:
						if ((0x200000002000000L & l) != 0L && kind > 16)
							kind = 16;
						break;
					case 14:
						if ((0x4000000040000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 13;
						break;
					case 15:
						if ((0x200000002L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 14;
						break;
					case 16:
						if ((0x200000002000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 15;
						break;
					case 17:
						if ((0x200000002000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 16;
						break;
					case 18:
						if ((0x20000000200000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 17;
						break;
					case 19:
						if ((0x8000000080000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 18;
						break;
					case 20:
						if ((0x200000002L & l) != 0L) {
							jjAddStates(18, 19);
						}
						break;
					case 21:
						if ((0x400000004000L & l) != 0L && kind > 15)
							kind = 15;
						break;
					case 22:
						if ((0x800000008000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 21;
						break;
					case 23:
						if ((0x20000000200L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 22;
						break;
					case 24:
						if ((0x8000000080L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 23;
						break;
					case 25:
						if ((0x2000000020L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 24;
						break;
					case 26:
						if ((0x4000000040000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 25;
						break;
					case 27:
						if ((0x8000000080000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 26;
						break;
					case 28:
						if ((0x20000000200L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 27;
						break;
					case 29:
						if ((0x8000000080000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 28;
						break;
					case 30:
						if ((0x200000002000000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 29;
						break;
					case 31:
						if ((0x100000001000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 30;
						break;
					case 32:
						if ((0x200000002L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 31;
						break;
					case 35:
						if ((0x400000004000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 34;
						break;
					case 36:
						if ((0x20000000200L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 35;
						break;
					case 37:
						if ((0x200000002000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 36;
						break;
					case 38:
						if ((0x1000000010L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 37;
						break;
					case 39:
						if ((0x4000000040000L & l) != 0L) {
							jjAddStates(16, 17);
						}
						break;
					case 40:
						if ((0x2000000020L & l) != 0L && kind > 15)
							kind = 15;
						break;
					case 41:
					case 54:
						if ((0x1000000010L & l) != 0L) {
							jjCheckNAdd(40);
						}
						break;
					case 42:
						if ((0x800000008000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 41;
						break;
					case 43:
						if ((0x800000008L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 42;
						break;
					case 44:
						if ((0x100000001000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 43;
						break;
					case 45:
						if ((0x20000000200L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 44;
						break;
					case 46:
						if ((0x4000000040000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 45;
						break;
					case 47:
						if ((0x2000000020L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 46;
						break;
					case 48:
						if ((0x1000000010000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 47;
						break;
					case 49:
						if ((0x400000004000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 48;
						break;
					case 50:
						if ((0x800000008000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 49;
						break;
					case 51:
						if ((0x20000000200L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 50;
						break;
					case 52:
						if ((0x8000000080L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 51;
						break;
					case 53:
						if ((0x2000000020L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 52;
						break;
					case 55:
						if ((0x800000008000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 54;
						break;
					case 56:
						if ((0x800000008L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 55;
						break;
					case 57:
						if ((0x1000000010000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 56;
						break;
					case 58:
						if ((0x20000000200000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 57;
						break;
					case 59:
						if ((0x800000008000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 58;
						break;
					case 60:
						if ((0x4000000040000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 59;
						break;
					case 61:
						if ((0x8000000080L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 60;
						break;
					case 62:
						if ((0x100000001000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 61;
						break;
					case 63:
						if ((0x20000000200L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 62;
						break;
					case 64:
						if ((0x4000000040000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 63;
						break;
					case 65:
						if ((0x2000000020L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 64;
						break;
					case 66:
						if ((0x1000000010000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 65;
						break;
					case 67:
						if ((0x400000004000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 66;
						break;
					case 68:
						if ((0x800000008000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 67;
						break;
					case 69:
						if ((0x20000000200L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 68;
						break;
					case 70:
						if ((0x8000000080L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 69;
						break;
					case 71:
						if ((0x2000000020L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 70;
						break;
					case 79:
						if ((0x1000000010000L & l) != 0L) {
							jjAddStates(14, 15);
						}
						break;
					case 80:
						if ((0x100000001000L & l) != 0L && kind > 15)
							kind = 15;
						break;
					case 81:
						if ((0x20000000200L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 80;
						break;
					case 82:
						if ((0x4000000040000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 81;
						break;
					case 83:
						if ((0x2000000020L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 82;
						break;
					case 84:
						if ((0x800000008000L & l) != 0L && kind > 16)
							kind = 16;
						break;
					case 85:
						if ((0x20000000200L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 84;
						break;
					case 86:
						if ((0x100000001000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 85;
						break;
					case 87:
						if ((0x800000008000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 86;
						break;
					case 88:
						if ((0x4000000040L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 87;
						break;
					case 89:
						if ((0x10000000100000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 88;
						break;
					case 90:
						if ((0x4000000040000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 89;
						break;
					case 91:
						if ((0x800000008000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 90;
						break;
					default:
						break;
					}
				} while (i != startsAt);
			} else {
				int i2 = (curChar & 0xff) >> 6;
				long l2 = 1L << (curChar & 077);
				do {
					switch (jjstateSet[--i]) {
					case 93:
					case 1:
						if ((jjbitVec0[i2] & l2) != 0L) {
							jjCheckNAddTwoStates(1, 2);
						}
						break;
					default:
						break;
					}
				} while (i != startsAt);
			}
			if (kind != 0x7fffffff) {
				jjmatchedKind = kind;
				jjmatchedPos = curPos;
				kind = 0x7fffffff;
			}
			++curPos;
			if ((i = jjnewStateCnt) == (startsAt = 92 - (jjnewStateCnt = startsAt)))
				return curPos;
			try {
				curChar = input_stream.readChar();
			} catch (java.io.IOException e) {
				return curPos;
			}
		}
	}

	static final int[] jjnextStates = { 76, 77, 3, 73, 74, 75, 76, 77, 3, 74, 75, 76, 77, 3, 83, 91, 53, 71, 33, 38, };

	/** Token literal values. */
	public static final String[] jjstrLiteralImages = { "", null, null, null, null, "\72", null, null, null, null,
			"\75", null, null, "\50", "\51", null, null, };

	protected Token jjFillToken() {
		final Token t;
		final String curTokenImage;
		final int beginLine;
		final int endLine;
		final int beginColumn;
		final int endColumn;
		String im = jjstrLiteralImages[jjmatchedKind];
		curTokenImage = (im == null) ? input_stream.GetImage() : im;
		beginLine = input_stream.getBeginLine();
		beginColumn = input_stream.getBeginColumn();
		endLine = input_stream.getEndLine();
		endColumn = input_stream.getEndColumn();
		t = Token.newToken(jjmatchedKind, curTokenImage);

		t.beginLine = beginLine;
		t.endLine = endLine;
		t.beginColumn = beginColumn;
		t.endColumn = endColumn;

		return t;
	}

	int curLexState = 0;
	int defaultLexState = 0;
	int jjnewStateCnt;
	int jjround;
	int jjmatchedPos;
	int jjmatchedKind;

	/** Get the next Token. */
	public Token getNextToken() {
		Token specialToken = null;
		Token matchedToken;
		int curPos = 0;

		EOFLoop: for (;;) {
			try {
				curChar = input_stream.BeginToken();
			} catch (Exception e) {
				jjmatchedKind = 0;
				jjmatchedPos = -1;
				matchedToken = jjFillToken();
				matchedToken.specialToken = specialToken;
				return matchedToken;
			}

			jjmatchedKind = 0x7fffffff;
			jjmatchedPos = 0;
			curPos = jjMoveStringLiteralDfa0_0();
			if (jjmatchedKind != 0x7fffffff) {
				if (jjmatchedPos + 1 < curPos)
					input_stream.backup(curPos - jjmatchedPos - 1);
				if ((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
					matchedToken = jjFillToken();
					matchedToken.specialToken = specialToken;
					return matchedToken;
				} else {
					if ((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
						matchedToken = jjFillToken();
						if (specialToken == null)
							specialToken = matchedToken;
						else {
							matchedToken.specialToken = specialToken;
							specialToken = (specialToken.next = matchedToken);
						}
					}
					continue EOFLoop;
				}
			}
			int error_line = input_stream.getEndLine();
			int error_column = input_stream.getEndColumn();
			String error_after = null;
			boolean EOFSeen = false;
			try {
				input_stream.readChar();
				input_stream.backup(1);
			} catch (java.io.IOException e1) {
				EOFSeen = true;
				error_after = curPos <= 1 ? "" : input_stream.GetImage();
				if (curChar == '\n' || curChar == '\r') {
					error_line++;
					error_column = 0;
				} else
					error_column++;
			}
			if (!EOFSeen) {
				input_stream.backup(1);
				error_after = curPos <= 1 ? "" : input_stream.GetImage();
			}
			throw new TokenMgrError(EOFSeen, curLexState, error_line, error_column, error_after, curChar,
					TokenMgrError.LEXICAL_ERROR);
		}
	}

	private void jjCheckNAdd(int state) {
		if (jjrounds[state] != jjround) {
			jjstateSet[jjnewStateCnt++] = state;
			jjrounds[state] = jjround;
		}
	}

	private void jjAddStates(int start, int end) {
		do {
			jjstateSet[jjnewStateCnt++] = jjnextStates[start];
		} while (start++ != end);
	}

	private void jjCheckNAddTwoStates(int state1, int state2) {
		jjCheckNAdd(state1);
		jjCheckNAdd(state2);
	}

	private void jjCheckNAddStates(int start, int end) {
		do {
			jjCheckNAdd(jjnextStates[start]);
		} while (start++ != end);
	}

	/** Constructor. */
	public ExposureSummaryConditionParserTokenManager(SimpleCharStream stream) {

		if (SimpleCharStream.staticFlag)
			throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");

		input_stream = stream;
	}

	/** Constructor. */
	public ExposureSummaryConditionParserTokenManager(SimpleCharStream stream, int lexState) {
		ReInit(stream);
		SwitchTo(lexState);
	}

	/** Reinitialise parser. */
	public void ReInit(SimpleCharStream stream) {

		jjmatchedPos = jjnewStateCnt = 0;
		curLexState = defaultLexState;
		input_stream = stream;
		ReInitRounds();
	}

	private void ReInitRounds() {
		int i;
		jjround = 0x80000001;
		for (i = 92; i-- > 0;)
			jjrounds[i] = 0x80000000;
	}

	/** Reinitialise parser. */
	public void ReInit(SimpleCharStream stream, int lexState) {

		ReInit(stream);
		SwitchTo(lexState);
	}

	/** Switch to specified lex state. */
	public void SwitchTo(int lexState) {
		if (lexState >= 1 || lexState < 0)
			throw new TokenMgrError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.",
					TokenMgrError.INVALID_LEXICAL_STATE);
		else
			curLexState = lexState;
	}

	/** Lexer state names. */
	public static final String[] lexStateNames = { "DEFAULT", };
	static final long[] jjtoToken = { 0x1ffe1L, };
	static final long[] jjtoSkip = { 0x1eL, };
	static final long[] jjtoSpecial = { 0x1eL, };
	protected SimpleCharStream input_stream;

	private final int[] jjrounds = new int[92];
	private final int[] jjstateSet = new int[2 * 92];

	protected int curChar;
}
