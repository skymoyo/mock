package work.skymoyo.test.model;

import lombok.Data;

import java.util.List;

@Data
public class Person {

    private String name;

    private int age;

    private List<Person> childs;

}
