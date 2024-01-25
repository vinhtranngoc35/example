package com.example.test.domain;

import jakarta.persistence.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.util.List;

@Entity
@Table(name = "categories")
@SQLDelete(sql = "UPDATE categories SET deleted = 'DELETED' WHERE id = ?")
public class Category  extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    public Category(){}

    public Category(Long id){
        this.id = id;
    }
    public Category(Long id, String name){
        this.id = id;
        this.name = name;
    }

    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
    @Where(clause = "deleted <> 'DELETED'")
    private List<Product> products;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
