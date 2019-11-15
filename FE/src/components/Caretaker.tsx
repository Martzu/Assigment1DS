import * as React from "react";
import AppBarWithCaretakerMenu from "./AppBarWithCaretakerMenu";
import HospitalUsersTable from "./tables/HospitalUsersTable";
import {useEffect, useState} from "react";
import axios from 'axios';
import HospitalUser from "../types/HospitalUser";
import WebSocketListener from "../ws/WebSocketListener";
import {createStyles, Paper, Theme} from "@material-ui/core";
import makeStyles from "@material-ui/core/styles/makeStyles";


const listener = new WebSocketListener();

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        paper: {
            position: 'absolute',
            width: '20vw',
        }
    }),
);



export default function CaretakerMainPage() {

    const classes = useStyles();
    listener.on("event", event => {
        if(event.type === "SENSOR_ANOMALY")
        {
            debugger;
            setWarnings([...warnings, "Patient with id " + event.patientProblemDTO.patientId + " exceeded the time for " + event.patientProblemDTO.message + "\n"]);
        }
    });

    const [warnings, setWarnings] = useState<string[]>([]);
    const [rows, setRows] = useState<HospitalUser[]>([]);
    useEffect(() => {
        axios.post("http://localhost:8080/patients", {
                sessionId: localStorage.getItem("sessionId")
        }).then(response => {
            setRows(response.data);

        })
    },[]);

    return(
        <>
            <AppBarWithCaretakerMenu/>
            <HospitalUsersTable fields={["name", "address", "gender", "birthDate"]} patients={rows} type={""}/>
            <Paper className={classes.paper}>
                {
                    warnings.map(warning => warning)
                }
            </Paper>
        </>
    )
};