package guice.demo;

import java.io.IOException;

import com.google.inject.Inject;

public class FrogMobile implements Vehicle {

  private FuelSource fuelSource;
  @Inject private Appendable recorder;
  
  @Inject
  public FrogMobile(FuelSource fuelSource){
    this.fuelSource = fuelSource;
  }
  
  public String zoom() {
    try {
      recorder.append(fuelSource.refuel());
    } catch (IOException e) {
      e.printStackTrace();
    }
    return "The FrogMobile hops slowly accross the landscape.\n";
  }

}
