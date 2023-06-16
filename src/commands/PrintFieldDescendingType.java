package commands;

import Organization.Organization;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;

import java.util.NavigableMap;

/**
 * Класс команды print_field_descending_type, которая выводит значения поля type всех элементов в порядке убывания.
 */
public class PrintFieldDescendingType extends Command{
    CollectionManager collectionManager;
    public PrintFieldDescendingType(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.needScanner = false;
    }
    @Override
    public String getInfo() {
        return "Выводит значения поля type всех элементов в порядке убывания.";
    }

    @Override
    public String getName() {
        return "print_field_descending_type";
    }

    /**
     * Проверяет количество аргументов команды и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     */
    @Override
    public void execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length>1) throw new WrongNumberOfArgumentsException();
        NavigableMap<Integer, Organization> descendedCollection =((NavigableMap<Integer, Organization>)
                collectionManager.getCollection()).descendingMap();
        if (descendedCollection.isEmpty()) {
            System.out.println("Коллекция пуста!");
            return;
        }
        for (Organization organization: descendedCollection.values()) {
            System.out.println(organization.getType());
        }
    }
}
