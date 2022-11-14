package com.rasyid.tagihan.exception;

public class TagihanNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -974394281910623558L;
	
	public TagihanNotFoundException (String message) {
		super(message);
	}
	
}
