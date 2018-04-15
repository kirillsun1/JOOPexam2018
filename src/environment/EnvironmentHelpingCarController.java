package environment;

public class EnvironmentHelpingCarController {
    private boolean needToSendHelpingCar;
    private boolean helpingCarIsAlive;

    EnvironmentHelpingCarController() {
        needToSendHelpingCar = false;
        helpingCarIsAlive = false;
    }

    public synchronized void askForHelp() {
        needToSendHelpingCar = true;
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized boolean needToSendHelpingCar() {
        return needToSendHelpingCar && !helpingCarIsAlive;
    }

    synchronized void informThatHelpingCarHasBeenSent() {
        helpingCarIsAlive = true;
    }

    public synchronized void informThatHelpingCarHasFixedSomeCar() {
        helpingCarIsAlive = false;
        notifyAll();
    }
}
