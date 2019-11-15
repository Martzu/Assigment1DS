package com.example.springdemo.grpcService;

import com.example.springdemo.dto.MedicalPlanDTO;
import com.example.springdemo.dto.MedicationDTO;
import com.example.springdemo.entities.HospitalUser;
import com.example.springdemo.entities.Medication;
import com.example.springdemo.grpc.MedicalPlan;
import com.example.springdemo.grpc.medicalPlanGrpc;
import com.example.springdemo.repositories.FactoryRepository;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


@Service
@RequiredArgsConstructor
public class MedicalPlanGRPCService extends medicalPlanGrpc.medicalPlanImplBase {

    private final FactoryRepository factoryRepository;

    private List<String> medications = Collections.synchronizedList(new ArrayList<>());

    @Override
    public void downloadMedicalPlan(MedicalPlan.ClientRequest request, StreamObserver<MedicalPlan.MedicalPlansResponse> responseObserver) throws Exception {
        Integer patientId = Integer.parseInt(request.getPatientId());
        Integer medicalPlanDay = Integer.parseInt(request.getMedicalPlanDay()) - 1;
        int index = 0;

        HospitalUser patient = factoryRepository.createHospitalUserRepository().findById(patientId).orElseThrow(() -> new Exception("No patient Found"));

        List<com.example.springdemo.entities.MedicalPlan> medicalPlanList = factoryRepository.createMedicalPlanRepository().findMedicalPlansByPatient(patient);

        com.example.springdemo.entities.MedicalPlan currentMedicalPlan = medicalPlanList.get(medicalPlanDay);

        medications = getMedicalPlan(currentMedicalPlan);



        MedicalPlan.MedicalPlansResponse.Builder response = MedicalPlan.MedicalPlansResponse.newBuilder();

        response.addAllMedicalPlans(medications).setIntakeIntervals(currentMedicalPlan.getIntakeIntervals());

        responseObserver.onNext(response.build());
        responseObserver.onCompleted();



    }

    @Override
    public void medicationTaken(MedicalPlan.Medication request, StreamObserver<MedicalPlan.Empty> responseObserver) {
        if(medications.size() > 0)
        {
            Iterator<String> it = medications.iterator();
                while(it.hasNext())
                {
                    String medication = it.next();
                    if(request.getName().contains(medication))
                    {
                        String infoAboutPatient = request.getName().contains("EXPIRED!") ? request.getName() : medication + " taken!";
                        System.out.println(infoAboutPatient);
                        it.remove();
                    }
                }
        }

        MedicalPlan.Empty.Builder response = MedicalPlan.Empty.newBuilder();
        responseObserver.onNext(response.build());
        responseObserver.onCompleted();
    }



    private List<String> getMedicalPlan(com.example.springdemo.entities.MedicalPlan medicalPlan)
    {
        List<com.example.springdemo.entities.MedicalPlan> medicalPlanWrapper = new ArrayList<>();
        List<String> medications = new ArrayList<>();
        medicalPlanWrapper.add(medicalPlan);
        factoryRepository.createMedicationRepository().findMedicationsByMedicalPlans(medicalPlanWrapper).forEach(medication -> medications.add(medication.getName()));
        return medications;
    }
}
