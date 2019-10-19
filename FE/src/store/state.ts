import {action, observable} from "mobx";
import State from "../types/State";

var appState: State = observable({
    tableColumns: [] as string[],
    tableRows: [] as string[][],
    setTableColumns(columns: string[]){
        this.tableColumns = columns;
    },
    setTableRows(rows: string[][]){
        this.tableRows = rows;
    }

}, {
    tableColumns: observable,
    tableRows: observable,
    setTableColumns: action,
    setTableRows: action

});

export default appState;
