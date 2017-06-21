package com.ecloud.framework.utils;

import java.util.Set;
import java.util.regex.Pattern;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.util.StringUtils;



/**
 * 校验工具类
 * 
 * @author LH
 */
public class ValidationUtils {

	/**
	 * BEAN类校验方法
	 * 
	 * @param bean
	 * @return ValidationBean
	 */
	public static ValidationBean validation(Object object) {
		try {
			// 注解校验
			ValidatorFactory vf = Validation.buildDefaultValidatorFactory();
			Validator validator = vf.getValidator();
			Set<ConstraintViolation<Object>> set = validator.validate(object);

			// 注解校验通过的话，再进行逻辑校验
			if (set.isEmpty()) {
				ValidationBean valid = new ValidationBean();
				valid.setValid(true);
				return valid;
			} else {
				// 注解校验不通过，返回第一个错误信息
				ValidationBean valid = new ValidationBean();
				valid.setValid(false);
				for (ConstraintViolation<Object> constraintViolation : set) {
					valid.setMessage(constraintViolation.getMessage());
					break;
				}
				return valid;
			}
		} catch (Exception e) {
			// 校验异常
			ValidationBean valid = new ValidationBean();
			valid.setValid(false);
			valid.setMessage("validation error!");
			return valid;
		}
	}


	/**
	 * 校验语句是否为SELECT语句
	 * 
	 * @param sql
	 *            语句
	 * @return 是否校验成功
	 */
	public static boolean verifySelectSql(String sql) {
		if (!StringUtils.isEmpty(sql)) {
			String regular = "^((?i)select)[^;]*$";
			return Pattern.matches(regular, sql);
		}
		return true;
	}

}
