import * as React from "react";
import AppBarWithCaretakerMenu from "./AppBarWithCaretakerMenu";
import HospitalUsersTable from "./tables/HospitalUsersTable";
import {useEffect, useState} from "react";
import axios from 'axios';
import HospitalUser from "../types/HospitalUser";
import WebSocketListener from "../ws/WebSocketListener";
import {Paper} from "@material-ui/core";


const listener = new WebSocketListener();

export default function CaretakerMainPage() {

    listener.on("event", event => {
        if(event.type === "SENSOR_ANOMALY")
        {
            debugger;
            setWarning("Patient with id " + event.patientProblemDTO.patientId + " exceeded the time for " + event.patientProblemDTO.message);
        }
    });

    const [rows, setRows] = useState<HospitalUser[]>([]);
    const [warning, setWarning] = useState("");
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
            <Paper>{warning}</Paper>
        </>
    )
};