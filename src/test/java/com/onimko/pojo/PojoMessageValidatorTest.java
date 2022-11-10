package com.onimko.pojo;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.util.Set;

public class PojoMessageValidatorTest extends TestCase {
    PojoMessage pojoMessage = new PojoMessage("f",10,
            LocalDateTime.of(2020,5,5,5,5));
    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();
    Set<ConstraintViolation<PojoMessage>> errors = validator.validate(pojoMessage);

    public void testValidName() {
        errors.forEach(e-> System.out.println(e.getMessage()+" value = "+e.getInvalidValue()));

    }

    public void testValidCount() {
    }

    public void testValidCreatedAt() {
    }
}