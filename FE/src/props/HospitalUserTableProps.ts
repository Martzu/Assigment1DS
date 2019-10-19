import HospitalUser from "../types/HospitalUser";

export default interface HospitalUserTableProps{
    fields: string[],
    patients: HospitalUser[]

}