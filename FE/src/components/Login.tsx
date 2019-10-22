import React, {useEffect, useState} from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import {Paper} from "@material-ui/core";
import useRouter from "use-react-router";
import appState from "../store/state";
import axios from 'axios';
import {observer} from "mobx-react";
import {url} from "inspector";

import useReactRouter from 'use-react-router';
import {Simulate} from "react-dom/test-utils";
import Session from "../types/Session";


const useStyles = makeStyles(theme => ({
    '@global': {
        body: {
            backgroundColor: theme.palette.common.white,
        },
    },
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(1),
    },
    submit: {
        margin: theme.spacing(3, 0, 2)
    },
}));

const SignIn = (function SignIn() {

    const {history, location, match} = useReactRouter();
    const classes = useStyles();
    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");

    useEffect(() => {
        localStorage.clear();
    },[]);

    const login = () => {
    debugger;
        axios.post("http://localhost:8080/login", {
                username: username,
                password: password
            },
        )
            .then((response) => {
                debugger;
                if(response.status == 200)
                {
                    console.log(response);
                    const currentSession: Session = response.data;
                    localStorage.setItem("sessionId",currentSession.sessionId);
                    localStorage.setItem("role", currentSession.role);
                    window.location.assign("/" + currentSession.role);
                }
            })
            .catch(error => {
                alert("Wrong credentials!");
            });
    };

    const isValid = () => {
        return username.length > 0 && password.length > 0
    };
    return (
        <>


            <Container component="main" maxWidth="xs">

                <Paper className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <LockOutlinedIcon />
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Sign in
                    </Typography>
                    <form className={classes.form} noValidate>
                        <TextField onChange={(e) => setUsername(e.target.value)}
                                   variant="outlined"
                                   margin="normal"
                                   required
                                   fullWidth
                                   id="username"
                                   label="Username"
                                   name="username"
                                   autoComplete="username"
                                   autoFocus
                        />
                        <TextField onChange={(e) => setPassword(e.target.value)}
                                   variant="outlined"
                                   margin="normal"
                                   required
                                   fullWidth
                                   name="password"
                                   label="Password"
                                   type="password"
                                   id="password"
                                   autoComplete="current-password"
                        />
                        <FormControlLabel
                            control={<Checkbox value="remember" color="primary" />}
                            label="Remember me"
                        />
                        <Button disabled={!isValid()} onClick={() => login()}
                                type="button"
                                fullWidth
                                variant="contained"
                                color="primary"
                                className={classes.submit}
                        >
                            Sign In
                        </Button>
                    </form>
                </Paper>
            </Container>

        </>

    );
});

export default SignIn;