import Medication from "../types/Medication";

export default interface MedicationTableProps{
    fields: string[],
    medications: Medication[]
}