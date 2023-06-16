package commands;

import Organization.Organization;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;

import java.util.Collection;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс команды show, которая выводит в стандартный поток вывода все элементы коллекции в строковом представлении.
 */
public class Show extends Command{
    CollectionManager collectionManager;
    public Show(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.needScanner=false;
    }
    @Override
    public String getInfo() {
        return "Выводит в стандартный поток вывода все элементы коллекции в строковом представлении.";
    }

    @Override
    public String getName() {
        return "show";
    }
    /**
     * Проверяет количество аргументов команды и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     */
    @Override
    public void execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length>1) throw new WrongNumberOfArgumentsException();
        Map<Integer, Organization> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста!");
        }
        else{
            for (Map.Entry<Integer, Organization> pair : collection.entrySet()) {
                Integer key = pair.getKey();
                Organization organization = pair.getValue();
                System.out.println(key + ": " + organization.toString());
            }
        }
    }
}
