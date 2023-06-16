package commands;

import Organization.Organization;
import Organization.Address;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Класс команды print_unique_postal_address, которая выводит уникальные значения поля postalAddress всех элементов в коллекции.
 */
public class PrintUniquePostalAddress extends Command{
    CollectionManager collectionManager;
    public PrintUniquePostalAddress(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.needScanner = false;
    }
    @Override
    public String getInfo() {
        return "Выводит уникальные значения поля postalAddress всех элементов в коллекции.";
    }

    @Override
    public String getName() {
        return "print_unique_postal_address";
    }

    /**
     * Проверяет количество аргументов комманды и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     */
    @Override
    public void execute(String[] arguments) throws WrongNumberOfArgumentsException {
        if (arguments.length>1) throw new WrongNumberOfArgumentsException();
        Map<Integer, Organization> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста!");
            return;
        }
        Set<Address> addressSet = new HashSet<>();
        System.out.println("Уникальные значения postalAddress всех элементов коллекции: ");
        for (Map.Entry<Integer, Organization> pair : collection.entrySet()) {
            Organization organization = pair.getValue();
            Address address = organization.getPostalAddress();
            if(address == null) continue;
            addressSet.add(address);
        }
        for (Address address: addressSet) {
            System.out.println(address.toString());
        }
    }
}
