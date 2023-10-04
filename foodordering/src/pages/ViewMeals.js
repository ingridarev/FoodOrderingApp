import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Container, Item } from "semantic-ui-react";
import drinksImage from "../images/drinks.jpg";

import breakfastImage from "../images/breakfast.jpg";
import desertsImage from "../images/deserts.jpg";
import dinnerImage from "../images/dinner.jpg";
import burgerImage from "../images/burger.jpg";
import foodImage from "../images/food.jpg";

export function ViewMeals() {
  const params = useParams();
  const [meals, setMeals] = useState([]);

  const getImageForMenu = (menu) => {
    switch (menu.title.toLowerCase()) {
      case "breakfast":
        return breakfastImage;
      case "deserts":
        return desertsImage;
      case "dinner":
        return dinnerImage;
      case "drinks":
        return drinksImage;
      case "burgers":
        return burgerImage;
      default:
        return foodImage;
    }
  };

  useEffect(() => {
    if (params.id) {
      fetch(`/api/v1/meals/all/${params.id}`)
        .then((response) => response.json())
        .then(setMeals)
        .catch((error) => console.error("Error fetching meals:", error));
    }
  }, [params.id]);

  const renderMeals = () => {
    return meals.map((meal) => (
      <Item>
        <Item.Image size="tiny" src={getImageForMenu(meal.menu)} />
        <Item.Content verticalAlign="middle">
          <b>{meal.title}</b>
        </Item.Content>
      </Item>
    ));
  };

  return (
    <Container className="meals mt-5">
      <Item.Group divided>{renderMeals()}</Item.Group>
    </Container>
  );
}
