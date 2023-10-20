package lt.techin.FoodOrderApp.api.dto.Mapper;


import lt.techin.FoodOrderApp.Model.FoodOrder;
import lt.techin.FoodOrderApp.api.dto.FoodOrderDto;
import lt.techin.FoodOrderApp.api.dto.FoodOrderEntityDto;

public class FoodOrderMapper {

    public static FoodOrderDto toFoodOrderDto(FoodOrder foodOrder) {
        var foodOrderDto = new FoodOrderDto();

        foodOrderDto.setCreatedDate(foodOrder.getCreatedDate());
        foodOrderDto.setTotalAmount(foodOrder.getTotalAmount());
        foodOrderDto.setOrderItems(foodOrder.getOrderItems());
        foodOrderDto.setConfirmed(foodOrder.getConfirmed());
        foodOrderDto.setUser(foodOrder.getUser());

        return foodOrderDto;
    }

    public static FoodOrderEntityDto toOrderEntityDto(FoodOrder foodOrder) {
        var foodOrderDto = new FoodOrderEntityDto();

        foodOrderDto.setId(foodOrder.getId());
        foodOrderDto.setCreatedDate(foodOrder.getCreatedDate());
        foodOrderDto.setTotalAmount(foodOrder.getTotalAmount());
        foodOrderDto.setOrderItems(foodOrder.getOrderItems());
        foodOrderDto.setConfirmed(foodOrder.getConfirmed());
        foodOrderDto.setUser(foodOrder.getUser());

        return foodOrderDto;
    }

    public static FoodOrder toFoodOrder(FoodOrderDto foodOrderDto) {
        var foodOrder = new FoodOrder();

        foodOrder.setCreatedDate(foodOrderDto.getCreatedDate());
        foodOrder.setTotalAmount(foodOrderDto.getTotalAmount());
        foodOrder.setOrderItems(foodOrderDto.getOrderItems());
        foodOrder.setConfirmed(foodOrderDto.getConfirmed());
        foodOrder.setUser(foodOrderDto.getUser());

        return foodOrder;
    }

    public static FoodOrder toFoodOrderEntityDto(FoodOrderEntityDto foodOrderDto) {
        var foodOrder = new FoodOrder();

        foodOrder.setId(foodOrderDto.getId());
        foodOrder.setCreatedDate(foodOrderDto.getCreatedDate());
        foodOrder.setTotalAmount(foodOrderDto.getTotalAmount());
        foodOrder.setOrderItems(foodOrderDto.getOrderItems());
        foodOrder.setConfirmed(foodOrderDto.getConfirmed());
        foodOrder.setUser(foodOrderDto.getUser());

        return foodOrder;
    }
}
