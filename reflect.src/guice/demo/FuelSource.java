package guice.demo;

import com.google.inject.ImplementedBy;

@ImplementedBy(HeavyWaterRefinery.class)
public interface FuelSource {
  String refuel();
}
