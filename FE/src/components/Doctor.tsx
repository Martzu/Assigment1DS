
import AppBarWithMenu from './AppBarWithMenu';
import {makeStyles, Paper} from "@material-ui/core";
import * as React from "react";
import PacientsTable from './PatientsTable';
import appState from "../store/state";
import {observer} from "mobx-react";
import Button from "@material-ui/core/Button";
import useRouter from "use-react-router";
import {useEffect} from "react";
import useReactRouter from 'use-react-router';
import axios from 'axios';
import {useState} from "react";
import HospitalUser from "../types/HospitalUser";
import Medication from "../types/Medication";
import AddPatientButton from "./buttons/AddPatientButton";
import DeletePatientButton from "./buttons/DeletePatientButton";

const useStyles = makeStyles(theme => ({
    '@global': {
        body: {
            backgroundColor: theme.palette.common.white,
        },
    },
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    }
}));


const DoctorMainPage = observer(function DoctorMainPage(){

    const {history, location, match} = useReactRouter();
    const [fields, setFields] = useState<string[]>(["name", "address", "gender", "birthDate"]);
    const[rows, setRows] = useState<HospitalUser[]>([]);
    const classes = useStyles();


    //gets all from backend, just need to make the backend for medication and we'll get them all
    //through props, it's passed, and each button will set the type
    const getFromBackend = (type: string): any => {
        axios.post("http://localhost:8080/" + type, {
            sessionId: localStorage.getItem("sessionId"),
            role: localStorage.getItem("role")
        }).then(response => {
            if(response.status == 200)
            {
                switch (type) {
                    case "medications": {
                        setFields(["name", "sideEffects", "dosage"])
                        break;
                    }
                    default: {
                        setFields(["name", "address", "gender", "birthDate"]);
                        break;
                    }
                }
                setRows(response.data);
            }
        });
    };

    useEffect(() => {
        debugger;
        if(localStorage.getItem("role") !== "doctor")
        {
            window.location.assign("/");
        }
        else
        {
            axios.post("http://localhost:8080/patients",{
                sessionId: localStorage.getItem("sessionId"),
                role: localStorage.getItem("role")
            }).then(response => {
                setRows(response.data);
            })
        }

    }, []);
    return (
        <>
            <AppBarWithMenu getFromBackend={getFromBackend}/>
            <Paper className={classes.paper}>
                <PacientsTable fields = {fields} patients={rows}/>
            </Paper>
            <AddPatientButton/>
            <DeletePatientButton/>

        </>
    );
});

export default DoctorMainPage;