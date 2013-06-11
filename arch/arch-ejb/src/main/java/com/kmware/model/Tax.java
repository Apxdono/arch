package com.kmware.model;

import javax.persistence.*;

@Table(name = "taxes")
@Entity
public class Tax extends DBObject {
    Country country = new Country();
    City city = new City();
    Company company = new Company();
    Double averageTax = Double.valueOf(0);

    public Tax(){
    }

    @ManyToOne
    @JoinColumn(name = "country_id")
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @ManyToOne
    @JoinColumn(name = "city_id")
    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    @ManyToOne
    @JoinColumn(name = "company_id")
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Column(name = "average_tax")
    public Double getAverageTax() {
        return averageTax;
    }

    public void setAverageTax(Double averageTax) {
        this.averageTax = averageTax;
    }

}
