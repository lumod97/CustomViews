package com.myaconsultancy.customviews.ValidatorInputLayout;

public class ValidatorInputLayoutItem {
    private String id;
    private String descripcion;
    private String secondaryId;

    // Constructor
    public ValidatorInputLayoutItem(String id, String descripcion, String secondaryId) {
        this.id = id;
        this.descripcion = descripcion;
        this.secondaryId = secondaryId;
    }

    // Getter y Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = String.valueOf(id);
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getSecondaryId() {
        return secondaryId;
    }

    public void setSecondaryId(String secondaryId) {
        this.secondaryId = secondaryId;
    }
}
