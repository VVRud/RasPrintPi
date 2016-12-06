package client.io;

import client.Client;
import client.data.PrintingData;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Created by vvrud on 12.09.16.
 *
 * @author vvrud
 *         This class provides sending information from client to server.
 */
public class SenderClient extends Thread {

    @Override
    public void run() {
        boolean printingInterrupted = PrintingData.isPrintingInterrupted();

        DataOutputStream dataOutput = Client.getDataOutput();

        if (!printingInterrupted) {
            try {
                dataOutput.writeBoolean(printingInterrupted);
                sendFile(dataOutput);
            } catch (IOException e) {
                System.out.println("Failed sending interruption false with file");
                e.printStackTrace();
            }
        } else {
            try {
                dataOutput.writeBoolean(printingInterrupted);
                System.out.println("Interruption sent");
            } catch (IOException e) {
                System.out.println("Failed sending interruption true");
                e.printStackTrace();
            }

        }
    }

    private void sendFile(DataOutputStream dataOutput) throws IOException {
        File file = PrintingData.getFile();
        FileInputStream input = new FileInputStream(file);
        long size = file.length();
        byte[] buffer = new byte[(int) size];

        dataOutput.writeLong(size);
        dataOutput.writeUTF(file.getName());

        int in;

        while ((in = input.read(buffer)) != -1) {
            dataOutput.write(buffer, 0, in);
        }

        input.close();
        dataOutput.flush();
    }
}
