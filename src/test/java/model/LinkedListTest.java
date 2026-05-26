package model;

import model.LinkedList.LinkedList;
import model.LinkedList.ListException;
import org.junit.jupiter.api.Test;

class LinkedListTest {

    @Test
    void personLinkedListTest() throws ListException {
        LinkedList<Person> personList = new LinkedList<>();

        personList.add(new Student("1", "Carlos", 21, 1.70, 68.5, "C001"));
        personList.add(new Student("2", "María", 22, 1.65, 62.3, "C002"));
        personList.add(new Student("3", "José", 24, 1.80, 78.0, "C003"));
        personList.add(new Student("4", "Ana", 20, 1.63, 60.5, "C004"));
        personList.add(new Student("5", "Luis", 23, 1.75, 74.0, "C005"));
        personList.add(new Student("6", "Sofía", 19, 1.60, 58.0, "C006"));

        personList.add(new Employee("7", "Roberto", 30, 1.72, 70.5, "Informático"));
        personList.add(new Employee("8", "Laura", 35, 1.68, 65.0, "Docente"));
        personList.add(new Employee("9", "Andrés", 41, 1.78, 82.0, "Administrador"));
        personList.add(new Employee("10", "Valeria", 28, 1.66, 63.2, "Doctora"));
        personList.add(new Employee("11", "Daniel", 39, 1.74, 74.5, "Arquitecto"));
        personList.add(new Employee("12", "Paula", 25, 1.62, 59.0, "Periodista"));

        System.out.println("Lista original");
        printList(personList);
        System.out.println("_".repeat(80));

        LinkedList<Person> studentsFilter = getPersonList(personList, 20, 23, 60.2, 75.0, 1.62, 1.75, "Estudiante");
        System.out.println("Filtro 1: Edad 20-23, Peso 60.2-75.0, Altura 1.62-1.75, Rol Estudiante");
        printList(studentsFilter);
        System.out.println("_".repeat(80));

        LinkedList<Person> employeesFilter = getPersonList(personList, 20, 40, 60.2, 75.0, 1.62, 1.75, "Empleado");
        System.out.println("Filtro 2: Edad 20-40, Peso 60.2-75.0, Altura 1.62-1.75, Rol Empleado");
        printList(employeesFilter);
        System.out.println("_".repeat(80));

        LinkedList<Person> allFilter = getPersonList(personList, 25, 36, 55.4, 80.0, 1.62, 1.83, "Todos");
        System.out.println("Filtro 3: Edad 25-36, Peso 55.4-80.0, Altura 1.62-1.83, Rol Todos");
        printList(allFilter);
    }

    @Test
    void reverseRequiredOutputTest() {
        LinkedList<Integer> list = new LinkedList<>();

        int[] valores = {16, 11, 2, 28, 34, 44, 14, 41, 17, 14};
        for (int v : valores) {
            list.add(v);
        }


        System.out.println(list.toString());

        System.out.println("---------------------------");

        System.out.println("llamando el metodo al reves ....");

        list.reverse();

        System.out.println(list.toString());
    }




    private LinkedList<Person> getPersonList(LinkedList<Person> linkedList, int minAge, int maxAge,
                                             double minWeight, double maxWeight,
                                             double minHeight, double maxHeight,
                                             String role) throws ListException {
        LinkedList<Person> result = new LinkedList<>();

        for (int i = 1; i <= linkedList.size(); i++) {
            Person person = linkedList.get(i);

            boolean validAge = person.getAge() >= minAge && person.getAge() <= maxAge;
            boolean validWeight = person.getWeight() >= minWeight && person.getWeight() <= maxWeight;
            boolean validHeight = person.getHeight() >= minHeight && person.getHeight() <= maxHeight;
            boolean validRole = role.equalsIgnoreCase("Todos")
                    || role.equalsIgnoreCase("Estudiante") && person instanceof Student
                    || role.equalsIgnoreCase("Empleado") && person instanceof Employee;

            if (validAge && validWeight && validHeight && validRole) {
                result.add(person);
            }
        }

        return result;
    }

    private void printList(LinkedList<Person> list) throws ListException {
        if (list.isEmpty()) {
            System.out.println("No hay personas que cumplan con el filtro");
            return;
        }

        for (int i = 1; i <= list.size(); i++) {
            System.out.println(list.get(i));
        }
    }
}