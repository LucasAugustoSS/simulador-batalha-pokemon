package com.github.lucasaugustoss.data.objects.templates;

import com.github.lucasaugustoss.data.classes.Type;

public class TypeTemplate {
    private String id;
    private String name;
    private TypeTemplate[] superEffective;
    private TypeTemplate[] notVeryEffective;
    private TypeTemplate[] ineffective;
    private Object[] additionalImmunities;

    public TypeTemplate(
        String id, String name
    ) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public TypeTemplate[] getSuperEffective() {
        return superEffective;
    }

    public TypeTemplate[] getNotVeryEffective() {
        return notVeryEffective;
    }

    public TypeTemplate[] getIneffective() {
        return ineffective;
    }

    public Object[] getAdditionalImmunities() {
        return additionalImmunities;
    }

    public void setSuperEffective(TypeTemplate[] superEffective) {
        this.superEffective = superEffective;
    }

    public void setNotVeryEffective(TypeTemplate[] notVeryEffective) {
        this.notVeryEffective = notVeryEffective;
    }

    public void setIneffective(TypeTemplate[] ineffective) {
        this.ineffective = ineffective;
    }

    public void setAdditionalImmunities(Object[] additionalImmunities) {
        this.additionalImmunities = additionalImmunities;
    }

    public boolean compare(TypeTemplate other) {
        return this.name.equals(other.getName());
    }

    public boolean compare(Type type) {
        return this.name.equals(type.getName());
    }
}
