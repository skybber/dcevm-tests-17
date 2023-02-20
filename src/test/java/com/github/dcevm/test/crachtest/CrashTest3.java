package com.github.dcevm.test.crachtest;
import static com.github.dcevm.test.util.HotSwapTestHelper.__toVersion__;

public class CrashTest3 {

    public static void main(String[] args) {
        try {
            for (int i = 0; i < 10000; i++) {
                long time = System.currentTimeMillis();
                __toVersion__(0);
                __toVersion__(1);
                System.out.println(i + " " + (System.currentTimeMillis() - time) + "ms");
            }
        } catch (InternalError e) {
            System.out.println("INTERNAL ERROR");
            e.printStackTrace();
            if (e.getCause() != null) {
                System.out.println(e.getCause());
                e.getCause().printStackTrace();
            }
        }
    }

@Entity
public class A {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    public A() {
    }

    public A(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

/**
 * Test entity
 */
@Entity
public class A___1 {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    // Make description transient to check that reload was successful
    private String description;

    public A___1() {
    }

    public A___1(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

}
