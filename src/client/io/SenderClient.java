package client.io;

import client.Client;
import client.data.PrintingData;

import java.io.*;

import static client.data.Constants.*;

/**
 * Created by vvrud on 12.09.16.
 *
 * @author vvrud
 *         This class provides sending information from client to server.
 */
public class SenderClient extends Thread {

    private int mode = 1;

    public SenderClient(int mode) {
        this.mode = mode;
    }

    private static void sendOptions(ObjectOutputStream objectOutput) throws IOException {
        objectOutput.writeObject(PrintingData.getOptions());
        objectOutput.flush();
    }

    @Override
    public void run() {
        boolean printingInterrupted = PrintingData.isPrintingInterrupted();

        DataOutputStream dataOutput = Client.getDataOutput();
        ObjectOutputStream objectOutput = Client.getObjectOutput();
        if (printingInterrupted) {
            try {
                dataOutput.writeInt(INTERRUPT);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (mode == JPG_MODE) {
            try {
                dataOutput.writeInt(TXT);
                sendOptions(objectOutput);
                sendFile(dataOutput, TXT);
            } catch (IOException e) {
                System.out.println("Failed sending file or options!");
                e.printStackTrace();
            }
        } else if (mode == BEZ_MODE) {
            try {
                dataOutput.writeInt(XML);
                sendFile(dataOutput, XML);
            } catch (IOException e) {
                System.out.println("Failed sending xml");
                e.printStackTrace();
            }
        }
    }

    private void sendFile(DataOutputStream dataOutput, int type) throws IOException {
        File file;
        if (type == TXT) {
            file = PrintingData.getTxtFileCreated();
        } else if (type == XML) {
            file = PrintingData.getXmlFileCreated();
        } else throw new RuntimeException("Type is unknown");

        if (file != null) {
            FileInputStream input = new FileInputStream(file);
            long size = file.length();
            byte[] buffer = new byte[(int) size];

            dataOutput.writeLong(size);

            int in;
            while ((in = input.read(buffer)) != -1) {
                dataOutput.write(buffer, 0, in);
            }

            input.close();
            dataOutput.flush();
        } else throw new IOException("File is null");
    }
}
