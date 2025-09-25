package com.blackcode.book_store_be.model.staff;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_refresh_token_staff", uniqueConstraints = @UniqueConstraint(columnNames = "staffId"))
public class StaffRefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn(name = "staff_id", referencedColumnName = "staffId")
    private Staff staff;

    @Column(nullable = false, unique = true)
    private String token;

    @Column(nullable = false)
    private Instant expiryDate;
}
