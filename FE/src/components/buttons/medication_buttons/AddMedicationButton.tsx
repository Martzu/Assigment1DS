import React, {useState} from 'react';
import { makeStyles, Theme, createStyles } from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import {Paper} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import HospitalUser from "../../../types/HospitalUser";
import Input from "@material-ui/core/Input";
import axios from 'axios';
import Medication from "../../../types/Medication";

function rand() {
    return Math.round(Math.random() * 20) - 10;
}

function getModalStyle() {
    const top = 50 + rand();
    const left = 50 + rand();

    return {
        top: `${top}%`,
        left: `${left}%`,
        transform: `translate(-${top}%, -${left}%)`,
    };
}

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        paper: {
            position: 'absolute',
            width: 400,
            backgroundColor: theme.palette.background.paper,
            border: '2px solid #000',
            boxShadow: theme.shadows[5],
            padding: theme.spacing(2, 4, 3),
        },
        button: {
            backgroundColor: theme.palette.background.paper
        }
    }),
);

export default function SimpleModal() {
    const classes = useStyles();
    // getModalStyle is not a pure function, we roll the style only on the first render
    const [modalStyle] = React.useState(getModalStyle);
    const [open, setOpen] = React.useState(false);

    const handleOpen = () => {
        setOpen(true);
    };

    let newMedication: Medication = {
        dosage: "", medicationName: "", sideEffects: ""

    };

    const addMedication = () => {
        axios.post("http://localhost:8080/medication",
            {sessionId: localStorage.getItem("sessionId"), medicationName: newMedication.medicationName, dosage: newMedication.dosage, sideEffects: newMedication.sideEffects}).then(response => {
            window.location.reload();
        })
    };

    const isValidMedication = (): boolean => {
        return newMedication.dosage.length > 0 && newMedication.medicationName.length > 0 && newMedication.sideEffects.length > 0;
    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <>
            <Button variant="contained" type="button" onClick={handleOpen}>
                Add new medication
            </Button>
            <Modal
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
                open={open}
                onClose={handleClose}
            >
                <Paper style={modalStyle} className={classes.paper}>
                    <Input placeholder="Medication name" onChange={e => newMedication.medicationName = e.target.value}/> <br/>
                    <Input placeholder="Side effects" onChange={e => newMedication.sideEffects = e.target.value}/> <br/>
                    <Input placeholder="Dosage" onChange={e => newMedication.dosage = e.target.value}/> <br/>
                    <Button className = {classes.button}  onClick={() => addMedication()}>
                        Add
                    </Button>
                </Paper>
            </Modal>
        </>
    );
}