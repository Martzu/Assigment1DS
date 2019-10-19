import {action, observable} from "mobx";
import State from "../types/State";

var appState = observable({
    tableColumns: [] as string[],
    //tableColumns: [] as string[],
    tableRows: [] as string[],
    //tableRows: [] as string[][],
    setTableColumns(columns: string[]){
        this.tableColumns = columns;
    },
    setTableRows(rows: []){
        debugger;
        this.tableRows = rows;
    },

}, {
    tableColumns: observable,
    tableRows: observable,
    setTableColumns: action,
    setTableRows: action
});

export default appState;
