import { useState, useEffect, useContext } from "react";
import { Link, NavLink } from "react-router-dom";
import {
  Card,
  Container,
  Button,
  Icon,
  Confirm,
  Image,
} from "semantic-ui-react";
import AuthContext from "../AuthContext";
import drinksImage from "../images/drinks.jpg";

import breakfastImage from "../images/breakfast.jpg";
import desertsImage from "../images/deserts.jpg";
import dinnerImage from "../images/dinner.jpg";
import burgerImage from "../images/burger.jpg";
import foodImage from "../images/food.jpg";

export function ViewMenus() {
  const { appState, setAppState } = useContext(AuthContext);

  const [menus, setMenus] = useState([]);
  const [open, setOpen] = useState(false);

  const fetchMenus = async () => {
    fetch("/api/v1/menus")
      .then((response) => response.json())
      .then((jsonResponse) => setMenus(jsonResponse));
  };

  const deleteMenus = async (id) => {
    fetch(`/api/v1/menus/` + id, {
      method: "DELETE",
    })
      .then((response) => {
        if (response.ok) {
          const updatedMenus = menus.filter((menu) => menu.id !== id);
          setMenus(updatedMenus);
        }
      })
      .catch((error) => {
        console.error("Error deleting menus:", error);
      });
  };

  useEffect(() => {
    fetchMenus();
  }, []);

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

  return (
    <Container className="menus mt-2" textAlign="center">
      {appState.isAuthenticated && (
        <Button
          className="create-button mb-4"
          style={{
            color: "grey",
            backgroundColor: "transparent",
            border: "1px solid grey",
          }}
          name="create"
          active={false}
          as={Link}
          to="/menus/create"
        >
          Add new menu
        </Button>
      )}
      <Card.Group centered>
        {menus.map((menu) => (
          <Card className="card" key={menu.id}>
            <Card.Content className="centered">
              <Image src={getImageForMenu(menu)} />
              <Card.Header className="m-2" textAlign="center">
                {menu.title}
              </Card.Header>
              <Card.Description>{menu.description} </Card.Description>
              <Card.Description className="buttons-container">
                <Button
                  className="mt-2 menu-button"
                  as={NavLink}
                  exact
                  to={`/meals/all/${menu.id}`}
                  style={{
                    color: "grey",
                    backgroundColor: "transparent",
                    border: "1px solid grey",
                  }}
                >
                  Open
                </Button>

                <Button
                  className="mt-2 menu-button"
                  style={{
                    color: "grey",
                    backgroundColor: "transparent",
                    border: "1px solid grey",
                  }}
                >
                  Update
                </Button>
                <Button
                  onClick={() => setOpen(menu.id)}
                  className="mt-2 trash"
                  style={{
                    color: "grey",
                    backgroundColor: "transparent",
                    border: "1px solid grey",
                  }}
                >
                  Delete
                  {/* <Icon name="trash" size="small" /> */}
                </Button>
              </Card.Description>
            </Card.Content>
          </Card>
        ))}
      </Card.Group>
      <Container centered>
        <Confirm
          open={open}
          header="Dėmesio!"
          content="Ar tikrai norite ištrinti?"
          cancelButton="Grįžti atgal"
          confirmButton="Taip"
          onCancel={() => setOpen(false)}
          onConfirm={() => deleteMenus(open).then(setOpen(false))}
          size="small"
        />
      </Container>
    </Container>
  );
}
