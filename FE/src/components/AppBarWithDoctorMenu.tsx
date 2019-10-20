import React from 'react';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';

import Logout from "@material-ui/icons/Remove";
import {Paper} from "@material-ui/core";
import Button from "@material-ui/core/Button";
import AppBarFunctionProps from "../props/AppBarFunctionProps";

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            flexGrow: 1,
        },
        button:{
            color: "white"
        }

    }),
);

export default function DenseAppBar(props: AppBarFunctionProps) {
    const classes = useStyles();


    return (
        <Paper className={classes.root}>
            <AppBar position="static">
                <Toolbar variant="dense">
                    <Button className = {classes.button} onClick={() => window.location.assign("/")}>
                        Logout
                    </Button>
                    <Button className = {classes.button} onClick={() => props.getFromBackend("patients")}>
                        Patients
                    </Button>
                    <Button className = {classes.button} onClick={() => props.getFromBackend("caretakers")}>
                        Caretakers
                    </Button>
                    <Button className = {classes.button} onClick={() => props.getFromBackend("medications")}>
                        Medications
                    </Button>
                </Toolbar>
            </AppBar>
        </Paper>
    );
}
