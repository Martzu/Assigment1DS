import React, {useEffect, useState} from 'react';
import { makeStyles } from '@material-ui/core/styles';
import TreeView from '@material-ui/lab/TreeView';
import ExpandMoreIcon from '@material-ui/icons/ExpandMore';
import ChevronRightIcon from '@material-ui/icons/ChevronRight';
import TreeItem from '@material-ui/lab/TreeItem';
import MedicalPlan from "../../types/MedicalPlan";
import axios from 'axios';

const useStyles = makeStyles({
    root: {
        height: 216,
        flexGrow: 1,
        maxWidth: 400,
    },
});

const hashing = (label: string) => {
    let hash = 1;
    for (let i = 0; i < label.length; i++) {
        let chr = label.charCodeAt(i);
        hash = ((hash << 5) - hash) + chr;
        hash |= 0; // Convert to 32bit integer
    }
    return hash;
}


export default function FileSystemNavigator() {
    const classes = useStyles();

    const [medicalPlans, setMedicalPlans] = useState<MedicalPlan[]>([]);

    useEffect(() => {
        debugger;
            axios.post("http://localhost:8080/medicalPlans", {
                sessionId: localStorage.getItem("sessionId")
            }).then(response => {
                if(response.status === 200)
                {
                    setMedicalPlans(response.data);
                }
            })

        },
        []);

    return (
        <TreeView
            className={classes.root}
            defaultCollapseIcon={<ExpandMoreIcon />}
            defaultExpandIcon={<ChevronRightIcon />}
        >
            {
                medicalPlans.map(medicalPlan => (
                        <TreeItem nodeId={String(hashing(String(medicalPlan.id)))} key={hashing(String(medicalPlan.id))} label={"Medical Plan" + medicalPlan.id}>
                            {
                                medicalPlan.medications.map(medication => (
                                    <TreeItem nodeId={String(hashing(String(medicalPlan.id + medication.medicationName + medication.id)))} key={hashing(String(medicalPlan.id + medication.medicationName + medication.id))} label={medication.medicationName}>
                                        {
                                            <>
                                                <TreeItem nodeId={medication.sideEffects} label={medication.sideEffects}/>
                                                <TreeItem nodeId={medication.dosage} label={medication.dosage}/>
                                            </>
                                        }
                                    </TreeItem>
                                ))
                            }
                        </TreeItem>

                    )
                )
            }

        </TreeView>
    );
}