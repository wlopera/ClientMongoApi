package com.ms.mongo.exception;

import org.springframework.core.NestedRuntimeException;

public class ClientException extends NestedRuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -5512024050035997366L;

	public ClientException(String id) {
        super(String.format("User with  Id '%s' not founded", id));
    }
}
