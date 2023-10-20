package lt.techin.FoodOrderApp.Service;

import lt.techin.FoodOrderApp.Model.FoodOrder;
import lt.techin.FoodOrderApp.Model.OrderItem;
import lt.techin.FoodOrderApp.api.dto.OrderItemDto;
import lt.techin.FoodOrderApp.dao.FoodOrderRepository;
import lt.techin.FoodOrderApp.dao.MealRepository;
import lt.techin.FoodOrderApp.dao.OrderItemRepository;
import lt.techin.FoodOrderApp.exception.FoodOrderAppValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static lt.techin.FoodOrderApp.api.dto.Mapper.OrderItemMapper.toOrderItem;

@Service
public class FoodOrderService {

    private final FoodOrderRepository foodOrderRepository;

    public FoodOrderService(FoodOrderRepository foodOrderRepository, MealRepository mealRepository, OrderItemRepository orderItemRepository, OrderItemService orderItemService, MealRepository mealRepository1, OrderItemRepository orderItemRepository1) {
        this.foodOrderRepository = foodOrderRepository;
    }

    @Autowired


    public List<FoodOrder> getAll() {
        return foodOrderRepository.findAll();
    }

    public FoodOrder create(FoodOrder foodOrder) {
        return foodOrderRepository.save(foodOrder);
    }

    public FoodOrder update(Long id, FoodOrder foodOrder) {
        var existingFoodOrder = foodOrderRepository.findById(id)
                .orElseThrow(() -> new FoodOrderAppValidationException("FoodOrder does not exist",
                        "id", "FoodOrder not found", id.toString()));

        existingFoodOrder.setCreatedDate(foodOrder.getCreatedDate());
        existingFoodOrder.setOrderItems(foodOrder.getOrderItems());
        existingFoodOrder.setTotalAmount(foodOrder.getTotalAmount());
        existingFoodOrder.setConfirmed(foodOrder.getConfirmed());

        return foodOrderRepository.save(existingFoodOrder);
    }

    public FoodOrder getById(Long id) {
        return foodOrderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found with id: " + id));
    }

    public boolean deleteById(Long id) {
        if (foodOrderRepository.existsById(id)) {
            foodOrderRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void updateFoodOrderTotalAmount(Long orderId, double totalAmount) {
        FoodOrder foodOrder = getById(orderId);
        foodOrder.setTotalAmount(totalAmount);
        foodOrderRepository.save(foodOrder);
    }

    public List<FoodOrder> getAllUnconfirmedOrders() {
        return foodOrderRepository.findByIsConfirmedFalse();
    }

    public FoodOrder addOrderItemToFoodOrder(Long foodOrderId, OrderItem orderItem) {
        FoodOrder foodOrder = foodOrderRepository.findById(foodOrderId)
                .orElseThrow(() -> new FoodOrderAppValidationException("FoodOrder does not exist",
                        "id", "FoodOrder not found", foodOrderId.toString()));

        orderItem.setFoodOrder(foodOrder);
        foodOrder.getOrderItems().add(orderItem);
        foodOrder = foodOrderRepository.save(foodOrder);

        return foodOrder;
    }

    @Transactional
    public FoodOrder confirmOrder(Long orderId) {
        FoodOrder foodOrder = getById(orderId);
        foodOrder.setConfirmed(true);
        return foodOrderRepository.save(foodOrder);
    }


}

