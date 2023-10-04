import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import { Container, Item, Icon } from "semantic-ui-react";

export function ViewUsers() {
  const params = useParams();
  const [users, setUsers] = useState([]);

  useEffect(() => {
    fetch(`/api/v1/users`)
      .then((response) => response.json())
      .then(setUsers)
      .catch((error) => console.error("Error fetching users:", error));
  });

  const renderUsers = () => {
    return users.map((user) => (
      <Item>
        <Icon name="user" />
        <Item.Content>
          <Item.Header as="a">{user.userName}</Item.Header>
          <Item.Meta>{user.role}</Item.Meta>
        </Item.Content>
      </Item>
    ));
  };

  return (
    <Container className="meals mt-5">
      <Item.Group divided>{renderUsers()}</Item.Group>
    </Container>
  );
}
