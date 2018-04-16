package com.grpc.demo;

import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InstructionService extends InstructionGrpc.InstructionImplBase {

    ArrayList<String> locationSteps = new ArrayList<String>() {{
        add("Move to wooden box.");
        add("Open the box.");
        add("Pick the knife.");
        add("Move to rope.");
        add("Cut the rope.");
    }};

    Map<String, ArrayList<String>> steps = new HashMap<String, ArrayList<String>>() {{
        put("front door", locationSteps);
    }};


    @Override
    public void getInstructions(InstructionRequest request, StreamObserver<InstructionResponse> responseObserver) {
        // Retrieve input
        String currentLocation = request.getCurrentLocation();
        System.out.println("\n>>> Retrieved current location: " + currentLocation + "\n");

        // Prepare and stream outputs
        ArrayList<String> locationSteps = null;
        for(String location : this.steps.keySet()) {
            if(location.equals(currentLocation)) {
                ArrayList<String> steps = this.steps.get(location);
                for(String step : steps){
                    InstructionResponse response = InstructionResponse.newBuilder().setStep(step).build();
                    responseObserver.onNext(response);
                    System.out.println(">>> Sent step: " + step);
                }
            }
        }

        // Mark the finishing of stream
        responseObserver.onCompleted();
    }

}
