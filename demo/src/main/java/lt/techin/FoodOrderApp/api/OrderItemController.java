package lt.techin.FoodOrderApp.api;

import lt.techin.FoodOrderApp.Model.FoodOrder;
import lt.techin.FoodOrderApp.Model.Meal;
import lt.techin.FoodOrderApp.Model.OrderItem;
import lt.techin.FoodOrderApp.Service.FoodOrderService;
import lt.techin.FoodOrderApp.Service.MealService;
import lt.techin.FoodOrderApp.Service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/api/v1/orderItems")
@Validated
public class OrderItemController {

    private final Logger logger = LoggerFactory.getLogger(OrderItemController.class);
    private final OrderItemService orderItemService;
    private final FoodOrderService foodOrderService;
    private final MealService mealService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService, FoodOrderService foodOrderService, MealService mealService) {
        this.orderItemService = orderItemService;
        this.foodOrderService = foodOrderService;
        this.mealService = mealService;
    }

    @GetMapping
    @ResponseBody
    public List<OrderItem> getOrderItems() {
        return orderItemService.getAllOrderItems();
    }

    @GetMapping("/{orderItemId}")
    @ResponseBody
    public ResponseEntity<OrderItem> getOrderItem(@PathVariable Long orderItemId) {
        OrderItem orderItem = orderItemService.getOrderItemById(orderItemId);
        return ResponseEntity.ok(orderItem);
    }

    @PostMapping("/{foodOrderId}/{mealId}/{quantity}/newOrderItem")
    public ResponseEntity<OrderItem> createOrderItem(@PathVariable Long foodOrderId, @PathVariable Long mealId, @PathVariable Integer quantity
    ) {
        FoodOrder foodOrder = foodOrderService.getById(foodOrderId);
        Meal meal = mealService.getById(mealId);

        if (foodOrder == null || meal == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Optional<OrderItem> existingItem = foodOrder.getOrderItems().stream()
                .filter(orderItem -> orderItem.getMeal().getId().equals(mealId))
                .findFirst();

        if (existingItem.isPresent()) {
            // Order item with the same meal exists, update the quantity
            OrderItem orderItem = existingItem.get();
            orderItem.setQuantity(orderItem.getQuantity() + quantity);
            OrderItem updatedOrderItem = orderItemService.update(orderItem);
            return ResponseEntity.status(HttpStatus.CREATED).body(updatedOrderItem);
        } else {
            // Order item with the same meal doesn't exist, create a new order item
            OrderItem orderItem = new OrderItem();
            orderItem.setMeal(meal);
            orderItem.setFoodOrder(foodOrder);
            orderItem.setQuantity(quantity);

            OrderItem createdOrderItem = orderItemService.createOrderItem(orderItem);

            return ResponseEntity.status(HttpStatus.CREATED).body(createdOrderItem);
        }
    }

    @DeleteMapping("/{orderItemId}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long orderItemId) {
        orderItemService.deleteOrderItem(orderItemId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/foodOrder/{foodOrderId}")
    @ResponseBody
    public List<OrderItem> getOrderItemsByFoodOrderId(@PathVariable Long foodOrderId) {
        return orderItemService.getOrderItemsByFoodOrderId(foodOrderId);
    }
}


