import com.grpc.demo.InstructionGrpc;
import com.grpc.demo.InstructionRequest;
import com.grpc.demo.InstructionResponse;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import java.util.Iterator;

public class InstructionClient {

    private final static String HOST = "localhost";
    private final static int PORT = 9100;

    public static void main(String[] args) {
        // Creating the channel
        ManagedChannel channel = ManagedChannelBuilder.forAddress(HOST, PORT)
                .usePlaintext(true)
                .build();

        // Retrieving the service stub
        InstructionGrpc.InstructionBlockingStub blockingStub = InstructionGrpc.newBlockingStub(channel);

        // Creating the request
        String currentLocation = "front door";
        InstructionRequest request = InstructionRequest.newBuilder().setCurrentLocation(currentLocation).build();

        // Invoking the service method with passing the request object
        Iterator<InstructionResponse> steps = blockingStub.getInstructions(request);
        System.out.println("\n>>> Request sent.");

        System.out.println("\n>>> Follow these instructions: \n");

        while (steps.hasNext()) {
            System.out.println(steps.next().getStep());
        }

    }

}
