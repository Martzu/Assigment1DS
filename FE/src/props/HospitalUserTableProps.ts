import HospitalUser from "../types/HospitalUser";
import Medication from "../types/Medication";

export default interface HospitalUserTableProps{
    fields: string[],
    patients: HospitalUser[],
    type: string

}