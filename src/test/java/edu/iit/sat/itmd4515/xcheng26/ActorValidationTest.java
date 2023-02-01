/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package edu.iit.sat.itmd4515.xcheng26;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Administrator
 */
public class ActorValidationTest {
    
    private static Validator validator;
    @BeforeAll
    public static void beforeAll() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @BeforeEach
    public void beforeEach() {
    }

    @Test
    public void actorIsValid() {
        // stand up some test data first
        Actor a = new Actor(
                33,
                "James",
                "Green",
                LocalDate.of(2021, 7, 12));

        // validate the data
        Set<ConstraintViolation<Actor>> violations = validator.validate(a);

        for (ConstraintViolation<Actor> violation : violations) {
            System.out.println(violation.toString());
        }

        // assert pass or fail
        //Assertions.assertEquals(0, violations.size());
    }
    @Test
    public void actorIsNotValid_badID() {
        // stand up some test data first
        Actor a = new Actor(
                -33,
                "James",
                "Green",
                LocalDate.of(2021, 7, 12));

        // validate the data
        Set<ConstraintViolation<Actor>> violations = validator.validate(a);

        for (ConstraintViolation<Actor> violation : violations) {
            System.out.println(violation.toString());
        }

        // assert pass or fail
        //Assertions.assertEquals(0, violations.size());
    }
    
    @Test
    public void actorIsNotValid_badFirstName() {
        // stand up some test data first
        Actor a = new Actor(
                1,
                " ",
                "Green",
                LocalDate.of(2021, 7, 12));

        // validate the data
        Set<ConstraintViolation<Actor>> violations = validator.validate(a);

        for (ConstraintViolation<Actor> violation : violations) {
            System.out.println(violation.toString());
        }

    }
    @AfterEach
    public void afterEach() {
    }

    @AfterAll
    public static void afterAll() {
    }
}
