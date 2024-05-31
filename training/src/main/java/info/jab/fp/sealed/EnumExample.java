package info.jab.fp.sealed;

public class EnumExample {

    public static void main() {
        
        enum Planet { MERCURY, VENUS, EARTH }

        Planet p = Planet.EARTH;

        switch (p) {
            case MERCURY -> System.out.println("Mercury");
            case VENUS -> System.out.println("Venus");
            case EARTH -> System.out.println("Earth");
        }
    }

}
