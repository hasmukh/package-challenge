package com.hasu.packagechallenge.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 
 * @author Hasmukh Maniya
 *
 */
@Data
@AllArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
public class FileParserException extends Exception {

	private static final long serialVersionUID = 1L;

	private long lineNumber;
	private String line;

	public FileParserException(String message, String line, long lineNumber) {
		super(message);
		this.line = line;
		this.lineNumber = lineNumber;
	}

	public FileParserException(Throwable cause) {
		super(cause);
	}
}
