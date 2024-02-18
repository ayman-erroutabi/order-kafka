package order.orderkafka.repositories;

import order.orderkafka.entities.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity,Long> {

   OrderEntity findOrderEntityById(long id);
}
