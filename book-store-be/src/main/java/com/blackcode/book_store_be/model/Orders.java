package com.blackcode.book_store_be.model;

import com.blackcode.book_store_be.model.user.User;
import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "tb_orders")
public class Orders {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ordersId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User userOder;

    private String orderTotalPrice;

    private String ordersStatus;

    private String ordersCreateAt;

}
