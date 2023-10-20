package lt.techin.FoodOrderApp.api;

import lt.techin.FoodOrderApp.Model.Meal;
import lt.techin.FoodOrderApp.Model.Menu;
import lt.techin.FoodOrderApp.Service.MealService;
import lt.techin.FoodOrderApp.Service.MenuService;
import lt.techin.FoodOrderApp.api.dto.Mapper.MenuMapper;
import lt.techin.FoodOrderApp.api.dto.MenuDto;
import lt.techin.FoodOrderApp.api.dto.MenuEntityDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static lt.techin.FoodOrderApp.api.dto.Mapper.MenuMapper.toMenu;
import static lt.techin.FoodOrderApp.api.dto.Mapper.MenuMapper.toMenuDto;

@Controller
@RequestMapping("/api/v1/menus")
@Validated
public class MenuController {

    private final Logger logger = LoggerFactory.getLogger(MenuController.class);
    private final MenuService menuService;
    private final MealService mealService;

    public MenuController(MenuService menuService, MealService mealService) {
        this.menuService = menuService;
        this.mealService = mealService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<List<MenuEntityDto>> getMenus() {
        List<MenuEntityDto> menuDtos = menuService.getAll().stream()
                .map(MenuMapper::toMenuEntityDto)
                .collect(toList());
        return ResponseEntity.ok(menuDtos);
    }

    @PostMapping(consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MenuDto> createMenu(@RequestBody MenuDto menuDto) {
        Menu createdMenu = menuService.create(toMenu(menuDto));
        return ResponseEntity.ok(toMenuDto(createdMenu));
    }

    @PatchMapping("/{menuId}")
    public ResponseEntity<MenuDto> update(@PathVariable Long menuId, @RequestBody MenuDto menuDto) {
        Menu updatedMenu = menuService.update(menuId, toMenu(menuDto));
        return ResponseEntity.ok(toMenuDto(updatedMenu));
    }

    @GetMapping(value = "/{menuId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<MenuEntityDto> getMenuById(@PathVariable Long menuId) {
        var menuOptional = menuService.getById(menuId);

        return menuOptional
                .map(menu -> ResponseEntity.ok(MenuMapper.toMenuEntityDto(menu)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/{menuId}/meals", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Meal>> getMealByMenuId(@PathVariable Long menuId) {
        List<Meal> meal = menuService.getMealByMenuId(menuId);
        return new ResponseEntity<>(meal, HttpStatus.OK);
    }

    @DeleteMapping("/{menuId}")
    public ResponseEntity<Void> deleteMenu(@PathVariable Long menuId) {
        logger.info("Attempt to delete menu by id: {}", menuId);

        boolean deleted = menuService.deleteMenuAndMeal(menuId);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
