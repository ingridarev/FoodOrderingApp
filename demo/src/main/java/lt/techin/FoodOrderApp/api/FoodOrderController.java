package lt.techin.FoodOrderApp.api;

import lt.techin.FoodOrderApp.Model.FoodOrder;
import lt.techin.FoodOrderApp.Service.FoodOrderService;
import lt.techin.FoodOrderApp.api.dto.FoodOrderDto;
import lt.techin.FoodOrderApp.api.dto.FoodOrderEntityDto;
import lt.techin.FoodOrderApp.api.dto.Mapper.FoodOrderMapper;
import lt.techin.FoodOrderApp.users.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static lt.techin.FoodOrderApp.api.dto.Mapper.FoodOrderMapper.toFoodOrder;
import static org.springframework.http.ResponseEntity.ok;

@Controller
@RequestMapping("/api/v1/foodOrders")
@Validated
public class FoodOrderController {

    private final Logger logger = LoggerFactory.getLogger(FoodOrderController.class);
    private final FoodOrderService foodOrderService;

    private final UserService userService;

    public FoodOrderController(FoodOrderService foodOrderService, UserService userService) {
        this.foodOrderService = foodOrderService;
        this.userService = userService;
    }

    @GetMapping
    @ResponseBody
    public List<FoodOrderEntityDto> getOrders() {
        return foodOrderService.getAll().stream()
                .map(FoodOrderMapper::toOrderEntityDto)
                .collect(toList());
    }

    @GetMapping("/unconfirmed")
    public ResponseEntity<List<FoodOrder>> getAllUnconfirmedOrders() {
        List<FoodOrder> unconfirmedOrders = foodOrderService.getAllUnconfirmedOrders();
        return new ResponseEntity<>(unconfirmedOrders, HttpStatus.OK);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<FoodOrder> createFoodOrder(@RequestBody FoodOrderDto foodOrderDto) {
        foodOrderDto.setConfirmed(false);

        var orders = foodOrderService.getAllUnconfirmedOrders();
        if (orders.isEmpty()) {
            FoodOrder createdFoodOrder = foodOrderService.create(toFoodOrder(foodOrderDto));
            return ResponseEntity.ok(createdFoodOrder);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{orderId}/confirm")
    public ResponseEntity<String> confirmFoodOrder(@PathVariable Long orderId) {
        try {
            FoodOrder confirmedOrder = foodOrderService.confirmOrder(orderId);
            return ResponseEntity.ok("Food order with ID " + orderId + " confirmed successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(404).body("Food order with ID " + orderId + " not found.");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while confirming the food order.");
        }
    }

//    @GetMapping("/by-user/{userId}/")
//    public List<FoodOrder> getFoodOrdersByUserId(@PathVariable Long userId) {
//        return foodOrderService.getFoodOrdersByUserId(userId);
//    }

    @GetMapping("/user/{userId}/foodorders")
    public ResponseEntity<List<FoodOrder>> getFoodOrdersByUserId(@PathVariable Long userId) {
        List<FoodOrder> foodOrders = userService.getFoodOrdersByUserId(userId);

        if (foodOrders.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(foodOrders, HttpStatus.OK);
        }
    }


}