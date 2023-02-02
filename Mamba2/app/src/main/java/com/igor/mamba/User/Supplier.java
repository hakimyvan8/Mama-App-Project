package com.igor.mamba.User;

public class Supplier {
    private String uuid ;
    private String supplier_email;
    private String supplier_state;
    private String supplier_district;
    private String supplier_Business;
    private String supplier_phone;

    public Supplier() {
    }

    public Supplier(String uuid, String supplier_email, String supplier_state, String supplier_district, String supplier_Business, String supplier_phone) {
        this.uuid = uuid;
        this.supplier_email = supplier_email;
        this.supplier_state = supplier_state;
        this.supplier_district = supplier_district;
        this.supplier_Business = supplier_Business;
        this.supplier_phone = supplier_phone;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSupplier_email() {
        return supplier_email;
    }

    public void setSupplier_email(String supplier_email) {
        this.supplier_email = supplier_email;
    }

    public String getSupplier_state() {
        return supplier_state;
    }

    public void setSupplier_state(String supplier_state) {
        this.supplier_state = supplier_state;
    }

    public String getSupplier_district() {
        return supplier_district;
    }

    public void setSupplier_district(String supplier_district) {
        this.supplier_district = supplier_district;
    }

    public String getSupplier_Business() {
        return supplier_Business;
    }

    public void setSupplier_Business(String supplier_Business) {
        this.supplier_Business = supplier_Business;
    }

    public String getSupplier_phone() {
        return supplier_phone;
    }

    public void setSupplier_phone(String supplier_phone) {
        this.supplier_phone = supplier_phone;
    }
}
