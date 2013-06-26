package com.kmware.processors;

import com.kmware.ui.constant.StringConstants;
import com.kmware.ui.enums.FormInputType;
import com.kmware.ui.scaffold.EditField;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.kmware.ui.enums.FormInputType.*;

/**
 * Created with IntelliJ IDEA.
 * User: oleg
 * Date: 6/20/13
 * Time: 4:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class EditFieldsProcessor extends ProcessorBase {
    public static final String ENTITY_FIELD_NAME = "entity";

    public EditFieldsProcessor(Class<?> klazz, String beanName) {
        super(klazz, beanName, ENTITY_FIELD_NAME);
    }

    public EditFieldsProcessor(Class<?> klazz, String beanName, String entityField) {
        super(klazz, beanName, entityField);
    }

    @Override
    public List<EditField> processEditFields() {
        List<Method> getters = getGetters();
        Set<EditField> fields = new HashSet<EditField>();

        for (Method m : getters) {
            if (m.isAnnotationPresent(com.kmware.ui.annotations.EditField.class)) {
                com.kmware.ui.annotations.EditField annot = m.getAnnotation(com.kmware.ui.annotations.EditField.class);
                EditField ef = new EditField();
                ef.setId(getFieldName(m));
                if (!fields.contains(ef)) {
                    ef.setLabel(new String(annot.label()));
                    ef.setMaxLength(annot.maxLength());
                    ef.setPlaceholder(annot.placeholder());
                    ef.setPlaceholderText(new String(annot.placeholderText()));
                    ef.setRequired(new String(annot.required()));
                    ef.setRendered(new String(annot.rendered()));
                    FormInputType type = annot.type();
                    ef.setType(type);

                    if (type != TEXT && type != CHECKBOX) {
                        ef.setValues(new String(annot.values()));
                        ef.setConverter(new String(annot.converter()));
                        ef.setVar(new String(annot.var()));
                        ef.setItemLabel( String.format("#{%s.%s}", ef.getVar(), annot.itemLabel()) );
                        ef.setItemValue( String.format("#{%s.%s}", ef.getVar(), annot.itemValue()) );
                    }

                    ef.setValue( String.format("#{%s.%s.%s}", beanName, entityField, (!annot.value().equals("")? annot.value() : getFieldName(m) ) ) );
                }
            }
        }
        return new ArrayList<EditField>(fields);
    }

}
