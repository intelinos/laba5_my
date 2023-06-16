package commands;

import Organization.Organization;
import exceptions.WrongArgumentInRequestInScriptException;
import exceptions.WrongNumberOfArgumentsException;
import managers.CollectionManager;
import requesters.OrganizationReguester;
import utility.ScriptChecker;

import java.util.*;

/**
 * Класс команды remove_lower, которая удаляет из коллекции все элементы, меньшие заданного.
 */
public class RemoveLower extends Command{

    CollectionManager collectionManager;
    public RemoveLower(CollectionManager collectionManager) {
        this.collectionManager = collectionManager;
        this.needScanner = true;
    }
    @Override
    public String getInfo() {
        return "Удаляет из коллекции все элементы, меньшие, чем заданный.";
    }

    @Override
    public String getName() {
        return "remove_lower";
    }

    /**
     * Проверяет количество аргументов команды и выполняет ее.
     * @param arguments Строка, содержащая имя команды и ее аргументы.
     * @throws WrongNumberOfArgumentsException Если были введены аргументы команды.
     */
    @Override
    public void execute(Scanner scanner, String[] arguments) throws WrongNumberOfArgumentsException {
        Map<Integer, Organization> collection = collectionManager.getCollection();
        if (collection.isEmpty()) {
            System.out.println("Коллекция пуста!");
            return;
        }
        if (arguments.length!=1) throw new WrongNumberOfArgumentsException();
        try {
            Organization mainOrganization = new OrganizationReguester(scanner).getOrganization();
            if (ScriptChecker.isScriptInProcess) System.out.println("Введенный элемент: " +"\n"+mainOrganization);
            HashSet<Map.Entry<Integer, Organization>> removedElements = new HashSet<>();
            for (Map.Entry<Integer, Organization> pair : collection.entrySet()) {
                if (pair.getValue().compareTo(mainOrganization) < 0) {
                    removedElements.add(pair);
                }
            }
            if (removedElements.size() == 0) System.out.println("Не было обнаружено элементов, меньших заданного.");
            else {
                collection.entrySet().removeAll(removedElements);
                System.out.println("Было удалено " + removedElements.size() + " элементов со следующими ключами: ");
                for (Map.Entry<Integer, Organization> removedElement : removedElements) {
                    System.out.print(removedElement.getKey() + " ");
                }
                System.out.println();
            }
        } catch (WrongArgumentInRequestInScriptException e) {
            System.out.println("Произошла ошибка: "+e.getMessage());
            ScriptChecker.clearScriptSet();
        }
    }
}
