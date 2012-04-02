package guice.demo;

import java.io.IOException;

import com.google.inject.Inject;
import com.google.inject.name.Named;

public class WeaselGirl implements Hero {

  private final Vehicle vehicle;
  private Appendable recorder;
  private String saying;
  
  @Inject
  public WeaselGirl(Vehicle vehicle, Appendable recorder, @Named("WeaselSaying") String saying) {
    this.vehicle = vehicle;
    this.recorder = recorder;
    this.saying = saying;
  }

  public void fightCrime() throws IOException {
    recorder.append("Weasel girl chatters her teeth!\n");
    recorder.append("And shouts, '" + saying + "\n");
    recorder.append(vehicle.zoom() + "\n");
  }
  
  
}
