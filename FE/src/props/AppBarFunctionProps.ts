import HospitalUser from "../types/HospitalUser";
import Medication from "../types/Medication";

export default interface AppBarFunctionProps{
    getFromBackend(type: string): HospitalUser|Medication;

}