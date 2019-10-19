import React, {useState} from 'react';
import { makeStyles, Theme, createStyles } from '@material-ui/core/styles';
import Modal from '@material-ui/core/Modal';
import {Paper} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import HospitalUser from "../../types/HospitalUser";
import Input from "@material-ui/core/Input";
import axios from 'axios';

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

export default function DeletePatientButton() {
    const classes = useStyles();
    // getModalStyle is not a pure function, we roll the style only on the first render
    const [modalStyle] = React.useState(getModalStyle);
    const [open, setOpen] = React.useState(false);

    const handleOpen = () => {
        setOpen(true);
    };


    const deletePatient = () => {
        axios.delete("http://localhost:8080/patient", {
            data: {
                sessionId: localStorage.getItem("sessionId"),
                patientIdToDelete: id
            }
        }).then(response => {
            window.location.reload();
        });
    };

    const [id, setId] = useState(0);

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <>
            <Button type="button" onClick={handleOpen}>
                Remove patient
            </Button>
            <Modal
                aria-labelledby="simple-modal-title"
                aria-describedby="simple-modal-description"
                open={open}
                onClose={handleClose}
            >
                <Paper style={modalStyle} className={classes.paper}>
                    <Input placeholder="Patient Id" onChange={e => setId(parseInt(e.target.value))}/> <br/>
                    <Button onClick={() => deletePatient()}>
                        Remove
                    </Button>
                </Paper>
            </Modal>
        </>
    );
}