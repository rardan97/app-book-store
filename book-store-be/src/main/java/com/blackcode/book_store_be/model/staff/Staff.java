package com.blackcode.book_store_be.model.staff;


import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_staff")
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long staffId;

    private String staffFullName;

    private String staffEmail;

    private String staffUsername;

    private String staffPassword;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roleStaffId")
    private StaffRole staffRole;

    public Staff(String staffFullName, String staffEmail, String staffUsername, String staffPassword, StaffRole staffRole) {
        this.staffFullName = staffFullName;
        this.staffEmail = staffEmail;
        this.staffUsername = staffUsername;
        this.staffPassword = staffPassword;
        this.staffRole = staffRole;
    }
}
