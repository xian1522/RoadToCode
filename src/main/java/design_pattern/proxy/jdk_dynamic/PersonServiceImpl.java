package design_pattern.proxy.jdk_dynamic;

public class PersonServiceImpl implements PersonService {
    private String name;

    public PersonServiceImpl(String name) {
        this.name = name;
    }

    @Override
    public String sayMyName() {
        return name;
    }

    @Override
    public String reply() {
        return "you're godDamn right";
    }
}
