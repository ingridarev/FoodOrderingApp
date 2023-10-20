package lt.techin.FoodOrderApp.api.dto.Mapper;

import lt.techin.FoodOrderApp.Model.OrderItem;
import lt.techin.FoodOrderApp.api.dto.OrderItemDto;
import lt.techin.FoodOrderApp.api.dto.OrderItemEntityDto;

public class OrderItemMapper {

    public static OrderItemDto toOrderItemDto(OrderItem orderItem) {
        var orderItemDto = new OrderItemDto();

        orderItemDto.setMeal(orderItem.getMeal());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setFoodOrder(orderItem.getFoodOrder());

        return orderItemDto;
    }

    public static OrderItemEntityDto toOrderItemEntityDto(OrderItem orderItem) {
        var orderItemDto = new OrderItemEntityDto();

        orderItemDto.setId(orderItem.getId());
        orderItemDto.setMeal(orderItem.getMeal());
        orderItemDto.setQuantity(orderItem.getQuantity());
        orderItemDto.setFoodOrder(orderItem.getFoodOrder());

        return orderItemDto;
    }

    public static OrderItem toOrderItem(OrderItemDto orderItemDto) {
        var orderItem = new OrderItem();

        orderItem.setMeal(orderItemDto.getMeal());
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setFoodOrder(orderItemDto.getFoodOrder());

        return orderItem;
    }

    public static OrderItem toOrderItemFromEntityDto(OrderItemEntityDto orderItemDto) {
        var orderItem = new OrderItem();

        orderItem.setMeal(orderItemDto.getMeal());
        orderItem.setQuantity(orderItemDto.getQuantity());
        orderItem.setFoodOrder(orderItemDto.getFoodOrder());

        return orderItem;
    }
}
