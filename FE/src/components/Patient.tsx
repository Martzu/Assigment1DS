import React from "react";

import AppBarWithPatientMenu from "./AppBarWithPatientMenu";
import MedicalPlansTreeView from "./tree_view/MedicalPlansTreeView";
import {Paper} from "@material-ui/core";

export default function Patient(){
    return (
        <>
            <AppBarWithPatientMenu/>
            <MedicalPlansTreeView/>
        </>
    )
}