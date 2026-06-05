package com.devapix.auth_service.model;

import com.devapix.auth_service.enums.Role;
import jakarta.persistence.*;
import java.sql.Timestamp;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="users")
public class User {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Integer userid;
     private String name;
     private String email;
     private String password;
     @Enumerated(EnumType.STRING)
     private Role role;
     @CreationTimestamp
     private Timestamp created_at;
}