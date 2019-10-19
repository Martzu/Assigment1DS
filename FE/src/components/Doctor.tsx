
import AppBarWithMenu from './AppBarWithMenu';
import {Paper} from "@material-ui/core";
import * as React from "react";
import PacientsTable from './Table';
import appState from "../store/state";
import {observer} from "mobx-react";
import Button from "@material-ui/core/Button";
import useRouter from "use-react-router";
import {useEffect} from "react";
import useReactRouter from 'use-react-router';

const DoctorMainPage = observer(function DoctorMainPage(){

    const {history, location, match} = useReactRouter();

    return (
        <Paper>
            <AppBarWithMenu/>
            <PacientsTable/>
            <Button onClick={() => history.push("/")}>
                Logout
            </Button>
        </Paper>
    );
});

export default DoctorMainPage;