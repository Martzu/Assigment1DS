import React from "react";

import AddMedicationButton from "./medication_buttons/AddMedicationButton";
import DeleteMedicationButton from "./medication_buttons/DeleteMedicationButton";
import UpdateMedicationButton from "./medication_buttons/UpdateMedicationButton";
export default function CaretakerCrudButtons() {
    return(
        <>
            <AddMedicationButton/>
            <DeleteMedicationButton/>
            <UpdateMedicationButton/>
        </>
    )
}