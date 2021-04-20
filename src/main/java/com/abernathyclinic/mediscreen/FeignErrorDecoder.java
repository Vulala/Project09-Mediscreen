package com.abernathyclinic.mediscreen;

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
		if (response.status() == 404) {
			return new PatientNotFoundException("In : " + methodKey);
		}

		return defaultErrorDecoder.decode(methodKey, response);
	}

}
