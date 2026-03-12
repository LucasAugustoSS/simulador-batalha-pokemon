package com.github.lucasaugustoss.data.objects.templates;

import com.github.lucasaugustoss.data.classes.Type;
import com.github.lucasaugustoss.loader.dtos.AdditionalImmunitiesDTO;

public class TypeTemplate extends Template {
    private String name;
    private TypeTemplate[] superEffective;
    private TypeTemplate[] notVeryEffective;
    private TypeTemplate[] ineffective;
    private AdditionalImmunitiesDTO additionalImmunityDTO;
    private Object[] additionalImmunities;

    public TypeTemplate(
        int index, String id,
        String name, AdditionalImmunitiesDTO additionalImmunityDTO
    ) {
        super(index, id);
        this.name = name;
        this.additionalImmunityDTO = additionalImmunityDTO;
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

    public AdditionalImmunitiesDTO getAdditionalImmunityDTO() {
        return additionalImmunityDTO;
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
