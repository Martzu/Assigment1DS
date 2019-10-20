import * as React from "react";
import AddPatientButton from "./patient_buttons/AddPatientButton";
import DeletePatientButton from "./patient_buttons/DeletePatientButton";
import UpdatePatientButton from "./patient_buttons/UpdatePatientButton";
export default function PatientCrudButtons() {
    return (
      <>
          <AddPatientButton/>
          <DeletePatientButton/>
          <UpdatePatientButton/>
      </>
    );
}