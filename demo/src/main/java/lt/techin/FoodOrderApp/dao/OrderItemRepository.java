package lt.techin.FoodOrderApp.dao;

import lt.techin.FoodOrderApp.Model.Menu;
import lt.techin.FoodOrderApp.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByFoodOrder_Id(Long foodOrderId);
}
