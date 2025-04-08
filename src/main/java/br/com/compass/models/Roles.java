package br.com.compass.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Roles {
    @Id
    private Long id;
    private String name;
    private String description;
} 