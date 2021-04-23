package com.abernathyclinic.mediscreen.exception;

import org.springframework.stereotype.Component;

import feign.Response;
import feign.codec.ErrorDecoder;

/**
 * Implementation of {@link ErrorDecoder} to handle the error returned by Feign
 * and to retrieve those returned by the others applications (micro-services).
 * <br>
 */
@Component
public class FeignErrorDecoder implements ErrorDecoder {

	private final ErrorDecoder defaultErrorDecoder = new Default();

	@Override
	public Exception decode(String methodKey, Response response) {
		int status = response.status();
		switch (status) {
		case 400:
			return new BodyNotValidException(methodKey);
		case 404:
			return new PatientNotFoundException(methodKey);
		default:
			return defaultErrorDecoder.decode(methodKey, response);
		}
	}
}
