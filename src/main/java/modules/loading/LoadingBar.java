package modules.loading;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.scene.control.ProgressBar;
import javafx.util.Duration;

public class LoadingBar extends ProgressBar {
    private final ScheduledService<Double> sche;
    private final BooleanProperty isOver = new SimpleBooleanProperty();
    public LoadingBar(double progress){
        super(progress);
        this.isOver.setValue(false);
        this.getStylesheets().add(String.valueOf(getClass().getResource("/CSS/LoadingBar.css")));
        this.setPrefWidth(500);
        sche = new ScheduledService<Double>() {
            double i = 0;
            @Override
            protected Task<Double> createTask() {
                return new Task<Double>() {
                    @Override
                    protected Double call() throws Exception {
                        return i = i + 0.005;
                    }

                    @Override
                    protected void updateValue(Double value) {
                        LoadingBar.this.setProgress(value);
                        if(LoadingBar.this.getProgress() >= 1){
                            sche.cancel();
//                            System.out.println("任务已取消!");
                            LoadingBar.this.isOver.setValue(true);
                        }
                    }
                };
            }
        };
        sche.setPeriod(Duration.seconds(0.01));
    }

    public void startAnimation(){
        this.sche.start();
    }

    public boolean isOver(){
        return this.isOver.get();
    }

    public BooleanProperty isOverProperty(){
        return this.isOver;
    }


}

