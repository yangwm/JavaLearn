package guice.demo;

import java.io.IOException;

import com.google.inject.Inject;

public class FrogMan implements Hero {
  private Vehicle vehicle;
  @Inject Appendable recorder;
    
  @Inject
  public FrogMan(Vehicle vehicle) {
    this.vehicle = vehicle;
  }
  
  
  public void fightCrime() throws IOException {
    recorder.append("FrogMan Lives!\n");
    recorder.append(vehicle.zoom() + "\n");
  }

}
