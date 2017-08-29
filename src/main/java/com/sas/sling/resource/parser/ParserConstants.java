/* Generated By:JavaCC: Do not edit this line. ParserConstants.java */
package com.sas.sling.resource.parser;


/**
 * Token literal values and constants.
 * Generated by org.javacc.parser.OtherFilesGen#start()
 */
public interface ParserConstants {

  /** End of File. */
  int EOF = 0;
  /** RegularExpression Id. */
  int ALPHA = 3;
  /** RegularExpression Id. */
  int ESCAPED_CHAR = 4;
  /** RegularExpression Id. */
  int UNRESERVED_STR = 5;
  /** RegularExpression Id. */
  int SINGLE_QUOTED_STR = 6;
  /** RegularExpression Id. */
  int DOUBLE_QUOTED_STR = 7;
  /** RegularExpression Id. */
  int AND = 8;
  /** RegularExpression Id. */
  int OR = 9;
  /** RegularExpression Id. */
  int LPAREN = 10;
  /** RegularExpression Id. */
  int RPAREN = 11;
  /** RegularExpression Id. */
  int LBRACKET = 12;
  /** RegularExpression Id. */
  int RBRACKET = 13;
  /** RegularExpression Id. */
  int COMP_ALT = 14;

  /** Lexical state. */
  int DEFAULT = 0;

  /** Literal token values. */
  String[] tokenImage = {
    "<EOF>",
    "\" \"",
    "\"\\t\"",
    "<ALPHA>",
    "<ESCAPED_CHAR>",
    "<UNRESERVED_STR>",
    "<SINGLE_QUOTED_STR>",
    "<DOUBLE_QUOTED_STR>",
    "<AND>",
    "<OR>",
    "\"(\"",
    "\")\"",
    "\"[\"",
    "\"]\"",
    "<COMP_ALT>",
  };

}
