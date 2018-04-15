package bird;

import environment.EnvironmentCenter;

public class Bird implements Runnable {
    private EnvironmentCenter environmentCenter;

    public Bird(EnvironmentCenter environmentCenter) {
        this.environmentCenter = environmentCenter;
    }

    @Override
    public void run() {
        final int SONG_DELAY = 4000;

        while (!Thread.interrupted()) {
            sing(environmentCenter.getEnvironmentDatabase().getCurrentPollutionValue());
            try {
                Thread.sleep(SONG_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sing(double currentPollutionValue) {
        final int BAD_POLLUTION_VALUE = 400;

        System.out.println(currentPollutionValue < BAD_POLLUTION_VALUE ? "Puhas õhk on puhas õhk on rõõmus linnu elu!"
                : "Inimene tark, inimene tark – saastet täis on linnapark");
    }
}
