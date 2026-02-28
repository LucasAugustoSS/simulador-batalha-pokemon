package com.github.lucasaugustoss.data.objects.templates;

public class Template {
    private int index;
    private String id;

    public Template(int index, String id) {
        this.index = index;
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public String getID() {
        return id;
    }
}
