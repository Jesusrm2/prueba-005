package com.prueba.jesus.model.entity;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Set;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user")
public class Usuario {
   @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer userid;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Date creationdate;

    @Column(nullable = false)
    private Integer usercreate;


    private Integer userapproval;
    

    private Date dateapproval;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "rolid")
    private Rol rol_rolid;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "statusid")
    private UserStatus status_statusid;

    @ManyToMany
    @JoinTable(
        name = "usercash",
        joinColumns = @JoinColumn(name = "user_userid"),
        inverseJoinColumns = @JoinColumn(name = "cash_cashid"))
    private Set<Cash> usercash;









    
}
