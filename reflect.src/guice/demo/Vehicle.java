package guice.demo;

import com.google.inject.ImplementedBy;

@ImplementedBy(FrogMobile.class)
public interface Vehicle {
  String zoom();
}
