import React, {Props, useEffect, useState, Fragment} from 'react';
import { createStyles, Theme, makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import axios from 'axios';
import HospitalUser from "../types/HospitalUser";
import HospitalUserTableProps from "../props/HospitalUserTableProps";

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            width: '100%',
        },
        table: {
            minWidth: 650,
        },
    }),
);



const DenseTable = (props: HospitalUserTableProps) => {
    const classes = useStyles();
    return (
            <>
                <Table className={classes.table} size="small" aria-label="a dense table">
                    <TableHead>
                        <TableRow>
                            <TableCell>Id</TableCell>
                            {
                                props.fields.map(field =>
                                    <TableCell align="right">{field}</TableCell>
                                )
                            }
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {props.patients.map(row => (
                            <TableRow key={row.id}>
                                <TableCell component="th" scope="row">
                                    {row.id}
                                </TableCell>
                                <TableCell align="right">{row.name}</TableCell>
                                <TableCell align="right">{row.address}</TableCell>
                                <TableCell align="right">{row.gender}</TableCell>
                                <TableCell align="right">{row.birthDate}</TableCell>
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </>
    );
};

export default DenseTable;