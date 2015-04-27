package org.owl.common.util;

public abstract class AbstractCommandLineParser {

	public static class ParseException extends Exception{
		public ParseException(String message){
			super(message);
		}
	}
	
	 /**
     * Prefix of the option. Subclasses may overwrite.
     */
    protected String OPTION_PREFIX = "-"; 

    /**
     * End of input token
     */
    protected static final int TOK_EOF = -1;

    /**
     * Option token
     */
    protected static final int TOK_OPTION = 0;

    /**
     * Literal (any string that doesn not start with defice actually)
     */
    protected static final int TOK_LITERAL = 3;

    /**
     * Parser input
     */
    private String[] args;

    /**
     * The length of the input array
     */
    private int argsLen;

    /**
     * Index of the of the next token to process
     */
    private int position;

    /**
     * Current token
     */
    private int token;

    /**
     * Current arg value
     */
    private String argValue;

    /**
     * Current token value
     */
    private String tokenValue;

    public AbstractCommandLineParser(String[] args){
    	assert(args != null):"args must be not null";
    	this.args = args;
    	this.argsLen = args.length;
    }
    
    /**
     * Main entry point of the parser
     * 
     * @throws ParseException
     */
    public void parse() throws ParseException {
        nextToken();
        start();
    }

    /**
     * Returns current arg
     * 
     * @return current arg
     */
    protected String getArgValue() {
        return argValue;
    }

    /**
     * Returns current token
     * 
     * @return current token
     */
    protected int getToken() {
        return token;
    }

    /**
     * Sets the current token
     * 
     * @param token
     */
    protected void setToken(int token) {
        this.token = token;
    }

    /**
     * Returns <code>String</code> value of the current token
     * 
     * @return
     */
    protected String getTokenValue() {
        return tokenValue;
    }

    /**
     * Sets the current token
     * 
     * @param tokenValue
     */
    protected void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    /**
     * Returns <code>String</code> value of the current token
     * 
     * @return
     */
    protected int getPosition() {
        return position;
    }

    /**
     * Real entry point of parser
     * 
     * @throws ParseException
     */
    protected abstract void start() throws ParseException;

    protected boolean hasNext() {
        return position < argsLen;
    }
    /**
     * Reads the next available token.
     */
    protected void nextToken() {
        nextArg();
        if (argValue != null) {
            if (argValue.startsWith(OPTION_PREFIX)) {
                token = TOK_OPTION;
                tokenValue = argValue.substring(OPTION_PREFIX.length());
            } else {
                token = TOK_LITERAL;
                tokenValue = argValue;
            }
        } else {
            tokenValue = null;
            token = TOK_EOF;
        }
    }

    protected void nextArg() {
        if (position < argsLen) {
            argValue = args[position++];
        } else {
            argValue = null;
        }
    }

    /**
     * Indicates the parse error
     * 
     * @param message
     * @throws ParseException
     */
    protected void error(String message) throws ParseException {
        throw new ParseException(message);
    }

    
}
