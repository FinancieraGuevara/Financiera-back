package com.financierag.financieraguevaraapi.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sede")
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="sed_id_in")
    private Integer id;

    @Column(name = "sed_nam_var", nullable = false)
    private String name;

    @Column(name = "sed_addr_var", nullable = false)
    private String address;

    @OneToOne
    @JoinColumn(name = "user_id_in", referencedColumnName = "user_id")
    private User user;

}
