package ma.therightman.orderkafka.repositories;

import ma.therightman.orderkafka.entities.OrderItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItemEntity,Long> {
}
