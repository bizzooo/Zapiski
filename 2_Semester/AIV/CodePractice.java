//Code practice

// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//Adapter 
//We use this pattern to make objects with incompatible interfaces work together

// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//Class Adapter(inheritance based) - We create an adapter that inherits methods from the incompatible parent class
//And we manipulate the methods so that they become compatible with the desired interface inside the class.

//This is the interface that is desired
public interface TempSensor {
    double getTempInCelcius();
}

//But we have a class that is incompatible with the desired interface
public class FahrenheitThermometer {
    public double getTempInFehren() {
        //some reading from some sensor 
        return temp;
    }
}

//We create a class called TempAdapter, that is a child class of the original FahrenheitThermometer. 
//We also implement the TempSensor interface so we can override the desired interface
//Because the class inherits the methods from the FahrenheitThermometer we can manipulate them inside the child class so that it is compatible with the desired interface
public class TempAdapter extends FahrenheitThermometer implements TempSensor{

    //In this case we create a helper function that converts Fahrenheit to Celcius
    private double convertFahrenToCelcius(double fahrenheit){
        return (fahrenheit - 32) * 5.0 / 9.0; 
    }

    @Override
    public getTempInCelcius(){
        double fahrenheit = getTempInFehren();          //We call the inherited function 
        return convertFahrenToCelcius(fahrenheit);      //We use the helper function so that the return value fits the expectation of the desired interface
    }
}


//Example of usage in main 
public class Main{
    public static void main(String[] args){
        TempSensor sensor = new TempAdapter();          //We create an instance of TempSensor

        double celcius = sensor.getTempInCelcius();     //Through that instance we call the method from the desired interface 

        System.out.println(celcius);
    }
}

// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//Object Adapter (Composition-Delegation based)

//This version of an Object Adapter uses Composition instead of Inheritance. Instead of inheriting the methods from the incompatible class the Adapter 
//has an instance of that class saved as an attribute. Through that instance we can get the desired methods needed to fit them into the desired interface


//Desired interface
public interface TempSensor{
    double getTempInCelcius();
}

//Incompatible class
public class FahrenheitThermometer {
    public double getTempInFehren() {
        //some reading from some sensor 
        return temp;
    }
}


//We create TempAdapter that implements the desired interface so that we can @Override the methods
//This TempAdapter class has an attribute private FahrenheitThermometer fahrenheitThermometer that we instanciate in the constructor
//Through this instance we get the desired methods and make them fit for the desired interface

public class TempAdapter implements TempSensor{
    private FahrenheitThermometer fahrenheitThermometer;    //instnace of FahrenheitThermometer as an attribute

    public TempAdapter(FahrenheitThermometer fahrenheitThermometer){
        this.fahrenheitThermometer = fahrenheitThermometer;     //instanciating the attribute in the constructor
    }

    private double convertFahrenToCelcius(double fahrenheit){   //In this case we create a helper function that converts Fahrenheit to Celcius
        return (fahrenheit - 32) * 5.0 / 9.0; 
    }

    @Override
    public double getTempInCelcius(){
        double fahrenheit = fahrenheitThermometer.getTempInFehren();    //through the attribute we get access to getTempInFahren();
        return convertFahrenToCelcius(fahrenheit);                      //We use the helper function so that the return value fits the expectation of the desired interface
    }

}

//Example of usage in main
public class Main{
    public static void main(String[] args){
        FahrenheitThermometer fahrenheitThermometer = new FahrenheitThermometer();      //We create an instance of the incompatible object

        TemperatureSensor sensor = new TempAdapter(fahrenheitThermometer);  //We create an instance of TempAdapter and pass the incompatible object instance as an argument
        double celsius = sensor.getTemperatureInCelsius();                  //Through the Adapter instance we call the desired method from the interface
        System.out.println(celcius);
    }
}

// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//Facade pattern
//We use the facade pattern when we want to simplify/streamline the usage of complex systems in our codebase / external library
//Let's say there is a system composed of multiple classes, each handling very specific tasks.
//Instead of having to interact with each class individually a Facade class wraps around these classes into a single method
//or a single set/interface of methods. The facade's method is called and the facade coordinates the calls to the subsystem classes

// Subsystem classes
class DVDPlayer {                                                       
    public void on(){
        System.out.println("DVD Player on");
    }
    public void play(String movie){
        System.out.println("Playing " + movie);
    }
}
class Projector{
    public void on(){
        System.out.println("Projector is on");
    }
    public void setInput(DVDPlayer dvd){
        System.out.println("Projector set to DVD input");
    }
}
class SoundSystem{
    public void on(){
        System.out.println("Sound System is on");
    }
    public void setVolume(int level){
        System.out.println("Volume set to" + level);
    }
}

// Facade class
class HomeTheaterFacade{
    private DVDPlayer dvdPlayer;
    private Projector projector;
    private SoundSystem soundSystem;

    public HomeTheaterFacade(DVDPlayer dvd, Projector proj, SoundSystem sound){
        this.dvdPlayer = dvd;
        this.projector = proj;
        this.soundSystem = sound;
    }

    public void watchMovie(String movie){
        dvdPlayer.on();
        projector.on();
        projector.setInput(dvdPlayer);
        soundSystem.on();
        soundSystem.setVolume(5);
        dvdPlayer.play(movie);
    }
}

// Usage code
public class Main{
    public static void main(String[] args){
        DVDPlayer dvd = new DVDPlayer();
        Projector projector = new Projector();
        SoundSystem soundSystem = new SoundSystem();

        HomeTheaterFacade homeTheater = new HomeTheaterFacade(dvd, projector, soundSystem);
        homeTheater.watchMovie("Star Wars");
    }
}

// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------




// --------------------------------------------------------------------------------------------------------------------------------------------------------------------------

//Singleton pattern
//This is a pattern used when you only want to use exclusively one instance of a given class

public class FooDAO{
    private final List<Foo> fooList = new ArrayList<>(); //No multithreading

    private final List<Foo> fooList = Collections.synchronizedList(new ArrayList<>()); //Multithreading safe

    private static volatile FooDAO instance; //This attribute is how we can access the one instance of this class 

    private Foo(){}; //Private default constructor ensures that we cannot make an instance of this object anywhere

    public static FooDAO getInstance(){     //Getter for instance attribute, this is how we will access our one instance of this object
        if(instance == null){       
            instance = new FooDAO();        //We check if this object is already instanciated. If it isn't we instanciate it through instance and return it.
        }
        return instance;
    }

    //Version of getter that supports multithreading

    public static FooDAO getInstance(){     
        if(instance == null){                       //We check if the object is instanciated in this thread
            synchronized(FooDAO.class){             //Then we synchronize the FooDAO class amongst all threads. This ensures they will have the same "data"
                if(instance == null){               //We then perform another check if the 
                    instance = new FooDAO();        //If instance is still null we instanciate the FooDAO class and return the instance
                }
            }
        }
        return instance;
    }

    public static FooDAO getInstance(){
        synchronized(FooDAO.class){                 //If we make it without the first instance == null we force locking every call (less performant)
            if(instance == null){
                instance = new FooDAO();
            }
        }
        return instance;
    }
}