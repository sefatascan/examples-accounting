package com.sefa.accounting.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.Set;

@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"firstName", "lastName", "email"}))
public class Specialist extends AbstractEntity {

    private String firstName;
    private String lastName;
    private String email;

    @OneToMany(mappedBy = "specialist", fetch = FetchType.LAZY)
    private Set<Transaction> transaction;
}
