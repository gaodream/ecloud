package com.ecloud.framework.utils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidationChildValidator implements ConstraintValidator<ValidationChild, Object> {

	public void initialize(ValidationChild arg0) {

	}

	public boolean isValid(Object object, ConstraintValidatorContext constraintContext) {
		if (object == null)
			return true;

		ValidationBean validationBean = ValidationUtils.validation(object);

		if (!validationBean.isValid()) {
			constraintContext.disableDefaultConstraintViolation();
			constraintContext.buildConstraintViolationWithTemplate(validationBean.getMessage())
					.addConstraintViolation();
		}
		return validationBean.isValid();
	}

}