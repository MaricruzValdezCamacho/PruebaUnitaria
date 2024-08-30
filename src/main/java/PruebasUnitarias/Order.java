package PruebasUnitarias;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Order {
   // private int id;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;


    private double amount;

    /**/
    public Order(long id, double amount) {
        this.id = id;
        this.amount = amount;
    }


    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


    public void setAmount(double amount) {
        this.amount = amount;
        //this.amount = 300.00;
    }


    public double getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "Order{id=" + id + ", amount=" + amount + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Math.toIntExact(id);
    }
}
