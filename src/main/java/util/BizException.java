package util;

public class BizException extends Exception {
	
	private static final long serialVersionUID = 1L;
	public BizException() { super(); }
	public BizException(String message) { super(message); }
	public void setMessage(String message) { this.setMessage(message); }
}
