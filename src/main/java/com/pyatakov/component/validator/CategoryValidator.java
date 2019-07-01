package com.pyatakov.component.validator;

import com.pyatakov.entity.Category;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component("categoryValidator")
public class CategoryValidator implements Validator {
    
    @Override
    public boolean supports(Class<?> aClass) {
        return Category.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name","name.empty"
                ,"Name must not be empty");

        Category category = (Category) o;
        if (!errors.hasErrors()) {
             if (category.getName().length() < 3)
                 errors.rejectValue("name", "field.invalid"
                         , "Name must be lenght > 3");
        }
    }

}
