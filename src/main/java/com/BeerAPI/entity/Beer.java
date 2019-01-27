package com.BeerAPI.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name="Beer")
@Table(name="Beer")
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Beer_Id", length = 10)
    private Integer beerID;

    @Column(name = "Beer_Name", length = 100)
    private String beerName;

    @Column(name = "Country", length = 100)
    private String country;

    @Column(name = "Price")
    private Double price;

    @Column(name = "Description", length = 5000)
    private String description;

    @Column(name = "Category_Id", length = 10)
    private Integer categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Category_Id", insertable = false, updatable = false)
    private Category category;
}
