/* Generated By:JavaCC: Do not edit this line. FilterParserConstants.java */
package org.apache.sling.resource.stream.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface FilterParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int DOT = 3;
  /** RegularExpression Id. */
  int PLUS = 4;
  /** RegularExpression Id. */
  int MINUS = 5;
  /** RegularExpression Id. */
  int DIGIT = 6;
  /** RegularExpression Id. */
  int EXP = 7;
  /** RegularExpression Id. */
  int NUMBER = 8;
  /** RegularExpression Id. */
  int INTEGER = 9;
  /** RegularExpression Id. */
  int FRACTIONAL_DIGITS = 10;
  /** RegularExpression Id. */
  int EXPONENT = 11;
  /** RegularExpression Id. */
  int DIGITS = 12;
  /** RegularExpression Id. */
  int STRING = 13;
  /** RegularExpression Id. */
  int SQUOTE = 14;
  /** RegularExpression Id. */
  int DQUOTE = 15;
  /** RegularExpression Id. */
  int AND = 16;
  /** RegularExpression Id. */
  int OR = 17;
  /** RegularExpression Id. */
  int NULL = 18;
  /** RegularExpression Id. */
  int LPAREN = 19;
  /** RegularExpression Id. */
  int RPAREN = 20;
  /** RegularExpression Id. */
  int COMMA = 21;
  /** RegularExpression Id. */
  int BOOLEAN = 22;
  /** RegularExpression Id. */
  int EQUAL = 23;
  /** RegularExpression Id. */
  int NOT_EQUAL = 24;
  /** RegularExpression Id. */
  int GREATER_THAN = 25;
  /** RegularExpression Id. */
  int GREATER_THAN_OR_EQUAL = 26;
  /** RegularExpression Id. */
  int LESS_THAN = 27;
  /** RegularExpression Id. */
  int LESS_THAN_OR_EQUAL = 28;
  /** RegularExpression Id. */
  int LIKE = 29;
  /** RegularExpression Id. */
  int LIKE_NOT = 30;
  /** RegularExpression Id. */
  int CONTAINS = 31;
  /** RegularExpression Id. */
  int CONTAINS_NOT = 32;
  /** RegularExpression Id. */
  int CONTAINS_ANY = 33;
  /** RegularExpression Id. */
  int CONTAINS_NOT_ANY = 34;
  /** RegularExpression Id. */
  int IN = 35;
  /** RegularExpression Id. */
  int NOT_IN = 36;
  /** RegularExpression Id. */
  int FUNCTION_NAME = 37;
  /** RegularExpression Id. */
  int PROPERTY = 38;
  /** RegularExpression Id. */
  int UNKNOWN = 39;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "\".\"",
    "\"+\"",
    "\"-\"",
    "<DIGIT>",
    "<EXP>",
    "<NUMBER>",
    "<INTEGER>",
    "<FRACTIONAL_DIGITS>",
    "<EXPONENT>",
    "<DIGITS>",
    "<STRING>",
    "<SQUOTE>",
    "<DQUOTE>",
    "<AND>",
    "<OR>",
    "\" null\"",
    "\"(\"",
    "\")\"",
    "\",\"",
    "<BOOLEAN>",
    "<EQUAL>",
    "<NOT_EQUAL>",
    "<GREATER_THAN>",
    "\">=\"",
    "<LESS_THAN>",
    "\"<=\"",
    "\"like\"",
    "<LIKE_NOT>",
    "\"contains\"",
    "\"contains not\"",
    "\"contains any\"",
    "\"contains not any\"",
    "\"in\"",
    "\"not in\"",
    "<FUNCTION_NAME>",
    "<PROPERTY>",
    "<UNKNOWN>",
  };

}
