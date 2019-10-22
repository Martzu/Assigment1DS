import React from 'react';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import {Paper} from "@material-ui/core";
import Button from "@material-ui/core/Button";

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            flexGrow: 1,
        },

    }),
);

export default function DenseAppBar() {
    const classes = useStyles();


    return (
        <Paper className={classes.root}>
            <AppBar position="static">
                <Toolbar variant="dense">
                    <Button onClick={() => window.location.assign("/")}>
                        Logout
                    </Button>
                </Toolbar>
            </AppBar>
        </Paper>
    );
}
