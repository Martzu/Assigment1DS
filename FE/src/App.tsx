import React from 'react';
import './App.css';
import Login from './components/Login';
import CssBaseline from "@material-ui/core/CssBaseline";
import {Route, Switch} from "react-router";
import DoctorMainPage from './components/Doctor';
import {BrowserRouter} from "react-router-dom";


const App: React.FC = () => {
  return (
          <>
                  <BrowserRouter>
                      <Switch>
                          <Route exact path = "/" component={Login}/>
                          {
                              localStorage.getItem("role") === "doctor" && <Route exact path = "/doctor" component={DoctorMainPage}
                              />
                          }
                      </Switch>
                  </BrowserRouter>
          </>

  );
};

export default App;
