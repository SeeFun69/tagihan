package com.rasyid.tagihan.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorObject {
	private int status;
	private String message;
	private long timestamp;
	
}
