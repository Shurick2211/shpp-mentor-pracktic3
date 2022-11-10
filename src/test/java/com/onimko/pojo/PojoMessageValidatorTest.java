package com.onimko.pojo;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import junit.framework.TestCase;

import java.time.LocalDateTime;
import java.util.Set;

public class PojoMessageValidatorTest extends TestCase {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    public final Validator validator = factory.getValidator();


    public void testValidName() {
        PojoMessage pojoMessage = new PojoMessage("fa",10,
                LocalDateTime.of(2020,5,5,5,5));
        Set<ConstraintViolation<PojoMessage>> errors = validator.validate(pojoMessage);
        assertEquals(1, errors.size());
        assertEquals("name", errors.iterator().next().getPropertyPath().toString());

        pojoMessage = new PojoMessage("fhtrhykk8o980",10,
                LocalDateTime.of(2020,5,5,5,5));
        errors = validator.validate(pojoMessage);
        assertEquals(1, errors.size());
        assertEquals("name", errors.iterator().next().getPropertyPath().toString());

    }

    public void testValidCount() {
        PojoMessage pojoMessage = new PojoMessage("fanhftyurkf",2,
                LocalDateTime.of(2020,5,5,5,5));
        Set<ConstraintViolation<PojoMessage>> errors = validator.validate(pojoMessage);
        assertEquals(1, errors.size());
        assertEquals("count", errors.iterator().next().getPropertyPath().toString());
    }

    public void testValidCreatedAt() {
        PojoMessage pojoMessage = new PojoMessage("fanhftyurkf",10,
                LocalDateTime.of(2025,5,5,5,5));
        Set<ConstraintViolation<PojoMessage>> errors = validator.validate(pojoMessage);
        assertEquals(1, errors.size());
        assertEquals("createdAt", errors.iterator().next().getPropertyPath().toString());
    }
}