
import AppBarWithMenu from './AppBarWithDoctorMenu';
import {makeStyles, Paper} from "@material-ui/core";
import * as React from "react";
import HospitalUsersTable from './tables/HospitalUsersTable';
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
import AddPatientButton from "./buttons/patient_buttons/AddPatientButton";
import DeletePatientButton from "./buttons/patient_buttons/DeletePatientButton";
import PatientCrudButtons from "./buttons/PatientCrudButtons";
import CaretakerCrudButtons from "./buttons/CaretakerCrudButtons";
import MedicationTable from "./tables/MedicationTable";
import MedicationCrudButtons from "./buttons/MedicationCrudButtons";

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
    const [usersRows, setUsersRows] = useState<HospitalUser[]>([]);
    const [medicationsRows, setMedicationsRows] = useState<Medication[]>([]);
    const classes = useStyles();


    //gets all from backend, just need to make the backend for medication and we'll get them all
    //through props, it's passed, and each button will set the type

    const [userTypeButtons, setUserTypeButtons] = useState("");
    const getFromBackend = (type: string): any => {
        axios.post("http://localhost:8080/" + type, {
            sessionId: localStorage.getItem("sessionId"),
            role: localStorage.getItem("role")
        }).then(response => {
            if(response.status === 200)
            {
                debugger;
                setUserTypeButtons(type);
                switch (type) {
                    case "medications": {
                        setFields(["medicationName", "sideEffects", "dosage"]);
                        setMedicationsRows(response.data);
                        break;
                    }
                    default: {
                        setFields(["name", "address", "gender", "birthDate"]);
                        setUsersRows(response.data);
                        break;
                    }
                }

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
            axios.all([
                axios.post("http://localhost:8080/patients",{
                    sessionId: localStorage.getItem("sessionId")}),
                axios.post("http://localhost:8080/medications", {
                    sessionId: localStorage.getItem("sessionId")})
            ]).then(axios.spread((patientsResponse, medicationsResponse) => {
                if(patientsResponse.status === 200)
                {
                    setUsersRows(patientsResponse.data);
                    setUserTypeButtons("patients");
                }
                if(medicationsResponse.status === 200)
                {
                    setMedicationsRows(medicationsResponse.data);
                }
            }))
        }
    }, []);
    return (
        <>
            <AppBarWithMenu getFromBackend={getFromBackend}/>
            <Paper className={classes.paper}>
                {userTypeButtons === "medications" ? <MedicationTable fields={fields} medications={medicationsRows}/> : <HospitalUsersTable fields = {fields} patients={usersRows} type={userTypeButtons}/>}
            </Paper>
            {userTypeButtons === "patients" ? <PatientCrudButtons/> : userTypeButtons === "medications" ? <MedicationCrudButtons/> : <CaretakerCrudButtons/>}

            </>
    );
});

export default DoctorMainPage;