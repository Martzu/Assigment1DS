import React, {useEffect, useState} from 'react';
import { makeStyles, Theme, createStyles } from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import Backdrop from '@material-ui/core/Backdrop';
import Fade from '@material-ui/core/Fade';
import {Paper} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import Medication from "../../types/Medication";
import TableRow from "@material-ui/core/TableRow";
import axios from 'axios';
import TableCell from "@material-ui/core/TableCell";
import MedicalButtonProps from "../../props/MedicalButtonProps";
import Input from "@material-ui/core/Input";
import TextField from "@material-ui/core/TextField";


const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        modal: {
            display: 'flex',
            alignItems: 'center',
            justifyContent: 'center',
        },
        paper: {
            backgroundColor: theme.palette.background.paper,
            border: '2px solid #000',
            boxShadow: theme.shadows[5],
            padding: theme.spacing(2, 4, 3),
        },
    }),
);


var medicalPlanMedications: number[] = [];

export default function TransitionsModal(props: MedicalButtonProps) {
    const classes = useStyles();
    const [open, setOpen] = React.useState(false);
    const [medications, setMedications] = useState<Medication[]>([]);
    useEffect(() => {
        axios.post("http://localhost:8080/medications", {
            sessionId: localStorage.getItem("sessionId"),
        }).then(response => {
            if(response.status === 200)
            {
                setMedications(response.data);
                medicalPlanMedications = [];
            }
        })
    },[]);

    const [timePeriod, setTimePeriod] = useState("");
    const [intakeIntervals, setIntakeIntervals] = useState("");

    const addMedication = (id: number | undefined) => {
        if(id !== undefined)
        {
            debugger;
            medicalPlanMedications[medicalPlanMedications.length++] = id;
            alert("Medication added");
        }
    };

    const sendMedicalPlan = () => {
        debugger;
        axios.post("http://localhost:8080/medicalPlan", {
            sessionId: localStorage.getItem("sessionId"),
            medicalPlanMedicationsId: medicalPlanMedications,
            patientId: props.patientId,
            timePeriod: timePeriod,
            intakeIntervals: intakeIntervals
        }).then(response => {
            if(response.status === 200)
            {
                alert("Successful!");
                window.location.reload();
            }
        })
    };

    const handleOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <>
            <Button variant="contained" type="button" onClick={handleOpen}>
                Add Medical Plan
            </Button>
            <Modal
                aria-labelledby="transition-modal-title"
                aria-describedby="transition-modal-description"
                className={classes.modal}
                open={open}
                onClose={handleClose}
                closeAfterTransition
                BackdropComponent={Backdrop}
                BackdropProps={{
                    timeout: 500,
                }}
            >
                <Fade in={open}>
                    <Paper className={classes.paper}>
                        {
                            medications.map(medication =>
                                <TableRow>
                                    <Paper>
                                        <TableCell>{medication.medicationName} </TableCell>
                                        <TableCell>{medication.sideEffects}</TableCell>
                                        <TableCell>{medication.dosage}</TableCell>
                                        <TableCell><Button type = "button" onClick={() => addMedication(medication.id)}>
                                            Add
                                        </Button></TableCell>
                                    </Paper>
                                </TableRow>
                            )
                        }
                        <TextField onChange={(e) => setTimePeriod(e.target.value)}
                                       variant="outlined"
                                       margin="normal"
                                       required
                                       fullWidth
                                       id="timePeriod"
                                       label="TimePeriod"
                                       name="timePeriod"
                                       autoFocus
                            />
                        <TextField onChange={(e) => setIntakeIntervals(e.target.value)}
                                       variant="outlined"
                                       margin="normal"
                                       required
                                       fullWidth
                                       id="intakeIntervals"
                                       label="IntakeIntervals"
                                       name="intakeIntervals"
                                       autoFocus
                            />
                        <Button type = "button" onClick={() => sendMedicalPlan()} variant="contained">
                            Add medical plan
                        </Button>
                    </Paper>
                </Fade>
            </Modal>
        </>
    );
}
