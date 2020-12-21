/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.payment.pay.entities;

/**
 *
 * @author Carlos TCHIOZEM
 */
public class RegistrationUser {
    
    private String PreferredName;
     private String Address1;
     private String City;
     private String Country;
     private String StateRegion;
     private String BirthDate;
     private int IDType;
     private String IDValue;
     private String PhoneNumber;
     private String CustomerSource;
     private int ReferredBy;
     private int SubCompany;

    public RegistrationUser() {
    }

    public String getPreferredName() {
        return PreferredName;
    }

    public void setPreferredName(String PreferredName) {
        this.PreferredName = PreferredName;
    }

    public String getAddress1() {
        return Address1;
    }

    public void setAddress1(String Address1) {
        this.Address1 = Address1;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String City) {
        this.City = City;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String Country) {
        this.Country = Country;
    }

    public String getStateRegion() {
        return StateRegion;
    }

    public void setStateRegion(String StateRegion) {
        this.StateRegion = StateRegion;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String BirthDate) {
        this.BirthDate = BirthDate;
    }

    public int getIDType() {
        return IDType;
    }

    public void setIDType(int IDType) {
        this.IDType = IDType;
    }

    public String getIDValue() {
        return IDValue;
    }

    public void setIDValue(String IDValue) {
        this.IDValue = IDValue;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String PhoneNumber) {
        this.PhoneNumber = PhoneNumber;
    }

    public String getCustomerSource() {
        return CustomerSource;
    }

    public void setCustomerSource(String CustomerSource) {
        this.CustomerSource = CustomerSource;
    }

    public int getReferredBy() {
        return ReferredBy;
    }

    public void setReferredBy(int ReferredBy) {
        this.ReferredBy = ReferredBy;
    }

    public int getSubCompany() {
        return SubCompany;
    }

    public void setSubCompany(int SubCompany) {
        this.SubCompany = SubCompany;
    }
     
    
     
     
     
     
    
}
