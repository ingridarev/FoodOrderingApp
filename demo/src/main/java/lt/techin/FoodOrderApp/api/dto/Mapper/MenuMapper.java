package lt.techin.FoodOrderApp.api.dto.Mapper;


import lt.techin.FoodOrderApp.Model.Menu;
import lt.techin.FoodOrderApp.api.dto.MenuDto;
import lt.techin.FoodOrderApp.api.dto.MenuEntityDto;

public class MenuMapper {

    public static MenuDto toMenuDto(Menu menu) {
        var menuDto = new MenuDto();

        menuDto.setTitle(menu.getTitle());
        menuDto.setMeals(menu.getMeals());

        return menuDto;
    }

    public static MenuEntityDto toMenuEntityDto(Menu menu) {
        var menuDto = new MenuEntityDto();
        menuDto.setId(menu.getId());
        menuDto.setTitle(menu.getTitle());
        menuDto.setMeals(menu.getMeals());

        return menuDto;
    }

    public static Menu toMenu(MenuDto menuDto) {
        var menu = new Menu();

        menu.setTitle(menuDto.getTitle());
        menu.setMeals(menuDto.getMeals());

        return menu;
    }

    public static Menu toMenuEntityDto(MenuEntityDto menuDto) {
        var menu = new Menu();

        menuDto.setId(menu.getId());
        menu.setTitle(menuDto.getTitle());
        menu.setMeals(menuDto.getMeals());

        return menu;
    }
}
