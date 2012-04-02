package guice.demo;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.name.Names;

public class HeroModule implements Module {

  public void configure(Binder binder) {
    binder.bind(Vehicle.class).annotatedWith(Names.named("Fast")).to(WeaselCopter.class);
    binder.bind(FuelSource.class).to(HeavyWaterRefinery.class);
    binder.bind(Appendable.class).toInstance(System.out);
    loadProperties(binder);
    binder.bind(String.class).annotatedWith(Names.named("LicenseKey")).toInstance("QWERTY");
  }


  @Provides @Inject
  Hero provideHero(FrogMan frogMan, WeaselGirl weaselGirl) {
    if (Math.random() > .5) {
      return frogMan;
    }
    return weaselGirl;
  }

  
  private void loadProperties(Binder binder) {
    InputStream stream =
      HeroModule.class.getResourceAsStream("/app.properties");
    Properties appProperties = new Properties();
    try {
      appProperties.load(stream);
      Names.bindProperties(binder, appProperties);
    } catch (IOException e) {
      binder.addError(e);
    }
  }
  
}
