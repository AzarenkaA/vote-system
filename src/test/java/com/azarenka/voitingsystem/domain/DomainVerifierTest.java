package com.azarenka.voitingsystem.domain;

import com.azarenka.votingsystem.domain.BaseEntity;
import com.azarenka.votingsystem.domain.Meal;
import com.azarenka.votingsystem.domain.User;
import com.azarenka.votingsystem.domain.auth.JwtResponse;
import com.azarenka.votingsystem.domain.auth.LoginForm;
import com.azarenka.votingsystem.domain.auth.SignUpForm;
import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.GetterMustExistRule;
import com.openpojo.validation.rule.impl.NoFieldShadowingRule;
import com.openpojo.validation.rule.impl.NoStaticExceptFinalRule;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Verifies domain.
 * <p>
 * (c) ant-azarenko@mail.ru 2020
 * </p>
 *
 * @author Anton Azarenka
 * Date 23.11.2020
 */
public class DomainVerifierTest {

    private static final Validator VALIDATOR = ValidatorBuilder.create()
        .with(new GetterMustExistRule())
        .with(new NoFieldShadowingRule())
        .with(new NoStaticExceptFinalRule())
        .with(new SetterTester())
        .with(new GetterTester())
        .build();

    @Test
    public void testPojoStructureAndBehavior() {
        testPojoStructureAndBehavior(
            Arrays.asList(BaseEntity.class, User.class, LoginForm.class, JwtResponse.class, SignUpForm.class, Meal.class));
    }

    private  void testPojoStructureAndBehavior(List<Class> classes) {
        List<PojoClass> pojoClasses = classes.stream().map(PojoClassFactory::getPojoClass).collect(Collectors.toList());
        VALIDATOR.validate(pojoClasses);
    }
}
