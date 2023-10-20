package lt.techin.FoodOrderApp.Service;

import lt.techin.FoodOrderApp.Model.Meal;
import lt.techin.FoodOrderApp.Model.OrderItem;
import lt.techin.FoodOrderApp.dao.OrderItemRepository;
import lt.techin.FoodOrderApp.exception.FoodOrderAppValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<OrderItem> getAllOrderItems() {
        return orderItemRepository.findAll();
    }

    public OrderItem getOrderItemById(Long orderItemId) {
        return orderItemRepository.findById(orderItemId)
                .orElseThrow(() -> new EntityNotFoundException("OrderItem not found with id: " + orderItemId));
    }

    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    public void deleteOrderItem(Long orderItemId) {
        orderItemRepository.deleteById(orderItemId);
    }

    public List<OrderItem> getOrderItemsByFoodOrderId(Long foodOrderId) {
        return orderItemRepository.findByFoodOrder_Id(foodOrderId);
    }

    public OrderItem update(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

}
