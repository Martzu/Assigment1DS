import * as React from "react";
import AppBarWithCaretakerMenu from "./AppBarWithCaretakerMenu";
import HospitalUsersTable from "./tables/HospitalUsersTable";
import {useEffect, useState} from "react";
import axios from 'axios';
import HospitalUser from "../types/HospitalUser";

export default function CaretakerMainPage() {

    const [rows, setRows] = useState<HospitalUser[]>([]);
    useEffect(() => {
        axios.post("http://localhost:8080/patients", {
                sessionId: localStorage.getItem("sessionId")
        }).then(response => setRows(response.data))
    },[]);

    return(
        <>
            <AppBarWithCaretakerMenu/>
            <HospitalUsersTable fields={["name", "address", "gender", "birthDate"]} patients={rows} type={""}/>
        </>
    )
};