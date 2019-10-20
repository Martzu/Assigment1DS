import React from "react";
import AddCaretakerButton from "./caretaker_buttons/AddCaretakerButton";
import DeleteCaretakerButton from "./caretaker_buttons/DeleteCaretakerButton";
import UpdateCaretakerButton from "./caretaker_buttons/UpdateCaretakerButton";

export default function CaretakerCrudButtons() {
    return(
        <>
            <AddCaretakerButton/>
            <DeleteCaretakerButton/>
            <UpdateCaretakerButton/>
        </>
    )
}