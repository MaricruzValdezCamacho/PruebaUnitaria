package PruebasUnitarias;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {

        Optional<Order> findById(long id);
        public Order save(Order order);
        void delete(Order order);

        List<Order> findAll();


    }

