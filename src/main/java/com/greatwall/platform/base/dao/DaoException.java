package com.greatwall.platform.base.dao;

public class DaoException extends Exception {

	/**
	 * @Fields serialVersionUID : 
	 */
	private static final long serialVersionUID = 3169599911707107491L;
	
	
	 public DaoException() {  
	        super();  
	    }  
	  
	    /** 
	     * @param arg0 
	     */  
	    public DaoException(String arg0) {  
	        super(arg0);  
	    }  
	  
	    /** 
	     * @param arg0 
	     * @param arg1 
	     */  
	    public DaoException(String arg0, Throwable arg1) {  
	        super(arg0, arg1);  
	    }  
	  
	    /** 
	     * @param arg0 
	     */  
	    public DaoException(Throwable arg0) {  
	        super(arg0);  
	    }  

}
