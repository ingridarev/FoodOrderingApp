import React, { useContext, useState } from "react";
import { Container, Menu } from "semantic-ui-react";
import { Link, useNavigate } from "react-router-dom";
import AuthContext from "../AuthContext";

export function TopMenu() {
  const { appState, setAppState } = useContext(AuthContext);
  const navigate = useNavigate();
  const [activeItem, setActiveItem] = useState("menus");

  const handleItemClick = (name) => {
    setActiveItem(name);
  };

  const logoutHandler = async () => {
    fetch("/logout", {
      method: "POST",
    }).then((response) => {
      setAppState({ type: "LOGOUT" });
      navigate("/", { replace: true });
    });
  };

  return (
    <Menu pointing secondary>
      <Container>
        {appState.isAuthenticated && (
          <>
            <Menu.Item
              name="menus"
              active={activeItem === "menus"}
              onClick={() => handleItemClick("menus")}
              as={Link}
              to="/menus"
            >
              Menus
            </Menu.Item>

            <Menu.Item
              name="users"
              
              active={activeItem === "users"}
              onClick={() => handleItemClick("users")}
              as={Link}
              to="/users"
            >
              Users
            </Menu.Item>
           

            <Menu.Item
              name="orders"
              active={activeItem === "orders"}
              onClick={() => handleItemClick("orders")}
              as={Link}
              to="/orders"
            >
              Orders
            </Menu.Item>
          </>
        )}

        {appState.isAuthenticated ? (
          <Menu.Menu position="right">
            <Menu.Item
              name="logout"
              active={activeItem === "logout"}
              onClick={() => handleItemClick("logout")}
              as={Link}
              to="/logout"
            >
              Logout
            </Menu.Item>
          </Menu.Menu>
        ) : (
          <Menu.Menu position="right">
            <Menu.Item
              name="login"
              active={activeItem === "login"}
              onClick={() => handleItemClick("login")}
              as={Link}
              to="/login"
            >
              Login
            </Menu.Item>
          </Menu.Menu>
        )}
      </Container>
    </Menu>
  );
}
