import { useReducer } from "react";
import { HashRouter, Route, Routes } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import { TopMenu } from "./components/TopMenu";
import { Container } from "semantic-ui-react";
import AuthContext from "./AuthContext";
import Authentication from "./Authentication";
import { LoginPage } from "./pages/LoginPage";
import { ViewMenus } from "./pages/ViewMenus";
import { ViewMeals } from "./pages/ViewMeals";
import "semantic-ui-css/semantic.min.css";
import { AddMenu } from "./pages/AddMenu";
import { CreateUserPage } from "./pages/CreateUserPage";
import { ViewUsers } from "./pages/ViewUsers";

function App() {
  var initState = {
    isAuthenticated: false,
    username: null,
    role: null,
  };

  const auth = (appState, action) => {
    switch (action.type) {
      case "LOGIN":
        return {
          ...appState,
          isAuthenticated: true,
          username: action.value.username,
          role: action.value.role,
        };
      case "LOADING":
        return {
          ...appState,
          isLoading: action.value,
        };
      case "LOGOUT":
        return {
          ...appState,
          isAuthenticated: false,
          username: null,
          role: null,
        };
      case "AUTHENTICATED":
        return {
          ...appState,
          isAuthenticated: true,
          admin: true,
        };
      default:
        return appState;
    }
  };
  const [appState, setAppState] = useReducer(auth, initState);

  return (
    <div>
      <Container>
        <AuthContext.Provider value={{ appState, setAppState }}>
          <HashRouter>
            <TopMenu />
            <div className="container">
              <Routes>
                <Route path="/login/" element={<LoginPage />} />
                <Route path="/logout/" element={<LoginPage />} />
                <Route path="/users/user/create" element={<CreateUserPage />} />
                <Route path="/" element={<LoginPage />} />
                <Route path="/meals/all/:id" element={<ViewMeals />} />
                <Route path="/menus/:id/meals" element={<ViewMeals />} />
                <Route path="/menus/create" element={<AddMenu />} />
                <Route path="/menus" element={<ViewMenus />} />
                <Route path="/users" element={<ViewUsers />} />
              </Routes>
            </div>
          </HashRouter>
        </AuthContext.Provider>
      </Container>
    </div>
  );
}

export default App;
