import Medication from "./Medication";

export default interface MedicalPlan{
    id: number;
    medications: Medication[],
    intakeIntervals: string,
    timePeriod: string
};