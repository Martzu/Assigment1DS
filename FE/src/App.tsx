import React from 'react';
import './App.css';
import Login from './components/Login';
import {Route, Switch} from "react-router";
import DoctorMainPage from './components/Doctor';
import {BrowserRouter} from "react-router-dom";
import CaretakerMainPage from "./components/Caretaker";


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
                    {
                        localStorage.getItem("role") === "caretaker" && <Route exact path = "/caretaker" component={CaretakerMainPage}
                        />
                    }
                </Switch>
            </BrowserRouter>
        </>

    );
};

export default App;
