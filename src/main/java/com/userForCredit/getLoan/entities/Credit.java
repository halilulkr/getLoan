package com.userForCredit.getLoan.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "credits")
public class Credit extends BaseEntity implements Serializable {

    @Column(name = "credit_amount")
    private Double creditAmount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
